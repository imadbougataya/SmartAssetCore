package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AssetMovementRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AssetMovementRequest getAssetMovementRequestSample1() {
        return new AssetMovementRequest()
            .id(1L)
            .reason("reason1")
            .fromLocationLabel("fromLocationLabel1")
            .toLocationLabel("toLocationLabel1")
            .esignWorkflowId("esignWorkflowId1");
    }

    public static AssetMovementRequest getAssetMovementRequestSample2() {
        return new AssetMovementRequest()
            .id(2L)
            .reason("reason2")
            .fromLocationLabel("fromLocationLabel2")
            .toLocationLabel("toLocationLabel2")
            .esignWorkflowId("esignWorkflowId2");
    }

    public static AssetMovementRequest getAssetMovementRequestRandomSampleGenerator() {
        return new AssetMovementRequest()
            .id(longCount.incrementAndGet())
            .reason(UUID.randomUUID().toString())
            .fromLocationLabel(UUID.randomUUID().toString())
            .toLocationLabel(UUID.randomUUID().toString())
            .esignWorkflowId(UUID.randomUUID().toString());
    }
}
