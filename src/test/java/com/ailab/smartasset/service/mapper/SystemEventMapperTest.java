package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.SystemEventAsserts.*;
import static com.ailab.smartasset.domain.SystemEventTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemEventMapperTest {

    private SystemEventMapper systemEventMapper;

    @BeforeEach
    void setUp() {
        systemEventMapper = new SystemEventMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSystemEventSample1();
        var actual = systemEventMapper.toEntity(systemEventMapper.toDto(expected));
        assertSystemEventAllPropertiesEquals(expected, actual);
    }
}
