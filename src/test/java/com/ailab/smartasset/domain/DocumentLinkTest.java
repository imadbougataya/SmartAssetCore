package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.DocumentLinkTestSamples.*;
import static com.ailab.smartasset.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentLinkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentLink.class);
        DocumentLink documentLink1 = getDocumentLinkSample1();
        DocumentLink documentLink2 = new DocumentLink();
        assertThat(documentLink1).isNotEqualTo(documentLink2);

        documentLink2.setId(documentLink1.getId());
        assertThat(documentLink1).isEqualTo(documentLink2);

        documentLink2 = getDocumentLinkSample2();
        assertThat(documentLink1).isNotEqualTo(documentLink2);
    }

    @Test
    void documentTest() {
        DocumentLink documentLink = getDocumentLinkRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        documentLink.setDocument(documentBack);
        assertThat(documentLink.getDocument()).isEqualTo(documentBack);

        documentLink.document(null);
        assertThat(documentLink.getDocument()).isNull();
    }
}
