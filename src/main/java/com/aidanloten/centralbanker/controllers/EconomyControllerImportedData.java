//package com.aidanloten.centralbanker.controllers;
//
//import com.aidanloten.centralbanker.data.entities.agents.Company;
//import com.aidanloten.centralbanker.data.entities.agents.Person;
//import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
//import com.aidanloten.centralbanker.data.util.InitialDataUtilityImportedData;
//import com.aidanloten.centralbanker.service.CompanyService;
//import com.aidanloten.centralbanker.service.EconomyService;
//import com.aidanloten.centralbanker.service.IndustryService;
//import com.aidanloten.centralbanker.service.PersonService;
//import com.aidanloten.centralbanker.utils.CBUtility;
//
//import java.util.List;
//
//public class EconomyControllerImportedData {
//    private InitialDataUtilityImportedData initialDataUtilityImportedData = new InitialDataUtilityImportedData();
//    private CompanyService companyService = new CompanyService();
//    private EconomyService economyService = new EconomyService();
//    private PersonService personService = new PersonService();
//    private IndustryService industryService = new IndustryService();
//
//    public void initializeSystem() {
//        initialDataUtilityImportedData.initializeSystem();
//        economyService.calculateAndUpdateGDP();
//    }
//
//    public void displayEconomyStats() {
//        List<Person> employees = personService.findAllPeople();
//        List<Company> companies = companyService.findAllCompanies();
//        double gdp = economyService.getGDP();
//        System.out.println("\n------- Economy Stats -------");
//        System.out.println("No. of employees=" + employees.size());
//        System.out.println("No. of companies=" + companies.size());
//        printIndustryRevenueShares();
//        System.out.println("gdp=" + CBUtility.formatCurrency(gdp));
//        System.out.println("------------------------------\n");
//    }
//
//    private void printIndustryRevenueShares() {
//        List<Industry> industries = industryService.getAllIndustries();
//        System.out.print("Industry Revenue Shares={");
//        for (Industry industry : industries) {
//            System.out.print(industry.getName() + "=" + String.format("%.2f", industry.getRevenueShare() * 100) + "%,");
//        }
//        System.out.println("}");
//    }
//
//    private String industryStatistics() {
//        // TODO
//        return "";
//    }
//}
