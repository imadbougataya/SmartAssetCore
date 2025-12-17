package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.GatewayTestSamples.*;
import static com.ailab.smartasset.domain.LocationEventTestSamples.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GatewayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gateway.class);
        Gateway gateway1 = getGatewaySample1();
        Gateway gateway2 = new Gateway();
        assertThat(gateway1).isNotEqualTo(gateway2);

        gateway2.setId(gateway1.getId());
        assertThat(gateway1).isEqualTo(gateway2);

        gateway2 = getGatewaySample2();
        assertThat(gateway1).isNotEqualTo(gateway2);
    }

    @Test
    void locationEventsTest() {
        Gateway gateway = getGatewayRandomSampleGenerator();
        LocationEvent locationEventBack = getLocationEventRandomSampleGenerator();

        gateway.addLocationEvents(locationEventBack);
        assertThat(gateway.getLocationEvents()).containsOnly(locationEventBack);
        assertThat(locationEventBack.getGateway()).isEqualTo(gateway);

        gateway.removeLocationEvents(locationEventBack);
        assertThat(gateway.getLocationEvents()).doesNotContain(locationEventBack);
        assertThat(locationEventBack.getGateway()).isNull();

        gateway.locationEvents(new HashSet<>(Set.of(locationEventBack)));
        assertThat(gateway.getLocationEvents()).containsOnly(locationEventBack);
        assertThat(locationEventBack.getGateway()).isEqualTo(gateway);

        gateway.setLocationEvents(new HashSet<>());
        assertThat(gateway.getLocationEvents()).doesNotContain(locationEventBack);
        assertThat(locationEventBack.getGateway()).isNull();
    }

    @Test
    void siteTest() {
        Gateway gateway = getGatewayRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        gateway.setSite(siteBack);
        assertThat(gateway.getSite()).isEqualTo(siteBack);

        gateway.site(null);
        assertThat(gateway.getSite()).isNull();
    }

    @Test
    void zoneTest() {
        Gateway gateway = getGatewayRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        gateway.setZone(zoneBack);
        assertThat(gateway.getZone()).isEqualTo(zoneBack);

        gateway.zone(null);
        assertThat(gateway.getZone()).isNull();
    }
}
