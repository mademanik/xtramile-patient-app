package com.xtramile.patient.backend.service;

import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;
import com.xtramile.patient.backend.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<Patient> getAllPatientsPagination(Pageable paging);

    public Page<Patient> getAllPatientsByPidPagination(Long pid, Pageable paging);

    public Page<Patient> getAllPatientsByNamePagination(String name, Pageable paging);

}
