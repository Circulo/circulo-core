package com.circulo.model.accounting;

import com.circulo.model.Organization;
import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Created by azim on 10/3/15.
 */
@Document(collection = "general_ledger")
public class GeneralLedger {
    @Id
    private String id;

    @DBRef
    private Organization organization;
    private BigDecimal debit;
    private BigDecimal credit;

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

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralLedger that = (GeneralLedger) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;
        if (debit != null ? !debit.equals(that.debit) : that.debit != null) return false;
        return !(credit != null ? !credit.equals(that.credit) : that.credit != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (debit != null ? debit.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("organization", organization)
                .add("debit", debit)
                .add("credit", credit)
                .toString();
    }
}
