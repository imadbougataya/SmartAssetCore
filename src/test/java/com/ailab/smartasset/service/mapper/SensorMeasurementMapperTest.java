package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.SensorMeasurementAsserts.*;
import static com.ailab.smartasset.domain.SensorMeasurementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SensorMeasurementMapperTest {

    private SensorMeasurementMapper sensorMeasurementMapper;

    @BeforeEach
    void setUp() {
        sensorMeasurementMapper = new SensorMeasurementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSensorMeasurementSample1();
        var actual = sensorMeasurementMapper.toEntity(sensorMeasurementMapper.toDto(expected));
        assertSensorMeasurementAllPropertiesEquals(expected, actual);
    }
}
