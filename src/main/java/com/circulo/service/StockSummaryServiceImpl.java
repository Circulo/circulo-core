package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.ProductRepository;
import com.circulo.model.repository.StockSummaryRepository;
import com.circulo.model.repository.StockTransactionRepository;
import com.circulo.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by tfulton on 6/18/15.
 */
@Service("stockSummaryService")
public class StockSummaryServiceImpl
        implements StockSummaryService {

    Logger logger = LoggerFactory.getLogger(StockSummaryService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockSummaryRepository stockSummaryRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Override
    public StockSummary getCurrentSummary(Organization organization) {

        // get the products for this org
        Map<String, Product> skuProductMap = new HashMap<>();
        productRepository.findByOrganization(organization).stream().forEach(prod -> {
            prod.getVariations().stream().forEach(var -> {
                skuProductMap.put(var.getSku(), prod);
            });
        });

        // pull the last stock summary
        StockSummary summary = getLatestSummary(organization);

        // pull the stock transactions for the org from the last summary calculation date
        List<StockTransaction> transactions = getLatestStockTransactions(organization, summary);

        // group the transactions by sku
        Map<String, List<StockTransaction>> transactionsBySku = transactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // iterate through the transaction map and adjust/create stock items where necessary
        transactionsBySku.forEach((sku, list) -> {

            Product prod = skuProductMap.get(sku);
            StockItem item = summary.getStockItemMap().compute(sku, (k, stockItem) -> {
                if (stockItem != null) {
                    return stockItem;
                } else {
                    stockItem = new StockItem();
                    stockItem.setProduct(prod);
                    stockItem.setSku(sku);
                    summary.getStockItemMap().put(sku, stockItem);
                    return stockItem;
                }
            });

            list.stream().forEach(tx -> {
                processTransaction(tx, item);
            });
        });

        // set date, id and save
        summary.setCalculatedAt(DateUtils.getUtcNow());
        summary.setId(null);
        summary.setOrganization(organization);
        stockSummaryRepository.save(summary);

        return summary;
    }

    private void processTransaction(StockTransaction transaction, StockItem item) {

        switch (transaction.getType()) {

            case ADJUSTMENT_NEGATIVE:
                item.setOnHand(item.getOnHand() - transaction.getCount());
                break;
            case ADJUSTMENT_POSITIVE:
                item.setOnHand(item.getOnHand() + transaction.getCount());
                break;
            case COMMITTMENT:
                item.setCommitted(item.getCommitted() + transaction.getCount());
                break;
            case PROCUREMENT:
                item.setOnHand(item.getOnHand() + transaction.getCount());
                break;
            case SALE:
                item.setOnHand(item.getOnHand() - transaction.getCount());
                break;
            case TRANSFER:

                break;
            default:
                throw new IllegalArgumentException("Stock Transaction type not recognized.");
        }
    }

    private StockSummary getLatestSummary(Organization organization) {

        StockSummary summary = stockSummaryRepository.findFirstByOrganizationOrderByCalculatedAtDesc(organization);
        return summary != null ? summary : new StockSummary();
    }

    private List<StockTransaction> getLatestStockTransactions(Organization organization, StockSummary summary) {

        if (summary.getCalculatedAt() == null) {
            return stockTransactionRepository.findByOrganization(organization);
        } else {
            return stockTransactionRepository.findByOrganizationAndCreatedAtGreaterThan(organization, summary.getCalculatedAt());
        }
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
