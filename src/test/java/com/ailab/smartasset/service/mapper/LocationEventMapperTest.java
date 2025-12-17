package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.LocationEventAsserts.*;
import static com.ailab.smartasset.domain.LocationEventTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationEventMapperTest {

    private LocationEventMapper locationEventMapper;

    @BeforeEach
    void setUp() {
        locationEventMapper = new LocationEventMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLocationEventSample1();
        var actual = locationEventMapper.toEntity(locationEventMapper.toDto(expected));
        assertLocationEventAllPropertiesEquals(expected, actual);
    }
}
