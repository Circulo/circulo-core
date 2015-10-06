package com.circulo.service.accounting;

import com.google.common.base.Preconditions;

/**
 * Created by azim on 10/5/15.
 */
public class AccountingContext {
    private AccountingStrategy accountingStrategy;

    public AccountingContext(AccountingStrategy accountingStrategy) {
        // Strategy should not be null.
        Preconditions.checkArgument(accountingStrategy != null);
        this.accountingStrategy = accountingStrategy;
    }

    public void execute() {
        accountingStrategy.execute();
    }
}
