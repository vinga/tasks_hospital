package com.kameo.hospitalservice.patient.domain;

import com.kameo.hospitalservice.patient.boundary.PatientProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PatientProfileService {
    private final PatientProfileRepository repository;

    public List<PatientProfile> findAllByCreatedAfter(Instant after, Pageable page) {
        return repository.findAllByCreatedAfter(after, page);
    }

    public Optional<String> getAsCsvFile(long id) {
        return repository.findById(id).map(profile -> {
            String namesLine = "id,name,age,created,last visit";
            String profileLine = String.join(",", String.valueOf(profile.getId()),
                    profile.getName(),
                    String.valueOf(profile.getAge()),
                    profile.getCreated().toString(),
                    Optional.ofNullable(profile.getLastVisitDate()).map(Object::toString).orElse(""));
            return namesLine + "\n" + profileLine + "\n";
        });
    }

    public void deleteInRange(Instant createdAfter, Instant createdBefore) {
        Set<Long> idsToDelete = repository.findAllIdsByCreatedBetween(createdAfter, createdBefore);
        repository.deleteAllById(idsToDelete);
    }
}
