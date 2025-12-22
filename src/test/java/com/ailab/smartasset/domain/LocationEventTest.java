package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.LocationEventTestSamples.*;
import static com.ailab.smartasset.domain.SensorTestSamples.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;
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
    void sensorTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Sensor sensorBack = getSensorRandomSampleGenerator();

        locationEvent.setSensor(sensorBack);
        assertThat(locationEvent.getSensor()).isEqualTo(sensorBack);

        locationEvent.sensor(null);
        assertThat(locationEvent.getSensor()).isNull();
    }

    @Test
    void matchedSiteTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        locationEvent.setMatchedSite(siteBack);
        assertThat(locationEvent.getMatchedSite()).isEqualTo(siteBack);

        locationEvent.matchedSite(null);
        assertThat(locationEvent.getMatchedSite()).isNull();
    }

    @Test
    void matchedZoneTest() {
        LocationEvent locationEvent = getLocationEventRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        locationEvent.setMatchedZone(zoneBack);
        assertThat(locationEvent.getMatchedZone()).isEqualTo(zoneBack);

        locationEvent.matchedZone(null);
        assertThat(locationEvent.getMatchedZone()).isNull();
    }
}
