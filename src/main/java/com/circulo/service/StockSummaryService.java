package com.circulo.service;

import com.circulo.model.Organization;
import com.circulo.model.StockSummary;
import com.circulo.model.StockTransaction;
import com.circulo.model.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by tfulton on 6/18/15.
 */
@Component
public class StockSummaryService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public StockSummary calculateSummary(Organization organization) {

        // find the latest/max summary snapshot

        // pull the stock transactions created since the snapshot above was calculated
        List<StockTransaction> transactionsSince = stockTransactionRepository.findByOrganization(organization);

        // filter through each SKU and add to, or update an existing summary item
        Stream<StockTransaction> txStream = transactionsSince.stream();
//        txStream.


        return null;
    }
}