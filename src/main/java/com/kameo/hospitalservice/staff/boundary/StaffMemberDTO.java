package com.kameo.hospitalservice.staff.boundary;

import com.kameo.hospitalservice.staff.domain.StaffMember;

import java.time.Instant;
import java.util.UUID;

public record StaffMemberDTO(UUID uuid, String name, Long id, Instant registration_date) {

    public static StaffMemberDTO ofEntity(StaffMember staffMember) {
        return new StaffMemberDTO(staffMember.getUuid(),
                staffMember.getName(),
                staffMember.getId(),
                staffMember.getRegistrationDate());
    }

}
