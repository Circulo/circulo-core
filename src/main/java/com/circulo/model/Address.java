package com.circulo.model;

import com.google.common.base.MoreObjects;

/**
 * Created by azim on 6/9/15.
 */
public class Address {

    private String postalAddress1;

    private String postalAddress2;

    private String city;

    private String postalCode;

    private String state;

    private String country;

    public String getPostalAddress1() {
        return postalAddress1;
    }

    public void setPostalAddress1(String postalAddress1) {
        this.postalAddress1 = postalAddress1;
    }

    public String getPostalAddress2() {
        return postalAddress2;
    }

    public void setPostalAddress2(String postalAddress2) {
        this.postalAddress2 = postalAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int result = postalAddress1 != null ? postalAddress1.hashCode() : 0;
        result = 31 * result + (postalAddress2 != null ? postalAddress2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Address address = (Address) obj;

        if (postalAddress1 != null ? !postalAddress1.equals(address.postalAddress1) : address.postalAddress1 != null) return false;
        if (postalAddress2 != null ? !postalAddress2.equals(address.postalAddress2) : address.postalAddress2 != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (postalCode != null ? !postalCode.equals(address.postalCode) : address.postalCode != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("postalAddress1", postalAddress1)
                .add("postalAddress2", postalAddress2)
                .add("city", city)
                .add("postalCode", postalCode)
                .add("state", state)
                .add("country", country)
                .toString();
    }
}
