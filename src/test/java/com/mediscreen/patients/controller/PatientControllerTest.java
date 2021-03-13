package com.mediscreen.patients.controller;

import com.mediscreen.patients.domain.Patient;
import com.mediscreen.patients.service.PatientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    PatientServiceImpl patientServiceMock;

    private Patient patient;

    @BeforeAll
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();

        patient = new Patient();
        LocalDate dob = LocalDate.of(Integer.parseInt("1999"), Integer.parseInt("01"), Integer.parseInt("01"));
        Patient patient = new Patient();
        patient.setGivenName("TestGiven");
        patient.setFamilyName("TestFamily");
        patient.setDob(dob);
        patient.setSex('F');
        patient.setAddress("123 Test Street");
        patient.setPhone("555-555-5555");
    }

    @Test
    public void getAllPatients_statusIsSuccessful() throws Exception {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        mockMvc.perform(get("/patient/list"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patient/list"));
    }

    @Test
    public void addPatientForm_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/patient/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void validate_statusIsSuccessful() throws Exception {
        mockMvc.perform(post("/patient/validate")
                .param("id", "100")
                .param("givenName", "TestGiven")
                .param("familyName", "TestFamily")
                .param("dob", "1999-01-01")
                .param("sex", "F")
                .param("address", "123 Test Street")
                .param("phone", "555-555-5555"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void showUpdateForm_statusIsSuccessful() throws Exception {
        when(patientServiceMock.findPatientById(100)).thenReturn(patient);

        mockMvc.perform(get("/patient/update/{id}", "100"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updatePatient_statusIsSuccessful() throws Exception {
        when(patientServiceMock.findPatientById(100)).thenReturn(patient);

        mockMvc.perform(post("/patient/update/" + 100)
                .param("givenName", "UpdatedGiven"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deletePatient_statusIsRedirection() throws Exception {
        when(patientServiceMock.findPatientById(100)).thenReturn(patient);

        mockMvc.perform(get("/patient/delete/" + 100))
                .andExpect(status().is3xxRedirection());
    }

}
