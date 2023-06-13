//package com.aidanloten.centralbanker.data.util;
//
//import com.aidanloten.centralbanker.data.entities.agents.Company;
//import com.aidanloten.centralbanker.data.entities.agents.Person;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
//import com.aidanloten.centralbanker.engine.assets.AssetConverter;
//import com.aidanloten.centralbanker.engine.transactions.Transaction;
//import com.aidanloten.centralbanker.engine.transactions.TransactionService;
//import com.aidanloten.centralbanker.service.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Unfinished, leaving this to build person 2 person trading economy first to understand fundementals of growth and
// * source of value.
// */
//public class InitialDataUtilityPerfectEconomy {
//    private final ReadCsvUtility readCsvUtility = new ReadCsvUtility();
//    private final IndustryService industryService = new IndustryService();
//    private final CompanyService companyService = new CompanyService();
//    private final PersonService personService = new PersonService();
//    private final FinancialStateService financialStateService = new FinancialStateService();
//    private final AssetService assetService = new AssetService();
//    private final AssetConverter assetConverter = new AssetConverter();
//    private final TransactionService transactionService = new TransactionService();
//    Logger logger = LoggerFactory.getLogger(InitialDataUtilityPerfectEconomy.class);
//
//    public void initializeSystem() {
//        readCsvTypeData();
//        initializeCompanies();
//        initializePeople(9);
//        initializePeopleFinancialStates();
//        initializeCompanyFinancialStates();
//
//    }
//
//    // 3rd company is bank
//    private void calculateBankAssets() {
//
//    }
//
//    // 2nd company sellsB2C
//    private void createConsumableAssetsForNonBankB2C() {
//        Company rawMaterialCompany = companyService.getCompanyByFormalName("Mitsubishi");
//        Company nonBankB2CCompany = companyService.getCompanyByFormalName("Macintosh");
//        Asset rawMaterialAsset = financialStateService.getAssetByAgentAndName(rawMaterialCompany, "copper");
//        Transaction transaction = new Transaction(nonBankB2CCompany, rawMaterialCompany, rawMaterialAsset);
////        transactionService.executeTransaction();
//        financialStateService.addAssetsToBalanceSheet(assetConverter.convertRawMaterialToLongTermConsumableElectronics(rawMaterialAsset), nonBankB2CCompany.getFinancialState().getBalanceSheet());
//    }
//
//    // first company extracts raw materials
//    private void createAndAssignRawMaterialAssets() {
//        Asset rawMaterialAsset =
//                new Asset().setAssetType(assetService.getRawMaterialAssetType()).setName("copper").setValue(1000000D);
//        BalanceSheet balanceSheet =
//                companyService.getRawMaterialExtractionCompanies().get(0).getFinancialState().getBalanceSheet();
//        financialStateService.addAssetToBalanceSheet(rawMaterialAsset, balanceSheet);
//    }
//
//    private void initializeCompanyAssets() {
//        createAndAssignRawMaterialAssets();
//        createConsumableAssetsForNonBankB2C();
//        calculateBankAssets();
//    }
//
//    // cash is salary divided by four to approximate emergency fund
//    private void initializePeopleFinancialStates() {
//        for (Person person : personService.getAllPeople()) {
//            FinancialState financialState = getCleanFinancialState();
//            financialStateService.increaseCash(person, person.getSalary() / 4);
//            person.setFinancialState(financialState);
//            personService.savePerson(person);
//        }
//    }
//
//    // arbitrary cash amount
//    private void initializeCompanyFinancialStates() {
//        for (Company company : companyService.getAllCompanies()) {
//            FinancialState financialState = getCleanFinancialState();
//            financialStateService.increaseCash(company, companyService.getEmployeeExpenditureByCompany(company) / 2);
//            company.setFinancialState(financialState);
//            companyService.saveCompany(company);
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
//    /**
//     * Assumes 3 company setup
//     */
//    private void assignPersonToCompany(Person person) {
//        int companyId = person.getId() + 1 % 3 == 0 ? 0 : person.getId() + 1 % 2 == 0 ? 1 : 2;
//        Company company = companyService.getById(companyId);
//        person.setCompany(company);
//        personService.savePerson(person);
//    }
//
//    private void createPerson(int id) {
//        Person person =
//                new Person().setFirstName(String.format("Person %i", id)).setSalary(100000).setDateOfBirth(LocalDate.of(2000, 1, 1));
//        personService.savePerson(person);
//    }
//
//
//    private void initializePeople(int numberOfPeople) {
//        for (int i = 0; i < numberOfPeople; i++) {
//            Optional<Person> optionalPerson = personService.getById(i);
//            if (optionalPerson.isPresent()) {
//                Person person = optionalPerson.get();
//                createPerson(i);
//                assignPersonToCompany(person);
//            } else {
//                // Handle the absence of the person, if needed
//            }
//        }
//    }
//
//    private void createCompanies() {
//        Company company1 = new Company().setFormalName("Macintosh").setIndustry(industryService.getIndustryByName(
//                "electronics")).setSellsB2C(true);
//        Company company2 = new Company().setFormalName("Mitsubishi").setIndustry(industryService.getIndustryByName(
//                "mining")).setSellsB2C(false);
//        Company company3 = new Company().setFormalName("Mitsui").setIndustry(industryService.getIndustryByName(
//                "banking")).setSellsB2C(true);
//
//        companyService.saveCompanies(Arrays.asList(company1, company2, company3));
//    }
//
//    private void initializeCompanies() {
//        createCompanies();
//
//    }
//
//    private void readCsvTypeData() {
//        readCsvUtility.readIndustryDataFromCsvToDb();
//        readCsvUtility.readCompanyExpenseTypeDataFromCsvToDb();
//        readCsvUtility.readAssetTypeDataFromCsvToDb();
//    }
//}