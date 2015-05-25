package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.util.DateFormat;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

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
    public void testCreateOrganization() {

//        Assert.assertTrue(false);

        String dateStr = DateFormat.getIso8061(new Date());

        Organization organization = new Organization();
        organization.setId(UUID.randomUUID().toString());
        organization.setName("blah blah");
        organization.setDescription(dateStr);

        organizationRepository.save(organization);

        Query query = new Query(Criteria.where("_id").is(organization.getId()));
        List<Organization> orgs = mongoTemplate.find(query, Organization.class);

        Assert.assertEquals(1, orgs.size());
        Assert.assertEquals(organization.getId(), orgs.get(0).getId());
        Assert.assertEquals(organization.getName(), orgs.get(0).getName());
        Assert.assertEquals(organization.getDescription(), orgs.get(0).getDescription());
    }
}

