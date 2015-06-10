package com.circulo.model.repository;

import com.circulo.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 6/9/15.
 */
public interface SupplierRepository extends MongoRepository<Supplier, String> {
}
