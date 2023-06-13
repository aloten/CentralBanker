package com.aidanloten.centralbanker.data.util;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.Asset;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.EconomyService;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataUtilityPersonTrading {
    @Autowired
    ReadCsvUtility readCsvUtility;
    @Autowired
    PersonService personService;
    @Autowired
    EconomyService economyService;
    @Autowired
    AssetService assetService;

    public void initializeSystem() {
        readCsvTypeData();
        initializePeople();
        initializePeopleFinancialStates();
    }

    private FinancialState getCleanFinancialState() {
        FinancialState financialState = new FinancialState();
        BalanceSheet balanceSheet = new BalanceSheet();
        financialState.setBalanceSheet(balanceSheet);
        return financialState;
    }

    private void initializePeopleFinancialStates() {
        double moneySupply = economyService.getEconomy().getMoneySupply();
        for (Person person : personService.findAllPeople()) {
            FinancialState financialState = getCleanFinancialState();
            person.setFinancialState(financialState);
            personService.savePerson(person);
        }
    }

    private void createPerson(int id, String jobTitle, AssetType assetTypeProduces) {
        Person person = Person.builder().firstName(String.format("Person %d", id)).jobTitle(jobTitle)
                .assetTypeProduces(assetTypeProduces).build();
        personService.savePerson(person);
    }

    private void initializePeople() {
        createPerson(1, "farmer", assetService.getFoodAssetType());
        createPerson(2, "blacksmith", assetService.getToolAssetType());
        createPerson(3, "lumberjack", assetService.getLumberAssetType());
    }

    private void readCsvTypeData() {
        readCsvUtility.readIndustryDataFromCsvToDb();
        readCsvUtility.readCompanyExpenseTypeDataFromCsvToDb();
        readCsvUtility.readAssetTypeDataFromCsvToDb();
    }
}
