package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AssetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Asset getAssetSample1() {
        return new Asset()
            .id(1L)
            .assetCode("assetCode1")
            .reference("reference1")
            .description("description1")
            .responsibleName("responsibleName1")
            .costCenter("costCenter1")
            .brand("brand1")
            .model("model1")
            .serialNumber("serialNumber1")
            .speedRpm(1)
            .ipRating("ipRating1")
            .insulationClass("insulationClass1")
            .dimensionsSource("dimensionsSource1")
            .maintenanceCount(1);
    }

    public static Asset getAssetSample2() {
        return new Asset()
            .id(2L)
            .assetCode("assetCode2")
            .reference("reference2")
            .description("description2")
            .responsibleName("responsibleName2")
            .costCenter("costCenter2")
            .brand("brand2")
            .model("model2")
            .serialNumber("serialNumber2")
            .speedRpm(2)
            .ipRating("ipRating2")
            .insulationClass("insulationClass2")
            .dimensionsSource("dimensionsSource2")
            .maintenanceCount(2);
    }

    public static Asset getAssetRandomSampleGenerator() {
        return new Asset()
            .id(longCount.incrementAndGet())
            .assetCode(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .responsibleName(UUID.randomUUID().toString())
            .costCenter(UUID.randomUUID().toString())
            .brand(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .serialNumber(UUID.randomUUID().toString())
            .speedRpm(intCount.incrementAndGet())
            .ipRating(UUID.randomUUID().toString())
            .insulationClass(UUID.randomUUID().toString())
            .dimensionsSource(UUID.randomUUID().toString())
            .maintenanceCount(intCount.incrementAndGet());
    }
}
