package com.circulo.model.repository;

import com.circulo.model.Member;
import com.circulo.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 7/11/15.
 */
public interface PatientRepository extends MongoRepository<Patient, String> {
}
