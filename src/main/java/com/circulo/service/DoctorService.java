package com.circulo.service;

import com.circulo.model.Doctor;

import java.util.List;

/**
 * Created by azim on 7/12/15.
 */
public interface DoctorService {

    /**
     * This will check doctor's license expire date against current date and modify license status as required.
     * @TODO : R&D Doctor's license verification services and add the verification here.
     * @param doctor The doctor object to check.
     * @return The updated doctor object.
     */
    public Doctor checkAndUpdateLicenseStatus(Doctor doctor);

    /**
     * Find a list of doctors by checking match against first name, last name, license number,
     * category, license status, email, office phone and mobile phone.
     * @param search text.
     * @return List of doctors matching the search.
     */
    public List<Doctor> find(String search);

    /**
     *
     * @param doctor
     * @return The doctor object saved.
     */
    public Doctor createDoctor(Doctor doctor);

    /**
     *
     * @param doctor
     * @return The doctor object saved.
     */
    public Doctor updateDoctor(Doctor doctor);

    /**
     *
     * @param doctor
     * @return True if deleted and false otherwise.
     */
    public boolean deleteDoctor(Doctor doctor);
}
