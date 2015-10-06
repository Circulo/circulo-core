package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.accounting.credit.CostGoodsLedger;
import com.circulo.model.accounting.credit.DiscountLedger;
import com.circulo.model.accounting.credit.SalesLedger;
import com.circulo.model.accounting.credit.SalesTaxLedger;
import com.circulo.model.accounting.debit.InventoryLedger;
import com.circulo.model.repository.accounting.credit.CostGoodsLedgerRepository;
import com.circulo.model.repository.accounting.credit.DiscountsLedgerRepository;
import com.circulo.model.repository.accounting.credit.SalesLedgerRepository;
import com.circulo.model.repository.accounting.credit.SalesTaxLedgerRepository;
import com.circulo.model.repository.accounting.debit.InventoryLedgerRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by azim on 10/5/15.
 */
public abstract class SalesStrategy extends BaseStrategy {

    protected final BigDecimal sales;
    protected final BigDecimal salesTax;
    protected final BigDecimal discount;
    protected final boolean trackingInventory;

    @Autowired
    private SalesLedgerRepository salesLedgerRepository;

    @Autowired
    private SalesTaxLedgerRepository salesTaxLedgerRepository;

    @Autowired
    private DiscountsLedgerRepository discountsLedgerRepository;

    @Autowired
    private InventoryLedgerRepository inventoryLedgerRepository;

    @Autowired
    private CostGoodsLedgerRepository costGoodsLedgerRepository;



    public SalesStrategy(Organization organization, BigDecimal sales, BigDecimal salesTax,
                         BigDecimal discount, boolean trackingInventory) {
        super(organization);
        // There should a saved organization having the same id.
        Preconditions.checkArgument(sales != null && sales.compareTo(BigDecimal.ZERO) > 0);

        // sales should be non null and > 0.
        Preconditions.checkArgument(sales != null && sales.compareTo(BigDecimal.ZERO) > 0);
        // disocunt should not be more than sales amount.
        Preconditions.checkArgument(sales.compareTo(discount) >= 0);
        // sales tax should not be more than sales amount.
        Preconditions.checkArgument(sales.compareTo(salesTax) >= 0);

        this.sales = sales;
        this.salesTax = salesTax;
        this.discount = discount;
        this.trackingInventory = trackingInventory;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public boolean isTrackingInventory() {
        return trackingInventory;
    }

    @Override
    public void execute() {
        saveSalesLedger();

        if (trackingInventory) {
            saveInventoryLedger();
            saveCostGoodLedger();
        }

        if (salesTax.compareTo(BigDecimal.ZERO) > 0) {
            saveSalesTaxLedger();
        }

        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            saveDiscountLedger();
        }
    }

    protected void saveDiscountLedger() {
        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(discount);
        DiscountLedger discountLedger = new DiscountLedger();
        discountLedger.setId(generateId());
        discountLedger.setDebit(discount);
        discountLedger.setGeneralLedger(generalLedger);
        discountsLedgerRepository.save(discountLedger);
    }

    protected void saveSalesTaxLedger() {
        GeneralLedger generalLedger = createAndSaveCreditGeneralLedger(salesTax);
        SalesTaxLedger salesTaxLedger = new SalesTaxLedger();
        salesTaxLedger.setId(generateId());
        salesTaxLedger.setCredit(salesTax);
        salesTaxLedger.setGeneralLedger(generalLedger);
        salesTaxLedgerRepository.save(salesTaxLedger);
    }

    protected void saveCostGoodLedger() {
        GeneralLedger generalLedger = createAndSaveDebitGeneralLedger(sales);
        CostGoodsLedger costGoodsLedger = new CostGoodsLedger();
        costGoodsLedger.setId(generateId());
        costGoodsLedger.setDebit(sales);
        costGoodsLedger.setGeneralLedger(generalLedger);
        costGoodsLedgerRepository.save(costGoodsLedger);
    }

    protected void saveInventoryLedger() {
        GeneralLedger generalLedger = createAndSaveCreditGeneralLedger(sales);
        InventoryLedger inventoryLedger = new InventoryLedger();
        inventoryLedger.setId(generateId());
        inventoryLedger.setCredit(sales);
        inventoryLedger.setGeneralLedger(generalLedger);
        inventoryLedgerRepository.save(inventoryLedger);
    }

    protected void saveSalesLedger() {
        GeneralLedger generalLedger = createAndSaveCreditGeneralLedger(sales);
        SalesLedger salesLedger = new SalesLedger();
        salesLedger.setId(generateId());
        salesLedger.setCredit(sales);
        salesLedger.setGeneralLedger(generalLedger);
        salesLedgerRepository.save(salesLedger);
    }

    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
