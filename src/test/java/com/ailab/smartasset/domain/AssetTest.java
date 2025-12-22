package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.ProductionLineTestSamples.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = getAssetSample1();
        Asset asset2 = new Asset();
        assertThat(asset1).isNotEqualTo(asset2);

        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);

        asset2 = getAssetSample2();
        assertThat(asset1).isNotEqualTo(asset2);
    }

    @Test
    void productionLineTest() {
        Asset asset = getAssetRandomSampleGenerator();
        ProductionLine productionLineBack = getProductionLineRandomSampleGenerator();

        asset.setProductionLine(productionLineBack);
        assertThat(asset.getProductionLine()).isEqualTo(productionLineBack);

        asset.productionLine(null);
        assertThat(asset.getProductionLine()).isNull();
    }

    @Test
    void allowedSiteTest() {
        Asset asset = getAssetRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        asset.setAllowedSite(siteBack);
        assertThat(asset.getAllowedSite()).isEqualTo(siteBack);

        asset.allowedSite(null);
        assertThat(asset.getAllowedSite()).isNull();
    }

    @Test
    void allowedZoneTest() {
        Asset asset = getAssetRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        asset.setAllowedZone(zoneBack);
        assertThat(asset.getAllowedZone()).isEqualTo(zoneBack);

        asset.allowedZone(null);
        assertThat(asset.getAllowedZone()).isNull();
    }
}
