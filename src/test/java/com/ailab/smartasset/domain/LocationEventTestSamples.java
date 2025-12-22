package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LocationEventTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static LocationEvent getLocationEventSample1() {
        return new LocationEvent()
            .id(1L)
            .zoneConfidence(1)
            .rssi(1)
            .txPower(1)
            .gnssConstellation("gnssConstellation1")
            .rawPayload("rawPayload1");
    }

    public static LocationEvent getLocationEventSample2() {
        return new LocationEvent()
            .id(2L)
            .zoneConfidence(2)
            .rssi(2)
            .txPower(2)
            .gnssConstellation("gnssConstellation2")
            .rawPayload("rawPayload2");
    }

    public static LocationEvent getLocationEventRandomSampleGenerator() {
        return new LocationEvent()
            .id(longCount.incrementAndGet())
            .zoneConfidence(intCount.incrementAndGet())
            .rssi(intCount.incrementAndGet())
            .txPower(intCount.incrementAndGet())
            .gnssConstellation(UUID.randomUUID().toString())
            .rawPayload(UUID.randomUUID().toString());
    }
}
