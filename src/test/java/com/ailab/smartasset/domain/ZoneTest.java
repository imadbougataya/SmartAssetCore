package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.SiteTestSamples.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zone.class);
        Zone zone1 = getZoneSample1();
        Zone zone2 = new Zone();
        assertThat(zone1).isNotEqualTo(zone2);

        zone2.setId(zone1.getId());
        assertThat(zone1).isEqualTo(zone2);

        zone2 = getZoneSample2();
        assertThat(zone1).isNotEqualTo(zone2);
    }

    @Test
    void siteTest() {
        Zone zone = getZoneRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        zone.setSite(siteBack);
        assertThat(zone.getSite()).isEqualTo(siteBack);

        zone.site(null);
        assertThat(zone.getSite()).isNull();
    }
}
