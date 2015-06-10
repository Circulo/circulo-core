package com.circulo.model.repository;

import com.circulo.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 6/10/15.
 */
public interface CategoryRepository extends MongoRepository<Category, String> {
}
