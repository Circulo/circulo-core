package com.circulo.model.repository;

import com.circulo.model.StockLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tfulton on 6/11/15.
 */
public interface StockLocationRepository extends MongoRepository<StockLocation, String> {
}
