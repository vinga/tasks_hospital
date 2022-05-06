package com.kameo.hospitalservice.patient.boundary;

import com.kameo.hospitalservice.patient.domain.PatientProfile;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.Optional;

@Validated
public record PatientProfileDTO(long id, String name, short age, Instant last_visit_date) {

    public static PatientProfileDTO ofEntity(PatientProfile patientProfile) {
        return new PatientProfileDTO(
                patientProfile.getId(),
                patientProfile.getName(),
                patientProfile.getAge(),
                Optional.ofNullable(patientProfile.getLastVisitDate())
                        .orElse(null));
    }


}
