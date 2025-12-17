package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.ZoneAsserts.*;
import static com.ailab.smartasset.domain.ZoneTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZoneMapperTest {

    private ZoneMapper zoneMapper;

    @BeforeEach
    void setUp() {
        zoneMapper = new ZoneMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getZoneSample1();
        var actual = zoneMapper.toEntity(zoneMapper.toDto(expected));
        assertZoneAllPropertiesEquals(expected, actual);
    }
}
