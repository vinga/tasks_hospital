package com.kameo.hospitalservice.infra.security;

import com.kameo.hospitalservice.staff.domain.StaffMember;

import java.util.Optional;
import java.util.UUID;

public interface IdToPrincipalResolver {

    Optional<StaffMember> findById(UUID fromString);
}
