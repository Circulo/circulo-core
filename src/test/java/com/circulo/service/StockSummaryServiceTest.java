package com.circulo.service;

import com.circulo.model.Organization;
import com.circulo.model.Product;
import com.circulo.model.StockTransaction;
import com.circulo.model.Variation;
import com.circulo.model.repository.OrganizationRepository;
import com.circulo.model.repository.StockTransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.circulo.util.TestUtil.*;

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
        for (int i=0; i < 3; i++) {
            Product product = createProduct();
            productList.add(product);
        }
    }

    @Test
    public void testCalculateFromZero() {

        // create a new organization
        Organization testOrg = generateOrg();
        organizationRepository.save(testOrg);

        List<StockTransaction> stockTransactions = new ArrayList<>();

        // add some stock transaction entries for several products and variations
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("UTC"));
        for (Product product : productList) {
            for (Variation variation: product.getVariations()) {

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
                transaction.setUserId(UUID.randomUUID().toString());
                stockTransactionRepository.save(transaction);

                stockTransactions.add(transaction);
            }
        }

        // calculate the stock summary

        // validate that the summary matches against the transactions

    }

    @Test
    public void testCalculateFromExisting() {

    }
}
