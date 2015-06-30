package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.OrganizationRepository;
import com.circulo.model.repository.StockTransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BinaryOperator;

import static com.circulo.util.TestUtil.*;
import static java.util.stream.Collectors.*;

/**
 * Created by tfulton on 6/18/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class StockSummaryServiceTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    private List<Product> productList = new ArrayList<>();

    @Before
    public void setup() {

        // create some products each with several variations
        for (int i = 0; i < 3; i++) {
            Product product = createProduct();
            productList.add(product);
        }
    }

    @Test
    public void testCalculateFromZero() {

        // create a new organization
        Organization testOrg = generateOrg();
        organizationRepository.save(testOrg);

        StockSummary summary = new StockSummary();
        summary.setStockItemMap(new HashMap<>());
        List<StockTransaction> stockTransactions = new ArrayList<>();

        // add some stock transaction entries for several products and variations
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));
        for (Product product : productList) {
            for (Variation variation : product.getVariations()) {

                StockItem item = new StockItem();
                item.setAssemblyItemId(UUID.randomUUID().toString());
                item.setNotes(UUID.randomUUID().toString());
                item.setSku(variation.getSku());

                // use the to create summary for this variation
                List<StockTransaction> variationTransactions = new ArrayList<>();

                int itemCount = randomInt(2, 10);
                for (int i = 0; i < itemCount; i++) {
                    StockTransaction transaction = new StockTransaction();
                    transaction.setCount(1);
                    transaction.setCreatedAt(startTime);
                    transaction.setId(UUID.randomUUID().toString());
                    transaction.setLocationFrom(null);
                    transaction.setLocationTo(null);
                    transaction.setNotes(UUID.randomUUID().toString());
                    transaction.setOrganization(testOrg);
                    transaction.setSku(variation.getSku());
                    transaction.setType(StockTransaction.StockTransactionType.PROCUREMENT);
                    transaction.setUnitOfMeasure(UUID.randomUUID().toString());
                    transaction.setUnitCost(randomBigDecial(1, 10));
                    transaction.setTax(new BigDecimal(0.8).multiply(transaction.getUnitCost()));
                    transaction.setUserId(UUID.randomUUID().toString());
                    transaction.calculateGrossValue();

                    stockTransactionRepository.save(transaction);

                    variationTransactions.add(transaction);
                    stockTransactions.add(transaction);
                }

                // item count on hand
                int variationCount = variationTransactions.stream()
                        .mapToInt(StockTransaction::getCount).sum();
                int variationCount2 = variationTransactions.stream()
                        .collect(summingInt(StockTransaction::getCount));

                Assert.assertEquals(itemCount, variationCount);
                Assert.assertEquals(itemCount, variationCount2);

                item.setCount(variationCount);

                // item total cost
                double variationUnitCostSum = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost).mapToDouble(BigDecimal::doubleValue)
                        .sum();

                BigDecimal variationUnitCostSum2 = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal variationUnitCostSum3 = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost)
                        .collect(reducing(BigDecimal.ZERO, BigDecimal::add));

                Assert.assertEquals(variationUnitCostSum2, variationUnitCostSum3);

                item.setCost(new BigDecimal(variationUnitCostSum));

                // item total tax
                double variationTaxSum = variationTransactions.stream()
                        .map(StockTransaction::getTax).mapToDouble(BigDecimal::doubleValue)
                        .sum();
                item.setTax(new BigDecimal(variationTaxSum));

                // add to the summary
                summary.getStockItemMap().put(item.getSku(), item);
            }
        }

        // calculate the stock summary using the transactions above
        Map<String, List<StockTransaction>> skuMap = stockTransactions.stream().collect(groupingBy(StockTransaction::getSku));

        // calculate the stock summary using the service

        // compare the two

    }

    @Test
    public void testCalculateFromExisting() {

    }
}
