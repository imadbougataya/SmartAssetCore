package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationEventDTO.class);
        LocationEventDTO locationEventDTO1 = new LocationEventDTO();
        locationEventDTO1.setId(1L);
        LocationEventDTO locationEventDTO2 = new LocationEventDTO();
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
        locationEventDTO2.setId(locationEventDTO1.getId());
        assertThat(locationEventDTO1).isEqualTo(locationEventDTO2);
        locationEventDTO2.setId(2L);
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
        locationEventDTO1.setId(null);
        assertThat(locationEventDTO1).isNotEqualTo(locationEventDTO2);
    }
}
