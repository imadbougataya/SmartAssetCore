package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentLinkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentLinkDTO.class);
        DocumentLinkDTO documentLinkDTO1 = new DocumentLinkDTO();
        documentLinkDTO1.setId(1L);
        DocumentLinkDTO documentLinkDTO2 = new DocumentLinkDTO();
        assertThat(documentLinkDTO1).isNotEqualTo(documentLinkDTO2);
        documentLinkDTO2.setId(documentLinkDTO1.getId());
        assertThat(documentLinkDTO1).isEqualTo(documentLinkDTO2);
        documentLinkDTO2.setId(2L);
        assertThat(documentLinkDTO1).isNotEqualTo(documentLinkDTO2);
        documentLinkDTO1.setId(null);
        assertThat(documentLinkDTO1).isNotEqualTo(documentLinkDTO2);
    }
}
