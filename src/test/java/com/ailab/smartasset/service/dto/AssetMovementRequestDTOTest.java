package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetMovementRequestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetMovementRequestDTO.class);
        AssetMovementRequestDTO assetMovementRequestDTO1 = new AssetMovementRequestDTO();
        assetMovementRequestDTO1.setId(1L);
        AssetMovementRequestDTO assetMovementRequestDTO2 = new AssetMovementRequestDTO();
        assertThat(assetMovementRequestDTO1).isNotEqualTo(assetMovementRequestDTO2);
        assetMovementRequestDTO2.setId(assetMovementRequestDTO1.getId());
        assertThat(assetMovementRequestDTO1).isEqualTo(assetMovementRequestDTO2);
        assetMovementRequestDTO2.setId(2L);
        assertThat(assetMovementRequestDTO1).isNotEqualTo(assetMovementRequestDTO2);
        assetMovementRequestDTO1.setId(null);
        assertThat(assetMovementRequestDTO1).isNotEqualTo(assetMovementRequestDTO2);
    }
}
