package com.circulo.model;

import java.math.BigDecimal;

/**
 * Created by tfulton on 6/10/15.
 */
public class StockItem {

    private String sku;

    private Integer count;

    private BigDecimal valuation;

    private String notes;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public void setValuation(BigDecimal valuation) {
        this.valuation = valuation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
