package com.circulo.model;

import com.circulo.enums.VerificationProvider;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

/**
 * Created by azim on 6/30/15.
 */
public class Recommendation {

    private VerificationProvider verificationProvider;

    private String recommendationNo;

    private Date validFrom;

    private Date validUpto;

    @DBRef
    private Doctor doctor;

    public VerificationProvider getVerificationProvider() {
        return verificationProvider;
    }

    public void setVerificationProvider(VerificationProvider verificationProvider) {
        this.verificationProvider = verificationProvider;
    }

    public String getRecommendationNo() {
        return recommendationNo;
    }

    public void setRecommendationNo(String recommendationNo) {
        this.recommendationNo = recommendationNo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(Date validUpto) {
        this.validUpto = validUpto;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
