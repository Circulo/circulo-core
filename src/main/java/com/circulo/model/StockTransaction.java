package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by tfulton on 6/10/15.
 */
@Document(collection = "stock_transaction")
public class StockTransaction {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    private StockTransactionType type;

    private String sku;

    private Integer count;

    private BigDecimal unitCost;

    private BigDecimal tax;

    private BigDecimal grossUnitCost;

    private String unitOfMeasure;

    private String userId;

    private StockLocation locationFrom;

    private StockLocation locationTo;

    private String notes;

    public enum StockTransactionType {

        ADJUSTMENT,
        PROCUREMENT,
        SALE,
        TRANSFER,
    }

    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));

    public void calculateGrossValue() {

        double grossValue = this.getCount() * this.getUnitCost().doubleValue();
        grossValue += this.getTax().doubleValue();
        this.grossUnitCost = new BigDecimal(grossValue);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public StockTransactionType getType() {
        return type;
    }

    public void setType(StockTransactionType type) {
        this.type = type;
    }

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

    public BigDecimal getGrossUnitCost() {
        return grossUnitCost;
    }

    public void setGrossUnitCost(BigDecimal grossUnitCost) {
        this.grossUnitCost = grossUnitCost;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public StockLocation getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(StockLocation locationFrom) {
        this.locationFrom = locationFrom;
    }

    public StockLocation getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(StockLocation locationTo) {
        this.locationTo = locationTo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockTransaction that = (StockTransaction) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (grossUnitCost != null ? !grossUnitCost.equals(that.grossUnitCost) : that.grossUnitCost != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (locationFrom != null ? !locationFrom.equals(that.locationFrom) : that.locationFrom != null) return false;
        if (locationTo != null ? !locationTo.equals(that.locationTo) : that.locationTo != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        if (tax != null ? !tax.equals(that.tax) : that.tax != null) return false;
        if (type != that.type) return false;
        if (unitCost != null ? !unitCost.equals(that.unitCost) : that.unitCost != null) return false;
        if (unitOfMeasure != null ? !unitOfMeasure.equals(that.unitOfMeasure) : that.unitOfMeasure != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (unitCost != null ? unitCost.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        result = 31 * result + (grossUnitCost != null ? grossUnitCost.hashCode() : 0);
        result = 31 * result + (unitOfMeasure != null ? unitOfMeasure.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (locationFrom != null ? locationFrom.hashCode() : 0);
        result = 31 * result + (locationTo != null ? locationTo.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
