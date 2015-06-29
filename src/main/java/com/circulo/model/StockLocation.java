package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by tfulton on 6/11/15.
 */
@Document(collection = "stock_location")
public class StockLocation {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    private String name;

    private String description;

    private Address address;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        StockLocation stockLocation = (StockLocation) obj;

        if (id != null ? !id.equals(stockLocation.id) : stockLocation.id != null) return false;
        if (organization != null ? !organization.equals(stockLocation.organization) : stockLocation.organization != null) return false;
        if (name != null ? !name.equals(stockLocation.name) : stockLocation.name != null) return false;
        if (description != null ? !description.equals(stockLocation.description) : stockLocation.description != null) return false;
        if (address != null ? !address.equals(stockLocation.address) : stockLocation.address != null) return false;

        return true;
    }
}
