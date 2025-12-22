package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GatewayTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gateway getGatewaySample1() {
        return new Gateway()
            .id(1L)
            .code("code1")
            .name("name1")
            .vendor("vendor1")
            .model("model1")
            .macAddress("macAddress1")
            .ipAddress("ipAddress1");
    }

    public static Gateway getGatewaySample2() {
        return new Gateway()
            .id(2L)
            .code("code2")
            .name("name2")
            .vendor("vendor2")
            .model("model2")
            .macAddress("macAddress2")
            .ipAddress("ipAddress2");
    }

    public static Gateway getGatewayRandomSampleGenerator() {
        return new Gateway()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .vendor(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .macAddress(UUID.randomUUID().toString())
            .ipAddress(UUID.randomUUID().toString());
    }
}
