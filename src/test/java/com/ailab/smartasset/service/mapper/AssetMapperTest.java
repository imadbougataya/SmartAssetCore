package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.AssetAsserts.*;
import static com.ailab.smartasset.domain.AssetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetMapperTest {

    private AssetMapper assetMapper;

    @BeforeEach
    void setUp() {
        assetMapper = new AssetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAssetSample1();
        var actual = assetMapper.toEntity(assetMapper.toDto(expected));
        assertAssetAllPropertiesEquals(expected, actual);
    }
}
