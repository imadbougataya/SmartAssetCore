package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.SensorType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Sensor} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.SensorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sensors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SensorType
     */
    public static class SensorTypeFilter extends Filter<SensorType> {

        public SensorTypeFilter() {}

        public SensorTypeFilter(SensorTypeFilter filter) {
            super(filter);
        }

        @Override
        public SensorTypeFilter copy() {
            return new SensorTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SensorTypeFilter sensorType;

    private StringFilter name;

    private StringFilter unit;

    private BigDecimalFilter minThreshold;

    private BigDecimalFilter maxThreshold;

    private InstantFilter installedAt;

    private BooleanFilter active;

    private StringFilter externalId;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter measurementsId;

    private LongFilter assetId;

    private Boolean distinct;

    public SensorCriteria() {}

    public SensorCriteria(SensorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.sensorType = other.optionalSensorType().map(SensorTypeFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.unit = other.optionalUnit().map(StringFilter::copy).orElse(null);
        this.minThreshold = other.optionalMinThreshold().map(BigDecimalFilter::copy).orElse(null);
        this.maxThreshold = other.optionalMaxThreshold().map(BigDecimalFilter::copy).orElse(null);
        this.installedAt = other.optionalInstalledAt().map(InstantFilter::copy).orElse(null);
        this.active = other.optionalActive().map(BooleanFilter::copy).orElse(null);
        this.externalId = other.optionalExternalId().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.measurementsId = other.optionalMeasurementsId().map(LongFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SensorCriteria copy() {
        return new SensorCriteria(this);
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

    public SensorTypeFilter getSensorType() {
        return sensorType;
    }

    public Optional<SensorTypeFilter> optionalSensorType() {
        return Optional.ofNullable(sensorType);
    }

    public SensorTypeFilter sensorType() {
        if (sensorType == null) {
            setSensorType(new SensorTypeFilter());
        }
        return sensorType;
    }

    public void setSensorType(SensorTypeFilter sensorType) {
        this.sensorType = sensorType;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public Optional<StringFilter> optionalUnit() {
        return Optional.ofNullable(unit);
    }

    public StringFilter unit() {
        if (unit == null) {
            setUnit(new StringFilter());
        }
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getMinThreshold() {
        return minThreshold;
    }

    public Optional<BigDecimalFilter> optionalMinThreshold() {
        return Optional.ofNullable(minThreshold);
    }

    public BigDecimalFilter minThreshold() {
        if (minThreshold == null) {
            setMinThreshold(new BigDecimalFilter());
        }
        return minThreshold;
    }

    public void setMinThreshold(BigDecimalFilter minThreshold) {
        this.minThreshold = minThreshold;
    }

    public BigDecimalFilter getMaxThreshold() {
        return maxThreshold;
    }

    public Optional<BigDecimalFilter> optionalMaxThreshold() {
        return Optional.ofNullable(maxThreshold);
    }

    public BigDecimalFilter maxThreshold() {
        if (maxThreshold == null) {
            setMaxThreshold(new BigDecimalFilter());
        }
        return maxThreshold;
    }

    public void setMaxThreshold(BigDecimalFilter maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public InstantFilter getInstalledAt() {
        return installedAt;
    }

    public Optional<InstantFilter> optionalInstalledAt() {
        return Optional.ofNullable(installedAt);
    }

    public InstantFilter installedAt() {
        if (installedAt == null) {
            setInstalledAt(new InstantFilter());
        }
        return installedAt;
    }

    public void setInstalledAt(InstantFilter installedAt) {
        this.installedAt = installedAt;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public Optional<BooleanFilter> optionalActive() {
        return Optional.ofNullable(active);
    }

    public BooleanFilter active() {
        if (active == null) {
            setActive(new BooleanFilter());
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getExternalId() {
        return externalId;
    }

    public Optional<StringFilter> optionalExternalId() {
        return Optional.ofNullable(externalId);
    }

    public StringFilter externalId() {
        if (externalId == null) {
            setExternalId(new StringFilter());
        }
        return externalId;
    }

    public void setExternalId(StringFilter externalId) {
        this.externalId = externalId;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<StringFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new StringFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Optional<StringFilter> optionalLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            setLastModifiedBy(new StringFilter());
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Optional<InstantFilter> optionalLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            setLastModifiedDate(new InstantFilter());
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getMeasurementsId() {
        return measurementsId;
    }

    public Optional<LongFilter> optionalMeasurementsId() {
        return Optional.ofNullable(measurementsId);
    }

    public LongFilter measurementsId() {
        if (measurementsId == null) {
            setMeasurementsId(new LongFilter());
        }
        return measurementsId;
    }

    public void setMeasurementsId(LongFilter measurementsId) {
        this.measurementsId = measurementsId;
    }

    public LongFilter getAssetId() {
        return assetId;
    }

    public Optional<LongFilter> optionalAssetId() {
        return Optional.ofNullable(assetId);
    }

    public LongFilter assetId() {
        if (assetId == null) {
            setAssetId(new LongFilter());
        }
        return assetId;
    }

    public void setAssetId(LongFilter assetId) {
        this.assetId = assetId;
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
        final SensorCriteria that = (SensorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sensorType, that.sensorType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(minThreshold, that.minThreshold) &&
            Objects.equals(maxThreshold, that.maxThreshold) &&
            Objects.equals(installedAt, that.installedAt) &&
            Objects.equals(active, that.active) &&
            Objects.equals(externalId, that.externalId) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(measurementsId, that.measurementsId) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            sensorType,
            name,
            unit,
            minThreshold,
            maxThreshold,
            installedAt,
            active,
            externalId,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            measurementsId,
            assetId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSensorType().map(f -> "sensorType=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalUnit().map(f -> "unit=" + f + ", ").orElse("") +
            optionalMinThreshold().map(f -> "minThreshold=" + f + ", ").orElse("") +
            optionalMaxThreshold().map(f -> "maxThreshold=" + f + ", ").orElse("") +
            optionalInstalledAt().map(f -> "installedAt=" + f + ", ").orElse("") +
            optionalActive().map(f -> "active=" + f + ", ").orElse("") +
            optionalExternalId().map(f -> "externalId=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalMeasurementsId().map(f -> "measurementsId=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
