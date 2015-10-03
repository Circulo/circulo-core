package com.circulo.model.repository.accounting.credit;

import com.circulo.model.accounting.credit.SalesTaxLedger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 10/3/15.
 */
public interface SalesTaxLedgerRepository extends MongoRepository<SalesTaxLedger, String> {
}
