package com.kameo.hospitalservice.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class PatientsDataGenerator {

    private void generatePatientsData() {
        int minAge = 5;
        int maxAge = 100;
        for (int i = 0; i < 110; i++) {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            LocalDateTime createdAt = LocalDateTime.of(random.nextInt(2015, 2023),
                    random.nextInt(1, 13),
                    1, 1, 1);
            LocalDateTime lastVisitAt = LocalDateTime.of(random.nextInt(2015, 2023),
                    random.nextInt(1, 13),
                    1, 1, 1);
            if (lastVisitAt.compareTo(createdAt) < 0) {
                lastVisitAt = createdAt.plusDays(3);
            }
            String createdString = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String lastVisitString = lastVisitAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            System.out.println("GenericPatient" + i + ";" + ThreadLocalRandom.current().nextInt(minAge, maxAge) + ";"
                    + createdString + ";" + lastVisitString);
        }
    }

}
