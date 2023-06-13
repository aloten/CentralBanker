package com.aidanloten.centralbanker.engine.transactions;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionExecutionStrategy transactionExecutionStrategy;

    public TransactionService(TransactionExecutionStrategy transactionExecutionStrategy) {
        this.transactionExecutionStrategy = transactionExecutionStrategy;
    }

    /**
     * Based on P2P trading, no money
     */
    public void executeTradeTransaction(TradeTransaction tradeTransaction) {
        transactionExecutionStrategy.executeTradeTransaction(tradeTransaction);
    }

    public void executeTransaction(Transaction transaction) {
        transactionExecutionStrategy.executeTransaction(transaction);
    }
}
