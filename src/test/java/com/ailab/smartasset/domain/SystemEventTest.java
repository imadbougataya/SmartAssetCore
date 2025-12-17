package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.SystemEventTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemEvent.class);
        SystemEvent systemEvent1 = getSystemEventSample1();
        SystemEvent systemEvent2 = new SystemEvent();
        assertThat(systemEvent1).isNotEqualTo(systemEvent2);

        systemEvent2.setId(systemEvent1.getId());
        assertThat(systemEvent1).isEqualTo(systemEvent2);

        systemEvent2 = getSystemEventSample2();
        assertThat(systemEvent1).isNotEqualTo(systemEvent2);
    }

    @Test
    void assetTest() {
        SystemEvent systemEvent = getSystemEventRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        systemEvent.setAsset(assetBack);
        assertThat(systemEvent.getAsset()).isEqualTo(assetBack);

        systemEvent.asset(null);
        assertThat(systemEvent.getAsset()).isNull();
    }
}
