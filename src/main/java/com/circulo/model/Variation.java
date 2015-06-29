package com.circulo.model;

import java.math.BigDecimal;

/**
 * Created by azim on 6/9/15.
 */
public class Variation {

    private String name;

    private String sku;

    private String description;

    private String barCode;

    private BigDecimal buyPrice;

    private BigDecimal wholesalePrice;

    private BigDecimal recommendedRetailPrice;

    private BigDecimal taxOverride;

    private String notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getNotes() {
        return notes;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(BigDecimal recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public BigDecimal getTaxOverride() {
        return taxOverride;
    }

    public void setTaxOverride(BigDecimal taxOverride) {
        this.taxOverride = taxOverride;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Variation variation = (Variation) obj;

        if (name != null ? !name.equals(variation.name) : variation.name != null) return false;
        if (sku != null ? !sku.equals(variation.sku) : variation.sku != null) return false;
        if (description != null ? !description.equals(variation.description) : variation.description != null) return false;
        if (barCode != null ? !barCode.equals(variation.barCode) : variation.barCode != null) return false;
        if (buyPrice != null ? !buyPrice.equals(variation.buyPrice) : variation.buyPrice != null) return false;
        if (wholesalePrice != null ? !wholesalePrice.equals(variation.wholesalePrice) : variation.wholesalePrice != null) return false;
        if (recommendedRetailPrice != null ? !recommendedRetailPrice.equals(variation.recommendedRetailPrice) : variation.recommendedRetailPrice != null) return false;
        if (taxOverride != null ? !taxOverride.equals(variation.taxOverride) : variation.taxOverride != null) return false;
        if (notes != null ? !notes.equals(variation.notes) : variation.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (barCode != null ? barCode.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (wholesalePrice != null ? wholesalePrice.hashCode() : 0);
        result = 31 * result + (recommendedRetailPrice != null ? recommendedRetailPrice.hashCode() : 0);
        result = 31 * result + (taxOverride != null ? taxOverride.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
