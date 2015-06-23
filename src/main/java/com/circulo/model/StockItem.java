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

    private String assemblyItemId;

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

    public String getAssemblyItemId() {
        return assemblyItemId;
    }

    public void setAssemblyItemId(String assemblyItemId) {
        this.assemblyItemId = assemblyItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockItem stockItem = (StockItem) o;

        if (assemblyItemId != null ? !assemblyItemId.equals(stockItem.assemblyItemId) : stockItem.assemblyItemId != null)
            return false;
        if (count != null ? !count.equals(stockItem.count) : stockItem.count != null) return false;
        if (notes != null ? !notes.equals(stockItem.notes) : stockItem.notes != null) return false;
        if (sku != null ? !sku.equals(stockItem.sku) : stockItem.sku != null) return false;
        if (valuation != null ? !valuation.equals(stockItem.valuation) : stockItem.valuation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sku != null ? sku.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (valuation != null ? valuation.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (assemblyItemId != null ? assemblyItemId.hashCode() : 0);
        return result;
    }
}
