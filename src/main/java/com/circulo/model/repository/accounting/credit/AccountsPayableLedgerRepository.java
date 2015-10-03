package com.circulo.model.repository.accounting.credit;

import com.circulo.model.accounting.credit.AccountsPayableLedger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 10/3/15.
 */
public interface AccountsPayableLedgerRepository extends MongoRepository<AccountsPayableLedger, String> {
}
