package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.MaintenanceEventAsserts.*;
import static com.ailab.smartasset.domain.MaintenanceEventTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaintenanceEventMapperTest {

    private MaintenanceEventMapper maintenanceEventMapper;

    @BeforeEach
    void setUp() {
        maintenanceEventMapper = new MaintenanceEventMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMaintenanceEventSample1();
        var actual = maintenanceEventMapper.toEntity(maintenanceEventMapper.toDto(expected));
        assertMaintenanceEventAllPropertiesEquals(expected, actual);
    }
}
