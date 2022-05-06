package com.kameo.hospitalservice.patient.boundary;

import com.kameo.hospitalservice.patient.domain.PatientProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/patient-profiles")
@RequiredArgsConstructor
public class PatientProfileController {
    private final PatientProfileService service;


    @GetMapping
    public List<PatientProfileDTO> findAllCreatedAfter(@RequestParam(required = false) Instant createdAfter) {
        if (createdAfter==null)
            createdAfter=Instant.now().minus(Duration.ofDays(365*2));
        log.debug("Searching for patient profiles createdAfter={}...", createdAfter);
        return service.findAllByCreatedAfter(createdAfter, Pageable.unpaged())
                .stream().map(PatientProfileDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = "application/csv", consumes = "application/csv")
    @ResponseBody
    public String getAsCsv(@PathVariable("id") long id) {
        log.debug("Returning profile in csv format id={}...", id);
        return service.getAsCsvFile(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteInRange(@RequestParam("createdAfter") Instant createdAfter,
                              @RequestParam("createdBefore") Instant createdBefore) {
        log.debug("Deleting patient profiles between={},{}...", createdAfter, createdBefore);
        service.deleteInRange(createdAfter, createdBefore);
    }
}
