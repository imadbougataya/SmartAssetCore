package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static com.ailab.smartasset.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
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
    void assetTest() {
        Document document = getDocumentRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        document.setAsset(assetBack);
        assertThat(document.getAsset()).isEqualTo(assetBack);

        document.asset(null);
        assertThat(document.getAsset()).isNull();
    }
}
