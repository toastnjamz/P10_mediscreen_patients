package com.mediscreen.patients.service;

import com.mediscreen.patients.domain.Patient;
import com.mediscreen.patients.repository.PatientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeAll
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
    public void findAllPatients_patientsExist_patientsReturned() {
        // arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        when(patientRepositoryMock.findAll()).thenReturn(patients);

        // act
        List<Patient> listResult = patientService.findAllPatients();

        // assert
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void findAllPatients_patientsDoNotExist_listSizeIsEmpty() {
        // arrange

        // act
        List<Patient> listResult = patientService.findAllPatients();

        // assert
        assertTrue(listResult.size() == 0);
    }

    @Test
    public void findPatientById_patientExists_patientReturned() {
        // arrange

        // act
        Patient result = patientService.findPatientById(100);

        // assert
        assertEquals(patient, result);
    }

    @Test
    public void findPatientById_patientDoesNotExist_nullReturned() {
        // arrange

        // act
        Patient result = patientService.findPatientById(200);

        // assert
        assertNull(result);
    }

    @Test
    public void createPatient_validPatient_patientSaved() {
        // arrange
        LocalDate dob = LocalDate.of(Integer.parseInt("2000"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient2 = new Patient();
        patient2.setId(101);
        patient2.setGivenName("TestGiven1");
        patient2.setFamilyName("TestFamily1");
        patient2.setDob(dob);
        patient2.setSex('M');
        patient2.setAddress("456 Test Street");
        patient2.setPhone("555-555-5555");

        when(patientRepositoryMock.save(patient2)).thenReturn(patient2);

        // act
        Patient result = patientService.createPatient(patient2);

        // assert
        verify(patientRepositoryMock, times(1)).save(any(Patient.class));
        assertEquals(patient2.getId(), result.getId(), 0);
    }

    @Test
    public void updatePatient_validPatient_patientSaved() {
        // arrange
        LocalDate dob = LocalDate.of(Integer.parseInt("2000"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient2 = new Patient();
        patient2.setId(101);
        patient2.setGivenName("TestGiven1");
        patient2.setFamilyName("TestFamily1");
        patient2.setDob(dob);
        patient2.setSex('M');
        patient2.setAddress("456 Test Street");
        patient2.setPhone("555-555-5555");

        patientService.createPatient(patient2);
        patient2.setAddress("Updated Address");

        // act
        patientService.updatePatient(patient2);

        // assert
        verify(patientRepositoryMock, times(1)).save(any(Patient.class));
        assertEquals(patient2.getAddress(), "Updated Address", "Updated Address");
    }

    @Test
    public void deletePatient_validPatient_patientDeleted() {
        // arrange

        // act
        patientService.deletePatientById(100);

        // assert
        Optional<Patient> patient = patientRepositoryMock.findById(100);
        assertFalse(patient.isPresent());
    }

}
