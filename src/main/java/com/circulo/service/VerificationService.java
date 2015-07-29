package com.circulo.service;

import com.circulo.model.Member;
import com.circulo.model.Patient;

import java.util.Map;

/**
 * Created by azim on 7/27/15.
 */
public interface VerificationService {
    boolean verify(Patient patient) throws Exception;
}
