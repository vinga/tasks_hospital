package com.kameo.hospitalservice.staff.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffMemberRepository extends CrudRepository<StaffMember, Long> {
    Optional<StaffMember> findByUuid(UUID uuid);
}
