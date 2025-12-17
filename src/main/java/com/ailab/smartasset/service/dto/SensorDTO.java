package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.SensorType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.Sensor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensorDTO implements Serializable {

    private Long id;

    @NotNull
    private SensorType sensorType;

    @Size(max = 150)
    private String name;

    @Size(max = 30)
    private String unit;

    private BigDecimal minThreshold;

    private BigDecimal maxThreshold;

    private Instant installedAt;

    @NotNull
    private Boolean active;

    @Size(max = 120)
    private String externalId;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private AssetDTO asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(BigDecimal minThreshold) {
        this.minThreshold = minThreshold;
    }

    public BigDecimal getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(BigDecimal maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Instant getInstalledAt() {
        return installedAt;
    }

    public void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorDTO)) {
            return false;
        }

        SensorDTO sensorDTO = (SensorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sensorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorDTO{" +
            "id=" + getId() +
            ", sensorType='" + getSensorType() + "'" +
            ", name='" + getName() + "'" +
            ", unit='" + getUnit() + "'" +
            ", minThreshold=" + getMinThreshold() +
            ", maxThreshold=" + getMaxThreshold() +
            ", installedAt='" + getInstalledAt() + "'" +
            ", active='" + getActive() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", asset=" + getAsset() +
            "}";
    }
}
