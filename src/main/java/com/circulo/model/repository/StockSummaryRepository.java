package com.circulo.model.repository;

import com.circulo.model.Organization;
import com.circulo.model.StockSummary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by tfulton on 6/11/15.
 */
public interface StockSummaryRepository extends MongoRepository<StockSummary, String> {

    public List<StockSummary> findByOrganization(Organization organization);

    public List<StockSummary> findByOrganizationOrderByCalculatedAtDesc(Organization organization);

    public StockSummary findFirstByOrganization(Organization organization, Sort sort);

    public StockSummary findFirstByOrganizationOrderByCalculatedAtDesc(Organization organization);

}
