package com.circulo.service;

import com.circulo.model.Patient;
import com.circulo.util.TestUtil;
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

/**
 * Created by azim on 8/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = {"classpath:spring-test-config.xml"})
public class GreenlifeVerificationServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(GreenlifeVerificationServiceTest.class);

    @Autowired
    @Qualifier("greenlifeVerificationService")
    private VerificationService verificationService;

    //@Ignore
    @Test
    public void testVerify() throws Exception {
        Patient patient = TestUtil.createPatient();
        patient.getRecommendation().setRecommendationNo("abc123");
        boolean isValid = verificationService.verify(patient);
        logger.debug("IsValid: " + isValid);
    }
}
