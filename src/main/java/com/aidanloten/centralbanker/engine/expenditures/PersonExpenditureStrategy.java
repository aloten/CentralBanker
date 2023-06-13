package com.aidanloten.centralbanker.engine.expenditures;

import com.aidanloten.centralbanker.data.entities.agents.Person;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Industry;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.PersonExpense;
import com.aidanloten.centralbanker.data.util.EconomicConstants;

public class PersonExpenditureStrategy {
    public PersonExpense calculatePersonExpense(Person person, Industry industry) {
        return PersonExpense.builder().person(person).industry(industry).value(getPersonExpenseValue(person, industry))
                .build();
    }

    public double getPersonExpenseValue(Person person, Industry industry) {
        return industry.getRevenueShare() * (person.getSalary() * (1 - EconomicConstants.PERSON_SAVINGS_RATE));
    }
}
