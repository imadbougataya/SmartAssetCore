package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.AssetMovementRequestAsserts.*;
import static com.ailab.smartasset.domain.AssetMovementRequestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetMovementRequestMapperTest {

    private AssetMovementRequestMapper assetMovementRequestMapper;

    @BeforeEach
    void setUp() {
        assetMovementRequestMapper = new AssetMovementRequestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAssetMovementRequestSample1();
        var actual = assetMovementRequestMapper.toEntity(assetMovementRequestMapper.toDto(expected));
        assertAssetMovementRequestAllPropertiesEquals(expected, actual);
    }
}
