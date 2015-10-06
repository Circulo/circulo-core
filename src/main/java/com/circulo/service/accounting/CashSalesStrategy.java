package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.accounting.debit.CashLedger;
import com.circulo.model.repository.accounting.debit.CashLedgerRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by azim on 10/5/15.
 */
public class CashSalesStrategy extends SalesStrategy {
    private final BigDecimal cash;

    @Autowired
    private CashLedgerRepository cashLedgerRepository;

    public CashSalesStrategy(Organization organization, BigDecimal sales) {
        this(organization, sales, BigDecimal.ZERO, BigDecimal.ZERO, false);
    }

    public CashSalesStrategy(Organization organization, BigDecimal sales, boolean trackingInventory) {
        this(organization, sales, BigDecimal.ZERO, BigDecimal.ZERO, trackingInventory);
    }

    public CashSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax) {
        this(organization, sales, salesTax, BigDecimal.ZERO, false);
    }

    public CashSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax, BigDecimal discount) {
        this(organization, sales, salesTax, discount, false);
    }

    public CashSalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax, BigDecimal discount, boolean trackingInventory) {
        super(organization, sales, salesTax, discount, trackingInventory);
        // cash = sales + salesTax - discount.
        cash = sales.add(salesTax).subtract(discount);
        // Check whether cash is greater than zero.
        Preconditions.checkArgument(cash.compareTo(BigDecimal.ZERO) > 0);
    }

    @Transactional
    @Override
    public void execute() {
        super.execute();

        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(cash);
        CashLedger cashLedger = new CashLedger();
        cashLedger.setId(generateId());
        cashLedger.setDebit(cash);
        cashLedger.setGeneralLedger(generalLedger);
        cashLedgerRepository.save(cashLedger);
    }
}
