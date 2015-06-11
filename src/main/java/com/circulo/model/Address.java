package com.circulo.model;

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
}
