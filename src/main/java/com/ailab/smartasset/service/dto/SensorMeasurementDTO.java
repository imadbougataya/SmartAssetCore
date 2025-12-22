package com.ailab.smartasset.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.SensorMeasurement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensorMeasurementDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant measuredAt;

    @NotNull
    private BigDecimal value;

    @Size(max = 40)
    private String quality;

    @Size(max = 80)
    private String source;

    @NotNull
    private SensorDTO sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorMeasurementDTO)) {
            return false;
        }

        SensorMeasurementDTO sensorMeasurementDTO = (SensorMeasurementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sensorMeasurementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorMeasurementDTO{" +
            "id=" + getId() +
            ", measuredAt='" + getMeasuredAt() + "'" +
            ", value=" + getValue() +
            ", quality='" + getQuality() + "'" +
            ", source='" + getSource() + "'" +
            ", sensor=" + getSensor() +
            "}";
    }
}
