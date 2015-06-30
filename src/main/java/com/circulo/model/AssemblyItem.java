package com.circulo.model;

/**
 * Created by azim on 6/17/15.
 */
public class AssemblyItem {

    private String sku;

    private Integer count;

    private String unitOfMeasure;

    private String notes;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int result = sku != null ? sku.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (unitOfMeasure != null ? unitOfMeasure.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AssemblyItem assemblyItem = (AssemblyItem) obj;

        if (sku != null ? !sku.equals(assemblyItem.sku) : assemblyItem.sku != null) return false;
        if (count != null ? !count.equals(assemblyItem.count) : assemblyItem.count != null) return false;
        if (unitOfMeasure != null ? !unitOfMeasure.equals(assemblyItem.unitOfMeasure) : assemblyItem.unitOfMeasure != null) return false;
        if (notes != null ? !notes.equals(assemblyItem.notes) : assemblyItem.notes != null) return false;

        return true;
    }
}
