package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SystemEventTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SystemEvent getSystemEventSample1() {
        return new SystemEvent().id(1L).eventType("eventType1").message("message1").createdBy("createdBy1").correlationId("correlationId1");
    }

    public static SystemEvent getSystemEventSample2() {
        return new SystemEvent().id(2L).eventType("eventType2").message("message2").createdBy("createdBy2").correlationId("correlationId2");
    }

    public static SystemEvent getSystemEventRandomSampleGenerator() {
        return new SystemEvent()
            .id(longCount.incrementAndGet())
            .eventType(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .correlationId(UUID.randomUUID().toString());
    }
}
