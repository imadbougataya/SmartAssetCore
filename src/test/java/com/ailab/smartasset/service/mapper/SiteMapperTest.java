package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.SiteAsserts.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiteMapperTest {

    private SiteMapper siteMapper;

    @BeforeEach
    void setUp() {
        siteMapper = new SiteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSiteSample1();
        var actual = siteMapper.toEntity(siteMapper.toDto(expected));
        assertSiteAllPropertiesEquals(expected, actual);
    }
}
