package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.GatewayTestSamples.*;
import static com.ailab.smartasset.domain.LocationEventTestSamples.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEvent.class);
        LocationEvent locationEvent1 = getLocationEventSample1();
        LocationEvent locationEvent2 = new LocationEvent();
        assertThat(locationEvent1).isNotEqualTo(locationEvent2);

        locationEvent2.setId(locationEvent1.getId());
        assertThat(locationEvent1).isEqualTo(locationEvent2);

        locationEvent2 = getLocationEventSample2();
        assertThat(locationEvent1).isNotEqualTo(locationEvent2);
    }

    @Test
    void assetTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        locationEvent.setAsset(assetBack);
        assertThat(locationEvent.getAsset()).isEqualTo(assetBack);

        locationEvent.asset(null);
        assertThat(locationEvent.getAsset()).isNull();
    }

    @Test
    void zoneTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        locationEvent.setZone(zoneBack);
        assertThat(locationEvent.getZone()).isEqualTo(zoneBack);

        locationEvent.zone(null);
        assertThat(locationEvent.getZone()).isNull();
    }

    @Test
    void gatewayTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Gateway gatewayBack = getGatewayRandomSampleGenerator();

        locationEvent.setGateway(gatewayBack);
        assertThat(locationEvent.getGateway()).isEqualTo(gatewayBack);

        locationEvent.gateway(null);
        assertThat(locationEvent.getGateway()).isNull();
    }
}
