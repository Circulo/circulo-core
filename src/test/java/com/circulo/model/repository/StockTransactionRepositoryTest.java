package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.StockTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.circulo.util.TestUtil.*;

/**
 * Created by tfulton on 6/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class StockTransactionRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    private Organization organization;

    @Before
    public void setup() {

        organization = createOrganization();
        organizationRepository.save(organization);

        organization = organizationRepository.findOne(organization.getId());
        Assert.assertNotNull(organization);
    }


    @Test
    public void testCreate() {

        StockTransaction transaction = new StockTransaction();
        transaction.setCount(1);
        transaction.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        transaction.setId(UUID.randomUUID().toString());
        transaction.setLocationFrom(null);
        transaction.setLocationTo(null);
        transaction.setNotes(UUID.randomUUID().toString());
        transaction.setOrganization(organization);
        transaction.setSku(UUID.randomUUID().toString());
        transaction.setType(StockTransaction.StockTransactionType.PROCUREMENT);
        transaction.setUnitOfMeasure(UUID.randomUUID().toString());
        transaction.setUserId(UUID.randomUUID().toString());
        stockTransactionRepository.save(transaction);

        StockTransaction foundTx = mongoTemplate.findById(transaction.getId(), StockTransaction.class);

        Assert.assertEquals(transaction.getCount(), foundTx.getCount());
        Assert.assertEquals(transaction.getCreatedAt(), foundTx.getCreatedAt());
        Assert.assertEquals(transaction.getId(), foundTx.getId());
        Assert.assertEquals(transaction.getLocationFrom(), foundTx.getLocationFrom());
        Assert.assertEquals(transaction.getLocationTo(), foundTx.getLocationTo());
        Assert.assertEquals(transaction.getNotes(), foundTx.getNotes());
        Assert.assertEquals(transaction.getOrganization(), foundTx.getOrganization());
        Assert.assertEquals(transaction.getSku(), foundTx.getSku());
        Assert.assertEquals(transaction.getType(), foundTx.getType());
        Assert.assertEquals(transaction.getUnitOfMeasure(), foundTx.getUnitOfMeasure());
        Assert.assertEquals(transaction.getUserId(), foundTx.getUserId());
    }

    @Test
    public void testFindByOrganization() {

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        int count = randomInt(10, 30);

        List<StockTransaction> transactions = new ArrayList<StockTransaction>();
        for (int i=0; i < count; i++) {
            StockTransaction transaction = new StockTransaction();
            transaction.setCount(1);
            transaction.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
            transaction.setId(UUID.randomUUID().toString());
            transaction.setLocationFrom(null);
            transaction.setLocationTo(null);
            transaction.setNotes(UUID.randomUUID().toString());
            transaction.setOrganization(testOrg);
            transaction.setSku(UUID.randomUUID().toString());
            transaction.setType(StockTransaction.StockTransactionType.PROCUREMENT);
            transaction.setUnitOfMeasure(UUID.randomUUID().toString());
            transaction.setUserId(UUID.randomUUID().toString());
            stockTransactionRepository.save(transaction);

            transactions.add(transaction);
        }

        List<StockTransaction> foundTransactionList = stockTransactionRepository.findByOrganization(testOrg);
        Assert.assertEquals(count, foundTransactionList.size());

        for(StockTransaction tx : foundTransactionList) {
            Assert.assertTrue(transactions.contains(tx));
        }
    }

    @Test
    public void testFindByDateTime() {

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        int countBefore = randomInt(10, 30);
        int countAfter = randomInt(20, 40);

        LocalDateTime beforeTime = LocalDateTime.of(2015, 1, 1, 10, 00, 00);
        LocalDateTime afterTime = LocalDateTime.of(2015, 1, 1, 12, 00, 00);

        for (int i=0; i < countBefore; i++) {

            LocalDateTime time = beforeTime.plusMinutes(1);
            StockTransaction transaction = new StockTransaction();
            transaction.setCount(1);
            transaction.setCreatedAt(time);
            transaction.setId(UUID.randomUUID().toString());
            transaction.setLocationFrom(null);
            transaction.setLocationTo(null);
            transaction.setNotes(UUID.randomUUID().toString());
            transaction.setOrganization(testOrg);
            transaction.setSku(UUID.randomUUID().toString());
            transaction.setType(StockTransaction.StockTransactionType.PROCUREMENT);
            transaction.setUnitOfMeasure(UUID.randomUUID().toString());
            transaction.setUserId(UUID.randomUUID().toString());
            stockTransactionRepository.save(transaction);
        }

        for (int i=0; i < countAfter; i++) {

            LocalDateTime time = afterTime.plusMinutes(1);
            StockTransaction transaction = new StockTransaction();
            transaction.setCount(1);
            transaction.setCreatedAt(time);
            transaction.setId(UUID.randomUUID().toString());
            transaction.setLocationFrom(null);
            transaction.setLocationTo(null);
            transaction.setNotes(UUID.randomUUID().toString());
            transaction.setOrganization(testOrg);
            transaction.setSku(UUID.randomUUID().toString());
            transaction.setType(StockTransaction.StockTransactionType.PROCUREMENT);
            transaction.setUnitOfMeasure(UUID.randomUUID().toString());
            transaction.setUserId(UUID.randomUUID().toString());
            stockTransactionRepository.save(transaction);
        }

        List<StockTransaction> transactionsByOrg = stockTransactionRepository.findByOrganization(testOrg);
        Assert.assertEquals(countBefore+countAfter, transactionsByOrg.size());

        List<StockTransaction> beforeTransactions =
                stockTransactionRepository.findByOrganizationAndCreatedAtLessThan(testOrg, afterTime);
        Assert.assertEquals(countBefore, beforeTransactions.size());

        List<StockTransaction> afterTransactions =
                stockTransactionRepository.findByOrganizationAndCreatedAtGreaterThan(testOrg, afterTime);
        Assert.assertEquals(countAfter, afterTransactions.size());
    }
}
