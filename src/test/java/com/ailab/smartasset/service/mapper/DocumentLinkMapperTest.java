package com.ailab.smartasset.service.mapper;

import static com.ailab.smartasset.domain.DocumentLinkAsserts.*;
import static com.ailab.smartasset.domain.DocumentLinkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentLinkMapperTest {

    private DocumentLinkMapper documentLinkMapper;

    @BeforeEach
    void setUp() {
        documentLinkMapper = new DocumentLinkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentLinkSample1();
        var actual = documentLinkMapper.toEntity(documentLinkMapper.toDto(expected));
        assertDocumentLinkAllPropertiesEquals(expected, actual);
    }
}
