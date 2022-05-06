package com.kameo.hospitalservice.staff.domain;

import com.kameo.hospitalservice.infra.security.IdToPrincipalResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class StaffMemberService implements IdToPrincipalResolver {
    private final StaffMemberRepository repository;

    public StaffMember add(StaffMember staffMember) {
        Assert.isNull(staffMember.getUuid(), "Uuid should be not set when adding new staff member.");
        staffMember.setUuid(UUID.randomUUID());
        return repository.save(staffMember);
    }

    public Optional<StaffMember> update(UUID staffMemberId, Consumer<StaffMember> updateStaffMember) {
        Assert.notNull(staffMemberId, "Uuid should be required on update.");
        return repository.findByUuid(staffMemberId)
                .map(member -> {
                    updateStaffMember.accept(member);
                    return repository.save(member);
                });
    }

    public Optional<StaffMember> findById(UUID uuid) {
        return repository.findByUuid(uuid);
    }
}
