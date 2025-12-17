package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.SystemEntityType;
import com.ailab.smartasset.domain.enumeration.SystemEventSeverity;
import com.ailab.smartasset.domain.enumeration.SystemEventSource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A SystemEvent.
 */
@Entity
@Table(name = "system_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SystemEvent implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "event_type", length = 120, nullable = false)
    private String eventType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private SystemEntityType entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private SystemEventSeverity severity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private SystemEventSource source;

    @Size(max = 1000)
    @Column(name = "message", length = 1000)
    private String message;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 120)
    @Column(name = "created_by", length = 120)
    private String createdBy;

    @Size(max = 64)
    @Column(name = "correlation_id", length = 64)
    private String correlationId;

    @Lob
    @Column(name = "payload")
    private String payload;

    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

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

    public SystemEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return this.eventType;
    }

    public SystemEvent eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public SystemEntityType getEntityType() {
        return this.entityType;
    }

    public SystemEvent entityType(SystemEntityType entityType) {
        this.setEntityType(entityType);
        return this;
    }

    public void setEntityType(SystemEntityType entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public SystemEvent entityId(Long entityId) {
        this.setEntityId(entityId);
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public SystemEventSeverity getSeverity() {
        return this.severity;
    }

    public SystemEvent severity(SystemEventSeverity severity) {
        this.setSeverity(severity);
        return this;
    }

    public void setSeverity(SystemEventSeverity severity) {
        this.severity = severity;
    }

    public SystemEventSource getSource() {
        return this.source;
    }

    public SystemEvent source(SystemEventSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(SystemEventSource source) {
        this.source = source;
    }

    public String getMessage() {
        return this.message;
    }

    public SystemEvent message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public SystemEvent createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SystemEvent createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public SystemEvent correlationId(String correlationId) {
        this.setCorrelationId(correlationId);
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getPayload() {
        return this.payload;
    }

    public SystemEvent payload(String payload) {
        this.setPayload(payload);
        return this;
    }

    public void setPayload(String payload) {
        this.payload = payload;
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

    public SystemEvent setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public SystemEvent asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemEvent)) {
            return false;
        }
        return getId() != null && getId().equals(((SystemEvent) o).getId());
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
        return "SystemEvent{" +
                "id=" + getId() +
                ", eventType='" + getEventType() + "'" +
                ", entityType='" + getEntityType() + "'" +
                ", entityId=" + getEntityId() +
                ", severity='" + getSeverity() + "'" +
                ", source='" + getSource() + "'" +
                ", message='" + getMessage() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", createdBy='" + getCreatedBy() + "'" +
                ", correlationId='" + getCorrelationId() + "'" +
                ", payload='" + getPayload() + "'" +
                "}";
    }
}
