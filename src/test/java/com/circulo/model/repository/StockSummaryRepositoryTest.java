package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.StockItem;
import com.circulo.model.StockSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.circulo.util.TestUtil.*;

/**
 * Created by tfulton on 6/18/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class StockSummaryRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StockSummaryRepository stockSummaryRepository;

    @Test
    public void testSaveBasicSummary() {

        Organization testOrg = generateOrg();
        organizationRepository.save(testOrg);

        StockSummary snapshot = new StockSummary();
        snapshot.setCalculatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        snapshot.setId(UUID.randomUUID().toString());
        snapshot.setOrganization(testOrg);
        snapshot.setStockItems(generateStockItems());
        stockSummaryRepository.save(snapshot);

        StockSummary foundSnapshot = mongoTemplate.findById(snapshot.getId(), StockSummary.class);

        Assert.assertEquals(snapshot.getCalculatedAt(), foundSnapshot.getCalculatedAt());
        Assert.assertEquals(snapshot.getId(), foundSnapshot.getId());
        Assert.assertEquals(snapshot.getOrganization(), foundSnapshot.getOrganization());
        Assert.assertEquals(snapshot.getStockItems(), foundSnapshot.getStockItems());
    }

    public static List<StockItem> generateStockItems() {

        List<StockItem> items = new ArrayList<>();
        for (int i=0; i < 10; i++) {

            StockItem item = new StockItem();
            item.setAssemblyItemId(UUID.randomUUID().toString());
            item.setCount(randomInt(10, 100));
            item.setNotes(UUID.randomUUID().toString());
            item.setSku(UUID.randomUUID().toString());
            item.setValuation(new BigDecimal(item.getCount()* new Random().nextDouble()*10));
        }

        return items;
    }
}
