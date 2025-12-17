package com.ailab.smartasset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A SensorMeasurement.
 */
@Entity
@Table(name = "sensor_measurement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensorMeasurement implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    @NotNull
    @Column(name = "value", precision = 21, scale = 2, nullable = false)
    private BigDecimal value;

    @Size(max = 40)
    @Column(name = "quality", length = 40)
    private String quality;

    @Size(max = 80)
    @Column(name = "source", length = 80)
    private String source;

    // Inherited createdBy definition
    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "measurements", "asset" }, allowSetters = true)
    private Sensor sensor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SensorMeasurement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMeasuredAt() {
        return this.measuredAt;
    }

    public SensorMeasurement measuredAt(Instant measuredAt) {
        this.setMeasuredAt(measuredAt);
        return this;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public SensorMeasurement value(BigDecimal value) {
        this.setValue(value);
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getQuality() {
        return this.quality;
    }

    public SensorMeasurement quality(String quality) {
        this.setQuality(quality);
        return this;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSource() {
        return this.source;
    }

    public SensorMeasurement source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
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

    public SensorMeasurement setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public SensorMeasurement sensor(Sensor sensor) {
        this.setSensor(sensor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorMeasurement)) {
            return false;
        }
        return getId() != null && getId().equals(((SensorMeasurement) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorMeasurement{" +
                "id=" + getId() +
                ", measuredAt='" + getMeasuredAt() + "'" +
                ", value=" + getValue() +
                ", quality='" + getQuality() + "'" +
                ", source='" + getSource() + "'" +
                "}";
    }
}
