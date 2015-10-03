package com.circulo.model.accounting.debit;

import com.circulo.model.accounting.BaseLedger;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by azim on 10/3/15.
 * DEBIT
 */
@Document(collection = "equity_ledger")
public class EquityLedger extends BaseLedger {
}
