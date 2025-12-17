package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SensorMeasurementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SensorMeasurement getSensorMeasurementSample1() {
        return new SensorMeasurement().id(1L).quality("quality1").source("source1");
    }

    public static SensorMeasurement getSensorMeasurementSample2() {
        return new SensorMeasurement().id(2L).quality("quality2").source("source2");
    }

    public static SensorMeasurement getSensorMeasurementRandomSampleGenerator() {
        return new SensorMeasurement()
            .id(longCount.incrementAndGet())
            .quality(UUID.randomUUID().toString())
            .source(UUID.randomUUID().toString());
    }
}
