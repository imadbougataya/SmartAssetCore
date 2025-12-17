package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.MaintenanceStatus;
import com.ailab.smartasset.domain.enumeration.MaintenanceType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.MaintenanceEvent} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.MaintenanceEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /maintenance-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaintenanceEventCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MaintenanceType
     */
    public static class MaintenanceTypeFilter extends Filter<MaintenanceType> {

        public MaintenanceTypeFilter() {}

        public MaintenanceTypeFilter(MaintenanceTypeFilter filter) {
            super(filter);
        }

        @Override
        public MaintenanceTypeFilter copy() {
            return new MaintenanceTypeFilter(this);
        }
    }

    /**
     * Class for filtering MaintenanceStatus
     */
    public static class MaintenanceStatusFilter extends Filter<MaintenanceStatus> {

        public MaintenanceStatusFilter() {}

        public MaintenanceStatusFilter(MaintenanceStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaintenanceStatusFilter copy() {
            return new MaintenanceStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MaintenanceTypeFilter maintenanceType;

    private MaintenanceStatusFilter status;

    private InstantFilter requestedAt;

    private InstantFilter plannedAt;

    private InstantFilter startedAt;

    private InstantFilter finishedAt;

    private StringFilter title;

    private StringFilter description;

    private StringFilter technician;

    private IntegerFilter downtimeMinutes;

    private BigDecimalFilter costAmount;

    private StringFilter notes;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter assetId;

    private Boolean distinct;

    public MaintenanceEventCriteria() {}

    public MaintenanceEventCriteria(MaintenanceEventCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.maintenanceType = other.optionalMaintenanceType().map(MaintenanceTypeFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(MaintenanceStatusFilter::copy).orElse(null);
        this.requestedAt = other.optionalRequestedAt().map(InstantFilter::copy).orElse(null);
        this.plannedAt = other.optionalPlannedAt().map(InstantFilter::copy).orElse(null);
        this.startedAt = other.optionalStartedAt().map(InstantFilter::copy).orElse(null);
        this.finishedAt = other.optionalFinishedAt().map(InstantFilter::copy).orElse(null);
        this.title = other.optionalTitle().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.technician = other.optionalTechnician().map(StringFilter::copy).orElse(null);
        this.downtimeMinutes = other.optionalDowntimeMinutes().map(IntegerFilter::copy).orElse(null);
        this.costAmount = other.optionalCostAmount().map(BigDecimalFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MaintenanceEventCriteria copy() {
        return new MaintenanceEventCriteria(this);
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

    public MaintenanceTypeFilter getMaintenanceType() {
        return maintenanceType;
    }

    public Optional<MaintenanceTypeFilter> optionalMaintenanceType() {
        return Optional.ofNullable(maintenanceType);
    }

    public MaintenanceTypeFilter maintenanceType() {
        if (maintenanceType == null) {
            setMaintenanceType(new MaintenanceTypeFilter());
        }
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceTypeFilter maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public MaintenanceStatusFilter getStatus() {
        return status;
    }

    public Optional<MaintenanceStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public MaintenanceStatusFilter status() {
        if (status == null) {
            setStatus(new MaintenanceStatusFilter());
        }
        return status;
    }

    public void setStatus(MaintenanceStatusFilter status) {
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

    public InstantFilter getPlannedAt() {
        return plannedAt;
    }

    public Optional<InstantFilter> optionalPlannedAt() {
        return Optional.ofNullable(plannedAt);
    }

    public InstantFilter plannedAt() {
        if (plannedAt == null) {
            setPlannedAt(new InstantFilter());
        }
        return plannedAt;
    }

    public void setPlannedAt(InstantFilter plannedAt) {
        this.plannedAt = plannedAt;
    }

    public InstantFilter getStartedAt() {
        return startedAt;
    }

    public Optional<InstantFilter> optionalStartedAt() {
        return Optional.ofNullable(startedAt);
    }

    public InstantFilter startedAt() {
        if (startedAt == null) {
            setStartedAt(new InstantFilter());
        }
        return startedAt;
    }

    public void setStartedAt(InstantFilter startedAt) {
        this.startedAt = startedAt;
    }

    public InstantFilter getFinishedAt() {
        return finishedAt;
    }

    public Optional<InstantFilter> optionalFinishedAt() {
        return Optional.ofNullable(finishedAt);
    }

    public InstantFilter finishedAt() {
        if (finishedAt == null) {
            setFinishedAt(new InstantFilter());
        }
        return finishedAt;
    }

    public void setFinishedAt(InstantFilter finishedAt) {
        this.finishedAt = finishedAt;
    }

    public StringFilter getTitle() {
        return title;
    }

    public Optional<StringFilter> optionalTitle() {
        return Optional.ofNullable(title);
    }

    public StringFilter title() {
        if (title == null) {
            setTitle(new StringFilter());
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
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

    public StringFilter getTechnician() {
        return technician;
    }

    public Optional<StringFilter> optionalTechnician() {
        return Optional.ofNullable(technician);
    }

    public StringFilter technician() {
        if (technician == null) {
            setTechnician(new StringFilter());
        }
        return technician;
    }

    public void setTechnician(StringFilter technician) {
        this.technician = technician;
    }

    public IntegerFilter getDowntimeMinutes() {
        return downtimeMinutes;
    }

    public Optional<IntegerFilter> optionalDowntimeMinutes() {
        return Optional.ofNullable(downtimeMinutes);
    }

    public IntegerFilter downtimeMinutes() {
        if (downtimeMinutes == null) {
            setDowntimeMinutes(new IntegerFilter());
        }
        return downtimeMinutes;
    }

    public void setDowntimeMinutes(IntegerFilter downtimeMinutes) {
        this.downtimeMinutes = downtimeMinutes;
    }

    public BigDecimalFilter getCostAmount() {
        return costAmount;
    }

    public Optional<BigDecimalFilter> optionalCostAmount() {
        return Optional.ofNullable(costAmount);
    }

    public BigDecimalFilter costAmount() {
        if (costAmount == null) {
            setCostAmount(new BigDecimalFilter());
        }
        return costAmount;
    }

    public void setCostAmount(BigDecimalFilter costAmount) {
        this.costAmount = costAmount;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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
        final MaintenanceEventCriteria that = (MaintenanceEventCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maintenanceType, that.maintenanceType) &&
            Objects.equals(status, that.status) &&
            Objects.equals(requestedAt, that.requestedAt) &&
            Objects.equals(plannedAt, that.plannedAt) &&
            Objects.equals(startedAt, that.startedAt) &&
            Objects.equals(finishedAt, that.finishedAt) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(technician, that.technician) &&
            Objects.equals(downtimeMinutes, that.downtimeMinutes) &&
            Objects.equals(costAmount, that.costAmount) &&
            Objects.equals(notes, that.notes) &&
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
            maintenanceType,
            status,
            requestedAt,
            plannedAt,
            startedAt,
            finishedAt,
            title,
            description,
            technician,
            downtimeMinutes,
            costAmount,
            notes,
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
        return "MaintenanceEventCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMaintenanceType().map(f -> "maintenanceType=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalRequestedAt().map(f -> "requestedAt=" + f + ", ").orElse("") +
            optionalPlannedAt().map(f -> "plannedAt=" + f + ", ").orElse("") +
            optionalStartedAt().map(f -> "startedAt=" + f + ", ").orElse("") +
            optionalFinishedAt().map(f -> "finishedAt=" + f + ", ").orElse("") +
            optionalTitle().map(f -> "title=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalTechnician().map(f -> "technician=" + f + ", ").orElse("") +
            optionalDowntimeMinutes().map(f -> "downtimeMinutes=" + f + ", ").orElse("") +
            optionalCostAmount().map(f -> "costAmount=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
