package com.circulo.service;

import com.circulo.model.Organization;
import com.circulo.model.StockLocation;
import com.circulo.model.StockSummary;
import com.circulo.model.StockTransaction;
import com.circulo.model.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by tfulton on 6/18/15.
 */
@Component
public class StockSummaryServiceImpl
    implements StockSummaryService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Override
    public StockSummary getCurrentSummary(Organization organization) {

        // pull the last stock summary
        // TODO:  pull the last stock summary by date

        // pull the stock transactions for the org from the last summary calculation date
        // TODO:  pull the stock transactions by date
        List<StockTransaction> transactionsSince = stockTransactionRepository.findByOrganization(organization);

        // need a new stock summary object
        StockSummary nextSummary = new StockSummary();

        // calculate on hand inventory summary by product and sku


        // calculate committed inventory summary by product and sku


        // calculate available inventory summary by product and sku


        return null;
    }

    @Override
    public Map<StockLocation, StockSummary> getCurrentSummaryByLocation(Organization organization) {
        return null;
    }

    @Override
    public StockSummary getSummaryByDate(Organization organization) {
        return null;
    }
}
