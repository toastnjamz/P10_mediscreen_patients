package com.mediscreen.patients.controller;

import com.mediscreen.patients.domain.Patient;
import com.mediscreen.patients.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    /**
     * HTTP GET request loads a list of patients
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/patient/list")
    public ModelAndView getAllPatients(Model model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("patientList", patientService.findAllPatients());
        mav.setViewName("patient/list");
        log.info("GET request received for getAllPatients()");
        return mav;
    }

    /**
     * HTTP GET request loads new patient entry form
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/patient/add")
    public ModelAndView addPatientForm(Model model) {
        ModelAndView mav = new ModelAndView();
        model.addAttribute("patient", new Patient());
        mav.setViewName("patient/add");
        log.info("GET request received for addPatientForm()");
        return mav;
    }

    /**
     * HTTP POST request validates and creates a new patient entry
     * @param patient
     * @param result
     * @param model
     * @return ModelAndView
     */
    @PostMapping(value = "/patient/validate")
    public ModelAndView validate(@Valid Patient patient, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (!result.hasErrors()) {
            patientService.createPatient(patient);
            model.addAttribute("patientList", patientService.findAllPatients());
            mav.setViewName("redirect:/patient/list");
            log.info("Add Patient " + patient.toString());
            return mav;
        }
        mav.setViewName("patient/add");
        return mav;
    }

    /**
     * HTTP GET request loads an existing patient update form
     * @param id
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/patient/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            model.addAttribute("patient", patient);
            mav.setViewName("patient/update");
            log.info("GET request received for showUpdateForm()");
            return mav;
        }
        return mav;
    }

    /**
     * HTTP POST request updates the requested patient demographic information
     * @param id
     * @param patient
     * @param result
     * @param model
     * @return ModelAndView
     */
    @PostMapping("/patient/update/{id}")
    public ModelAndView updatePatient(@PathVariable("id") Integer id, @Valid Patient patient,
                                  BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("patient/update");
            return mav;
        }
        patient.setId(id);
        patientService.updatePatient(patient);
        model.addAttribute("patient", patientService.findAllPatients());
        mav.setViewName("redirect:/patient/list");
        log.info("Update Patient " + patient.toString());
        return mav;
    }

    /**
     * HTTP GET request deletes the requested patient
     * @param id
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/patient/delete/{id}")
    public ModelAndView deletePatient(@PathVariable("id") Integer id, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            patientService.deletePatientById(id);
            model.addAttribute("patient", patientService.findAllPatients());
            mav.setViewName("redirect:/patient/list");
            log.info("Delete Patient " + patient.toString());
            return mav;
        }
        return mav;
    }
}
