package com.circulo.model.repository;

import com.circulo.model.Procurement;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tfulton on 6/11/15.
 */
public interface ProcurementRepository extends MongoRepository<Procurement, String> {
}
