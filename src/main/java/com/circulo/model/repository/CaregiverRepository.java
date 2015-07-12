package com.circulo.model.repository;

import com.circulo.model.Caregiver;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 7/12/15.
 */
public interface CaregiverRepository extends MongoRepository<Caregiver, String> {
}
