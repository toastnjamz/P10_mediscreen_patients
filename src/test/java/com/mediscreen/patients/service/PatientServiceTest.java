package com.mediscreen.patients.service;

import com.mediscreen.patients.domain.Patient;
import com.mediscreen.patients.repository.PatientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @Before
    public void setup() {
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient = new Patient();
        patient.setId(100);
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');
        patient.setAddress("123 Test Street");
        patient.setPhone("555-555-5555");
    }

    @Test
    public void findPatientById_patientExists_patientReturned() {
        // arrange
        when(patientRepositoryMock.findById(patient.getId())).thenReturn(Optional.of(patient));

        // act
        Patient result = patientService.findPatientById(100);

        // assert
        assertEquals(patient, result);
    }
}
