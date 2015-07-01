package com.circulo.model;

/**
 * Created by azim on 6/30/15.
 */
public enum VerificationProvider {
    MEDIBOOK,   // It uses recommendation number
    GREENLIFE,
    MCCDIRECTORY,   // It uses DOB and Driver License/State ID/Password No. to verify
    CANNASSIST, // It uses ZipCode and recommendation number (CannAssist ID)
    MEDICANN,   // It used DOB and recommendation number (verification ID)
    SONAPATEL,  // It uses recommendation number (chart ID)
    REGISTRYNUMBER, // It uses registry or certificate number @TODO : Not sure how it works.
    CUSTOM      // Custom verification. It uses recommendation number and verification link.

}
