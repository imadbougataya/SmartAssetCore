package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.ProductionLineAsserts.*;
import static com.ailab.smartasset.domain.ProductionLineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionLineMapperTest {

    private ProductionLineMapper productionLineMapper;

    @BeforeEach
    void setUp() {
        productionLineMapper = new ProductionLineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProductionLineSample1();
        var actual = productionLineMapper.toEntity(productionLineMapper.toDto(expected));
        assertProductionLineAllPropertiesEquals(expected, actual);
    }
}
