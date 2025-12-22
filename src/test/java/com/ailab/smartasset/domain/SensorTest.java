package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.SensorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sensor.class);
        Sensor sensor1 = getSensorSample1();
        Sensor sensor2 = new Sensor();
        assertThat(sensor1).isNotEqualTo(sensor2);

        sensor2.setId(sensor1.getId());
        assertThat(sensor1).isEqualTo(sensor2);

        sensor2 = getSensorSample2();
        assertThat(sensor1).isNotEqualTo(sensor2);
    }

    @Test
    void assetTest() {
        Sensor sensor = getSensorRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        sensor.setAsset(assetBack);
        assertThat(sensor.getAsset()).isEqualTo(assetBack);

        sensor.asset(null);
        assertThat(sensor.getAsset()).isNull();
    }
}
