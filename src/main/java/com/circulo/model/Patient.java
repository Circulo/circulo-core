package com.circulo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by azim on 7/12/15.
 */
@Document(collection = "patient")
public class Patient {

    @Id
    private String id;

    private Member member;

    private Recommendation recommendation;

    private ObjectId applicationFormFileId;

    @DBRef
    private List<Caregiver> caregivers;

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

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public ObjectId getApplicationFormFileId() {
        return applicationFormFileId;
    }

    public void setApplicationFormFileId(ObjectId applicationFormFileId) {
        this.applicationFormFileId = applicationFormFileId;
    }

    public List<Caregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(List<Caregiver> caregivers) {
        this.caregivers = caregivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (id != null ? !id.equals(patient.id) : patient.id != null) return false;
        if (member != null ? !member.equals(patient.member) : patient.member != null) return false;
        if (recommendation != null ? !recommendation.equals(patient.recommendation) : patient.recommendation != null)
            return false;
        if (applicationFormFileId != null ? !applicationFormFileId.equals(patient.applicationFormFileId) : patient.applicationFormFileId != null)
            return false;
        return !(caregivers != null ? !caregivers.equals(patient.caregivers) : patient.caregivers != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (recommendation != null ? recommendation.hashCode() : 0);
        result = 31 * result + (applicationFormFileId != null ? applicationFormFileId.hashCode() : 0);
        result = 31 * result + (caregivers != null ? caregivers.hashCode() : 0);
        return result;
    }
}
