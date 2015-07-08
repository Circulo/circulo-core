package com.circulo.model.repository;

import com.circulo.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by tfulton on 5/23/15.
 */
public interface OrganizationRepository
    extends MongoRepository<Organization, String> {

    @Query("{ 'name' : ?0 }")
    public List<Organization> findOrganizationByName(String name);
}
