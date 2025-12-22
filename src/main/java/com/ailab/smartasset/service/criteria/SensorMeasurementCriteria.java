package com.ailab.smartasset.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.SensorMeasurement} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.SensorMeasurementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sensor-measurements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensorMeasurementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter measuredAt;

    private BigDecimalFilter value;

    private StringFilter quality;

    private StringFilter source;

    private LongFilter sensorId;

    private Boolean distinct;

    public SensorMeasurementCriteria() {}

    public SensorMeasurementCriteria(SensorMeasurementCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.measuredAt = other.optionalMeasuredAt().map(InstantFilter::copy).orElse(null);
        this.value = other.optionalValue().map(BigDecimalFilter::copy).orElse(null);
        this.quality = other.optionalQuality().map(StringFilter::copy).orElse(null);
        this.source = other.optionalSource().map(StringFilter::copy).orElse(null);
        this.sensorId = other.optionalSensorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SensorMeasurementCriteria copy() {
        return new SensorMeasurementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getMeasuredAt() {
        return measuredAt;
    }

    public Optional<InstantFilter> optionalMeasuredAt() {
        return Optional.ofNullable(measuredAt);
    }

    public InstantFilter measuredAt() {
        if (measuredAt == null) {
            setMeasuredAt(new InstantFilter());
        }
        return measuredAt;
    }

    public void setMeasuredAt(InstantFilter measuredAt) {
        this.measuredAt = measuredAt;
    }

    public BigDecimalFilter getValue() {
        return value;
    }

    public Optional<BigDecimalFilter> optionalValue() {
        return Optional.ofNullable(value);
    }

    public BigDecimalFilter value() {
        if (value == null) {
            setValue(new BigDecimalFilter());
        }
        return value;
    }

    public void setValue(BigDecimalFilter value) {
        this.value = value;
    }

    public StringFilter getQuality() {
        return quality;
    }

    public Optional<StringFilter> optionalQuality() {
        return Optional.ofNullable(quality);
    }

    public StringFilter quality() {
        if (quality == null) {
            setQuality(new StringFilter());
        }
        return quality;
    }

    public void setQuality(StringFilter quality) {
        this.quality = quality;
    }

    public StringFilter getSource() {
        return source;
    }

    public Optional<StringFilter> optionalSource() {
        return Optional.ofNullable(source);
    }

    public StringFilter source() {
        if (source == null) {
            setSource(new StringFilter());
        }
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public LongFilter getSensorId() {
        return sensorId;
    }

    public Optional<LongFilter> optionalSensorId() {
        return Optional.ofNullable(sensorId);
    }

    public LongFilter sensorId() {
        if (sensorId == null) {
            setSensorId(new LongFilter());
        }
        return sensorId;
    }

    public void setSensorId(LongFilter sensorId) {
        this.sensorId = sensorId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SensorMeasurementCriteria that = (SensorMeasurementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(measuredAt, that.measuredAt) &&
            Objects.equals(value, that.value) &&
            Objects.equals(quality, that.quality) &&
            Objects.equals(source, that.source) &&
            Objects.equals(sensorId, that.sensorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measuredAt, value, quality, source, sensorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorMeasurementCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMeasuredAt().map(f -> "measuredAt=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalQuality().map(f -> "quality=" + f + ", ").orElse("") +
            optionalSource().map(f -> "source=" + f + ", ").orElse("") +
            optionalSensorId().map(f -> "sensorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
