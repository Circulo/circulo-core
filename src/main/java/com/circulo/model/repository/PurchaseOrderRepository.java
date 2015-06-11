package com.circulo.model.repository;

import com.circulo.model.PurchaseOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tfulton on 6/11/15.
 */
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {
}
