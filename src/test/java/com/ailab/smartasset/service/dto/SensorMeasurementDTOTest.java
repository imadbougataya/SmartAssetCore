package com.ailab.smartasset.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorMeasurementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorMeasurementDTO.class);
        SensorMeasurementDTO sensorMeasurementDTO1 = new SensorMeasurementDTO();
        sensorMeasurementDTO1.setId(1L);
        SensorMeasurementDTO sensorMeasurementDTO2 = new SensorMeasurementDTO();
        assertThat(sensorMeasurementDTO1).isNotEqualTo(sensorMeasurementDTO2);
        sensorMeasurementDTO2.setId(sensorMeasurementDTO1.getId());
        assertThat(sensorMeasurementDTO1).isEqualTo(sensorMeasurementDTO2);
        sensorMeasurementDTO2.setId(2L);
        assertThat(sensorMeasurementDTO1).isNotEqualTo(sensorMeasurementDTO2);
        sensorMeasurementDTO1.setId(null);
        assertThat(sensorMeasurementDTO1).isNotEqualTo(sensorMeasurementDTO2);
    }
}
