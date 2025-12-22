package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MaintenanceEventTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MaintenanceEvent getMaintenanceEventSample1() {
        return new MaintenanceEvent()
            .id(1L)
            .title("title1")
            .description("description1")
            .technician("technician1")
            .downtimeMinutes(1)
            .notes("notes1");
    }

    public static MaintenanceEvent getMaintenanceEventSample2() {
        return new MaintenanceEvent()
            .id(2L)
            .title("title2")
            .description("description2")
            .technician("technician2")
            .downtimeMinutes(2)
            .notes("notes2");
    }

    public static MaintenanceEvent getMaintenanceEventRandomSampleGenerator() {
        return new MaintenanceEvent()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .technician(UUID.randomUUID().toString())
            .downtimeMinutes(intCount.incrementAndGet())
            .notes(UUID.randomUUID().toString());
    }
}
