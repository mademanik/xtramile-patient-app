package com.xtramile.patient.backend.service;

import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    public PatientResponse createPatient(PatientRequest patientRequest);

    public List<PatientResponse> getAllPatient();

    public List<PatientResponse> getAllPatientByName(String name);

    public List<PatientResponse> getAllPatientByPid(Long pid);

    public Optional<PatientResponse> getPatientByPid(Long pid);

    public void deletePatientByPid(Long pid);

    public PatientResponse updatePatientByPid(Long pid, PatientRequest patientRequest);

}
