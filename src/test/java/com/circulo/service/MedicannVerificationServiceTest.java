package com.circulo.service;

import com.circulo.model.Member;
import com.circulo.model.Patient;
import org.htmlcleaner.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.circulo.util.TestUtil;

/**
 * Created by azim on 7/27/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class MedicannVerificationServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(MedicannVerificationServiceTest.class);

    @Autowired
    @Qualifier("medicannVerificationService")
    private VerificationService verificationService;

    @Ignore
    @Test
    public void testVerify() throws Exception {
        Patient patient = TestUtil.createPatient();
        patient.getRecommendation().setRecommendationNo("11111111");
        verificationService.verify(patient);
    }
}
