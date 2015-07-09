package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.StockItem;
import com.circulo.model.StockSummary;
import com.circulo.util.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.circulo.util.TestUtil.createOrganization;
import static com.circulo.util.TestUtil.randomInt;
import static java.util.stream.Collectors.*;

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

    @Test
    public void testFindByOrgOrdered() {

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        // create several summaries manually
        LocalDateTime date = DateUtils.getUtcNow();
        for (int i=0; i < 10; i++) {

            StockSummary snapshot = new StockSummary();
            snapshot.setCalculatedAt(date);
            snapshot.setId(UUID.randomUUID().toString());
            snapshot.setOrganization(testOrg);
            snapshot.setStockItemMap(generateStockItems());
            stockSummaryRepository.save(snapshot);

            date = date.plusDays(10);
        }

        List<StockSummary> orderedSummaries = stockSummaryRepository.findByOrganizationOrderByCalculatedAtDesc(testOrg);
        StockSummary lastSummary = null;
        for (StockSummary summary : orderedSummaries) {
            if (lastSummary != null) {
                Assert.assertTrue(summary.getCalculatedAt().compareTo(lastSummary.getCalculatedAt()) < 0);
            }
        }
    }

    @Test
    public void testFindByOrgAndMaxDate() {

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        // create several summaries manually
        LocalDateTime date = DateUtils.getUtcNow();
        for (int i=0; i < 10; i++) {

            StockSummary snapshot = new StockSummary();
            snapshot.setCalculatedAt(date);
            snapshot.setId(UUID.randomUUID().toString());
            snapshot.setOrganization(testOrg);
            snapshot.setStockItemMap(generateStockItems());
            stockSummaryRepository.save(snapshot);

            date = date.plusDays(10);
        }

        List<StockSummary> summaries = stockSummaryRepository.findByOrganization(testOrg);
        StockSummary latestSummary = summaries.stream().sorted((o1, o2) ->
                        o2.getCalculatedAt().compareTo(o1.getCalculatedAt())
        ).collect(toList()).get(0);

        StockSummary summary = stockSummaryRepository.findFirstByOrganization(testOrg, new Sort(Sort.Direction.DESC, "calculatedAt"));
        Assert.assertEquals(latestSummary, summary);
    }

    @Test
    public void testFindByOrgAndMaxDate2() {

        Organization testOrg = createOrganization();
        organizationRepository.save(testOrg);

        // create several summaries manually
        LocalDateTime date = DateUtils.getUtcNow();
        for (int i=0; i < 10; i++) {

            StockSummary snapshot = new StockSummary();
            snapshot.setCalculatedAt(date);
            snapshot.setId(UUID.randomUUID().toString());
            snapshot.setOrganization(testOrg);
            snapshot.setStockItemMap(generateStockItems());
            stockSummaryRepository.save(snapshot);

            date = date.plusDays(10);
        }

        List<StockSummary> summaries = stockSummaryRepository.findByOrganizationOrderByCalculatedAtDesc(testOrg);
        StockSummary latestSummary = summaries.get(0);

        StockSummary summary = stockSummaryRepository.findFirstByOrganizationOrderByCalculatedAtDesc(testOrg);
        Assert.assertEquals(latestSummary, summary);
    }

    public static Map<String, StockItem> generateStockItems() {

        Map<String, StockItem> stockItemMap = new HashMap<>();
        for (int i=0; i < 10; i++) {

            StockItem item = new StockItem();
            item.setOnHand(randomInt(10, 100));
            item.setNotes(UUID.randomUUID().toString());
            item.setSku(UUID.randomUUID().toString());
            item.setCost(new BigDecimal(item.getOnHand() * new Random().nextDouble() * 10));
            stockItemMap.put(item.getSku(), item);
        }

        return stockItemMap;
    }
}
