package com.aidanloten.centralbanker.data.entities.descriptors.economy;

import com.aidanloten.centralbanker.data.entities.agents.Company;

public class PrimaryOccupation {
    private final String title;
    private final String description;
    private final Company company;

    public PrimaryOccupation(String title, String description, Company company) {
        this.title = title;
        this.description = description;
        this.company = company;
    }
}
