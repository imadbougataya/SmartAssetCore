package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaintenanceEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaintenanceEventDTO.class);
        MaintenanceEventDTO maintenanceEventDTO1 = new MaintenanceEventDTO();
        maintenanceEventDTO1.setId(1L);
        MaintenanceEventDTO maintenanceEventDTO2 = new MaintenanceEventDTO();
        assertThat(maintenanceEventDTO1).isNotEqualTo(maintenanceEventDTO2);
        maintenanceEventDTO2.setId(maintenanceEventDTO1.getId());
        assertThat(maintenanceEventDTO1).isEqualTo(maintenanceEventDTO2);
        maintenanceEventDTO2.setId(2L);
        assertThat(maintenanceEventDTO1).isNotEqualTo(maintenanceEventDTO2);
        maintenanceEventDTO1.setId(null);
        assertThat(maintenanceEventDTO1).isNotEqualTo(maintenanceEventDTO2);
    }
}
