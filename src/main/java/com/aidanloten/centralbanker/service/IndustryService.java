package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.industry.IndustryRepository;
import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {
    @Autowired
    CompanyService companyService;
    @Autowired
    IndustryRepository industryRepository;
    @Autowired
    EconomyService economyService;


    public boolean companyIsIndustry(Company company, IndustryName industryName) {
        return company.getIndustry().getName().equals(industryName);
    }

    public Industry findIndustryByName(String name) {
        return industryRepository.findByName(name);
    }

    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    public void saveIndustry(Industry industry) {
        industryRepository.save(industry);
    }

    public void calculateAndUpdateIndustryRevenueShares() {
        economyService.calculateAndUpdateGDP();
        double gdp = economyService.getGDP();
        for (Company c : companyService.findAllCompanies()) {
            double revenueShare = ((c.getB2bRevenue() + c.getB2cRevenue()) / gdp);
            Industry industry = c.getIndustry();
            industry.setRevenueShare(revenueShare);
            industryRepository.save(industry);
        }
    }

    public enum IndustryName {
        banking,
        manufacturing,
        farming
    }
}
