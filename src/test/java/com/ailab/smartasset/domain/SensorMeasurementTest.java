package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.SensorMeasurementTestSamples.*;
import static com.ailab.smartasset.domain.SensorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorMeasurementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorMeasurement.class);
        SensorMeasurement sensorMeasurement1 = getSensorMeasurementSample1();
        SensorMeasurement sensorMeasurement2 = new SensorMeasurement();
        assertThat(sensorMeasurement1).isNotEqualTo(sensorMeasurement2);

        sensorMeasurement2.setId(sensorMeasurement1.getId());
        assertThat(sensorMeasurement1).isEqualTo(sensorMeasurement2);

        sensorMeasurement2 = getSensorMeasurementSample2();
        assertThat(sensorMeasurement1).isNotEqualTo(sensorMeasurement2);
    }

    @Test
    void sensorTest() {
        SensorMeasurement sensorMeasurement = getSensorMeasurementRandomSampleGenerator();
        Sensor sensorBack = getSensorRandomSampleGenerator();

        sensorMeasurement.setSensor(sensorBack);
        assertThat(sensorMeasurement.getSensor()).isEqualTo(sensorBack);

        sensorMeasurement.sensor(null);
        assertThat(sensorMeasurement.getSensor()).isNull();
    }
}
