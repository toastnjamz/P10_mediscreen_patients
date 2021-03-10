package com.mediscreen.patients.service;

import com.mediscreen.patients.domain.Patient;
import com.mediscreen.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findPatientById(Integer id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            return patient;
        }
        return null;
    }

//    @Override
//    public Patient findPatientByFamilyName(String familyName) {
//        Optional<Patient> patientOptional = patientRepository.findByFamilyName(familyName);
//        if (patientOptional.isPresent()) {
//            Patient patient = patientOptional.get();
//            return patient;
//        }
//        return null;
//    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        Optional<Patient> patientOptional = patientRepository.findById(patient.getId());
        if (patientOptional.isPresent()) {
            patientRepository.save(patient);
        }
    }

    @Override
    public void deletePatientById(Integer id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.delete(patientOptional.get());
        }
    }
}
