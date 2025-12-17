package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.GatewayAsserts.*;
import static com.ailab.smartasset.domain.GatewayTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayMapperTest {

    private GatewayMapper gatewayMapper;

    @BeforeEach
    void setUp() {
        gatewayMapper = new GatewayMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGatewaySample1();
        var actual = gatewayMapper.toEntity(gatewayMapper.toDto(expected));
        assertGatewayAllPropertiesEquals(expected, actual);
    }
}
