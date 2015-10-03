package com.circulo.model.repository.accounting.debit;

import com.circulo.model.accounting.debit.CashLedger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 10/3/15.
 */
public interface CashLedgerRepository extends MongoRepository<CashLedger, String> {
}
