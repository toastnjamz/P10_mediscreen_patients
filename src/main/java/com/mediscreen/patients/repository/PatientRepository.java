package com.mediscreen.patients.repository;

import com.mediscreen.patients.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

        List<Patient> findAll();
        Optional<Patient> findById(Integer id);

}
