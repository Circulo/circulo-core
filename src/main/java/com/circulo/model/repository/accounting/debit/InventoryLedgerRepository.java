package com.circulo.model.repository.accounting.debit;

import com.circulo.model.accounting.debit.InventoryLedger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 10/3/15.
 */
public interface InventoryLedgerRepository extends MongoRepository<InventoryLedger, String> {
}
