package com.circulo.model;

import java.math.BigDecimal;

/**
 * Created by tfulton on 6/10/15.
 */
public class StockItem {

    private Product product;

    private String sku;

    private Integer onHand;

    private Integer committed;

    private Integer available;

    private Integer onOrder;

    private BigDecimal cost;

    private BigDecimal tax;

    private String notes;

    private String assemblyItemId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    public Integer getCommitted() {
        return committed;
    }

    public void setCommitted(Integer committed) {
        this.committed = committed;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(Integer onOrder) {
        this.onOrder = onOrder;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

        StockItem item = (StockItem) o;

        if (assemblyItemId != null ? !assemblyItemId.equals(item.assemblyItemId) : item.assemblyItemId != null)
            return false;
        if (available != null ? !available.equals(item.available) : item.available != null) return false;
        if (committed != null ? !committed.equals(item.committed) : item.committed != null) return false;
        if (cost != null ? !cost.equals(item.cost) : item.cost != null) return false;
        if (notes != null ? !notes.equals(item.notes) : item.notes != null) return false;
        if (onHand != null ? !onHand.equals(item.onHand) : item.onHand != null) return false;
        if (onOrder != null ? !onOrder.equals(item.onOrder) : item.onOrder != null) return false;
        if (sku != null ? !sku.equals(item.sku) : item.sku != null) return false;
        if (tax != null ? !tax.equals(item.tax) : item.tax != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sku != null ? sku.hashCode() : 0;
        result = 31 * result + (onHand != null ? onHand.hashCode() : 0);
        result = 31 * result + (committed != null ? committed.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (onOrder != null ? onOrder.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (assemblyItemId != null ? assemblyItemId.hashCode() : 0);
        return result;
    }
}
