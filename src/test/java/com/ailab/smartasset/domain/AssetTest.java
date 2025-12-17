package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetMovementRequestTestSamples.*;
import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.LocationEventTestSamples.*;
import static com.ailab.smartasset.domain.MaintenanceEventTestSamples.*;
import static com.ailab.smartasset.domain.ProductionLineTestSamples.*;
import static com.ailab.smartasset.domain.SensorTestSamples.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void sensorsTest() {
        Asset asset = getAssetRandomSampleGenerator();
        Sensor sensorBack = getSensorRandomSampleGenerator();

        asset.addSensors(sensorBack);
        assertThat(asset.getSensors()).containsOnly(sensorBack);
        assertThat(sensorBack.getAsset()).isEqualTo(asset);

        asset.removeSensors(sensorBack);
        assertThat(asset.getSensors()).doesNotContain(sensorBack);
        assertThat(sensorBack.getAsset()).isNull();

        asset.sensors(new HashSet<>(Set.of(sensorBack)));
        assertThat(asset.getSensors()).containsOnly(sensorBack);
        assertThat(sensorBack.getAsset()).isEqualTo(asset);

        asset.setSensors(new HashSet<>());
        assertThat(asset.getSensors()).doesNotContain(sensorBack);
        assertThat(sensorBack.getAsset()).isNull();
    }

    @Test
    void maintenanceEventsTest() {
        Asset asset = getAssetRandomSampleGenerator();
        MaintenanceEvent maintenanceEventBack = getMaintenanceEventRandomSampleGenerator();

        asset.addMaintenanceEvents(maintenanceEventBack);
        assertThat(asset.getMaintenanceEvents()).containsOnly(maintenanceEventBack);
        assertThat(maintenanceEventBack.getAsset()).isEqualTo(asset);

        asset.removeMaintenanceEvents(maintenanceEventBack);
        assertThat(asset.getMaintenanceEvents()).doesNotContain(maintenanceEventBack);
        assertThat(maintenanceEventBack.getAsset()).isNull();

        asset.maintenanceEvents(new HashSet<>(Set.of(maintenanceEventBack)));
        assertThat(asset.getMaintenanceEvents()).containsOnly(maintenanceEventBack);
        assertThat(maintenanceEventBack.getAsset()).isEqualTo(asset);

        asset.setMaintenanceEvents(new HashSet<>());
        assertThat(asset.getMaintenanceEvents()).doesNotContain(maintenanceEventBack);
        assertThat(maintenanceEventBack.getAsset()).isNull();
    }

    @Test
    void movementRequestsTest() {
        Asset asset = getAssetRandomSampleGenerator();
        AssetMovementRequest assetMovementRequestBack = getAssetMovementRequestRandomSampleGenerator();

        asset.addMovementRequests(assetMovementRequestBack);
        assertThat(asset.getMovementRequests()).containsOnly(assetMovementRequestBack);
        assertThat(assetMovementRequestBack.getAsset()).isEqualTo(asset);

        asset.removeMovementRequests(assetMovementRequestBack);
        assertThat(asset.getMovementRequests()).doesNotContain(assetMovementRequestBack);
        assertThat(assetMovementRequestBack.getAsset()).isNull();

        asset.movementRequests(new HashSet<>(Set.of(assetMovementRequestBack)));
        assertThat(asset.getMovementRequests()).containsOnly(assetMovementRequestBack);
        assertThat(assetMovementRequestBack.getAsset()).isEqualTo(asset);

        asset.setMovementRequests(new HashSet<>());
        assertThat(asset.getMovementRequests()).doesNotContain(assetMovementRequestBack);
        assertThat(assetMovementRequestBack.getAsset()).isNull();
    }

    @Test
    void locationEventsTest() {
        Asset asset = getAssetRandomSampleGenerator();
        LocationEvent locationEventBack = getLocationEventRandomSampleGenerator();

        asset.addLocationEvents(locationEventBack);
        assertThat(asset.getLocationEvents()).containsOnly(locationEventBack);
        assertThat(locationEventBack.getAsset()).isEqualTo(asset);

        asset.removeLocationEvents(locationEventBack);
        assertThat(asset.getLocationEvents()).doesNotContain(locationEventBack);
        assertThat(locationEventBack.getAsset()).isNull();

        asset.locationEvents(new HashSet<>(Set.of(locationEventBack)));
        assertThat(asset.getLocationEvents()).containsOnly(locationEventBack);
        assertThat(locationEventBack.getAsset()).isEqualTo(asset);

        asset.setLocationEvents(new HashSet<>());
        assertThat(asset.getLocationEvents()).doesNotContain(locationEventBack);
        assertThat(locationEventBack.getAsset()).isNull();
    }

    @Test
    void siteTest() {
        Asset asset = getAssetRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        asset.setSite(siteBack);
        assertThat(asset.getSite()).isEqualTo(siteBack);

        asset.site(null);
        assertThat(asset.getSite()).isNull();
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
    void currentZoneTest() {
        Asset asset = getAssetRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        asset.setCurrentZone(zoneBack);
        assertThat(asset.getCurrentZone()).isEqualTo(zoneBack);

        asset.currentZone(null);
        assertThat(asset.getCurrentZone()).isNull();
    }
}
