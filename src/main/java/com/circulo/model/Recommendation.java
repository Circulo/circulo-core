package com.circulo.model;

import com.circulo.enums.VerificationProvider;
import com.mongodb.gridfs.GridFSDBFile;
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

    private GridFSDBFile recommendationFile;

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

    public GridFSDBFile getRecommendationFile() {
        return recommendationFile;
    }

    public void setRecommendationFile(GridFSDBFile recommendationFile) {
        this.recommendationFile = recommendationFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recommendation that = (Recommendation) o;

        if (verificationProvider != that.verificationProvider) return false;
        if (recommendationNo != null ? !recommendationNo.equals(that.recommendationNo) : that.recommendationNo != null)
            return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validUpto != null ? !validUpto.equals(that.validUpto) : that.validUpto != null) return false;
        if (doctor != null ? !doctor.equals(that.doctor) : that.doctor != null) return false;
        return !(recommendationFile != null ? !recommendationFile.equals(that.recommendationFile) : that.recommendationFile != null);

    }

    @Override
    public int hashCode() {
        int result = verificationProvider != null ? verificationProvider.hashCode() : 0;
        result = 31 * result + (recommendationNo != null ? recommendationNo.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validUpto != null ? validUpto.hashCode() : 0);
        result = 31 * result + (doctor != null ? doctor.hashCode() : 0);
        result = 31 * result + (recommendationFile != null ? recommendationFile.hashCode() : 0);
        return result;
    }
}
