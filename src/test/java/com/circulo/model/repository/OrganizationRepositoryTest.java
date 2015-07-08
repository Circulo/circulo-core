package com.circulo.model.repository;

import com.circulo.model.Organization;
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

import java.util.List;
import java.util.UUID;

import static com.circulo.util.TestUtil.createOrganization;

/**
 * Created by tfulton on 5/24/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class OrganizationRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void testCreate() {

        Organization org = createOrganization();
        organizationRepository.save(org);

        Query query = new Query(Criteria.where("_id").is(org.getId()));
        List<Organization> orgs = mongoTemplate.find(query, Organization.class);

        Assert.assertEquals(1, orgs.size());
        Assert.assertEquals(org.getId(), orgs.get(0).getId());
        Assert.assertEquals(org.getName(), orgs.get(0).getName());
        Assert.assertEquals(org.getDescription(), orgs.get(0).getDescription());
    }

    @Test
    public void testFind() {

        Organization org = createOrganization();
        organizationRepository.save(org);

        Organization copy = organizationRepository.findOne(org.getId());
        Assert.assertEquals(org.getId(), copy.getId());
        Assert.assertEquals(org.getName(), copy.getName());
        Assert.assertEquals(org.getDescription(), copy.getDescription());
    }

    @Test
    public void testUpdate() {

        Organization org = createOrganization();
        organizationRepository.save(org);

        Organization copy = organizationRepository.findOne(org.getId());
        Assert.assertEquals(org.getId(), copy.getId());
        Assert.assertEquals(org.getName(), copy.getName());
        Assert.assertEquals(org.getDescription(), copy.getDescription());

        org.setName(UUID.randomUUID().toString());
        org.setDescription(UUID.randomUUID().toString());
        organizationRepository.save(org);

        copy = organizationRepository.findOne(org.getId());
        Assert.assertEquals(org.getId(), copy.getId());
        Assert.assertEquals(org.getName(), copy.getName());
        Assert.assertEquals(org.getDescription(), copy.getDescription());
    }

    @Test
    public void testDelete() {

        Organization org = createOrganization();
        organizationRepository.save(org);

        Organization copy = organizationRepository.findOne(org.getId());
        Assert.assertEquals(org.getId(), copy.getId());
        Assert.assertEquals(org.getName(), copy.getName());
        Assert.assertEquals(org.getDescription(), copy.getDescription());

        organizationRepository.delete(org);
        copy = organizationRepository.findOne(org.getId());
        Assert.assertNull(copy);
    }

    @Test
    public void testFindByName() {

        Organization org = createOrganization();
        organizationRepository.save(org);

        List<Organization> orgs = organizationRepository.findOrganizationByName(org.getName());
        Assert.assertEquals(1, orgs.size());

        Organization copy = orgs.get(0);
        Assert.assertEquals(org.getId(), copy.getId());
        Assert.assertEquals(org.getName(), copy.getName());
        Assert.assertEquals(org.getDescription(), copy.getDescription());
    }

    @Test
    public void testFindByDesc() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Organization org = generateOrg();
        organizationRepository.save(org);

        List<Organization> orgs = organizationRepository.findByDescription(org.getDescription());
        Assert.assertEquals(1, orgs.size());
    }
}

