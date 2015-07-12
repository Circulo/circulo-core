package com.circulo.service;

import com.circulo.model.Caregiver;
import com.circulo.model.Patient;

/**
 * Created by azim on 7/12/15.
 */
public interface PatientService {

    /**
     * Add a caregiver into patient's caregiver list.
     * @param patient
     * @param caregiver
     * @return
     */
    public Caregiver addCaregiver(Patient patient, Caregiver caregiver);

    /**
     * Delete a caregiver from patient's caregiver list.
     * @param patient
     * @param caregiver
     * @return
     */
    public Caregiver deleteCaregiver(Patient patient, Caregiver caregiver);

    /**
     * Add a patient in the system.
     * @param patient
     * @return
     */
    public Patient addPatient(Patient patient);

    /**
     * Update a patient in the system.
     * @param patient
     * @return
     */
    public Patient updatePatient(Patient patient);

    /**
     * Delete a patient from the system.
     * @param patient
     * @return
     */
    public boolean deletePatient(Patient patient);
}
