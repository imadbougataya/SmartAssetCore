package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.SensorType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sensor extends AbstractAuditingEntity<Long> implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sensor_type", nullable = false)
    private SensorType sensorType;

    @Size(max = 150)
    @Column(name = "name", length = 150)
    private String name;

    @Size(max = 30)
    @Column(name = "unit", length = 30)
    private String unit;

    @Column(name = "min_threshold", precision = 21, scale = 2)
    private BigDecimal minThreshold;

    @Column(name = "max_threshold", precision = 21, scale = 2)
    private BigDecimal maxThreshold;

    @Column(name = "installed_at")
    private Instant installedAt;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Size(max = 120)
    @Column(name = "external_id", length = 120)
    private String externalId;

    // Inherited createdBy definition
    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sensor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sensor" }, allowSetters = true)
    private Set<SensorMeasurement> measurements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "sensors", "maintenanceEvents", "movementRequests", "locationEvents", "site", "productionLine", "currentZone" },
        allowSetters = true
    )
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sensor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorType getSensorType() {
        return this.sensorType;
    }

    public Sensor sensorType(SensorType sensorType) {
        this.setSensorType(sensorType);
        return this;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public String getName() {
        return this.name;
    }

    public Sensor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return this.unit;
    }

    public Sensor unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getMinThreshold() {
        return this.minThreshold;
    }

    public Sensor minThreshold(BigDecimal minThreshold) {
        this.setMinThreshold(minThreshold);
        return this;
    }

    public void setMinThreshold(BigDecimal minThreshold) {
        this.minThreshold = minThreshold;
    }

    public BigDecimal getMaxThreshold() {
        return this.maxThreshold;
    }

    public Sensor maxThreshold(BigDecimal maxThreshold) {
        this.setMaxThreshold(maxThreshold);
        return this;
    }

    public void setMaxThreshold(BigDecimal maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Instant getInstalledAt() {
        return this.installedAt;
    }

    public Sensor installedAt(Instant installedAt) {
        this.setInstalledAt(installedAt);
        return this;
    }

    public void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Sensor active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public Sensor externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    // Inherited createdBy methods
    public Sensor createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    // Inherited createdDate methods
    public Sensor createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    // Inherited lastModifiedBy methods
    public Sensor lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // Inherited lastModifiedDate methods
    public Sensor lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @org.springframework.data.annotation.Transient
    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Sensor setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<SensorMeasurement> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(Set<SensorMeasurement> sensorMeasurements) {
        if (this.measurements != null) {
            this.measurements.forEach(i -> i.setSensor(null));
        }
        if (sensorMeasurements != null) {
            sensorMeasurements.forEach(i -> i.setSensor(this));
        }
        this.measurements = sensorMeasurements;
    }

    public Sensor measurements(Set<SensorMeasurement> sensorMeasurements) {
        this.setMeasurements(sensorMeasurements);
        return this;
    }

    public Sensor addMeasurements(SensorMeasurement sensorMeasurement) {
        this.measurements.add(sensorMeasurement);
        sensorMeasurement.setSensor(this);
        return this;
    }

    public Sensor removeMeasurements(SensorMeasurement sensorMeasurement) {
        this.measurements.remove(sensorMeasurement);
        sensorMeasurement.setSensor(null);
        return this;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Sensor asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sensor)) {
            return false;
        }
        return getId() != null && getId().equals(((Sensor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sensor{" +
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
            "}";
    }
}
