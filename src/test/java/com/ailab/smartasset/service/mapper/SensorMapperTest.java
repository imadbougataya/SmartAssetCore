package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.SensorAsserts.*;
import static com.ailab.smartasset.domain.SensorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SensorMapperTest {

    private SensorMapper sensorMapper;

    @BeforeEach
    void setUp() {
        sensorMapper = new SensorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSensorSample1();
        var actual = sensorMapper.toEntity(sensorMapper.toDto(expected));
        assertSensorAllPropertiesEquals(expected, actual);
    }
}
