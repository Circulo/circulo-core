package com.circulo.model.repository;

import com.circulo.model.Category;
import com.circulo.model.Product;
import com.circulo.model.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by azim on 6/9/15.
 */
public interface ProductRepository extends MongoRepository<Product, String> {

    public List<Product> findByCategory(Category category);

    public List<Product> findByBrand(String brand);
}
