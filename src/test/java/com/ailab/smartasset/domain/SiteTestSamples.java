package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SiteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Site getSiteSample1() {
        return new Site().id(1L).code("code1").name("name1").description("description1").radiusMeters(1);
    }

    public static Site getSiteSample2() {
        return new Site().id(2L).code("code2").name("name2").description("description2").radiusMeters(2);
    }

    public static Site getSiteRandomSampleGenerator() {
        return new Site()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .radiusMeters(intCount.incrementAndGet());
    }
}
