package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.MaintenanceStatus;
import com.ailab.smartasset.domain.enumeration.MaintenanceType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.MaintenanceEvent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaintenanceEventDTO implements Serializable {

    private Long id;

    @NotNull
    private MaintenanceType maintenanceType;

    @NotNull
    private MaintenanceStatus status;

    @NotNull
    private Instant requestedAt;

    private Instant plannedAt;

    private Instant startedAt;

    private Instant finishedAt;

    @Size(max = 180)
    private String title;

    @Size(max = 2000)
    private String description;

    @Size(max = 120)
    private String technician;

    private Integer downtimeMinutes;

    private BigDecimal costAmount;

    @Size(max = 2000)
    private String notes;

    @NotNull
    private AssetDTO asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Instant getPlannedAt() {
        return plannedAt;
    }

    public void setPlannedAt(Instant plannedAt) {
        this.plannedAt = plannedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public Integer getDowntimeMinutes() {
        return downtimeMinutes;
    }

    public void setDowntimeMinutes(Integer downtimeMinutes) {
        this.downtimeMinutes = downtimeMinutes;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(o instanceof MaintenanceEventDTO)) {
            return false;
        }

        MaintenanceEventDTO maintenanceEventDTO = (MaintenanceEventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, maintenanceEventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaintenanceEventDTO{" +
            "id=" + getId() +
            ", maintenanceType='" + getMaintenanceType() + "'" +
            ", status='" + getStatus() + "'" +
            ", requestedAt='" + getRequestedAt() + "'" +
            ", plannedAt='" + getPlannedAt() + "'" +
            ", startedAt='" + getStartedAt() + "'" +
            ", finishedAt='" + getFinishedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", technician='" + getTechnician() + "'" +
            ", downtimeMinutes=" + getDowntimeMinutes() +
            ", costAmount=" + getCostAmount() +
            ", notes='" + getNotes() + "'" +
            ", asset=" + getAsset() +
            "}";
    }
}
