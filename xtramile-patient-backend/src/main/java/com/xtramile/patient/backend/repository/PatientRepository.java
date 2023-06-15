package com.xtramile.patient.backend.repository;

import com.xtramile.patient.backend.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT * FROM patients where concat(first_name,' ', last_name) like %:name%", nativeQuery = true)
    List<Patient> findByNameContaining(String name);

    @Query(value = "SELECT * FROM patients where pid like %:pid%", nativeQuery = true)
    List<Patient> findByPidContaining(Long pid);

    @Query(value = "SELECT * FROM patients", nativeQuery = true)
    Page<Patient> findAllPatientPagination(Pageable paging);

    @Query(value = "SELECT * FROM patients where concat(first_name,' ', last_name) like %:name%", nativeQuery = true)
    Page<Patient> findByNamePagination(String name, Pageable paging);

    @Query(value = "SELECT * FROM patients where pid like %:pid%", nativeQuery = true)
    Page<Patient> findByPidPagination(Long pid, Pageable paging);


}
