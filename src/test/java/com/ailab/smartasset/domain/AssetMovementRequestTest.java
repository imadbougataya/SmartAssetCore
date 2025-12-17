package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.AssetMovementRequestTestSamples.*;
import static com.ailab.smartasset.domain.AssetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetMovementRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetMovementRequest.class);
        AssetMovementRequest assetMovementRequest1 = getAssetMovementRequestSample1();
        AssetMovementRequest assetMovementRequest2 = new AssetMovementRequest();
        assertThat(assetMovementRequest1).isNotEqualTo(assetMovementRequest2);

        assetMovementRequest2.setId(assetMovementRequest1.getId());
        assertThat(assetMovementRequest1).isEqualTo(assetMovementRequest2);

        assetMovementRequest2 = getAssetMovementRequestSample2();
        assertThat(assetMovementRequest1).isNotEqualTo(assetMovementRequest2);
    }

    @Test
    void assetTest() {
        AssetMovementRequest assetMovementRequest = getAssetMovementRequestRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        assetMovementRequest.setAsset(assetBack);
        assertThat(assetMovementRequest.getAsset()).isEqualTo(assetBack);

        assetMovementRequest.asset(null);
        assertThat(assetMovementRequest.getAsset()).isNull();
    }
}
