package com.circulo.model;

import com.circulo.enums.Gender;
import com.circulo.enums.MemberType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by azim on 6/30/15.
 */
public class Member {
    private String firstName;

    private String middleInitial;

    private String lastName;

    private Gender gender;

    private Date dateOfBirth;

    private String stateID;

    private String alternateID;

    private String email;

    private Address address;

    private String mobilePhone;

    private String homePhone;

    private ObjectId stateIdFileId;

    private ObjectId alternateIdFileId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getAlternateID() {
        return alternateID;
    }

    public void setAlternateID(String alternateID) {
        this.alternateID = alternateID;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public ObjectId getStateIdFileId() {
        return stateIdFileId;
    }

    public void setStateIdFileId(ObjectId stateIdFileId) {
        this.stateIdFileId = stateIdFileId;
    }

    public ObjectId getAlternateIdFileId() {
        return alternateIdFileId;
    }

    public void setAlternateIdFileId(ObjectId alternateIdFileId) {
        this.alternateIdFileId = alternateIdFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (firstName != null ? !firstName.equals(member.firstName) : member.firstName != null) return false;
        if (middleInitial != null ? !middleInitial.equals(member.middleInitial) : member.middleInitial != null)
            return false;
        if (lastName != null ? !lastName.equals(member.lastName) : member.lastName != null) return false;
        if (gender != member.gender) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(member.dateOfBirth) : member.dateOfBirth != null) return false;
        if (stateID != null ? !stateID.equals(member.stateID) : member.stateID != null) return false;
        if (alternateID != null ? !alternateID.equals(member.alternateID) : member.alternateID != null) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (address != null ? !address.equals(member.address) : member.address != null) return false;
        if (mobilePhone != null ? !mobilePhone.equals(member.mobilePhone) : member.mobilePhone != null) return false;
        if (homePhone != null ? !homePhone.equals(member.homePhone) : member.homePhone != null) return false;
        if (stateIdFileId != null ? !stateIdFileId.equals(member.stateIdFileId) : member.stateIdFileId != null)
            return false;
        return !(alternateIdFileId != null ? !alternateIdFileId.equals(member.alternateIdFileId) : member.alternateIdFileId != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (middleInitial != null ? middleInitial.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (stateID != null ? stateID.hashCode() : 0);
        result = 31 * result + (alternateID != null ? alternateID.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
        result = 31 * result + (stateIdFileId != null ? stateIdFileId.hashCode() : 0);
        result = 31 * result + (alternateIdFileId != null ? alternateIdFileId.hashCode() : 0);
        return result;
    }
}
