package com.circulo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by azim on 7/12/15.
 */
@Document(collection = "caregiver")
public class Caregiver {

    @Id
    private String id;

    private Member member;

    private ObjectId applicationFormFileId;

    @DBRef
    private List<Patient> patients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public ObjectId getApplicationFormFileId() {
        return applicationFormFileId;
    }

    public void setApplicationFormFileId(ObjectId applicationFormFileId) {
        this.applicationFormFileId = applicationFormFileId;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Caregiver caregiver = (Caregiver) o;

        if (id != null ? !id.equals(caregiver.id) : caregiver.id != null) return false;
        if (member != null ? !member.equals(caregiver.member) : caregiver.member != null) return false;
        if (applicationFormFileId != null ? !applicationFormFileId.equals(caregiver.applicationFormFileId) : caregiver.applicationFormFileId != null)
            return false;
        return !(patients != null ? !patients.equals(caregiver.patients) : caregiver.patients != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (applicationFormFileId != null ? applicationFormFileId.hashCode() : 0);
        result = 31 * result + (patients != null ? patients.hashCode() : 0);
        return result;
    }
}
