package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.StockSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by tfulton on 6/11/15.
 */
public interface StockSummaryRepository extends MongoRepository<StockSummary, String> {

    public List<StockSummary> findByOrganization(Organization organization);

}
