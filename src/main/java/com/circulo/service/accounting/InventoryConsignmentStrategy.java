package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.accounting.credit.AccountsPayableLedger;
import com.circulo.model.accounting.debit.InventoryLedger;
import com.circulo.model.repository.accounting.credit.AccountsPayableLedgerRepository;
import com.circulo.model.repository.accounting.debit.InventoryLedgerRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by azim on 10/5/15.
 */
public class InventoryConsignmentStrategy extends BaseStrategy {
    private final BigDecimal amount;

    @Autowired
    private InventoryLedgerRepository inventoryLedgerRepository;

    @Autowired
    private AccountsPayableLedgerRepository accountsPayableLedgerRepository;

    public InventoryConsignmentStrategy(Organization organization, BigDecimal amount) {
        super(organization);
        this.amount = amount;
        // Amount should be greater than zero
        Preconditions.checkArgument(this.amount.compareTo(BigDecimal.ZERO) > 0);
    }

    @Transactional
    @Override
    public void execute() {
        saveInventoryLedger();
        saveAccountsPayableLedger();
    }

    protected void saveInventoryLedger() {
        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(amount);
        InventoryLedger inventoryLedger = new InventoryLedger();
        inventoryLedger.setId(generateId());
        inventoryLedger.setDebit(amount);
        inventoryLedger.setGeneralLedger(generalLedger);
        inventoryLedgerRepository.save(inventoryLedger);
    }

    protected void saveAccountsPayableLedger() {
        GeneralLedger generalLedger = createAndSaveCreditGeneralLedger(amount);
        AccountsPayableLedger accountsPayableLedger = new AccountsPayableLedger();
        accountsPayableLedger.setId(generateId());
        accountsPayableLedger.setCredit(amount);
        accountsPayableLedger.setGeneralLedger(generalLedger);
        accountsPayableLedgerRepository.save(accountsPayableLedger);
    }
}
