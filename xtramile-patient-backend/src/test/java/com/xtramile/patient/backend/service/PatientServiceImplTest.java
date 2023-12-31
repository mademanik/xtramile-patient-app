package com.xtramile.patient.backend.service;

import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;
import com.xtramile.patient.backend.model.Patient;
import com.xtramile.patient.backend.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.willDoNothing;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    Patient patient = new Patient();

    Patient patient1 = new Patient();

    Patient patient2 = new Patient();

    List<Patient> patientList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .pid(1L)
                .firstName("Made Maharani")
                .lastName("Putri")
                .dateOfBirth(Date.valueOf("1996-04-13"))
                .gender("Female")
                .address("Banyuwangi")
                .suburb("Jawa Timur")
                .state("Indonesia")
                .postcode("68462")
                .phoneNumber("083834595231")
                .build();

        patient1 = Patient.builder()
                .pid(1L)
                .firstName("Made Manik")
                .lastName("Datu Yasa")
                .dateOfBirth(Date.valueOf("1994-09-26"))
                .gender("Male")
                .address("Banyuwangi")
                .suburb("Jawa Timur")
                .state("Indonesia")
                .postcode("68462")
                .phoneNumber("083834595232")
                .build();

        patient2 = Patient.builder()
                .pid(1L)
                .firstName("Iluh Ishita")
                .lastName("Kharisma Yasa")
                .dateOfBirth(Date.valueOf("1029-05-12"))
                .gender("Female")
                .address("Banyuwangi")
                .suburb("Jawa Timur")
                .state("Indonesia")
                .postcode("68462")
                .phoneNumber("083834595233")
                .build();

        patientList.add(patient1);
        patientList.add(patient2);
    }

    @Test
    @DisplayName("Test for getAllPatientsPagination methods service")
    void getAllPatientsPagination() {
        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(List.of(patient, patient2), pageable, 2);
        when(patientRepository.findAllPatientPagination(pageable)).thenReturn(patients);

        // Call the service method
        Page<Patient> resultPage = patientService.getAllPatientsPagination(pageable);

        // then - verify the output
        org.junit.jupiter.api.Assertions.assertEquals(2, resultPage.getTotalElements());
        org.junit.jupiter.api.Assertions.assertEquals("Made Maharani", resultPage.getContent().get(0).getFirstName());
        org.junit.jupiter.api.Assertions.assertEquals("Iluh Ishita", resultPage.getContent().get(1).getFirstName());
    }

    @Test
    @DisplayName("Test for getAllPatientsByPidPagination methods service")
    void getAllPatientsByPidPagination() {
        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(List.of(patient, patient2), pageable, 2);
        when(patientRepository.findByPidPagination(1L, pageable)).thenReturn(patients);

        // Call the service method
        Page<Patient> resultPage = patientService.getAllPatientsByPidPagination(1L, pageable);

        // then - verify the output
        org.junit.jupiter.api.Assertions.assertEquals(2, resultPage.getTotalElements());
        org.junit.jupiter.api.Assertions.assertEquals("Made Maharani", resultPage.getContent().get(0).getFirstName());
        org.junit.jupiter.api.Assertions.assertEquals("Iluh Ishita", resultPage.getContent().get(1).getFirstName());
    }

    @Test
    @DisplayName("Test for getAllPatientsByNamePagination methods service")
    void getAllPatientsByNamePagination() {
        // given - precondition or setup
        Pageable pageable = PageRequest.of(0, 3);

        // when -  action or the behaviour that we are going test
        Page<Patient> patients = new PageImpl<>(List.of(patient, patient2), pageable, 2);
        when(patientRepository.findByNamePagination("made", pageable)).thenReturn(patients);

        // Call the service method
        Page<Patient> resultPage = patientService.getAllPatientsByNamePagination("made", pageable);

        // then - verify the output
        org.junit.jupiter.api.Assertions.assertEquals(2, resultPage.getTotalElements());
        org.junit.jupiter.api.Assertions.assertEquals("Made Maharani", resultPage.getContent().get(0).getFirstName());
        org.junit.jupiter.api.Assertions.assertEquals("Iluh Ishita", resultPage.getContent().get(1).getFirstName());
    }

    @Test
    @DisplayName("Test for createPatient methods service")
    void createPatient() {

        // given - precondition or setup
        PatientRequest patientRequest = PatientRequest
                .builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .address(patient.getSuburb())
                .state(patient.getState())
                .postcode(patient.getPostcode())
                .phoneNumber(patient.getPhoneNumber())
                .build();

        // when -  action or the behaviour that we are going test
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        PatientResponse createPatient = patientService.createPatient(patientRequest);
        // then - verify the output
        Assertions.assertThat(createPatient).isNotNull();
    }

    @Test
    @DisplayName("Test for getAllPatient methods service")
    void getAllPatient() {
        // when -  action or the behaviour that we are going test
        when(patientRepository.findAll()).thenReturn(patientList);
        // then - verify the output
        assertEquals(2, patientService.getAllPatient().size());
    }

    @Test
    @DisplayName("Test for getAllPatientByName methods service")
    void getAllPatientByName() {
        // when -  action or the behaviour that we are going test
        when(patientRepository.findByNameContaining(Mockito.any(String.class))).thenReturn(patientList);
        List<PatientResponse> getPatientsName = patientService.getAllPatientByName(patient.getFirstName());
        // then - verify the output
        Assertions.assertThat(getPatientsName).isNotNull();
    }

    @Test
    @DisplayName("Test for getAllPatientByPid methods service")
    void getAllPatientByPid() {
        // when -  action or the behaviour that we are going test
        when(patientRepository.findByPidContaining(1L)).thenReturn(patientList);
        List<PatientResponse> getPatientsPid = patientService.getAllPatientByPid(patient.getPid());
        // then - verify the output
        Assertions.assertThat(getPatientsPid).isNotNull();
    }

    @Test
    @DisplayName("Test for getPatientByPid methods service")
    void getPatientByPid() {
        // when -  action or the behaviour that we are going test
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        PatientResponse getPatient = patientService.getPatientByPid(patient.getPid()).get();
        // then - verify the output
        Assertions.assertThat(getPatient).isNotNull();
    }

    @Test
    @DisplayName("Test for deletePatientByPid methods service")
    void deletePatientByPid() {
        // when -  action or the behaviour that we are going test
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        // then - verify the output
        assertAll(() -> patientService.deletePatientByPid(1L));
    }

    @Test
    @DisplayName("Test for updatePatientByPid methods service")
    void updatePatientByPid() {
        // given - precondition or setup
        PatientRequest patientRequest = PatientRequest
                .builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .address(patient.getSuburb())
                .state(patient.getState())
                .postcode(patient.getPostcode())
                .phoneNumber(patient.getPhoneNumber())
                .build();

        // when -  action or the behaviour that we are going test
        when(patientRepository.findById(1L)).thenReturn(Optional.ofNullable(patient));
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        PatientResponse updatePatient = patientService.updatePatientByPid(1L, patientRequest);
        // then - verify the output
        Assertions.assertThat(updatePatient).isNotNull();
    }

}