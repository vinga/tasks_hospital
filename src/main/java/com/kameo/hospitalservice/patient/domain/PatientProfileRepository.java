package com.kameo.hospitalservice.patient.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Repository
public interface PatientProfileRepository extends CrudRepository<PatientProfile, Long> {
    List<PatientProfile> findAllByCreatedAfter(Instant after, Pageable page);

    @Query("select e.id from #{#entityName} e")
    Set<Long> findAllIdsByCreatedBetween(Instant after, Instant before);

}
