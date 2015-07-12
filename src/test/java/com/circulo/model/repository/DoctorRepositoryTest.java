package com.circulo.model.repository;

import com.circulo.enums.DoctorLicenseStatus;
import com.circulo.model.Doctor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.circulo.util.TestUtil;

import java.util.List;

/**
 * Created by azim on 7/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class DoctorRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(PatientRepositoryTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testCreate() {
        Doctor doctor = createDocuments();
        Query query = new Query(Criteria.where("_id").is(doctor.getId()));
        List<Doctor> doctors = mongoTemplate.find(query, Doctor.class);
        Assert.assertEquals(1, doctors.size());

        Doctor doctorFound = doctors.get(0);
        checkDoctorData(doctor, doctorFound);
    }

    private void checkDoctorData(Doctor doctor, Doctor doctorFound) {
        Assert.assertNotNull(doctorFound);
        Assert.assertEquals(doctor.getId(), doctorFound.getId());
        Assert.assertEquals(doctor.getAddress(), doctorFound.getAddress());
        Assert.assertEquals(doctor.getCategory(), doctorFound.getCategory());
        Assert.assertEquals(doctor.getEmail(), doctorFound.getEmail());
        Assert.assertEquals(doctor.getFirstName(), doctorFound.getFirstName());
        Assert.assertEquals(doctor.getLastName(), doctorFound.getLastName());
        Assert.assertEquals(doctor.getLicenseExpirationDate(), doctorFound.getLicenseExpirationDate());
        Assert.assertEquals(doctor.getLicenseNo(), doctorFound.getLicenseNo());
        Assert.assertEquals(doctor.getLicenseStatus(), doctorFound.getLicenseStatus());
        Assert.assertEquals(doctor.getMobilePhone(), doctorFound.getMobilePhone());
        Assert.assertEquals(doctor.getOfficePhone(), doctorFound.getOfficePhone());
    }

    private Doctor createDocuments() {
        Doctor doctor = TestUtil.createDoctor();
        doctorRepository.save(doctor);
        return doctor;
    }

    @Test
    public void testFind() {
        Doctor doctor = createDocuments();
        Doctor doctorFound = doctorRepository.findOne(doctor.getId());
        checkDoctorData(doctor, doctorFound);
    }

    @Test
    public void testUpdate() {
        Doctor doctor = createDocuments();
        Doctor doctorFound = doctorRepository.findOne(doctor.getId());
        checkDoctorData(doctor, doctorFound);

        doctor.setAddress(TestUtil.createAddress());
        doctor.setLicenseStatus(DoctorLicenseStatus.EXPIRED);
        doctorRepository.save(doctor);

        doctorFound = doctorRepository.findOne(doctor.getId());
        checkDoctorData(doctor, doctorFound);
    }

    @Test
    public void testDelete() {
        Doctor doctor = createDocuments();
        Doctor doctorFound = doctorRepository.findOne(doctor.getId());
        checkDoctorData(doctor, doctorFound);

        doctorRepository.delete(doctor);
        doctorFound = doctorRepository.findOne(doctor.getId());
        Assert.assertNull(doctorFound);
    }
}
