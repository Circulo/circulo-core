package com.circulo.model.repository;

import com.circulo.enums.MemberType;
import com.circulo.model.Caregiver;
import com.circulo.model.Member;
import com.circulo.model.Patient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
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
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.circulo.util.TestUtil;

/**
 * Created by azim on 7/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class PatientRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(PatientRepositoryTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    GridFsOperations operations;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CaregiverRepository caregiverRepository;

    @Test
    public void testCreate() {
        Patient patient = createDocuments();
        Query query = new Query(Criteria.where("_id").is(patient.getId()));
        List<Patient> patients = mongoTemplate.find(query, Patient.class);

        Assert.assertEquals(1, patients.size());

        Patient patientFound = patients.get(0);
        checkPatientData(patient, patientFound);
    }

    private void checkPatientData(Patient patient, Patient patientFound) {
        Assert.assertNotNull(patientFound);
        Assert.assertEquals(patient.getId(), patientFound.getId());
        Assert.assertEquals(patient.getMember().getFirstName(), patientFound.getMember().getFirstName());
        Assert.assertEquals(patient.getMember().getMiddleInitial(), patientFound.getMember().getMiddleInitial());
        Assert.assertEquals(patient.getMember().getLastName(), patientFound.getMember().getLastName());
        Assert.assertEquals(patient.getMember().getAddress(), patientFound.getMember().getAddress());
        Assert.assertEquals(patient.getMember().getEmail(), patientFound.getMember().getEmail());
        Assert.assertEquals(patient.getMember().getHomePhone(), patientFound.getMember().getHomePhone());
        Assert.assertEquals(patient.getMember().getMobilePhone(), patientFound.getMember().getMobilePhone());
        Assert.assertEquals(patient.getMember().getGender(), patientFound.getMember().getGender());
        Assert.assertEquals(patient.getMember().getAlternateID(), patientFound.getMember().getAlternateID());
        Assert.assertEquals(patient.getMember().getStateID(), patientFound.getMember().getStateID());
        Assert.assertEquals(patient.getMember().getDateOfBirth(), patientFound.getMember().getDateOfBirth());
        Assert.assertEquals(patient.getRecommendation(), patientFound.getRecommendation());
        Assert.assertEquals(patient.getMember().getAlternateIdFileId(), patientFound.getMember().getAlternateIdFileId());
        Assert.assertEquals(patient.getApplicationFormFileId(), patientFound.getApplicationFormFileId());
        Assert.assertEquals(patient.getMember().getStateIdFileId(), patientFound.getMember().getStateIdFileId());

        compareFiles(patient.getMember().getAlternateIdFileId(), patientFound.getMember().getAlternateIdFileId(), "TestAlternateIdFile.pdf");
        compareFiles(patient.getMember().getStateIdFileId(), patientFound.getMember().getStateIdFileId(), "TestStateIdFile.pdf");
        compareFiles(patient.getApplicationFormFileId(), patientFound.getApplicationFormFileId(), "TestApplicationFormFile.pdf");

        Assert.assertEquals(patient.getCaregivers(), patientFound.getCaregivers());
    }

    @Test
    public void testFind() {
        Patient patient = createDocuments();
        Patient patientFound = patientRepository.findOne(patient.getId());
        checkPatientData(patient, patientFound);
    }

    @Test
    public void testUpdate() {
        Patient patient = createDocuments();
        Patient patientFound = patientRepository.findOne(patient.getId());
        checkPatientData(patient, patientFound);

        Caregiver caregiver = TestUtil.createCaregiver();
        caregiverRepository.save(caregiver);

        patient.getCaregivers().add(caregiver);
        patient.getMember().setAddress(TestUtil.createAddress());

        patientRepository.save(patient);
        patientFound = patientRepository.findOne(patient.getId());
        checkPatientData(patient, patientFound);
    }

    @Test
    public void testDelete() {
        Patient patient = createDocuments();
        Patient patientFound = patientRepository.findOne(patient.getId());
        checkPatientData(patient, patientFound);

        // test delete files
        operations.delete(Query.query(GridFsCriteria.where("_id").is(patient.getMember().getAlternateIdFileId())));
        operations.delete(Query.query(GridFsCriteria.where("_id").is(patient.getMember().getStateIdFileId())));
        operations.delete(Query.query(GridFsCriteria.where("_id").is(patient.getApplicationFormFileId())));

        List<GridFSDBFile> files = operations.find(Query.query(GridFsCriteria.where("_id").is(patient.getMember().getAlternateIdFileId())));
        Assert.assertEquals(0, files.size());
        files = operations.find(Query.query(GridFsCriteria.where("_id").is(patient.getMember().getStateIdFileId())));
        Assert.assertEquals(0, files.size());
        files = operations.find(Query.query(GridFsCriteria.where("_id").is(patient.getApplicationFormFileId())));
        Assert.assertEquals(0, files.size());

        patientRepository.delete(patient);
        patientFound = patientRepository.findOne(patient.getId());
        Assert.assertNull(patientFound);
    }

    private Patient createDocuments() {
        Patient patient = TestUtil.createPatient();

        patient.setCaregivers(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            Caregiver caregiver = TestUtil.createCaregiver();
            caregiverRepository.save(caregiver);
            patient.getCaregivers().add(caregiver);
        }

        patient.getMember().setAlternateIdFileId(storeFile(patient.getId(), "UploadedFiles/TestAlternateIdFile.pdf", "AlternateIDFile", "TestAlternateIdFile_"));
        patient.setApplicationFormFileId(storeFile(patient.getId(), "UploadedFiles/TestApplicationFormFile.pdf", "ApplicationFormFile", "TestApplicationFormFile_"));
        patient.getMember().setStateIdFileId(storeFile(patient.getId(), "UploadedFiles/TestStateIdFile.pdf", "StateIdFile", "TestStateIdFile_"));

        doctorRepository.save(patient.getRecommendation().getDoctor());
        patientRepository.save(patient);
        return patient;
    }

    private ObjectId storeFile(String id, String filePath, String fileCategory, String fileNamePrefix) {
        DBObject alternateIdFileMetaData = new BasicDBObject();
        alternateIdFileMetaData.put("FileCategory", fileCategory);
        InputStream inputStream = null;

        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            return (ObjectId) operations.store(inputStream, fileNamePrefix + id, alternateIdFileMetaData).getId();
        } catch (Exception ex) {
            logger.error("Exception in reading alternate id file", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {}
            }
        }
        return null;
    }

    private void compareFiles(ObjectId fileId, ObjectId fileFoundId, String retrivedFileName) {
        List<GridFSDBFile> files = operations.find(Query.query(GridFsCriteria.where("_id").is(fileId)));
        Assert.assertEquals(1, files.size());
        GridFSDBFile file = files.get(0);

        files = operations.find(Query.query(GridFsCriteria.where("_id").is(fileFoundId)));
        Assert.assertEquals(1, files.size());
        GridFSDBFile fileFound = files.get(0);

        Assert.assertEquals(file.getId(), fileFound.getId());
        Assert.assertEquals(file.getFilename(), fileFound.getFilename());
        Assert.assertEquals(file.getChunkSize(), fileFound.getChunkSize());
        Assert.assertEquals(file.getContentType(), fileFound.getContentType());
        Assert.assertEquals(file.getLength(), fileFound.getLength());
        Assert.assertEquals(file.getMD5(), fileFound.getMD5());
        Assert.assertEquals(file.getMetaData(), fileFound.getMetaData());

        // test writing the saved file. It's not unit test. But adding it to see how GridFSDBFile works
        try {
            files.get(0).writeTo("/tmp/" + retrivedFileName);
        } catch (Exception ex) {
            logger.error("Cannot write file " + retrivedFileName, ex);
        }
    }
}
