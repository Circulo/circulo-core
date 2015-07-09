package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.CategoryRepository;
import com.circulo.model.repository.OrganizationRepository;
import com.circulo.model.repository.ProductRepository;
import com.circulo.model.repository.StockTransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    private StockSummaryService stockSummaryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    private List<Product> productList = new ArrayList<>();

    @Before
    public void setup() {

        // create some products each with several variations
        for (int i = 0; i < 3; i++) {

            Product product = createProduct();
            categoryRepository.save(product.getCategory());

            productList.add(product);
            productRepository.save(productList);
        }
    }

    @Test
    public void testCalculateFromZero() {

        // create a new organization
        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        // keep track of the test transactions we create
        List<StockTransaction> stockTransactions = new ArrayList<>();

        // add some stock transaction entries for several products and variations
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));
        for (Product product : productList) {
            for (Variation variation : product.getVariations()) {

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
            }
        }

        StockSummary fromServiceSummary = stockSummaryService.getCurrentSummary(testOrg);

        // group the transactions by sku and calculate the count of items in the sku group
        Map<String, List<StockTransaction>> transactionsBySku = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // validate
        fromServiceSummary.getStockItemMap().values().forEach(item -> {

            Integer testCount = transactionsBySku.get(item.getSku()).stream()
                    .mapToInt(StockTransaction::getCount).sum();
            Assert.assertTrue(item.getOnHand().compareTo(testCount) == 0);

            // make sure products match in the test transactions sku map
            Map<Product, List<StockTransaction>> productSkuMap = transactionsBySku.get(item.getSku()).stream()
                    .collect(groupingBy(StockTransaction::getProduct));
            Assert.assertEquals(1, productSkuMap.size());
            Product testProduct = productSkuMap.keySet().iterator().next();


            Assert.assertEquals(testProduct, item.getProduct());
        });
    }

    @Test
    public void testCalculateFromLatest() {

    }
}
