package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by tfulton on 6/10/15.
 */
@Document(collection = "stock_summary")
public class StockSummary {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    private Map<String, StockItem> stockItemMap;

    private LocalDateTime calculatedAt;

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

    public Map<String, StockItem> getStockItemMap() {
        return stockItemMap;
    }

    public void setStockItemMap(Map<String, StockItem> stockItemMap) {
        this.stockItemMap = stockItemMap;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
}
