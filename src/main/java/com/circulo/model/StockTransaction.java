package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
