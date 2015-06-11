package com.circulo.model.repository;

import com.circulo.model.Product;
import com.circulo.model.StockTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tfulton on 6/11/15.
 */
public interface StockTransactionRepository extends MongoRepository<StockTransaction, String> {
}
