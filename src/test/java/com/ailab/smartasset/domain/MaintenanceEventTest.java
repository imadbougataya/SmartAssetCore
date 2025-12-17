package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.MaintenanceEventTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaintenanceEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaintenanceEvent.class);
        MaintenanceEvent maintenanceEvent1 = getMaintenanceEventSample1();
        MaintenanceEvent maintenanceEvent2 = new MaintenanceEvent();
        assertThat(maintenanceEvent1).isNotEqualTo(maintenanceEvent2);

        maintenanceEvent2.setId(maintenanceEvent1.getId());
        assertThat(maintenanceEvent1).isEqualTo(maintenanceEvent2);

        maintenanceEvent2 = getMaintenanceEventSample2();
        assertThat(maintenanceEvent1).isNotEqualTo(maintenanceEvent2);
    }

    @Test
    void assetTest() {
        MaintenanceEvent maintenanceEvent = getMaintenanceEventRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        maintenanceEvent.setAsset(assetBack);
        assertThat(maintenanceEvent.getAsset()).isEqualTo(assetBack);

        maintenanceEvent.asset(null);
        assertThat(maintenanceEvent.getAsset()).isNull();
    }
}
