package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.company.CompanyRepository;
import com.aidanloten.centralbanker.data.dao.companyexpense.CompanyExpenseRepository;
import com.aidanloten.centralbanker.data.dao.companyexpensetype.CompanyExpenseTypeRepository;
import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpense;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.CompanyExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyExpenseTypeRepository companyExpenseTypeRepository;
    @Autowired
    CompanyExpenseRepository companyExpenseRepository;
    @Autowired
    PersonService personService;

    public void saveCompanies(List<Company> companies) {
        companyRepository.saveAll(companies);
    }

    public List<Company> getCompaniesByIndustry(Industry industry) {
        return companyRepository.findByIndustry(industry);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public void saveCompanyExpense(CompanyExpense companyExpense) {
        companyExpenseRepository.save(companyExpense);
    }

    public void saveCompanyExpenseType(CompanyExpenseType companyExpenseType) {
        companyExpenseTypeRepository.save(companyExpenseType);
    }

    public Optional<Company> getById(int id) {
        return companyRepository.findById(id);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }
}
