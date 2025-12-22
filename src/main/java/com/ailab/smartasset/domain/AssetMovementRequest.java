package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.EsignStatus;
import com.ailab.smartasset.domain.enumeration.MovementRequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetMovementRequest.
 */
@Entity
@Table(name = "asset_movement_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetMovementRequest implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "esign_status", nullable = false)
    private EsignStatus esignStatus;

    @Column(name = "esign_last_update")
    private Instant esignLastUpdate;

    @Column(name = "signed_at")
    private Instant signedAt;

    @Column(name = "executed_at")
    private Instant executedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productionLine", "allowedSite", "allowedZone" }, allowSetters = true)
    private Asset asset;

    @ManyToOne(optional = false)
    @NotNull
    private User requestedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approvedBy;

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

    public EsignStatus getEsignStatus() {
        return this.esignStatus;
    }

    public AssetMovementRequest esignStatus(EsignStatus esignStatus) {
        this.setEsignStatus(esignStatus);
        return this;
    }

    public void setEsignStatus(EsignStatus esignStatus) {
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

    public User getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(User user) {
        this.requestedBy = user;
    }

    public AssetMovementRequest requestedBy(User user) {
        this.setRequestedBy(user);
        return this;
    }

    public User getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(User user) {
        this.approvedBy = user;
    }

    public AssetMovementRequest approvedBy(User user) {
        this.setApprovedBy(user);
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
            "}";
    }
}
