package com.xtramile.patient.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;
import com.xtramile.patient.backend.model.Patient;
import com.xtramile.patient.backend.repository.PatientRepository;
import com.xtramile.patient.backend.service.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientServiceImpl patientService;

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;

    private Patient patient2;

    private PatientRequest patientRequest;

    private PatientRequest patientRequest2;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .pid(1L)
                .firstName("Kadek Kanaya")
                .lastName("Kharisma Yasa")
                .dateOfBirth(Date.valueOf("2020-07-25"))
                .gender("Female")
                .address("Banyuwangi")
                .suburb("Jawa Timur")
                .state("Indonesia")
                .postcode("68462")
                .phoneNumber("083834595231")
                .build();

        patient2 = Patient.builder()
                .pid(2L)
                .firstName("Iluh Iliana")
                .lastName("Ishita Yasa")
                .dateOfBirth(Date.valueOf("2019-05-12"))
                .gender("Female")
                .address("Banyuwangi")
                .suburb("Jawa Timur")
                .state("Indonesia")
                .postcode("68462")
                .phoneNumber("083834595232")
                .build();


        patientRequest = mapToPatientRequest(patient);
        patientRequest2 = mapToPatientRequest(patient);
    }

    @Test
    @DisplayName("Test for getAllPatientsPagination controller service")
    void getAllPatientsPagination() throws Exception {

        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(List.of(patient, patient2), pageable, 2);
        when(patientService.getAllPatientsPagination(pageable)).thenReturn(patients);

        // then - verify the result or output
        mockMvc.perform(get("/patient/api/page").param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.totalItems", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.patients[0].firstName", is("Kadek Kanaya")))
                .andExpect(jsonPath("$.patients[1].firstName", is("Iluh Iliana")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test for getAllPatientsPaginationByPid controller service")
    void getAllPatientsPaginationByPid() throws Exception {

        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(Collections.singletonList(patient), pageable, 1);
        when(patientService.getAllPatientsByPidPagination(1L, pageable)).thenReturn(patients);

        // then - verify the result or output
        mockMvc.perform(get("/patient/api/page").param("page", "0")
                        .param("size", "3").param("pid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.totalItems", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.patients[0].firstName", is("Kadek Kanaya")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test for getAllPatientsPaginationByName controller service")
    void getAllPatientsPaginationByName() throws Exception {

        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(Collections.singletonList(patient), pageable, 1);
        when(patientService.getAllPatientsByNamePagination("kadek", pageable)).thenReturn(patients);

        // then - verify the result or output
        mockMvc.perform(get("/patient/api/page").param("page", "0")
                        .param("size", "3").param("name", "kadek"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.totalItems", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.patients[0].firstName", is("Kadek Kanaya")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test for createPatient controller service")
    void createPatient() throws Exception {
        // given - precondition or setup
        PatientResponse patientResponse = mapToPatientResponse(patient);

        // when - action or behaviour that we are going test
        Mockito.when(patientService.createPatient(patientRequest)).thenReturn(patientResponse);

        // then - verify the result or output
        String requestBody = objectMapper.writeValueAsString(patientRequest);
        mockMvc.perform(post("/patient/api").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Test for getAllPatients controller service")
    void getAllPatients() throws Exception {

        PatientResponse patientResponse1 = mapToPatientResponse(patient);
        PatientResponse patientResponse2 = mapToPatientResponse(patient2);

        List<PatientResponse> listPatients = List.of(patientResponse1, patientResponse2);

        Mockito.when(patientService.getAllPatient()).thenReturn(listPatients);

        mockMvc.perform(get("/patient/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName", is("Kadek Kanaya")))
                .andExpect(jsonPath("$[1].firstName", is("Iluh Iliana")))
                .andDo(print());
    }

    @Test
    @DisplayName("Test for getPatientById controller service")
    void getPatientById() throws Exception {
        // given - precondition or setup
        Long pid = 1L;
        String requestURI = "/patient/api" + "/" + pid;
        String firstName = patient.getFirstName();

        PatientResponse patientResponse = mapToPatientResponse(patient);

        // when - action or behaviour that we are going test
        Mockito.when(patientService.getPatientByPid(pid)).thenReturn(Optional.of(patientResponse));

        // then - verify the result or output
        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andDo(print());
    }

    @Test
    @DisplayName("Test for deletePatientById controller service")
    void deletePatientById() throws Exception {
        Long pid = 3L;
        String requestURI = "/patient/api" + "/" + pid;

        Mockito.doNothing().when(patientService).deletePatientByPid(pid);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Test for updatePatientById controller service")
    void updatePatientById() throws Exception {
        Long pid = 2L;
        String requestURI = "/patient/api" + "/" + pid;
        String firstName = patient.getFirstName();

        PatientResponse patientResponse = mapToPatientResponse(patient);

        Mockito.when(patientService.updatePatientByPid(pid, patientRequest)).thenReturn(patientResponse);

        String requestBody = objectMapper.writeValueAsString(patientRequest);

        mockMvc.perform(put(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andDo(print());
    }

    private PatientResponse mapToPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .pid(patient.getPid())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .suburb(patient.getSuburb())
                .state(patient.getState())
                .postcode(patient.getPostcode())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

    private PatientRequest mapToPatientRequest(Patient patient) {
        return PatientRequest.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .suburb(patient.getSuburb())
                .state(patient.getState())
                .postcode(patient.getPostcode())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }
}