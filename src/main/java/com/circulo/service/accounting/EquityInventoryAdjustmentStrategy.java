package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.accounting.debit.EquityLedger;
import com.circulo.model.accounting.debit.InventoryLedger;
import com.circulo.model.repository.accounting.debit.EquityLedgerRepository;
import com.circulo.model.repository.accounting.debit.InventoryLedgerRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by azim on 10/5/15.
 */
public class EquityInventoryAdjustmentStrategy extends BaseStrategy {
    private final BigDecimal amount;

    @Autowired
    private InventoryLedgerRepository inventoryLedgerRepository;

    @Autowired
    private EquityLedgerRepository equityLedgerRepository;

    public EquityInventoryAdjustmentStrategy(Organization organization, BigDecimal amount) {
        super(organization);
        this.amount = amount;
        // Amount should be greater than zero
        Preconditions.checkArgument(this.amount.compareTo(BigDecimal.ZERO) > 0);
    }

    @Transactional
    @Override
    public void execute() {
        saveInventoryLedger();
        saveEquityLedger();
    }

    protected void saveInventoryLedger() {
        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(amount);
        InventoryLedger inventoryLedger = new InventoryLedger();
        inventoryLedger.setId(generateId());
        inventoryLedger.setDebit(amount);
        inventoryLedger.setGeneralLedger(generalLedger);
        inventoryLedgerRepository.save(inventoryLedger);
    }

    protected void saveEquityLedger() {
        GeneralLedger generalLedger = createAndSaveCreditGeneralLedger(amount);
        EquityLedger equityLedger = new EquityLedger();
        equityLedger.setId(generateId());
        equityLedger.setCredit(amount);
        equityLedger.setGeneralLedger(generalLedger);
        equityLedgerRepository.save(equityLedger);
    }
}
