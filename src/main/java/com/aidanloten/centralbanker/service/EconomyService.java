package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.economy.EconomyRepository;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Economy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EconomyService {
    Logger logger = LoggerFactory.getLogger(EconomyService.class);
    @Autowired
    EconomyRepository economyRepository;
    @Autowired
    CompanyService companyService;
    @Autowired
    PersonService personService;

    public double getGDP() {
        return getEconomy().getGdp();
    }



    /**
     * Update gdp based on a strategy
     */
    public void calculateAndUpdateGDP() {
        saveEconomy(getEconomy());
    }

    /**
     * Calculate GDP by income
     * Equation: GDP = Total National Income + Sales Taxes + Depreciation + Net Foreign Factor Income
     * <p>
     * Total National Income = the sum of all wages, rent, interest, and profits.
     * Sales Taxes = consumer taxes imposed by the government on the sales of goods and services.
     * Depreciation = cost allocated to a tangible asset over its useful life.
     * Net Foreign Factor Income = the difference between the total income that a country’s citizens and companies
     * generate in foreign countries, versus the total income foreign citizens and companies generate in the domestic
     * country.
     */
    private double calculateGDPBasedOnIncome() {
        double totalNationalIncome = personService.getSumOfSalaries();
        double salesTaxes = 0;
        double depreciation = 0;
        double netForeignFactorIncome = 0;
        double gdp = totalNationalIncome + salesTaxes + depreciation + netForeignFactorIncome;
        return gdp;
    }

    /**
     * Calculate GDP by expenditure
     * Equation: GDP = C + G + I + NX
     * <p>
     * C = consumption or all private consumer spending within a country’s economy, including, durable goods (items
     * with a lifespan greater than three years), non-durable goods (food & clothing), and services.
     * G = total government expenditures, including salaries of government employees, road construction/repair,
     * public schools, and military expenditure.
     * I = sum of a country’s investments spent on capital equipment, inventories, and housing.
     * NX = net exports or a country’s total exports less total imports.
     */
    private double calculateGDPBasedOnPersonExpenditure() {
        double c = personService.getSumOfAllPersonExpenses();
        double g = 0;
        double i = 0;
        double nx = 0;
        double gdp = c + g + i + nx;
        return gdp;
    }

//    private double calculateGDPBasedOnCompanyExpenditure() {
//        double c = 0;
//        double g = 0;
//        double i = companyService.getSumOfAllB2BExpenses() + companyService.getSumOfAllEmployeeExpenses(); // assumes
//        // all b2b expenditure
//        // is investment
//        double nx = 0;
//        double gdp = c + g + i + nx;
//        return gdp;
//    }

    public Economy getEconomy() {
        return economyRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException("Economy not found"));
    }

    public void saveEconomy(Economy economy) {
        economyRepository.save(economy);
    }
}
