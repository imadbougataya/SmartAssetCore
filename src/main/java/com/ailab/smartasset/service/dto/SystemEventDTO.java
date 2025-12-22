package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.SystemEventSeverity;
import com.ailab.smartasset.domain.enumeration.SystemEventSource;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.SystemEvent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SystemEventDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String eventType;

    @NotNull
    private SystemEventSeverity severity;

    @NotNull
    private SystemEventSource source;

    @Size(max = 1000)
    private String message;

    @NotNull
    private Instant createdAt;

    @Size(max = 120)
    private String createdBy;

    @Size(max = 64)
    private String correlationId;

    @Lob
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public SystemEventSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(SystemEventSeverity severity) {
        this.severity = severity;
    }

    public SystemEventSource getSource() {
        return source;
    }

    public void setSource(SystemEventSource source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemEventDTO)) {
            return false;
        }

        SystemEventDTO systemEventDTO = (SystemEventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, systemEventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemEventDTO{" +
            "id=" + getId() +
            ", eventType='" + getEventType() + "'" +
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
