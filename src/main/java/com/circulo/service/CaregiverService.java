package com.circulo.service;

import com.circulo.model.Caregiver;
import com.circulo.model.Member;
import com.circulo.model.Patient;

/**
 * Created by azim on 7/12/15.
 */
public interface CaregiverService {

    /**
     * Add a patient into caregiver's patient list.
     * @param patient
     * @param caregiver
     * @return
     */
    public Caregiver addPatient(Patient patient, Caregiver caregiver);

    /**
     * Delete a patient from caregiver's patient list.
     * @param patient
     * @param caregiver
     * @return
     */
    public Caregiver deletePatient(Patient patient, Caregiver caregiver);

    /**
     * Add a caregiver in the system.
     * @param caregiver
     * @return
     */
    public Caregiver addCaregiver(Caregiver caregiver);

    /**
     * Update a caregiver in the system.
     * @param caregiver
     * @return
     */
    public Caregiver updateCaregiver(Caregiver caregiver);

    /**
     * Delete a caregiver from the system.
     * @param caregiver
     * @return
     */
    public Caregiver deleteCaregiver(Caregiver caregiver);

}
