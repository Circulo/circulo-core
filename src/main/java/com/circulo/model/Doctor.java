package com.circulo.model;

import com.circulo.enums.DoctorLicenseStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by azim on 6/30/15.
 */
@Document(collection = "doctor")
public class Doctor {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String licenseNo;

    private Date licenseExpirationDate;

    private String category;    // Example "MD", "DO" etc.

    private DoctorLicenseStatus licenseStatus;

    private String email;

    private Address address;

    private String officePhone;

    private String mobilePhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(Date licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DoctorLicenseStatus getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(DoctorLicenseStatus licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (id != null ? !id.equals(doctor.id) : doctor.id != null) return false;
        if (firstName != null ? !firstName.equals(doctor.firstName) : doctor.firstName != null) return false;
        if (lastName != null ? !lastName.equals(doctor.lastName) : doctor.lastName != null) return false;
        if (licenseNo != null ? !licenseNo.equals(doctor.licenseNo) : doctor.licenseNo != null) return false;
        if (licenseExpirationDate != null ? !licenseExpirationDate.equals(doctor.licenseExpirationDate) : doctor.licenseExpirationDate != null)
            return false;
        if (category != null ? !category.equals(doctor.category) : doctor.category != null) return false;
        if (licenseStatus != doctor.licenseStatus) return false;
        if (email != null ? !email.equals(doctor.email) : doctor.email != null) return false;
        if (address != null ? !address.equals(doctor.address) : doctor.address != null) return false;
        if (officePhone != null ? !officePhone.equals(doctor.officePhone) : doctor.officePhone != null) return false;
        return !(mobilePhone != null ? !mobilePhone.equals(doctor.mobilePhone) : doctor.mobilePhone != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (licenseNo != null ? licenseNo.hashCode() : 0);
        result = 31 * result + (licenseExpirationDate != null ? licenseExpirationDate.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (licenseStatus != null ? licenseStatus.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (officePhone != null ? officePhone.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        return result;
    }
}
