package com.kameo.hospitalservice.staff.boundary;

import com.kameo.hospitalservice.staff.domain.StaffMember;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Validated
public record StaffMemberCreateDTO(
        @NotNull Long id,
        @NotEmpty String name) {

    public StaffMember toEntity() {
        return StaffMember.builder()
                .name(name)
                .id(id)
                .registrationDate(Instant.now())
                .build();
    }

}
