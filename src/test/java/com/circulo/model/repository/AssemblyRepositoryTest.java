package com.circulo.model.repository;

import com.circulo.model.Assembly;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.circulo.util.TestUtil.createAssembly;
import static com.circulo.util.TestUtil.createAssemblyItem;

/**
 * Created by azim on 6/28/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class AssemblyRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AssemblyRepository assemblyRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StockLocationRepository stockLocationRepository;

    @Test
    public void testCreate() {
        Assembly assembly = createDocuments();
        Query query = new Query(Criteria.where("_id").is(assembly.getId()));
        List<Assembly> assemblies = mongoTemplate.find(query, Assembly.class);

        Assert.assertEquals(1, assemblies.size());

        Assembly assemblyFound = assemblies.get(0);
        checkAssemblyData(assembly, assemblyFound);
    }

    private void checkAssemblyData(Assembly assembly, Assembly assemblyFound) {
        Assert.assertNotNull(assembly);
        Assert.assertNotNull(assemblyFound);

        Assert.assertEquals(assembly.getId(), assemblyFound.getId());
        Assert.assertEquals(assembly.getOrganization(), assemblyFound.getOrganization());
        Assert.assertEquals(assembly.getStockLocation(), assemblyFound.getStockLocation());
        Assert.assertEquals(assembly.getCreatedAt(), assemblyFound.getCreatedAt());
        Assert.assertEquals(assembly.getUpdatedAt(), assemblyFound.getUpdatedAt());

        Assert.assertEquals(assembly.getAssemblyInput().size(), assemblyFound.getAssemblyInput().size());
        Assert.assertTrue(assembly.getAssemblyInput().containsAll(assemblyFound.getAssemblyInput()));
        Assert.assertTrue(assemblyFound.getAssemblyInput().containsAll(assembly.getAssemblyInput()));

        Assert.assertEquals(assembly.getAssemblyOutput().size(), assemblyFound.getAssemblyOutput().size());
        Assert.assertTrue(assembly.getAssemblyOutput().containsAll(assemblyFound.getAssemblyOutput()));
        Assert.assertTrue(assemblyFound.getAssemblyOutput().containsAll(assembly.getAssemblyOutput()));

        Assert.assertEquals(assembly.getLeftOverAssemblyInput().size(), assemblyFound.getLeftOverAssemblyInput().size());
        Assert.assertTrue(assembly.getLeftOverAssemblyInput().containsAll(assemblyFound.getLeftOverAssemblyInput()));
        Assert.assertTrue(assemblyFound.getLeftOverAssemblyInput().containsAll(assembly.getLeftOverAssemblyInput()));

        Assert.assertEquals(assembly.getLostAssemblyInput().size(), assemblyFound.getLostAssemblyInput().size());
        Assert.assertTrue(assembly.getLostAssemblyInput().containsAll(assemblyFound.getLostAssemblyInput()));
        Assert.assertTrue(assemblyFound.getLostAssemblyInput().containsAll(assembly.getLostAssemblyInput()));
    }

    private Assembly createDocuments() {
        Assembly assembly = createAssembly();
        organizationRepository.save(assembly.getOrganization());
        organizationRepository.save(assembly.getStockLocation().getOrganization());
        stockLocationRepository.save(assembly.getStockLocation());
        assemblyRepository.save(assembly);
        return assembly;
    }

    @Test
    public void testFind() {
        Assembly assembly = createDocuments();
        Assembly assemblyFound = assemblyRepository.findOne(assembly.getId());
        checkAssemblyData(assembly, assemblyFound);
    }

    @Test
    public void testUpdate() {
        Assembly assembly = createDocuments();
        Assembly assemblyFound = assemblyRepository.findOne(assembly.getId());
        checkAssemblyData(assembly, assemblyFound);

        assembly.getAssemblyInput().add(createAssemblyItem());
        assembly.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        assemblyRepository.save(assembly);

        assemblyFound = assemblyRepository.findOne(assembly.getId());
        Assert.assertEquals(6, assemblyFound.getAssemblyInput().size());
        checkAssemblyData(assembly, assemblyFound);
    }

    @Test
    public void testDelete() {
        Assembly assembly = createDocuments();
        Assembly assemblyFound = assemblyRepository.findOne(assembly.getId());
        checkAssemblyData(assembly, assemblyFound);

        assemblyRepository.delete(assembly);
        assemblyFound = assemblyRepository.findOne(assembly.getId());
        Assert.assertNull(assemblyFound);
    }
}
