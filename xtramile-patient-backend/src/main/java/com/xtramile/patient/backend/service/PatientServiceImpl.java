package com.xtramile.patient.backend.service;

import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;
import com.xtramile.patient.backend.model.Patient;
import com.xtramile.patient.backend.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientResponse createPatient(PatientRequest patientRequest) {
        Patient patient = Patient.builder()
                .firstName(patientRequest.getFirstName())
                .lastName(patientRequest.getLastName())
                .dateOfBirth(patientRequest.getDateOfBirth())
                .gender(patientRequest.getGender())
                .address(patientRequest.getAddress())
                .suburb(patientRequest.getSuburb())
                .state(patientRequest.getState())
                .postcode(patientRequest.getPostcode())
                .phoneNumber(patientRequest.getPhoneNumber())
                .build();

        patientRepository.save(patient);
        log.info("Patient with pid : {} is successfully created", patient.getPid());
        return mapToPatientResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        log.info("getAllPatient successfully retrieved");
        return patients.stream().map(this::mapToPatientResponse).collect(Collectors.toList());
    }

    @Override
    public List<PatientResponse> getAllPatientByName(String name) {
        List<Patient> patients = patientRepository.findByNameContaining(name);
        log.info("getAllPatientByName successfully retrieved");
        return patients.stream().map(this::mapToPatientResponse).collect(Collectors.toList());
    }

    @Override
    public List<PatientResponse> getAllPatientByPid(Long pid) {
        List<Patient> patients = patientRepository.findByPidContaining(pid);
        log.info("getAllPatientByPid successfully retrieved");
        return patients.stream().map(this::mapToPatientResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<PatientResponse> getPatientByPid(Long pid) {
        Optional<Patient> patient = patientRepository.findById(pid);
        log.info("getPatientById: {} successfully retrieved", pid);
        return patient.map(this::mapToPatientResponse);
    }

    @Override
    public void deletePatientByPid(Long pid) {
        Optional<Patient> patient = patientRepository.findById(pid);
        patientRepository.deleteById(pid);
        log.info("Patient with pid: {} is successfully deleted", pid);
    }

    @Override
    public PatientResponse updatePatientByPid(Long pid, PatientRequest patientRequest) {
        Optional<Patient> patientData = patientRepository.findById(pid);

        if (patientData.isPresent()) {
            Patient patient = patientData.get();
            patient.setFirstName(patientRequest.getFirstName());
            patient.setLastName(patientRequest.getLastName());
            patient.setDateOfBirth(patientRequest.getDateOfBirth());
            patient.setGender(patientRequest.getGender());
            patient.setAddress(patientRequest.getAddress());
            patient.setSuburb(patientRequest.getSuburb());
            patient.setState(patientRequest.getState());
            patient.setPostcode(patientRequest.getPostcode());
            patient.setPhoneNumber(patientRequest.getPhoneNumber());
            log.info("Patient with pid: {} is successfully updated", pid);
            patientRepository.save(patient);

            return mapToPatientResponse(patient);
        } else {
            return null;
        }
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
}
