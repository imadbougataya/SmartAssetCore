package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.MovementRequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A AssetMovementRequest.
 */
@Entity
@Table(name = "asset_movement_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetMovementRequest extends AbstractAuditingEntity<Long> implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MovementRequestStatus status;

    @NotNull
    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Size(max = 500)
    @Column(name = "reason", length = 500)
    private String reason;

    @Size(max = 200)
    @Column(name = "from_location_label", length = 200)
    private String fromLocationLabel;

    @Size(max = 200)
    @Column(name = "to_location_label", length = 200)
    private String toLocationLabel;

    @Size(max = 120)
    @Column(name = "esign_workflow_id", length = 120)
    private String esignWorkflowId;

    @Size(max = 80)
    @Column(name = "esign_status", length = 80)
    private String esignStatus;

    @Column(name = "esign_last_update")
    private Instant esignLastUpdate;

    @Column(name = "signed_at")
    private Instant signedAt;

    @Column(name = "executed_at")
    private Instant executedAt;

    @Size(max = 120)
    @Column(name = "requested_by", length = 120)
    private String requestedBy;

    @Size(max = 120)
    @Column(name = "approved_by", length = 120)
    private String approvedBy;

    // Inherited createdBy definition
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

    public AssetMovementRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementRequestStatus getStatus() {
        return this.status;
    }

    public AssetMovementRequest status(MovementRequestStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(MovementRequestStatus status) {
        this.status = status;
    }

    public Instant getRequestedAt() {
        return this.requestedAt;
    }

    public AssetMovementRequest requestedAt(Instant requestedAt) {
        this.setRequestedAt(requestedAt);
        return this;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getReason() {
        return this.reason;
    }

    public AssetMovementRequest reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromLocationLabel() {
        return this.fromLocationLabel;
    }

    public AssetMovementRequest fromLocationLabel(String fromLocationLabel) {
        this.setFromLocationLabel(fromLocationLabel);
        return this;
    }

    public void setFromLocationLabel(String fromLocationLabel) {
        this.fromLocationLabel = fromLocationLabel;
    }

    public String getToLocationLabel() {
        return this.toLocationLabel;
    }

    public AssetMovementRequest toLocationLabel(String toLocationLabel) {
        this.setToLocationLabel(toLocationLabel);
        return this;
    }

    public void setToLocationLabel(String toLocationLabel) {
        this.toLocationLabel = toLocationLabel;
    }

    public String getEsignWorkflowId() {
        return this.esignWorkflowId;
    }

    public AssetMovementRequest esignWorkflowId(String esignWorkflowId) {
        this.setEsignWorkflowId(esignWorkflowId);
        return this;
    }

    public void setEsignWorkflowId(String esignWorkflowId) {
        this.esignWorkflowId = esignWorkflowId;
    }

    public String getEsignStatus() {
        return this.esignStatus;
    }

    public AssetMovementRequest esignStatus(String esignStatus) {
        this.setEsignStatus(esignStatus);
        return this;
    }

    public void setEsignStatus(String esignStatus) {
        this.esignStatus = esignStatus;
    }

    public Instant getEsignLastUpdate() {
        return this.esignLastUpdate;
    }

    public AssetMovementRequest esignLastUpdate(Instant esignLastUpdate) {
        this.setEsignLastUpdate(esignLastUpdate);
        return this;
    }

    public void setEsignLastUpdate(Instant esignLastUpdate) {
        this.esignLastUpdate = esignLastUpdate;
    }

    public Instant getSignedAt() {
        return this.signedAt;
    }

    public AssetMovementRequest signedAt(Instant signedAt) {
        this.setSignedAt(signedAt);
        return this;
    }

    public void setSignedAt(Instant signedAt) {
        this.signedAt = signedAt;
    }

    public Instant getExecutedAt() {
        return this.executedAt;
    }

    public AssetMovementRequest executedAt(Instant executedAt) {
        this.setExecutedAt(executedAt);
        return this;
    }

    public void setExecutedAt(Instant executedAt) {
        this.executedAt = executedAt;
    }

    public String getRequestedBy() {
        return this.requestedBy;
    }

    public AssetMovementRequest requestedBy(String requestedBy) {
        this.setRequestedBy(requestedBy);
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getApprovedBy() {
        return this.approvedBy;
    }

    public AssetMovementRequest approvedBy(String approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    // Inherited createdBy methods
    public AssetMovementRequest createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    // Inherited createdDate methods
    public AssetMovementRequest createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    // Inherited lastModifiedBy methods
    public AssetMovementRequest lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // Inherited lastModifiedDate methods
    public AssetMovementRequest lastModifiedDate(Instant lastModifiedDate) {
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

    public AssetMovementRequest setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetMovementRequest asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetMovementRequest)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetMovementRequest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetMovementRequest{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", requestedAt='" + getRequestedAt() + "'" +
            ", reason='" + getReason() + "'" +
            ", fromLocationLabel='" + getFromLocationLabel() + "'" +
            ", toLocationLabel='" + getToLocationLabel() + "'" +
            ", esignWorkflowId='" + getEsignWorkflowId() + "'" +
            ", esignStatus='" + getEsignStatus() + "'" +
            ", esignLastUpdate='" + getEsignLastUpdate() + "'" +
            ", signedAt='" + getSignedAt() + "'" +
            ", executedAt='" + getExecutedAt() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", approvedBy='" + getApprovedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
