package com.ailab.smartasset.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Site} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.SiteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private DoubleFilter centerLat;

    private DoubleFilter centerLon;

    private IntegerFilter radiusMeters;

    private Boolean distinct;

    public SiteCriteria() {}

    public SiteCriteria(SiteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.centerLat = other.optionalCenterLat().map(DoubleFilter::copy).orElse(null);
        this.centerLon = other.optionalCenterLon().map(DoubleFilter::copy).orElse(null);
        this.radiusMeters = other.optionalRadiusMeters().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SiteCriteria copy() {
        return new SiteCriteria(this);
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

    public IntegerFilter getRadiusMeters() {
        return radiusMeters;
    }

    public Optional<IntegerFilter> optionalRadiusMeters() {
        return Optional.ofNullable(radiusMeters);
    }

    public IntegerFilter radiusMeters() {
        if (radiusMeters == null) {
            setRadiusMeters(new IntegerFilter());
        }
        return radiusMeters;
    }

    public void setRadiusMeters(IntegerFilter radiusMeters) {
        this.radiusMeters = radiusMeters;
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
        final SiteCriteria that = (SiteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(centerLat, that.centerLat) &&
            Objects.equals(centerLon, that.centerLon) &&
            Objects.equals(radiusMeters, that.radiusMeters) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, centerLat, centerLon, radiusMeters, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalCenterLat().map(f -> "centerLat=" + f + ", ").orElse("") +
            optionalCenterLon().map(f -> "centerLon=" + f + ", ").orElse("") +
            optionalRadiusMeters().map(f -> "radiusMeters=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
