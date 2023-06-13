package com.aidanloten.centralbanker.data.entities.agents;

import com.aidanloten.centralbanker.data.entities.EntityModel;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.finance.FinancialState;

public interface Agent extends EntityModel {
    FinancialState getFinancialState();
    void setFinancialState(FinancialState financialState);
}
