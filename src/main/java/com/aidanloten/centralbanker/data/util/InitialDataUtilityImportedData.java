//package com.aidanloten.centralbanker.data.util;
//
//import com.aidanloten.centralbanker.data.entities.agents.Company;
//import com.aidanloten.centralbanker.data.entities.agents.Person;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpense;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.PersonExpense;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
//import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
//import com.aidanloten.centralbanker.engine.expenditures.PersonExpenditureStrategy;
//import com.aidanloten.centralbanker.service.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class InitialDataUtilityImportedData {
//    private final ReadCsvUtility readCsvUtility = new ReadCsvUtility();
//    private final CompanyService companyService = new CompanyService();
//    private final IndustryService industryService = new IndustryService();
//    private final EconomyService economyService = new EconomyService();
//    private final PersonService personService = new PersonService();
//    private final FinancialStateService financialStateService = new FinancialStateService();
//    private final PersonExpenditureStrategy personExpenditureStrategy = new PersonExpenditureStrategy();
//    private final AssetService assetService = new AssetService();
//
//    public void initializeSystem() {
//        readCsvData();
//        setupPeople();
//        initCompanyStates();
//        calculateInitialPersonExpenses();
//        updateCompanyB2CRevenue();
//        assignFinancialStatesToCompanies();
//    }
//
//    private void readCsvData() {
//        readCsvUtility.readIndustryDataFromCsvToDb();
//        readCsvUtility.readCompanyExpenseTypeDataFromCsvToDb();
//        readCsvUtility.readPeopleDataFromCsvToDb();
//        readCsvUtility.readCompanyDataFromCsvToDb();
//        readCsvUtility.readPersonalitiesDataFromCsvToDb();
//        readCsvUtility.readAssetTypeDataFromCsvToDb();
//    }
//
//    private void assignFinancialStatesToCompanies() {
//        for (Company company : companyService.getAllCompanies()) {
//            FinancialState financialState = getCleanFinancialState();
//            company.setFinancialState(financialState);
//            financialStateService.getCashAsset(company).setValue((company.getB2bRevenue() + company.getB2cRevenue()) * EconomicConstants.FRACTION_OF_REVENUE_FOR_STARTING_CASH_VALUE);
//            companyService.saveCompany(company);
//        }
//
//    }
//
//    private void setupPeople() {
//        assignFinancialStatesToPeople(personService.getAllPeople());
//        assignPeopleToCompanies();
//        assignPersonalitiesToPeople();
//    }
//
//    /**
//     * Choose starting cash value here
//     */
//    private void assignFinancialStatesToPeople(List<Person> people) {
//        for (Person person : people) {
//            FinancialState financialState = getCleanFinancialState();
//            person.setFinancialState(financialState);
//            financialStateService.getCashAsset(person).setValue(person.getSalary() * EconomicConstants.FRACTION_OF_SALARY_FOR_STARTING_CASH_VALUE);
//            personService.savePerson(person);
//        }
//    }
//
//    private FinancialState getCleanFinancialState() {
//        FinancialState financialState = new FinancialState();
//        BalanceSheet balanceSheet = new BalanceSheet();
//        Asset cash = new Asset().setValue(0D).setAssetType(assetService.getCashAssetType());
//        List<Asset> assets = Arrays.asList(cash);
//        balanceSheet.setAssets(assets);
//        financialState.setBalanceSheet(balanceSheet);
//        return financialState;
//    }
//
//    private void assignPersonalitiesToPeople() {
//        List<Personality> personalities = personService.getAllPersonalities();
//        List<Person> people = personService.getAllPeople();
//        for (int i = 0; i < people.size(); i++) {
//            Person person = people.get(i);
//            person.setPersonality(personalities.get(i));
//            personService.savePerson(person);
//        }
//    }
//
//    private void setCompanyExpenseEmployee(Person employee, Company company, double salary) {
//        CompanyExpense companyExpense = new CompanyExpense(companyService.getEMPLOYEECompanyExpenseType(), salary, company).setEmployee(employee);
//        companyService.saveCompanyExpense(companyExpense);
//    }
//
//    /*
//    Assign people to companies and create company expenses for employees
//     */
//    private void assignPeopleToCompanies() {
//        List<Company> companies = companyService.getAllCompanies();
//        for (Person person : personService.getAllPeople()) {
//            int random = (int) (Math.random() * (companies.size()));
//            Company company = companies.get(random);
//            person.setCompany(company);
//            company.setEmployeeCount(company.getEmployeeCount() + 1);
//            personService.savePerson(person);
//            companyService.saveCompany(company);
//            setCompanyExpenseEmployee(person, company, person.getSalary());
//        }
//    }
//
//    /*
//    Init company states: use employee expenditure (salaries are hard coded in initial data) to create a temp gdp; then
//    init industry revenue shares using this gdp; then use revenue shares to create b2b expenses; then add b2b expenditure
//    to employee expenditure to get starting revenue; then update gdp with sum of company revenues
//     */
//    private void initCompanyStates() {
//        initGdp();
//        initIndustryRevenueShares(economyService.getGDP());
//        List<Company> companies = companyService.getAllCompanies();
//
//        for (Company company : companies) {
//            calculateCompanyB2BExpenses(company);
//            company.setB2bExpenditure(companyService.getSumOfB2BExpensesByCompanyExpender(company));
//            companyService.saveCompany(company);
//        }
//        // I refactored so this would come after all companies had their b2b expenses calculated
//        // but weirdly, there were still non-zero values for b2b revenue for each company before I refactored ??
//        for (Company company : companies) {
//            company.setB2bRevenue(companyService.getSumOfB2BExpensesByCompanyVendor(company));
//            companyService.saveCompany(company);
//        }
//    }
//
//    private void updateCompanyB2CRevenue() {
//        for (Company company : companyService.getAllCompanies()) {
//            company.setB2cRevenue(companyService.calculateB2CRevenue(company));
//            companyService.saveCompany(company);
//        }
//    }
//
//    private void initGdp() {
//        double gdp = 0;
//        for (Company company : companyService.getAllCompanies()) {
//            double employeeExpenditure = companyService.getEmployeeExpenditureByCompany(company);
//            company.setEmployeeExpenditure(employeeExpenditure);
//            companyService.saveCompany(company);
//            gdp += employeeExpenditure;
//        }
//        economyService.saveEconomy(new Economy().setGdp(gdp));
//    }
//
//
//    /**
//     * Initialize industryRevenueShares when 1 company = 1 industry
//     * Gdp is currently total emp expenditure as the argument. We are saying revenue share will currently equal employee
//     * expenditure share relative to other companies, and base off of initial people data with salaries
//     */
//    private void initIndustryRevenueShares(double gdp) {
//        for (Industry industry : industryService.getAllIndustries()) {
//            Company company = companyService.getFirstCompanyByIndustry(industry);
//            double revenueShare = company.getEmployeeExpenditure() / gdp;
//            industry.setRevenueShare(revenueShare);
//            industryService.saveIndustry(industry);
//        }
//    }
//
//    /**
//     * Calculate company b2b expenditure by setting it equal to (company employee expenditure * industry revenue share)
//     * so a company's b2c expenditure will equal its b2b expenditure
//     */
//    private void calculateCompanyB2BExpenses(Company company) {
//        for (Industry industry : industryService.getAllIndustries()) {
//            double value = industry.getRevenueShare() * company.getEmployeeExpenditure();
//            CompanyExpense companyExpense = new CompanyExpense();
//            companyExpense.setCompanyExpender(company).setType(companyService.getB2BCompanyExpenseType()).setValue(value).setCompanyVendor(companyService.getCompaniesByIndustry(industry).get(0));
//            companyService.saveCompanyExpense(companyExpense);
//        }
//    }
//
//    private void calculateInitialPersonExpenses() {
//        for (Person person : personService.getAllPeople()) {
//            for (Industry industry : industryService.getAllIndustries()) {
//                PersonExpense personExpense = personExpenditureStrategy.calculatePersonExpense(person, industry);
//                personService.savePersonExpense(personExpense);
//            }
//        }
//    }
//}
