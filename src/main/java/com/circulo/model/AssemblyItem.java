package com.circulo.model;

import java.math.BigDecimal;

/**
 * Created by azim on 6/17/15.
 */
public class AssemblyItem {

    private String id;

    // Scenario 1: a procurement item may be divided into one or more assembly items. In that case, keeping a single procurement item here suffices.
    // Scenario 2 : One ore more procurement items having the same sku, may be combined into one assembly item. In that case, keeping a list of procurement items may be needed.
    // But how to make sure the list consists of same skus??
    //@TODO : Confirm with Todd whether it should contain a list or a single procurement item.
    // For the time being keeping a single procuremnt item reference here covering scenario 1.
    String procurementItemId;

    private Integer count;

    private BigDecimal unitCost;

    private BigDecimal tax;

    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcurementItemId() {
        return procurementItemId;
    }

    public void setProcurementItemId(String procurementItemId) {
        this.procurementItemId = procurementItemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
