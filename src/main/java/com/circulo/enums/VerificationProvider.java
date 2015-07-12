package com.circulo.enums;

/**
 * Created by azim on 6/30/15.
 */
// http://signup.verification420.com/
    // Verification420, Greenlife, MCC Directory, Sona Patel MD, MediCann
public enum VerificationProvider {
    MEDIBOOK,   // It uses recommendation number

    // https://verify.greenlifemedical.com/recommendations
    GREENLIFE,  // https://github.com/joshuaterrill/greenlife-api

    // https://www.mccdirectory.org/services/verify/verification_check.lasso
    MCCDIRECTORY,   // It uses DOB and Driver License/State ID/Password No. to verify

    // https://verify.greenlifemedical.com/cannassist
    CANNASSIST, // It uses ZipCode and recommendation number (CannAssist ID)

    // http://www.medicann.com/verification/
    // http://www.medicann.com/verification/verify.php
    MEDICANN,   // It used DOB and recommendation number (verification ID)

    // http://www.doc420.com/verify/
    SONAPATEL,  // It uses recommendation number (chart ID)

    // http://www.calmmp.ca.gov/MMIC_Search.aspx
    REGISTRYNUMBER, // It uses registry or certificate number @TODO : Not sure how it works.
    CUSTOM      // Custom verification. It uses recommendation number and verification link.

}
