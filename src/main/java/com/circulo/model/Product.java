package com.circulo.model;

import com.circulo.enums.ProductStatus;
import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by azim on 6/9/15.
 */
@Document(collection = "product")
public class Product {

    @Id
    private String id;

    @DBRef
    private Organization organization;

    private String name;

    @DBRef
    private Category category;

    private String description;

    @DBRef
    private Supplier supplier;

    private String brand;

    private List<String> tags;

    private ProductStatus status;

    private List<Variation> variations;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (variations != null ? variations.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (organization != null ? !organization.equals(product.organization) : product.organization != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (supplier != null ? !supplier.equals(product.supplier) : product.supplier != null) return false;
        if (brand != null ? !brand.equals(product.brand) : product.brand != null) return false;
        if (tags != null ? !tags.equals(product.tags) : product.tags != null) return false;
        if (status != null ? !status.equals(product.status) : product.status != null) return false;
        if (variations != null ? !variations.equals(product.variations) : product.variations != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("organization", organization)
                .add("name", name)
                .add("category", category)
                .add("description", description)
                .add("supplier", supplier)
                .add("brand", brand)
                .add("tags", tags)
                .add("status", status)
                .add("variations", variations)
                .toString();
    }
}
