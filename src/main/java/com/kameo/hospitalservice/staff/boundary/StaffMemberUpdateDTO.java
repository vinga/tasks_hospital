package com.kameo.hospitalservice.staff.boundary;

import com.kameo.hospitalservice.staff.domain.StaffMember;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public record StaffMemberUpdateDTO(@NotEmpty String name) {

    public void updateEntity(StaffMember updateEntity) {
        updateEntity.setName(name);
    }
}
