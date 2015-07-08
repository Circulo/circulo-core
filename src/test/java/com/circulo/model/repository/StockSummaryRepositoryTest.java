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
import java.util.*;

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

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        // create a stock summary manually
        StockSummary snapshot = new StockSummary();
        snapshot.setCalculatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        snapshot.setId(UUID.randomUUID().toString());
        snapshot.setOrganization(testOrg);
        snapshot.setStockItemMap(generateStockItems());
        stockSummaryRepository.save(snapshot);

        StockSummary foundSnapshot = mongoTemplate.findById(snapshot.getId(), StockSummary.class);

        Assert.assertEquals(snapshot.getCalculatedAt(), foundSnapshot.getCalculatedAt());
        Assert.assertEquals(snapshot.getId(), foundSnapshot.getId());
        Assert.assertEquals(snapshot.getOrganization(), foundSnapshot.getOrganization());
        Assert.assertEquals(snapshot.getStockItemMap(), foundSnapshot.getStockItemMap());
    }

    public static Map<String, StockItem> generateStockItems() {

        Map<String, StockItem> stockItemMap = new HashMap<>();
        for (int i=0; i < 10; i++) {

            StockItem item = new StockItem();
            item.setAssemblyItemId(UUID.randomUUID().toString());
            item.setOnHand(randomInt(10, 100));
            item.setNotes(UUID.randomUUID().toString());
            item.setSku(UUID.randomUUID().toString());
            item.setCost(new BigDecimal(item.getOnHand() * new Random().nextDouble() * 10));
            stockItemMap.put(item.getSku(), item);
        }

        return stockItemMap;
    }
}
