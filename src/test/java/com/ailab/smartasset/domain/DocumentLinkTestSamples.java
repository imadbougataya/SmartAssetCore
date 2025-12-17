package com.ailab.smartasset.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentLinkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DocumentLink getDocumentLinkSample1() {
        return new DocumentLink().id(1L).entityId(1L).label("label1").createdBy("createdBy1").lastModifiedBy("lastModifiedBy1");
    }

    public static DocumentLink getDocumentLinkSample2() {
        return new DocumentLink().id(2L).entityId(2L).label("label2").createdBy("createdBy2").lastModifiedBy("lastModifiedBy2");
    }

    public static DocumentLink getDocumentLinkRandomSampleGenerator() {
        return new DocumentLink()
            .id(longCount.incrementAndGet())
            .entityId(longCount.incrementAndGet())
            .label(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
