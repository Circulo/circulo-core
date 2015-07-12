package com.circulo.model.repository;

import com.circulo.enums.DoctorLicenseStatus;
import com.circulo.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by azim on 7/11/15.
 */
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    public List<Doctor> findByFirstName(String firstName);

    public List<Doctor> findByLastName(String lastName);

    public List<Doctor> findByLicenseNo(String licenseNo);

    public List<Doctor> findByCategory(String category);

    public List<Doctor> findByLicenseStatus(DoctorLicenseStatus licenseStatus);

    public List<Doctor> findByEmail(String email);

    public List<Doctor> findByOfficePhone(String officePhone);

    public List<Doctor> findByMobilePhone(String mobilePhone);
}
