package com.circulo.model.repository.accounting;

import com.circulo.model.accounting.GeneralLedger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 10/3/15.
 */
public interface GeneralLedgerRepository extends MongoRepository<GeneralLedger, String> {
}
