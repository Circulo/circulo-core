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
        Organization testOrg = createOrganization();
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
                item.setProduct(product);
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
                    transaction.setProduct(product);
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

                // sum the item count using only the terminal operation "sum"
                int variationCount = variationTransactions.stream()
                        .mapToInt(StockTransaction::getCount).sum();

                // sum the item count using a collector and a summing argument
                int variationCount2 = variationTransactions.stream()
                        .collect(summingInt(StockTransaction::getCount));

                Assert.assertEquals(itemCount, variationCount);
                Assert.assertEquals(itemCount, variationCount2);

                item.setOnHand(variationCount2);

                // sum the transaction unit costs by mapping into double
                double variationUnitCostSum = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost).mapToDouble(BigDecimal::doubleValue)
                        .sum();

                // sum the transaction unit cost using big decimal and reduce
                BigDecimal variationUnitCostSum2 = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // sum the transaction unit cost using big decimal and collector with reduce argument
                BigDecimal variationUnitCostSum3 = variationTransactions.stream()
                        .map(StockTransaction::getUnitCost)
                        .collect(reducing(BigDecimal.ZERO, BigDecimal::add));

                Assert.assertTrue(variationUnitCostSum - variationUnitCostSum2.doubleValue() < 1);
                Assert.assertEquals(variationUnitCostSum2, variationUnitCostSum3);

                item.setCost(new BigDecimal(variationUnitCostSum));

                // add to the summary
                summary.getStockItemMap().put(item.getSku(), item);
            }
        }

        // group the transactions by sku and calculate the count of items in the sku group
        Map<String, Integer> skuItemCount = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku, summingInt(StockTransaction::getCount)));

        // validate
        summary.getStockItemMap().values().forEach(item -> {

            Integer outsideCount = skuItemCount.get(item.getSku());
            Assert.assertTrue(item.getOnHand().compareTo(outsideCount) == 0);
        });

        // group the transactions by product, then sku and calculate the count of items in the product/sku group
        Map<Product, Map<String, Integer>> productSkuItemCount = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getProduct,
                        groupingBy(StockTransaction::getSku, summingInt(StockTransaction::getCount))));

        Assert.assertTrue(true);
    }

    @Test
    public void testCalculateFromExisting() {

    }
}
