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
import java.util.UUID;

import static com.circulo.model.repository.OrganizationRepositoryTest.*;

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

        organization = generateOrg();
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

}
