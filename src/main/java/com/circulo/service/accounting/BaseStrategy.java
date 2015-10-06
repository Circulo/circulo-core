package com.circulo.service.accounting;

import com.circulo.model.Organization;
import com.circulo.model.accounting.GeneralLedger;
import com.circulo.model.repository.OrganizationRepository;
import com.circulo.model.repository.accounting.GeneralLedgerRepository;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by azim on 10/5/15.
 */
public abstract class BaseStrategy implements AccountingStrategy {
    protected Organization organization;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    protected GeneralLedgerRepository generalLedgerRepository;

    public BaseStrategy(Organization organization) {
        this.organization = organization;
        // passed in organization's id should not be empty
        Preconditions.checkArgument(organization != null && StringUtils.isNotEmpty(organization.getId()));

        this.organization = organizationRepository.findOne(organization.getId());
    }

    protected GeneralLedger createAndSaveDebitGeneralLedger(BigDecimal debit) {
        GeneralLedger generalLedger = createGeneralLedger();
        generalLedger.setDebit(debit);
        return generalLedgerRepository.save(generalLedger);
    }

    protected GeneralLedger createAndSaveCreditGeneralLedger(BigDecimal credit) {
        GeneralLedger generalLedger = createGeneralLedger();
        generalLedger.setCredit(credit);
        return generalLedgerRepository.save(generalLedger);
    }

    private GeneralLedger createGeneralLedger() {
        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setOrganization(organization);
        return generalLedger;
    }

    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
