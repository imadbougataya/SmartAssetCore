package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SensorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sensor getSensorSample1() {
        return new Sensor().id(1L).externalId("externalId1").name("name1").unit("unit1");
    }

    public static Sensor getSensorSample2() {
        return new Sensor().id(2L).externalId("externalId2").name("name2").unit("unit2");
    }

    public static Sensor getSensorRandomSampleGenerator() {
        return new Sensor()
            .id(longCount.incrementAndGet())
            .externalId(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .unit(UUID.randomUUID().toString());
    }
}
