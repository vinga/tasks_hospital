package com.kameo.hospitalservice.staff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMember {
    @Id
    private UUID uuid;

    @Column(unique = true)
    private long id;

    @Column(unique = true)
    private String name;

    @Basic
    private Instant registrationDate;
}
