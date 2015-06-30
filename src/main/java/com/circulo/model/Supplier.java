package com.circulo.model;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by azim on 6/9/15.
 */
@Document(collection = "supplier")
public class Supplier {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    private String name;

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
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Supplier supplier = (Supplier) obj;

        if (id != null ? !id.equals(supplier.id) : supplier.id != null) return false;
        if (organization != null ? !organization.equals(supplier.organization) : supplier.organization != null) return false;
        if (name != null ? !name.equals(supplier.name) : supplier.name != null) return false;
        if (address != null ? !address.equals(supplier.address) : supplier.address != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("organization", organization)
                .add("name", name)
                .add("address", address)
                .toString();
    }
}
