package com.kameo.hospitalservice.patient.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class PatientProfile {
    @Id
    @GeneratedValue()
    private long id;

    private String name;
    private short age;

    private Instant created;

    private Instant lastVisitDate;
}
