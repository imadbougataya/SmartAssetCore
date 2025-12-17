package com.ailab.smartasset.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Zone} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.ZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private StringFilter zoneType;

    private DoubleFilter centerLat;

    private DoubleFilter centerLon;

    private DoubleFilter radiusMeters;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter locationEventsId;

    private LongFilter siteId;

    private Boolean distinct;

    public ZoneCriteria() {}

    public ZoneCriteria(ZoneCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.zoneType = other.optionalZoneType().map(StringFilter::copy).orElse(null);
        this.centerLat = other.optionalCenterLat().map(DoubleFilter::copy).orElse(null);
        this.centerLon = other.optionalCenterLon().map(DoubleFilter::copy).orElse(null);
        this.radiusMeters = other.optionalRadiusMeters().map(DoubleFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.locationEventsId = other.optionalLocationEventsId().map(LongFilter::copy).orElse(null);
        this.siteId = other.optionalSiteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ZoneCriteria copy() {
        return new ZoneCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getZoneType() {
        return zoneType;
    }

    public Optional<StringFilter> optionalZoneType() {
        return Optional.ofNullable(zoneType);
    }

    public StringFilter zoneType() {
        if (zoneType == null) {
            setZoneType(new StringFilter());
        }
        return zoneType;
    }

    public void setZoneType(StringFilter zoneType) {
        this.zoneType = zoneType;
    }

    public DoubleFilter getCenterLat() {
        return centerLat;
    }

    public Optional<DoubleFilter> optionalCenterLat() {
        return Optional.ofNullable(centerLat);
    }

    public DoubleFilter centerLat() {
        if (centerLat == null) {
            setCenterLat(new DoubleFilter());
        }
        return centerLat;
    }

    public void setCenterLat(DoubleFilter centerLat) {
        this.centerLat = centerLat;
    }

    public DoubleFilter getCenterLon() {
        return centerLon;
    }

    public Optional<DoubleFilter> optionalCenterLon() {
        return Optional.ofNullable(centerLon);
    }

    public DoubleFilter centerLon() {
        if (centerLon == null) {
            setCenterLon(new DoubleFilter());
        }
        return centerLon;
    }

    public void setCenterLon(DoubleFilter centerLon) {
        this.centerLon = centerLon;
    }

    public DoubleFilter getRadiusMeters() {
        return radiusMeters;
    }

    public Optional<DoubleFilter> optionalRadiusMeters() {
        return Optional.ofNullable(radiusMeters);
    }

    public DoubleFilter radiusMeters() {
        if (radiusMeters == null) {
            setRadiusMeters(new DoubleFilter());
        }
        return radiusMeters;
    }

    public void setRadiusMeters(DoubleFilter radiusMeters) {
        this.radiusMeters = radiusMeters;
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

    public LongFilter getLocationEventsId() {
        return locationEventsId;
    }

    public Optional<LongFilter> optionalLocationEventsId() {
        return Optional.ofNullable(locationEventsId);
    }

    public LongFilter locationEventsId() {
        if (locationEventsId == null) {
            setLocationEventsId(new LongFilter());
        }
        return locationEventsId;
    }

    public void setLocationEventsId(LongFilter locationEventsId) {
        this.locationEventsId = locationEventsId;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public Optional<LongFilter> optionalSiteId() {
        return Optional.ofNullable(siteId);
    }

    public LongFilter siteId() {
        if (siteId == null) {
            setSiteId(new LongFilter());
        }
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
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
        final ZoneCriteria that = (ZoneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(zoneType, that.zoneType) &&
            Objects.equals(centerLat, that.centerLat) &&
            Objects.equals(centerLon, that.centerLon) &&
            Objects.equals(radiusMeters, that.radiusMeters) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(locationEventsId, that.locationEventsId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            name,
            description,
            zoneType,
            centerLat,
            centerLon,
            radiusMeters,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            locationEventsId,
            siteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalZoneType().map(f -> "zoneType=" + f + ", ").orElse("") +
            optionalCenterLat().map(f -> "centerLat=" + f + ", ").orElse("") +
            optionalCenterLon().map(f -> "centerLon=" + f + ", ").orElse("") +
            optionalRadiusMeters().map(f -> "radiusMeters=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalLocationEventsId().map(f -> "locationEventsId=" + f + ", ").orElse("") +
            optionalSiteId().map(f -> "siteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
