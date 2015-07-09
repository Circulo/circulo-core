package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.ProductRepository;
import com.circulo.model.repository.StockSummaryRepository;
import com.circulo.model.repository.StockTransactionRepository;
import com.circulo.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.circulo.model.StockTransaction.StockTransactionType.COMMITTMENT;
import static java.util.stream.Collectors.*;

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
        List<Product> products = productRepository.findByOrganization(organization);
        products.stream().map(Product::getVariations).flatMap(Variation::getSku).collect(toList())

        // pull the last stock summary
        StockSummary summary = getLatestSummary(organization);

        // pull the stock transactions for the org from the last summary calculation date
        List<StockTransaction> transactions = getLatestStockTransactions(organization, summary);

        // group the transactions grouped by sku and transaction type
        Map<String, Map<StockTransaction.StockTransactionType, List<StockTransaction>>> transactionSkuMap = transactions.stream()
                .collect(groupingBy(StockTransaction::getSku,
                        groupingBy(StockTransaction::getType)));

        // on hand stock changes by sku
        StockSummary newSummary = new StockSummary();
        Map<String, StockItem> stockItemMap = new HashMap<>();
        transactionSkuMap.forEach((sku, map) -> {

            StockItem item = summary.getStockItemMap().compute(sku, (k, stockItem) -> {
                if (stockItem != null) {
                    return stockItem;
                } else {
                    stockItem = new StockItem();
                    stockItem.setProductId(map.values().stream().findFirst().get().get(0).getProductId());
                    stockItem.setSku(sku);
                    return stockItem;
                }
            });

            if (map.containsKey(COMMITTMENT)) {  // determine stock commitments
                // TODO: should these be time sensitive?
                // calcuate the committed amounts for the sku
                List<StockTransaction> commitList = map.get(COMMITTMENT);
                if (commitList.size() > 0) { // quick check for efficiency
                    Integer netChange = commitList.stream().mapToInt(StockTransaction::getCount)
                            .reduce(0, (a, b) -> a + b);
                    item.setCommitted(item.getCommitted() + netChange);
                }

                // remove the committed items so its easier to deal with the rest
                map.remove(COMMITTMENT);

            } else { // determine other changes
                map.forEach((type, list) -> {
                    Integer netChange = list.stream().mapToInt(StockTransaction::getCount)
                            .reduce(0, (a, b) -> a + b);
                    item.setOnHand(item.getOnHand() + netChange);
                });
            }

            stockItemMap.put(sku, item);
        });

        // set date, id and save
        newSummary.setOrganization(organization);
        newSummary.setStockItemMap(stockItemMap);
        newSummary.setCalculatedAt(DateUtils.getUtcNow());
        stockSummaryRepository.save(newSummary);

        return summary;
    }

    private StockSummary getLatestSummary(Organization organization) {

        StockSummary summary = stockSummaryRepository.findFirstByOrganizationOrderByCalculatedAtDesc(organization);
        return summary != null ? summary : new StockSummary();
    }

    private List<StockTransaction> getLatestStockTransactions(Organization organization, StockSummary summary) {

        if (summary.getCalculatedAt() == null) {
            return stockTransactionRepository.findByOrganization(organization);
        }
        else {
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
