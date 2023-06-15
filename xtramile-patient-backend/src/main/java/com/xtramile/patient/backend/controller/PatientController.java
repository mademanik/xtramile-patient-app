package com.xtramile.patient.backend.controller;

import com.xtramile.patient.backend.dto.PatientRequest;
import com.xtramile.patient.backend.dto.PatientResponse;
import com.xtramile.patient.backend.model.Patient;
import com.xtramile.patient.backend.repository.PatientRepository;
import com.xtramile.patient.backend.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/patient/api")
@Slf4j
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest) {
        try {
            PatientResponse patientResponse = patientService.createPatient(patientRequest);
            return new ResponseEntity<>(patientResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Error creating patient {} :", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients(@RequestParam(required = false) String name, Long pid) {
        List<PatientResponse> patientResponses = new ArrayList<PatientResponse>();
        try {

            if (name != null) {
                patientService.getAllPatientByName(name).forEach(patientResponses::add);
            }

            if (pid != null) {
                patientService.getAllPatientByPid(pid).forEach(patientResponses::add);
            }

            if (name == null && pid == null) {
                patientService.getAllPatient().forEach(patientResponses::add);
            }

            if (patientResponses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(patientResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getAllPatientsPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String name, Long pid) {
        try {
            List<Patient> patients = new ArrayList<Patient>();
            Pageable paging = PageRequest.of(page, size);

            if (name != null) {
                Page<Patient> pagePats = patientService.getAllPatientsByNamePagination(name, paging);
                patients = pagePats.getContent();

                Map<String, Object> response = new HashMap<>();
                response = mapPatientPagination(patients, pagePats);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (pid != null) {
                Page<Patient> pagePats = patientService.getAllPatientsByPidPagination(pid, paging);
                patients = pagePats.getContent();

                Map<String, Object> response = new HashMap<>();
                response = mapPatientPagination(patients, pagePats);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (name == null && pid == null) {
                Page<Patient> pagePats = patientService.getAllPatientsPagination(paging);
                patients = pagePats.getContent();

                Map<String, Object> response = new HashMap<>();
                response = mapPatientPagination(patients, pagePats);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{pid}")
    public ResponseEntity<Optional<PatientResponse>> getPatientById(@PathVariable("pid") Long pid) {
        try {
            Optional<PatientResponse> patientResponse = patientService.getPatientByPid(pid);
            return new ResponseEntity<>(patientResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<HttpStatus> deletePatientById(@PathVariable("pid") Long pid) {
        try {
            patientService.deletePatientByPid(pid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{pid}")
    public ResponseEntity<PatientResponse> updatePatientById(@PathVariable("pid") Long pid, @RequestBody PatientRequest patientRequest) {
        try {
            PatientResponse patientResponse = patientService.updatePatientByPid(pid, patientRequest);
            return new ResponseEntity<>(patientResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, Object> mapPatientPagination(List<Patient> patients, Page<Patient> pagePats) {

        Map<String, Object> response = new HashMap<>();
        response.put("patients", patients);
        response.put("currentPage", pagePats.getNumber());
        response.put("totalItems", pagePats.getTotalElements());
        response.put("totalPages", pagePats.getTotalPages());

        return response;

    }
}
