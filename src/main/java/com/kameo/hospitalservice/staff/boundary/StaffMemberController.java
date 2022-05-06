package com.kameo.hospitalservice.staff.boundary;

import com.kameo.hospitalservice.staff.domain.StaffMember;
import com.kameo.hospitalservice.staff.domain.StaffMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@Slf4j
@RestController
@RequestMapping(StaffMemberController.STAFF_MEMBERS_URL)
@RequiredArgsConstructor
public class StaffMemberController {
    public static final String STAFF_MEMBERS_URL = "/staff-members";
    private final StaffMemberService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StaffMemberDTO add(@RequestBody @Valid StaffMemberCreateDTO dto) {
        log.debug("Creating new staff member {}...", dto);
        StaffMember staffMember = service.add(dto.toEntity());
        return StaffMemberDTO.ofEntity(staffMember);
    }

    @PutMapping("/{uuid}")
    public StaffMemberDTO update(@AuthenticationPrincipal StaffMember caller,
                                 @PathVariable UUID uuid,
                                 @RequestBody @Valid StaffMemberUpdateDTO dto) {
        log.debug("Updating new staff member id={}, {}, caller={}...", uuid, dto, caller.getName());
        if (!caller.getUuid().equals(uuid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Staff members can update only their own profiles");
        }

        return service.update(uuid, dto::updateEntity)
                .map(StaffMemberDTO::ofEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
