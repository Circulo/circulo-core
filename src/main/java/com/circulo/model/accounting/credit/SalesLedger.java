package com.circulo.model.accounting.credit;

import com.circulo.model.accounting.BaseLedger;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by azim on 10/3/15.
 * CREDIT
 */
@Document(collection = "sales_ledger")
public class SalesLedger extends BaseLedger {
}
