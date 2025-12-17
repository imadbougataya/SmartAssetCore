package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemEventDTO.class);
        SystemEventDTO systemEventDTO1 = new SystemEventDTO();
        systemEventDTO1.setId(1L);
        SystemEventDTO systemEventDTO2 = new SystemEventDTO();
        assertThat(systemEventDTO1).isNotEqualTo(systemEventDTO2);
        systemEventDTO2.setId(systemEventDTO1.getId());
        assertThat(systemEventDTO1).isEqualTo(systemEventDTO2);
        systemEventDTO2.setId(2L);
        assertThat(systemEventDTO1).isNotEqualTo(systemEventDTO2);
        systemEventDTO1.setId(null);
        assertThat(systemEventDTO1).isNotEqualTo(systemEventDTO2);
    }
}
