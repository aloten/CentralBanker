package com.aidanloten.centralbanker.engine.assets;

import com.aidanloten.centralbanker.data.entities.agents.Company;
import com.aidanloten.centralbanker.service.CompanyService;
import com.aidanloten.centralbanker.service.IndustryService;

public class CompanyAssetGenerator implements AssetGeneratorStrategy {
    private final CompanyService companyService;
    private final IndustryService industryService;

    public CompanyAssetGenerator(CompanyService companyService, IndustryService industryService) {
        this.companyService = companyService;
        this.industryService = industryService;
    }

    @Override
    public void generateAssetsForCycle() {
        try {
            for (Company company : companyService.findAllCompanies()) {
                if (industryService.companyIsIndustry(company, IndustryService.IndustryName.manufacturing)) {

                }
            }
        } catch (Exception e) {
            logger.error("Error generating assets in cycle");
            e.printStackTrace();
        }
    }
}
