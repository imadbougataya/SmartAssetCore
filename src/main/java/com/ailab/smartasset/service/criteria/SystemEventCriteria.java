package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.SystemEntityType;
import com.ailab.smartasset.domain.enumeration.SystemEventSeverity;
import com.ailab.smartasset.domain.enumeration.SystemEventSource;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.SystemEvent} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.SystemEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /system-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SystemEventCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SystemEntityType
     */
    public static class SystemEntityTypeFilter extends Filter<SystemEntityType> {

        public SystemEntityTypeFilter() {}

        public SystemEntityTypeFilter(SystemEntityTypeFilter filter) {
            super(filter);
        }

        @Override
        public SystemEntityTypeFilter copy() {
            return new SystemEntityTypeFilter(this);
        }
    }

    /**
     * Class for filtering SystemEventSeverity
     */
    public static class SystemEventSeverityFilter extends Filter<SystemEventSeverity> {

        public SystemEventSeverityFilter() {}

        public SystemEventSeverityFilter(SystemEventSeverityFilter filter) {
            super(filter);
        }

        @Override
        public SystemEventSeverityFilter copy() {
            return new SystemEventSeverityFilter(this);
        }
    }

    /**
     * Class for filtering SystemEventSource
     */
    public static class SystemEventSourceFilter extends Filter<SystemEventSource> {

        public SystemEventSourceFilter() {}

        public SystemEventSourceFilter(SystemEventSourceFilter filter) {
            super(filter);
        }

        @Override
        public SystemEventSourceFilter copy() {
            return new SystemEventSourceFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eventType;

    private SystemEntityTypeFilter entityType;

    private LongFilter entityId;

    private SystemEventSeverityFilter severity;

    private SystemEventSourceFilter source;

    private StringFilter message;

    private InstantFilter createdAt;

    private StringFilter createdBy;

    private StringFilter correlationId;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter assetId;

    private Boolean distinct;

    public SystemEventCriteria() {}

    public SystemEventCriteria(SystemEventCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.eventType = other.optionalEventType().map(StringFilter::copy).orElse(null);
        this.entityType = other.optionalEntityType().map(SystemEntityTypeFilter::copy).orElse(null);
        this.entityId = other.optionalEntityId().map(LongFilter::copy).orElse(null);
        this.severity = other.optionalSeverity().map(SystemEventSeverityFilter::copy).orElse(null);
        this.source = other.optionalSource().map(SystemEventSourceFilter::copy).orElse(null);
        this.message = other.optionalMessage().map(StringFilter::copy).orElse(null);
        this.createdAt = other.optionalCreatedAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.correlationId = other.optionalCorrelationId().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SystemEventCriteria copy() {
        return new SystemEventCriteria(this);
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

    public StringFilter getEventType() {
        return eventType;
    }

    public Optional<StringFilter> optionalEventType() {
        return Optional.ofNullable(eventType);
    }

    public StringFilter eventType() {
        if (eventType == null) {
            setEventType(new StringFilter());
        }
        return eventType;
    }

    public void setEventType(StringFilter eventType) {
        this.eventType = eventType;
    }

    public SystemEntityTypeFilter getEntityType() {
        return entityType;
    }

    public Optional<SystemEntityTypeFilter> optionalEntityType() {
        return Optional.ofNullable(entityType);
    }

    public SystemEntityTypeFilter entityType() {
        if (entityType == null) {
            setEntityType(new SystemEntityTypeFilter());
        }
        return entityType;
    }

    public void setEntityType(SystemEntityTypeFilter entityType) {
        this.entityType = entityType;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public Optional<LongFilter> optionalEntityId() {
        return Optional.ofNullable(entityId);
    }

    public LongFilter entityId() {
        if (entityId == null) {
            setEntityId(new LongFilter());
        }
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public SystemEventSeverityFilter getSeverity() {
        return severity;
    }

    public Optional<SystemEventSeverityFilter> optionalSeverity() {
        return Optional.ofNullable(severity);
    }

    public SystemEventSeverityFilter severity() {
        if (severity == null) {
            setSeverity(new SystemEventSeverityFilter());
        }
        return severity;
    }

    public void setSeverity(SystemEventSeverityFilter severity) {
        this.severity = severity;
    }

    public SystemEventSourceFilter getSource() {
        return source;
    }

    public Optional<SystemEventSourceFilter> optionalSource() {
        return Optional.ofNullable(source);
    }

    public SystemEventSourceFilter source() {
        if (source == null) {
            setSource(new SystemEventSourceFilter());
        }
        return source;
    }

    public void setSource(SystemEventSourceFilter source) {
        this.source = source;
    }

    public StringFilter getMessage() {
        return message;
    }

    public Optional<StringFilter> optionalMessage() {
        return Optional.ofNullable(message);
    }

    public StringFilter message() {
        if (message == null) {
            setMessage(new StringFilter());
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public Optional<InstantFilter> optionalCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            setCreatedAt(new InstantFilter());
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
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

    public StringFilter getCorrelationId() {
        return correlationId;
    }

    public Optional<StringFilter> optionalCorrelationId() {
        return Optional.ofNullable(correlationId);
    }

    public StringFilter correlationId() {
        if (correlationId == null) {
            setCorrelationId(new StringFilter());
        }
        return correlationId;
    }

    public void setCorrelationId(StringFilter correlationId) {
        this.correlationId = correlationId;
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
        final SystemEventCriteria that = (SystemEventCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eventType, that.eventType) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(severity, that.severity) &&
            Objects.equals(source, that.source) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(correlationId, that.correlationId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            eventType,
            entityType,
            entityId,
            severity,
            source,
            message,
            createdAt,
            createdBy,
            correlationId,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            assetId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemEventCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEventType().map(f -> "eventType=" + f + ", ").orElse("") +
            optionalEntityType().map(f -> "entityType=" + f + ", ").orElse("") +
            optionalEntityId().map(f -> "entityId=" + f + ", ").orElse("") +
            optionalSeverity().map(f -> "severity=" + f + ", ").orElse("") +
            optionalSource().map(f -> "source=" + f + ", ").orElse("") +
            optionalMessage().map(f -> "message=" + f + ", ").orElse("") +
            optionalCreatedAt().map(f -> "createdAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCorrelationId().map(f -> "correlationId=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
