package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
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

    private Map<String, StockItem> stockItemMap = new HashMap<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockSummary summary = (StockSummary) o;

        if (id != null ? !id.equals(summary.id) : summary.id != null) return false;
        if (organization != null ? !organization.equals(summary.organization) : summary.organization != null)
            return false;
        if (stockItemMap != null ? !stockItemMap.equals(summary.stockItemMap) : summary.stockItemMap != null)
            return false;
        return !(calculatedAt != null ? !calculatedAt.equals(summary.calculatedAt) : summary.calculatedAt != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (stockItemMap != null ? stockItemMap.hashCode() : 0);
        result = 31 * result + (calculatedAt != null ? calculatedAt.hashCode() : 0);
        return result;
    }
}
