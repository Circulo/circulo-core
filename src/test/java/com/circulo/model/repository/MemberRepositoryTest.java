package com.circulo.model.repository;

import com.circulo.enums.MemberType;
import com.circulo.model.Member;
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
import java.util.List;

import com.circulo.util.TestUtil;

/**
 * Created by azim on 7/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class MemberRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    GridFsOperations operations;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testCreate() {
        Member member = createDocuments();
        Query query = new Query(Criteria.where("_id").is(member.getId()));
        List<Member> members = mongoTemplate.find(query, Member.class);

        Assert.assertEquals(1, members.size());

        Member memberFound = members.get(0);
        checkMemberData(member, memberFound);
    }

    private void checkMemberData(Member member, Member memberFound) {
        Assert.assertNotNull(memberFound);
        Assert.assertEquals(member.getId(), memberFound.getId());
        Assert.assertEquals(member.getFirstName(), memberFound.getFirstName());
        Assert.assertEquals(member.getMiddleInitial(), memberFound.getMiddleInitial());
        Assert.assertEquals(member.getLastName(), memberFound.getLastName());
        Assert.assertEquals(member.getAddress(), memberFound.getAddress());
        Assert.assertEquals(member.getEmail(), memberFound.getEmail());
        Assert.assertEquals(member.getHomePhone(), memberFound.getHomePhone());
        Assert.assertEquals(member.getMobilePhone(), memberFound.getMobilePhone());
        Assert.assertEquals(member.getGender(), memberFound.getGender());
        Assert.assertEquals(member.getMemberType(), memberFound.getMemberType());
        Assert.assertEquals(member.getAlternateID(), memberFound.getAlternateID());
        Assert.assertEquals(member.getStateID(), memberFound.getStateID());
        Assert.assertEquals(member.getDateOfBirth(), memberFound.getDateOfBirth());
        Assert.assertEquals(member.getRecommendation(), memberFound.getRecommendation());
        Assert.assertEquals(member.getAlternateIdFileId(), memberFound.getAlternateIdFileId());
        Assert.assertEquals(member.getApplicationFormFileId(), memberFound.getApplicationFormFileId());
        Assert.assertEquals(member.getStateIdFileId(), memberFound.getStateIdFileId());

        compareFiles(member.getAlternateIdFileId(), memberFound.getAlternateIdFileId(), "TestAlternateIdFile.pdf");
        compareFiles(member.getStateIdFileId(), memberFound.getStateIdFileId(), "TestStateIdFile.pdf");
        compareFiles(member.getApplicationFormFileId(), memberFound.getApplicationFormFileId(), "TestApplicationFormFile.pdf");
    }

    @Test
    public void testFind() {
        Member member = createDocuments();
        Member memberFound = memberRepository.findOne(member.getId());
        checkMemberData(member, memberFound);
    }

    @Test
    public void testUpdate() {
        Member member = createDocuments();
        Member memberFound = memberRepository.findOne(member.getId());
        checkMemberData(member, memberFound);

        member.setAddress(TestUtil.createAddress());
        member.setMemberType(MemberType.CAREGIVER);
        memberRepository.save(member);
        memberFound = memberRepository.findOne(member.getId());
        checkMemberData(member, memberFound);
    }

    @Test
    public void testDelete() {
        Member member = createDocuments();
        Member memberFound = memberRepository.findOne(member.getId());
        checkMemberData(member, memberFound);

        // test delete files
        operations.delete(Query.query(GridFsCriteria.where("_id").is(member.getAlternateIdFileId())));
        operations.delete(Query.query(GridFsCriteria.where("_id").is(member.getStateIdFileId())));
        operations.delete(Query.query(GridFsCriteria.where("_id").is(member.getApplicationFormFileId())));

        List<GridFSDBFile> files = operations.find(Query.query(GridFsCriteria.where("_id").is(member.getAlternateIdFileId())));
        Assert.assertEquals(0, files.size());
        files = operations.find(Query.query(GridFsCriteria.where("_id").is(member.getStateIdFileId())));
        Assert.assertEquals(0, files.size());
        files = operations.find(Query.query(GridFsCriteria.where("_id").is(member.getStateIdFileId())));
        Assert.assertEquals(0, files.size());

        memberRepository.delete(member);
        memberFound = memberRepository.findOne(member.getId());
        Assert.assertNull(memberFound);
    }

    private Member createDocuments() {
        Member member = TestUtil.createMember();

        member.setAlternateIdFileId(storeFile(member.getId(), "UploadedFiles/TestAlternateIdFile.pdf", "AlternateIDFile", "TestAlternateIdFile_"));
        member.setApplicationFormFileId(storeFile(member.getId(), "UploadedFiles/TestApplicationFormFile.pdf", "ApplicationFormFile", "TestApplicationFormFile_"));
        member.setStateIdFileId(storeFile(member.getId(), "UploadedFiles/TestStateIdFile.pdf", "StateIdFile", "TestStateIdFile_"));

        doctorRepository.save(member.getRecommendation().getDoctor());
        memberRepository.save(member);
        return member;
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
