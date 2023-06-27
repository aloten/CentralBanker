package com.aidanloten.centralbanker.data.util;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Modifier;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.BalanceSheet;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.EconomyService;
import com.aidanloten.centralbanker.service.ModifierService;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    ModifierService modifierService;

    public void initializeSystem() {
        readCsvTypeData();
        initializePeople();
        initializePersonalities();
        initializePeopleFinancialStates();
        initializePeopleProductionModifiers();
    }

    private Personality getPersonality() {
        Personality personality = new Personality();
        personality.setHardworking((int) Math.floor(Math.random() * 10));
        personality.setInnovative((int) Math.floor(Math.random() * 10));
        personService.savePersonality(personality);
        return personality;
    }

    private void initializePersonalities() {
        for (Person person : personService.findAllPeople()) {
            person.setPersonality(getPersonality());
            personService.savePerson(person);
        }
    }

    private Modifier getProductionModifier() {
        Modifier productionModifier = Modifier.builder()
                .description("modifies person production of asset person " + "produces").name("PRODUCTION_MODIFIER")
                .build();
        modifierService.saveModifier(productionModifier);
        return productionModifier;
    }

    private void initializePeopleProductionModifiers() {
        for (Person person : personService.findAllPeople()) {
            person.setProductionModifier(getProductionModifier());
            personService.savePerson(person);
        }
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
