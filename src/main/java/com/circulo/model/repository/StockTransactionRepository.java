package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.Product;
import com.circulo.model.StockTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tfulton on 6/11/15.
 */
public interface StockTransactionRepository extends MongoRepository<StockTransaction, String> {

    public List<StockTransaction> findByOrganization(Organization organization);

    public List<StockTransaction> findByOrganizationAndCreatedAtLessThan(Organization organization, LocalDateTime dateTime);

    public List<StockTransaction> findByOrganizationAndCreatedAtGreaterThan(Organization organization, LocalDateTime dateTime);
}
