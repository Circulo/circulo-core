package com.circulo.service;

import com.circulo.model.*;
import com.circulo.model.repository.CategoryRepository;
import com.circulo.model.repository.OrganizationRepository;
import com.circulo.model.repository.ProductRepository;
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
import static com.circulo.model.StockTransaction.StockTransactionType.*;
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

    private Organization organization;

    @Before
    public void setup() {

        // create a new organization
        organization = createOrganization();
        organizationRepository.save(organization);
        
        // create some products each with several variations
        for (int i = 0; i < 1; i++) {

            Product product = createProduct(organization);
            categoryRepository.save(product.getCategory());
            productRepository.save(product);
            productList.add(product);
        }
    }

    @Test
    public void testCalculateFromZero() {

        // keep track of the test transactions we create
        List<StockTransaction> stockTransactions = createTransactions(organization, productList);

        // now go get our summary and do some validations
        StockSummary summary = stockSummaryService.getCurrentSummary(organization);

        // group the transactions by sku and calculate the count of items in the sku group
        Map<String, List<StockTransaction>> transactionsBySku = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // validate
        summary.getStockItemMap().values().forEach(item -> {

            Integer testCount = transactionsBySku.get(item.getSku()).stream()
                    .mapToInt(StockTransaction::getCount).sum();
            Assert.assertTrue(item.getOnHand().compareTo(testCount) == 0);

            // make sure products match in the test transactions sku map
            Assert.assertEquals(getSkuProductMap(organization).get(item.getSku()),
                    item.getProduct());
        });
    }

    @Test
    public void testAddInventory()
        throws Exception {

        // keep track of the test transactions we create
        List<StockTransaction> stockTransactions = createTransactions(organization, productList);

        // now go get our summary and do some validations
        StockSummary summary = stockSummaryService.getCurrentSummary(organization);

        // group the transactions by sku
        Map<String, List<StockTransaction>> transactionsBySku = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // validate
        summary.getStockItemMap().values().forEach(item -> {

            Integer testCount = transactionsBySku.get(item.getSku()).stream()
                    .mapToInt(StockTransaction::getCount).sum();
            Assert.assertTrue(item.getOnHand().compareTo(testCount) == 0);

            // make sure products match in the test transactions sku map
            Assert.assertEquals(getSkuProductMap(organization).get(item.getSku()),
                    item.getProduct());
        });

        ///////////////////////////////////////
        // DO IT AGAIN!!!
        ///////////////////////////////////////
        Thread.sleep(1000);

        // keep track of the test transactions we create
        List<StockTransaction> newStockTransactions = createTransactions(organization, productList);

        // now go get our summary and do some validations
        StockSummary secondSummary = stockSummaryService.getCurrentSummary(organization);

        // group the transactions by sku
        newStockTransactions.addAll(stockTransactions);
        Map<String, List<StockTransaction>> newTransactionsBySku = newStockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // validate
        secondSummary.getStockItemMap().values().forEach(item -> {

            Integer testCount = newTransactionsBySku.get(item.getSku()).stream()
                    .mapToInt(StockTransaction::getCount).sum();
            Assert.assertTrue(item.getOnHand().compareTo(testCount) == 0);

            // make sure products match in the test transactions sku map
            Assert.assertEquals(getSkuProductMap(organization).get(item.getSku()),
                    item.getProduct());
        });
    }

    @Test
    public void testRemoveInventory()
            throws Exception {

        // keep track of the test transactions we create
        List<StockTransaction> stockTransactions = createTransactions(organization, productList);

        // now go get our summary and do some validations
        StockSummary summary = stockSummaryService.getCurrentSummary(organization);

        // group the transactions by sku
        Map<String, List<StockTransaction>> transactionsBySku = stockTransactions.stream()
                .collect(groupingBy(StockTransaction::getSku));

        // validate
        summary.getStockItemMap().values().forEach(item -> {

            Integer testCount = transactionsBySku.get(item.getSku()).stream()
                    .mapToInt(StockTransaction::getCount).sum();
            Assert.assertTrue(item.getOnHand().compareTo(testCount) == 0);

            // make sure products match in the test transactions sku map
            Assert.assertEquals(getSkuProductMap(organization).get(item.getSku()),
                    item.getProduct());
        });

        ///////////////////////////////////////
        // DO IT AGAIN!!!
        ///////////////////////////////////////
        Thread.sleep(1000);

        // create purchase transaction (subtract from inventory)
        StockTransaction purchaseTx = createPurchase(organization, productList.get(0).getVariations().get(0));

        // now go get our summary and do some validations
        StockSummary secondSummary = stockSummaryService.getCurrentSummary(organization);

        // validate
        StockItem item = summary.getStockItemMap().get(purchaseTx.getSku());
        StockItem updatedItem = secondSummary.getStockItemMap().get(purchaseTx.getSku());
        Assert.assertTrue(new Integer(item.getOnHand() - purchaseTx.getCount()).compareTo(updatedItem.getOnHand()) == 0);
    }

    @Test
    public void testManyTransactions() {

        Product myProduct = productList.get(0);
        Variation variation = myProduct.getVariations().get(0);

        // ongoing count
        Integer onHand = 0;

        // create a series of different transactions and check as we go
        for (int i=0; i < 100; i++) {

            // add an an item count and check
            StockTransaction transaction = createTransaction(organization, PROCUREMENT, variation.getSku(), 10);
            onHand = onHand + transaction.getCount();
            stockTransactionRepository.save(transaction);
        }

        // get the summary, validate
        StockSummary summary = stockSummaryService.getCurrentSummary(organization);
        StockItem stockItem = summary.getStockItemMap().get(variation.getSku());
        Assert.assertEquals(onHand, stockItem.getOnHand());

        // create a series of different transactions and check as we go
        for (int i=0; i < 100; i++) {

            // add an an item count and check
            StockTransaction transaction = createTransaction(organization, SALE, variation.getSku(), 1);
            onHand = onHand - transaction.getCount();
            stockTransactionRepository.save(transaction);
        }

        summary = stockSummaryService.getCurrentSummary(organization);
        stockItem = summary.getStockItemMap().get(variation.getSku());
        Assert.assertEquals(onHand, stockItem.getOnHand());

        // create a series of different transactions and check as we go
        for (int i=0; i < 100; i++) {

            // add an an item count and check
            StockTransaction transaction = createTransaction(organization, ADJUSTMENT_POSITIVE, variation.getSku(), 3);
            onHand = onHand + transaction.getCount();
            stockTransactionRepository.save(transaction);
        }

        summary = stockSummaryService.getCurrentSummary(organization);
        stockItem = summary.getStockItemMap().get(variation.getSku());
        Assert.assertEquals(onHand, stockItem.getOnHand());

        // create a series of different transactions and check as we go
        for (int i=0; i < 100; i++) {

            // add an an item count and check
            StockTransaction transaction = createTransaction(organization, ADJUSTMENT_NEGATIVE, variation.getSku(), 5);
            onHand = onHand - transaction.getCount();
            stockTransactionRepository.save(transaction);
        }

        summary = stockSummaryService.getCurrentSummary(organization);
        stockItem = summary.getStockItemMap().get(variation.getSku());
        Assert.assertEquals(onHand, stockItem.getOnHand());

        // create a series of different transactions and check as we go
        Integer committed = 0;
        for (int i=0; i < 100; i++) {

            // add an an item count and check
            StockTransaction transaction = createTransaction(organization, COMMITTMENT, variation.getSku(), 2);
//            onHand = onHand - transaction.getCount();
            committed = committed + transaction.getCount();
            stockTransactionRepository.save(transaction);
        }

        summary = stockSummaryService.getCurrentSummary(organization);
        stockItem = summary.getStockItemMap().get(variation.getSku());
        Assert.assertEquals(onHand, stockItem.getOnHand());
        Assert.assertEquals(committed, stockItem.getCommitted());

        Integer available = onHand - committed;
        Assert.assertEquals(available, stockItem.getAvailable());
    }

    private Map<String, Product> getSkuProductMap(Organization organization) {

        // get the products for this org
        List<Product> products = productRepository.findByOrganization(organization);
        Map<String, Product> skuProductMap = new HashMap<>();
        products.stream().forEach(prod -> {
            prod.getVariations().stream().forEach(var -> {
                skuProductMap.put(var.getSku(), prod);
            });
        });

        return skuProductMap;
    }

    private StockTransaction createPurchase(Organization organization, Variation variation) {

        StockTransaction transaction = new StockTransaction();
        transaction.setCount(1);
        transaction.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        transaction.setId(UUID.randomUUID().toString());
        transaction.setLocationFrom(null);
        transaction.setLocationTo(null);
        transaction.setNotes(UUID.randomUUID().toString());
        transaction.setOrganization(organization);
        transaction.setSku(variation.getSku());
        transaction.setType(StockTransaction.StockTransactionType.SALE);
        transaction.setUnitOfMeasure(UUID.randomUUID().toString());
        transaction.setUnitCost(randomBigDecial(1, 10));
        transaction.setTax(new BigDecimal(0.8).multiply(transaction.getUnitCost()));
        transaction.setUserId(UUID.randomUUID().toString());
        transaction.calculateGrossValue();
        stockTransactionRepository.save(transaction);

        return transaction;
    }

    private List<StockTransaction> createTransactions(Organization organization, List<Product> productList) {

        // add some stock transaction entries for several products and variations
        List<StockTransaction> stockTransactions = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));
        for (Product product : productList) {
            for (Variation variation : product.getVariations()) {

                // use the to create summary for this variation
                List<StockTransaction> variationTransactions = new ArrayList<>();

//                int itemCount = randomInt(2, 10);
                int itemCount = 1; //
                for (int i = 0; i < itemCount; i++) {
                    StockTransaction transaction = createTransaction(organization, PROCUREMENT, variation.getSku(), 1);
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

        return stockTransactions;
    }

    private StockTransaction createTransaction(Organization organization, StockTransaction.StockTransactionType type,
                                               String sku, Integer count) {

        StockTransaction transaction = new StockTransaction();
        transaction.setCount(count);
        transaction.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        transaction.setId(UUID.randomUUID().toString());
        transaction.setLocationFrom(null);
        transaction.setLocationTo(null);
        transaction.setNotes(UUID.randomUUID().toString());
        transaction.setOrganization(organization);
        transaction.setSku(sku);
        transaction.setType(type);
        transaction.setUnitOfMeasure(UUID.randomUUID().toString());
        transaction.setUnitCost(randomBigDecial(1, 10));
        transaction.setTax(new BigDecimal(0.8).multiply(transaction.getUnitCost()));
        transaction.setUserId(UUID.randomUUID().toString());
        transaction.calculateGrossValue();

        return transaction;
    }
}
