package com.aidanloten.centralbanker.data.util;

import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import com.aidanloten.centralbanker.service.*;

import java.util.List;

public class InitialDataUtilityPerfectEconomy {
    private final ReadCsvUtility readCsvUtility;
    private final PersonService personService;
    private final EconomyService economyService;
    private final AssetService assetService;
    private final ModifierService modifierService;
    private final IndustryService industryService;
    private final CompanyService companyService;
    private final int STARTING_POPULATION = 10;

    public InitialDataUtilityPerfectEconomy(ReadCsvUtility readCsvUtility, PersonService personService,
                                            EconomyService economyService, AssetService assetService,
                                            ModifierService modifierService, IndustryService industryService,
                                            CompanyService companyService) {
        this.readCsvUtility = readCsvUtility;
        this.personService = personService;
        this.economyService = economyService;
        this.assetService = assetService;
        this.modifierService = modifierService;
        this.industryService = industryService;
        this.companyService = companyService;
    }

    public void initializeSystem() {
        readCsvTypeData();
        initializePeople();
        initializeCompanies();
        assignPeopleToCompanies();
    }

    private void assignPeopleToCompanies() {
        List<Company> companies = companyService.findAllCompanies();
        for (Person person : personService.findAllPeople()) {
            if (person.getId() < STARTING_POPULATION * (1 / 3)) {
                person.setCompany(companies.get(0));
            } else if (person.getId() < STARTING_POPULATION * (2 / 3)) {
                person.setCompany(companies.get(1));
            } else {
                person.setCompany(companies.get(2));
            }
            personService.savePerson(person);
        }
    }

    private void initializeCompanies() {
        Company blackberryRanch = new Company();
        blackberryRanch.setFormalName("Blackberry Ranch");
        blackberryRanch.setFinancialState(getCleanFinancialState());
        blackberryRanch.setIndustry(industryService.findIndustryByName("farming"));
        companyService.saveCompany(blackberryRanch);

        Company mitsubishi = new Company();
        mitsubishi.setFormalName("Mitsubishi");
        mitsubishi.setFinancialState(getCleanFinancialState());
        mitsubishi.setIndustry(industryService.findIndustryByName("manufacturing"));
        companyService.saveCompany(mitsubishi);

        Company mitsui = new Company();
        mitsui.setFormalName("Mitsui");
        mitsui.setFinancialState(getCleanFinancialState());
        mitsui.setIndustry(industryService.findIndustryByName("Mitsui"));
        companyService.saveCompany(mitsui);
    }

    private void initializePeople() {
        for (int i = 0; i < 100; i++) {
            Person person = Person.builder().firstName(String.format("Person %d", i + 1)).build();
            person.setFinancialState(getCleanFinancialState());
            person.setPersonality(getPersonality());
            personService.savePerson(person);
        }
    }


    private Personality getPersonality() {
        Personality personality = new Personality();
        personality.setHardworking((int) Math.floor(Math.random() * 10));
        personality.setInnovative((int) Math.floor(Math.random() * 10));
        personService.savePersonality(personality);
        return personality;
    }

    private FinancialState getCleanFinancialState() {
        FinancialState financialState = new FinancialState();
        BalanceSheet balanceSheet = new BalanceSheet();
        financialState.setBalanceSheet(balanceSheet);
        return financialState;
    }

    private void readCsvTypeData() {
        readCsvUtility.readIndustryDataFromCsvToDb();
        readCsvUtility.readCompanyExpenseTypeDataFromCsvToDb();
        readCsvUtility.readAssetTypeDataFromCsvToDb();
    }

}