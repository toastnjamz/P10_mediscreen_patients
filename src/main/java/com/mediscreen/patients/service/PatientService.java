package com.mediscreen.patients.service;

import com.mediscreen.patients.domain.Patient;

import java.util.List;

public interface PatientService {

    public List<Patient> findAllPatients();

    public Patient findPatientById(Integer id);

//    public Patient findPatientByFamilyName(String familyName);

    public Patient createPatient(Patient patient);

    public void updatePatient(Patient patient);

    public void deletePatientById(Integer id);
}
