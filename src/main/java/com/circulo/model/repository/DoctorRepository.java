package com.circulo.model.repository;

import com.circulo.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by azim on 7/11/15.
 */
public interface DoctorRepository extends MongoRepository<Doctor, String> {
}
