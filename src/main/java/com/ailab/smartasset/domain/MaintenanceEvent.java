package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.MaintenanceStatus;
import com.ailab.smartasset.domain.enumeration.MaintenanceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MaintenanceEvent.
 */
@Entity
@Table(name = "maintenance_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaintenanceEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_type", nullable = false)
    private MaintenanceType maintenanceType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MaintenanceStatus status;

    @NotNull
    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "planned_at")
    private Instant plannedAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Size(max = 180)
    @Column(name = "title", length = 180)
    private String title;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @Size(max = 120)
    @Column(name = "technician", length = 120)
    private String technician;

    @Column(name = "downtime_minutes")
    private Integer downtimeMinutes;

    @Column(name = "cost_amount", precision = 21, scale = 2)
    private BigDecimal costAmount;

    @Size(max = 2000)
    @Column(name = "notes", length = 2000)
    private String notes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productionLine", "allowedSite", "allowedZone" }, allowSetters = true)
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaintenanceEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaintenanceType getMaintenanceType() {
        return this.maintenanceType;
    }

    public MaintenanceEvent maintenanceType(MaintenanceType maintenanceType) {
        this.setMaintenanceType(maintenanceType);
        return this;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public MaintenanceStatus getStatus() {
        return this.status;
    }

    public MaintenanceEvent status(MaintenanceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public Instant getRequestedAt() {
        return this.requestedAt;
    }

    public MaintenanceEvent requestedAt(Instant requestedAt) {
        this.setRequestedAt(requestedAt);
        return this;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Instant getPlannedAt() {
        return this.plannedAt;
    }

    public MaintenanceEvent plannedAt(Instant plannedAt) {
        this.setPlannedAt(plannedAt);
        return this;
    }

    public void setPlannedAt(Instant plannedAt) {
        this.plannedAt = plannedAt;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public MaintenanceEvent startedAt(Instant startedAt) {
        this.setStartedAt(startedAt);
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public MaintenanceEvent finishedAt(Instant finishedAt) {
        this.setFinishedAt(finishedAt);
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public MaintenanceEvent title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public MaintenanceEvent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnician() {
        return this.technician;
    }

    public MaintenanceEvent technician(String technician) {
        this.setTechnician(technician);
        return this;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public Integer getDowntimeMinutes() {
        return this.downtimeMinutes;
    }

    public MaintenanceEvent downtimeMinutes(Integer downtimeMinutes) {
        this.setDowntimeMinutes(downtimeMinutes);
        return this;
    }

    public void setDowntimeMinutes(Integer downtimeMinutes) {
        this.downtimeMinutes = downtimeMinutes;
    }

    public BigDecimal getCostAmount() {
        return this.costAmount;
    }

    public MaintenanceEvent costAmount(BigDecimal costAmount) {
        this.setCostAmount(costAmount);
        return this;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getNotes() {
        return this.notes;
    }

    public MaintenanceEvent notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public MaintenanceEvent asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaintenanceEvent)) {
            return false;
        }
        return getId() != null && getId().equals(((MaintenanceEvent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaintenanceEvent{" +
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
            "}";
    }
}
