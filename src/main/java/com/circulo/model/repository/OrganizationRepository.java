package com.circulo.model.repository;

import com.circulo.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tfulton on 5/23/15.
 */
public interface OrganizationRepository
    extends MongoRepository<Organization, String> {

    public List<Organization> findUsersById(String id);
}
