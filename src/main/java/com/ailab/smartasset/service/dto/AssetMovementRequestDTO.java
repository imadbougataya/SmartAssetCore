package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.EsignStatus;
import com.ailab.smartasset.domain.enumeration.MovementRequestStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.AssetMovementRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetMovementRequestDTO implements Serializable {

    private Long id;

    @NotNull
    private MovementRequestStatus status;

    @NotNull
    private Instant requestedAt;

    @Size(max = 500)
    private String reason;

    @Size(max = 200)
    private String fromLocationLabel;

    @Size(max = 200)
    private String toLocationLabel;

    @Size(max = 120)
    private String esignWorkflowId;

    @NotNull
    private EsignStatus esignStatus;

    private Instant esignLastUpdate;

    private Instant signedAt;

    private Instant executedAt;

    @NotNull
    private AssetDTO asset;

    @NotNull
    private UserDTO requestedBy;

    private UserDTO approvedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementRequestStatus getStatus() {
        return status;
    }

    public void setStatus(MovementRequestStatus status) {
        this.status = status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromLocationLabel() {
        return fromLocationLabel;
    }

    public void setFromLocationLabel(String fromLocationLabel) {
        this.fromLocationLabel = fromLocationLabel;
    }

    public String getToLocationLabel() {
        return toLocationLabel;
    }

    public void setToLocationLabel(String toLocationLabel) {
        this.toLocationLabel = toLocationLabel;
    }

    public String getEsignWorkflowId() {
        return esignWorkflowId;
    }

    public void setEsignWorkflowId(String esignWorkflowId) {
        this.esignWorkflowId = esignWorkflowId;
    }

    public EsignStatus getEsignStatus() {
        return esignStatus;
    }

    public void setEsignStatus(EsignStatus esignStatus) {
        this.esignStatus = esignStatus;
    }

    public Instant getEsignLastUpdate() {
        return esignLastUpdate;
    }

    public void setEsignLastUpdate(Instant esignLastUpdate) {
        this.esignLastUpdate = esignLastUpdate;
    }

    public Instant getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(Instant signedAt) {
        this.signedAt = signedAt;
    }

    public Instant getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Instant executedAt) {
        this.executedAt = executedAt;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
        this.asset = asset;
    }

    public UserDTO getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(UserDTO requestedBy) {
        this.requestedBy = requestedBy;
    }

    public UserDTO getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserDTO approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetMovementRequestDTO)) {
            return false;
        }

        AssetMovementRequestDTO assetMovementRequestDTO = (AssetMovementRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetMovementRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetMovementRequestDTO{" +
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
            ", asset=" + getAsset() +
            ", requestedBy=" + getRequestedBy() +
            ", approvedBy=" + getApprovedBy() +
            "}";
    }
}
