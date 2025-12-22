package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Document getDocumentSample1() {
        return new Document()
            .id(1L)
            .fileName("fileName1")
            .mimeType("mimeType1")
            .sizeBytes(1L)
            .storageRef("storageRef1")
            .checksumSha256("checksumSha2561")
            .uploadedBy("uploadedBy1");
    }

    public static Document getDocumentSample2() {
        return new Document()
            .id(2L)
            .fileName("fileName2")
            .mimeType("mimeType2")
            .sizeBytes(2L)
            .storageRef("storageRef2")
            .checksumSha256("checksumSha2562")
            .uploadedBy("uploadedBy2");
    }

    public static Document getDocumentRandomSampleGenerator() {
        return new Document()
            .id(longCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .mimeType(UUID.randomUUID().toString())
            .sizeBytes(longCount.incrementAndGet())
            .storageRef(UUID.randomUUID().toString())
            .checksumSha256(UUID.randomUUID().toString())
            .uploadedBy(UUID.randomUUID().toString());
    }
}
