package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.DocumentLinkTestSamples.*;
import static com.ailab.smartasset.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void linksTest() {
        Document document = getDocumentRandomSampleGenerator();
        DocumentLink documentLinkBack = getDocumentLinkRandomSampleGenerator();

        document.addLinks(documentLinkBack);
        assertThat(document.getLinks()).containsOnly(documentLinkBack);
        assertThat(documentLinkBack.getDocument()).isEqualTo(document);

        document.removeLinks(documentLinkBack);
        assertThat(document.getLinks()).doesNotContain(documentLinkBack);
        assertThat(documentLinkBack.getDocument()).isNull();

        document.links(new HashSet<>(Set.of(documentLinkBack)));
        assertThat(document.getLinks()).containsOnly(documentLinkBack);
        assertThat(documentLinkBack.getDocument()).isEqualTo(document);

        document.setLinks(new HashSet<>());
        assertThat(document.getLinks()).doesNotContain(documentLinkBack);
        assertThat(documentLinkBack.getDocument()).isNull();
    }
}
