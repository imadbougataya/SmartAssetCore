package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.MovementRequestStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.AssetMovementRequest} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.AssetMovementRequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-movement-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetMovementRequestCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MovementRequestStatus
     */
    public static class MovementRequestStatusFilter extends Filter<MovementRequestStatus> {

        public MovementRequestStatusFilter() {}

        public MovementRequestStatusFilter(MovementRequestStatusFilter filter) {
            super(filter);
        }

        @Override
        public MovementRequestStatusFilter copy() {
            return new MovementRequestStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MovementRequestStatusFilter status;

    private InstantFilter requestedAt;

    private StringFilter reason;

    private StringFilter fromLocationLabel;

    private StringFilter toLocationLabel;

    private StringFilter esignWorkflowId;

    private StringFilter esignStatus;

    private InstantFilter esignLastUpdate;

    private InstantFilter signedAt;

    private InstantFilter executedAt;

    private StringFilter requestedBy;

    private StringFilter approvedBy;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter assetId;

    private Boolean distinct;

    public AssetMovementRequestCriteria() {}

    public AssetMovementRequestCriteria(AssetMovementRequestCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(MovementRequestStatusFilter::copy).orElse(null);
        this.requestedAt = other.optionalRequestedAt().map(InstantFilter::copy).orElse(null);
        this.reason = other.optionalReason().map(StringFilter::copy).orElse(null);
        this.fromLocationLabel = other.optionalFromLocationLabel().map(StringFilter::copy).orElse(null);
        this.toLocationLabel = other.optionalToLocationLabel().map(StringFilter::copy).orElse(null);
        this.esignWorkflowId = other.optionalEsignWorkflowId().map(StringFilter::copy).orElse(null);
        this.esignStatus = other.optionalEsignStatus().map(StringFilter::copy).orElse(null);
        this.esignLastUpdate = other.optionalEsignLastUpdate().map(InstantFilter::copy).orElse(null);
        this.signedAt = other.optionalSignedAt().map(InstantFilter::copy).orElse(null);
        this.executedAt = other.optionalExecutedAt().map(InstantFilter::copy).orElse(null);
        this.requestedBy = other.optionalRequestedBy().map(StringFilter::copy).orElse(null);
        this.approvedBy = other.optionalApprovedBy().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AssetMovementRequestCriteria copy() {
        return new AssetMovementRequestCriteria(this);
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

    public MovementRequestStatusFilter getStatus() {
        return status;
    }

    public Optional<MovementRequestStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public MovementRequestStatusFilter status() {
        if (status == null) {
            setStatus(new MovementRequestStatusFilter());
        }
        return status;
    }

    public void setStatus(MovementRequestStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getRequestedAt() {
        return requestedAt;
    }

    public Optional<InstantFilter> optionalRequestedAt() {
        return Optional.ofNullable(requestedAt);
    }

    public InstantFilter requestedAt() {
        if (requestedAt == null) {
            setRequestedAt(new InstantFilter());
        }
        return requestedAt;
    }

    public void setRequestedAt(InstantFilter requestedAt) {
        this.requestedAt = requestedAt;
    }

    public StringFilter getReason() {
        return reason;
    }

    public Optional<StringFilter> optionalReason() {
        return Optional.ofNullable(reason);
    }

    public StringFilter reason() {
        if (reason == null) {
            setReason(new StringFilter());
        }
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public StringFilter getFromLocationLabel() {
        return fromLocationLabel;
    }

    public Optional<StringFilter> optionalFromLocationLabel() {
        return Optional.ofNullable(fromLocationLabel);
    }

    public StringFilter fromLocationLabel() {
        if (fromLocationLabel == null) {
            setFromLocationLabel(new StringFilter());
        }
        return fromLocationLabel;
    }

    public void setFromLocationLabel(StringFilter fromLocationLabel) {
        this.fromLocationLabel = fromLocationLabel;
    }

    public StringFilter getToLocationLabel() {
        return toLocationLabel;
    }

    public Optional<StringFilter> optionalToLocationLabel() {
        return Optional.ofNullable(toLocationLabel);
    }

    public StringFilter toLocationLabel() {
        if (toLocationLabel == null) {
            setToLocationLabel(new StringFilter());
        }
        return toLocationLabel;
    }

    public void setToLocationLabel(StringFilter toLocationLabel) {
        this.toLocationLabel = toLocationLabel;
    }

    public StringFilter getEsignWorkflowId() {
        return esignWorkflowId;
    }

    public Optional<StringFilter> optionalEsignWorkflowId() {
        return Optional.ofNullable(esignWorkflowId);
    }

    public StringFilter esignWorkflowId() {
        if (esignWorkflowId == null) {
            setEsignWorkflowId(new StringFilter());
        }
        return esignWorkflowId;
    }

    public void setEsignWorkflowId(StringFilter esignWorkflowId) {
        this.esignWorkflowId = esignWorkflowId;
    }

    public StringFilter getEsignStatus() {
        return esignStatus;
    }

    public Optional<StringFilter> optionalEsignStatus() {
        return Optional.ofNullable(esignStatus);
    }

    public StringFilter esignStatus() {
        if (esignStatus == null) {
            setEsignStatus(new StringFilter());
        }
        return esignStatus;
    }

    public void setEsignStatus(StringFilter esignStatus) {
        this.esignStatus = esignStatus;
    }

    public InstantFilter getEsignLastUpdate() {
        return esignLastUpdate;
    }

    public Optional<InstantFilter> optionalEsignLastUpdate() {
        return Optional.ofNullable(esignLastUpdate);
    }

    public InstantFilter esignLastUpdate() {
        if (esignLastUpdate == null) {
            setEsignLastUpdate(new InstantFilter());
        }
        return esignLastUpdate;
    }

    public void setEsignLastUpdate(InstantFilter esignLastUpdate) {
        this.esignLastUpdate = esignLastUpdate;
    }

    public InstantFilter getSignedAt() {
        return signedAt;
    }

    public Optional<InstantFilter> optionalSignedAt() {
        return Optional.ofNullable(signedAt);
    }

    public InstantFilter signedAt() {
        if (signedAt == null) {
            setSignedAt(new InstantFilter());
        }
        return signedAt;
    }

    public void setSignedAt(InstantFilter signedAt) {
        this.signedAt = signedAt;
    }

    public InstantFilter getExecutedAt() {
        return executedAt;
    }

    public Optional<InstantFilter> optionalExecutedAt() {
        return Optional.ofNullable(executedAt);
    }

    public InstantFilter executedAt() {
        if (executedAt == null) {
            setExecutedAt(new InstantFilter());
        }
        return executedAt;
    }

    public void setExecutedAt(InstantFilter executedAt) {
        this.executedAt = executedAt;
    }

    public StringFilter getRequestedBy() {
        return requestedBy;
    }

    public Optional<StringFilter> optionalRequestedBy() {
        return Optional.ofNullable(requestedBy);
    }

    public StringFilter requestedBy() {
        if (requestedBy == null) {
            setRequestedBy(new StringFilter());
        }
        return requestedBy;
    }

    public void setRequestedBy(StringFilter requestedBy) {
        this.requestedBy = requestedBy;
    }

    public StringFilter getApprovedBy() {
        return approvedBy;
    }

    public Optional<StringFilter> optionalApprovedBy() {
        return Optional.ofNullable(approvedBy);
    }

    public StringFilter approvedBy() {
        if (approvedBy == null) {
            setApprovedBy(new StringFilter());
        }
        return approvedBy;
    }

    public void setApprovedBy(StringFilter approvedBy) {
        this.approvedBy = approvedBy;
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
        final AssetMovementRequestCriteria that = (AssetMovementRequestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(requestedAt, that.requestedAt) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(fromLocationLabel, that.fromLocationLabel) &&
            Objects.equals(toLocationLabel, that.toLocationLabel) &&
            Objects.equals(esignWorkflowId, that.esignWorkflowId) &&
            Objects.equals(esignStatus, that.esignStatus) &&
            Objects.equals(esignLastUpdate, that.esignLastUpdate) &&
            Objects.equals(signedAt, that.signedAt) &&
            Objects.equals(executedAt, that.executedAt) &&
            Objects.equals(requestedBy, that.requestedBy) &&
            Objects.equals(approvedBy, that.approvedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
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
            status,
            requestedAt,
            reason,
            fromLocationLabel,
            toLocationLabel,
            esignWorkflowId,
            esignStatus,
            esignLastUpdate,
            signedAt,
            executedAt,
            requestedBy,
            approvedBy,
            createdBy,
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
        return "AssetMovementRequestCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalRequestedAt().map(f -> "requestedAt=" + f + ", ").orElse("") +
            optionalReason().map(f -> "reason=" + f + ", ").orElse("") +
            optionalFromLocationLabel().map(f -> "fromLocationLabel=" + f + ", ").orElse("") +
            optionalToLocationLabel().map(f -> "toLocationLabel=" + f + ", ").orElse("") +
            optionalEsignWorkflowId().map(f -> "esignWorkflowId=" + f + ", ").orElse("") +
            optionalEsignStatus().map(f -> "esignStatus=" + f + ", ").orElse("") +
            optionalEsignLastUpdate().map(f -> "esignLastUpdate=" + f + ", ").orElse("") +
            optionalSignedAt().map(f -> "signedAt=" + f + ", ").orElse("") +
            optionalExecutedAt().map(f -> "executedAt=" + f + ", ").orElse("") +
            optionalRequestedBy().map(f -> "requestedBy=" + f + ", ").orElse("") +
            optionalApprovedBy().map(f -> "approvedBy=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
