package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.StockSummaryRepository;
import com.circulo.model.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static com.circulo.model.StockTransaction.StockTransactionType.COMMITTMENT;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * Created by tfulton on 6/18/15.
 */
@Service("stockSummaryService")
public class StockSummaryServiceImpl
        implements StockSummaryService {

    @Autowired
    private StockSummaryRepository stockSummaryRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Override
    public StockSummary getCurrentSummary(Organization organization) {

        // pull the last stock summary
        // TODO:  pull the last stock summary by date
        StockSummary summary = new StockSummary();

        // pull the stock transactions for the org from the last summary calculation date
        // TODO:  pull the stock transactions by date
        List<StockTransaction> transactions = stockTransactionRepository.findByOrganization(organization);

        // group the transactions grouped by skuk and transaction type
        Map<String, Map<StockTransaction.StockTransactionType, List<StockTransaction>>> transactionSkuMap = transactions.stream()
                .collect(groupingBy(StockTransaction::getSku,
                        groupingBy(StockTransaction::getType)));

        // on hand stock changes by sku
        transactionSkuMap.forEach((sku, map) -> {

            StockItem item = summary.getStockItemMap().compute(sku, (k, stockItem) -> {
                if (stockItem != null) {
                    return stockItem;
                } else {
                    stockItem = new StockItem();
                    stockItem.setProduct(map.values().stream().findFirst().get().get(0).getProduct());
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
        });

        // set date, id and save
        summary.setId(null);
        summary.setCalculatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        stockSummaryRepository.save(summary);

        return summary;
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
