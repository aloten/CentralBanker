package com.aidanloten.centralbanker.data.util;

import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpenseType;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.assets.AssetType;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import com.aidanloten.centralbanker.service.AssetService;
import com.aidanloten.centralbanker.service.CompanyService;
import com.aidanloten.centralbanker.service.IndustryService;
import com.aidanloten.centralbanker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
public class ReadCsvUtility {
    private final String INITIAL_DATA_PATH = "initialdata/";
    private final String PEOPLE_DATA_PATH = INITIAL_DATA_PATH + "people.csv";
    private final String COMPANY_DATA_PATH = INITIAL_DATA_PATH + "companies.csv";
    private final String INDUSTRY_DATA_PATH = INITIAL_DATA_PATH + "industries.csv";
    private final String COMPANY_EXPENSE_TYPE_DATA_PATH = INITIAL_DATA_PATH + "companyExpenseType.csv";
    private final String PERSONALITIES_DATA_PATH = INITIAL_DATA_PATH + "personalities.csv";
    private final String ASSET_TYPE_DATA_PATH = INITIAL_DATA_PATH + "assetType.csv";

    @Autowired
    PersonService personService;
    @Autowired
    CompanyService companyService;
    @Autowired
    IndustryService industryService;
    @Autowired
    AssetService assetService;

    public void readAssetTypeDataFromCsvToDb() {
        for (String assetTypeRow : readCsvIntoRows(ASSET_TYPE_DATA_PATH)) {
            String[] values = assetTypeRow.split(",");
            String name = values[0];
            String description = values[1];
            assetService.saveAssetType(AssetType.builder().name(name).description(description).build());
        }
    }

    public void readPersonalitiesDataFromCsvToDb() {
        for (String personalityRow : readCsvIntoRows(PERSONALITIES_DATA_PATH)) {
            String[] values = personalityRow.split(",");
            int industrySkillLevel = parseInt(values[0]);
            int communicationLevel = parseInt(values[1]);
            int industriousLevel = parseInt(values[2]);
            int abilityToLearnLevel = parseInt(values[3]);
//            personService.savePersonality(
//                    Personality.builder().industrySkillLevel(industrySkillLevel).communicationLevel(communicationLevel)
//                            .industriousLevel(industriousLevel).abilityToLearnLevel(abilityToLearnLevel).build());
        }
    }

    public void readCompanyExpenseTypeDataFromCsvToDb() {
        for (String companyExpenseTypeName : readCsvIntoRows(COMPANY_EXPENSE_TYPE_DATA_PATH)) {
            companyService.saveCompanyExpenseType(CompanyExpenseType.builder().name(companyExpenseTypeName).build());
        }
    }

    public void readIndustryDataFromCsvToDb() {
        for (String industryName : readCsvIntoRows(INDUSTRY_DATA_PATH)) {
            industryService.saveIndustry(Industry.builder().name(industryName).build());
        }
    }

    public void readPeopleDataFromCsvToDb() {
        for (String personRow : readCsvIntoRows(PEOPLE_DATA_PATH)) {
            personRow = personRow.replaceAll("\"", "");
            String[] values = personRow.split(",");
            String lastName = values[0];
            String firstName = values[1].strip();
            double salary = Double.parseDouble(values[2]);
            String jobTitle = values[3];
            String dateOfBirthRawStr = values[4];
            String[] dobExpanded = dateOfBirthRawStr.split("/");
            LocalDate dateOfBirth = LocalDate.of(1900 + parseInt(dobExpanded[2]), parseInt(dobExpanded[0]),
                    parseInt(dobExpanded[1]));
            Period p = Period.between(dateOfBirth, LocalDate.now());
            int age = p.getYears();
            String sex = values[5];
            personService.savePerson(
                    Person.builder().firstName(firstName).lastName(lastName).salary(salary).jobTitle(jobTitle).age(age)
                            .dateOfBirth(dateOfBirth).sex(sex).build());
        }
    }

    public void readCompanyDataFromCsvToDb() {
        for (String companyRow : readCsvIntoRows(COMPANY_DATA_PATH)) {
            String[] values = companyRow.split(",");
            String formalName = values[0];
            String industryName = values[1];
            boolean sellsB2C = Boolean.parseBoolean(values[2]);
            companyService.saveCompany(
                    Company.builder().formalName(formalName).industry(industryService.findIndustryByName(industryName))
                            .sellsB2C(sellsB2C).build());
        }
    }

    /*
    Read a csv with 1 column meant to be stored as strings in the DB
     */
    private List<String> readCsvIntoRows(String filepath) {
        ClassPathResource resource = new ClassPathResource(filepath);

        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            br.readLine(); // column names first line

            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
