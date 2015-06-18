package com.circulo.model.repository;

import com.circulo.model.Assembly;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 6/18/15.
 */
public interface AssemblyRepository extends MongoRepository<Assembly, String> {
}
