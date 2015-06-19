package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
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

    /**
     * Correlation between assemblyInput, assemblyOutput and lostAssemblyInput:
     * Say we have two product variations having sku=ABC123 and sku=DEF123.
     * assemblyInput has 2 entries for that variation. They are 7 grams of sku=ABC123 and 12 grams of sku=DEF123.
     * We have assembled them as 3 entries. They are 5 grams of sku=ABC123, 5 grams of sku=DEF123 and another 5 grams of sku=DEF123.
     * We have lost 1 gram of sku=DEF123.
     * We have 2 grams of sku=ABC123 and 1 gram of sku=DEF123 left.
     * So assemblyInput = [
     *  {"sku": "ABC123", "unitOfMeasure": "gram", "count" : 7},
     *  {"sku": "DEF123", "unitOfMeasure": "gram", "count" : 12}
     * ]
     * assemblyOutput = [
     *  {"sku": "ABC123", "unitOfMeasure": "gram", "count" : 5},
     *  {"sku": "DEF123", "unitOfMeasure": "gram", "count" : 5},
     *  {"sku": "DEF123", "unitOfMeasure": "gram", "count" : 5}
     * ]
     * lostAssemblyInput = [
     *  {"sku": "DEF123", "unitOfMeasure": "gram", "count" : 1}
     * ]
     * leftOverAssemblyInput = [
     *  {"sku": "ABC123", "unitOfMeasure": "gram", "count" : 2},
     *  {"sku": "DEF123", "unitOfMeasure": "gram", "count" : 1}
     * ]
     * assemblyInput has one to many relationship with assemblyOutput.
     */
    private List<AssemblyItem> assemblyInput;

    private List<AssemblyItem> assemblyOutput;

    // list of assembly items which have been lost as part of assembly process
    // say we were given 6 grams of sku1 and 11 grams of sku2 as input.
    // we created 5 grams of sku1 and 10 grams of sku2 as output and lost 1 gram of sku1 and 1 gram of sku2 in the assembly process.
    // so lostAssemblyInput will be 1 gram of sku1 and 1 gram of sku2.
    private List<AssemblyItem> lostAssemblyInput;

    private List<AssemblyItem> leftOverAssemblyInput;

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

    public List<AssemblyItem> getAssemblyInput() {
        return assemblyInput;
    }

    public void setAssemblyInput(List<AssemblyItem> assemblyInput) {
        this.assemblyInput = assemblyInput;
    }

    public List<AssemblyItem> getAssemblyOutput() {
        return assemblyOutput;
    }

    public void setAssemblyOutput(List<AssemblyItem> assemblyOutput) {
        this.assemblyOutput = assemblyOutput;
    }

    public List<AssemblyItem> getLostAssemblyInput() {
        return lostAssemblyInput;
    }

    public void setLostAssemblyInput(List<AssemblyItem> lostAssemblyInput) {
        this.lostAssemblyInput = lostAssemblyInput;
    }

    public List<AssemblyItem> getLeftOverAssemblyInput() {
        return leftOverAssemblyInput;
    }

    public void setLeftOverAssemblyInput(List<AssemblyItem> leftOverAssemblyInput) {
        this.leftOverAssemblyInput = leftOverAssemblyInput;
    }
}
