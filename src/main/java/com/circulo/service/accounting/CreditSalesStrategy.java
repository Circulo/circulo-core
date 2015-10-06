package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.accounting.debit.AccountsReceivableLedger;
import com.circulo.model.repository.accounting.debit.AccountsReceivableLedgerRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by azim on 10/5/15.
 */
public class CreditSalesStrategy extends SalesStrategy {
    private final BigDecimal credit;

    @Autowired
    private AccountsReceivableLedgerRepository accountsReceivableLedgerRepository;

    public CreditSalesStrategy(Organization organization, BigDecimal sales) {
        this(organization, sales, BigDecimal.ZERO, BigDecimal.ZERO, false);
    }

    public CreditSalesStrategy(Organization organization, BigDecimal sales, boolean trackingInventory) {
        this(organization, sales, BigDecimal.ZERO, BigDecimal.ZERO, trackingInventory);
    }

    public CreditSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax) {
        this(organization, sales, salesTax, BigDecimal.ZERO, false);
    }

    public CreditSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax, BigDecimal discount) {
        this(organization, sales, salesTax, discount, false);
    }

    public CreditSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax, BigDecimal discount, boolean trackingInventory) {
        super(organization, sales, salesTax, discount, trackingInventory);
        // cash = sales + salesTax - discount.
        credit = sales.add(salesTax).subtract(discount);
        // Check whether credit is greater than zero.
        Preconditions.checkArgument(credit.compareTo(BigDecimal.ZERO) > 0);
    }

    @Transactional
    @Override
    public void execute() {
        super.execute();
        saveAccountsReceivableLedger();
    }

    private void saveAccountsReceivableLedger() {
        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(credit);
        AccountsReceivableLedger accountsReceivableLedger = new AccountsReceivableLedger();
        accountsReceivableLedger.setId(generateId());
        accountsReceivableLedger.setDebit(credit);
        accountsReceivableLedger.setGeneralLedger(generalLedger);
        accountsReceivableLedgerRepository.save(accountsReceivableLedger);
    }
}
