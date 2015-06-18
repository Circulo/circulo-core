package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * It represents packaging of a particular procurement when it's received.
 * @TODO : Talk with Todd for a better naming.
 * Created by azim on 6/17/15.
 */
@Document(collection = "assembly")
public class Assembly {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    @DBRef
    private StockLocation stockLocation;

    @DBRef
    private Procurement procurement;

    // gross amount of pre tax loss in USD which happens in the process of assembly.
    // handle the calculation of this in service layer.
    private BigDecimal lossSubTotal;

    // gross amount of tax loss in USD which happens in the process of assembly
    // handle the calculation of this in service layer.
    private BigDecimal lossTaxTotal;

    // gross amount of total loss in USD which happens in the process of assembly.
    // handle the calculation of this in service layer.
    private BigDecimal lossTotal;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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

    public StockLocation getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(StockLocation stockLocation) {
        this.stockLocation = stockLocation;
    }

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public BigDecimal getLossSubTotal() {
        return lossSubTotal;
    }

    public void setLossSubTotal(BigDecimal lossSubTotal) {
        this.lossSubTotal = lossSubTotal;
    }

    public BigDecimal getLossTaxTotal() {
        return lossTaxTotal;
    }

    public void setLossTaxTotal(BigDecimal lossTaxTotal) {
        this.lossTaxTotal = lossTaxTotal;
    }

    public BigDecimal getLossTotal() {
        return lossTotal;
    }

    public void setLossTotal(BigDecimal lossTotal) {
        this.lossTotal = lossTotal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
