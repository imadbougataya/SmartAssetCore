package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ZoneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Zone getZoneSample1() {
        return new Zone().id(1L).code("code1").name("name1").description("description1").radiusMeters(1);
    }

    public static Zone getZoneSample2() {
        return new Zone().id(2L).code("code2").name("name2").description("description2").radiusMeters(2);
    }

    public static Zone getZoneRandomSampleGenerator() {
        return new Zone()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .radiusMeters(intCount.incrementAndGet());
    }
}
