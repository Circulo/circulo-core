package com.circulo.enums;

/**
 * Created by azim on 7/11/15.
 */
public enum DoctorLicenseStatus {
    CURRENT("Current"),
    EXPIRED("Expired");

    private String value;

    DoctorLicenseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
