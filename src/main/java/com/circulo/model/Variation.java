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

}
