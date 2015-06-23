package com.circulo.service;

import com.circulo.model.Procurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tfulton on 6/18/15.
 */
@Component
public class ProcurementService {

    @Autowired
    private StockSummaryService stockSummaryService;

    public Procurement createProcurement(Procurement procurement) {

        return null;
    }

    public Procurement updateProcurement(Procurement procurement) {

        return null;
    }

    public Procurement getProcurement(String id) {

        return null;
    }

    public Procurement receiveProcurement(Procurement procurement) {

        // validate totals including quantities, unit cost totals and tax totals

        // create stock transactions for each procurement item

        // update the stock summary snapshot

        // return the updated/received procurement

        return null;
    }
}
