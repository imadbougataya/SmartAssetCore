package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.DocumentEntityType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.DocumentLink} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.DocumentLinkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-links?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentLinkCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DocumentEntityType
     */
    public static class DocumentEntityTypeFilter extends Filter<DocumentEntityType> {

        public DocumentEntityTypeFilter() {}

        public DocumentEntityTypeFilter(DocumentEntityTypeFilter filter) {
            super(filter);
        }

        @Override
        public DocumentEntityTypeFilter copy() {
            return new DocumentEntityTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DocumentEntityTypeFilter entityType;

    private LongFilter entityId;

    private StringFilter label;

    private InstantFilter linkedAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter documentId;

    private Boolean distinct;

    public DocumentLinkCriteria() {}

    public DocumentLinkCriteria(DocumentLinkCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.entityType = other.optionalEntityType().map(DocumentEntityTypeFilter::copy).orElse(null);
        this.entityId = other.optionalEntityId().map(LongFilter::copy).orElse(null);
        this.label = other.optionalLabel().map(StringFilter::copy).orElse(null);
        this.linkedAt = other.optionalLinkedAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.documentId = other.optionalDocumentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DocumentLinkCriteria copy() {
        return new DocumentLinkCriteria(this);
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

    public DocumentEntityTypeFilter getEntityType() {
        return entityType;
    }

    public Optional<DocumentEntityTypeFilter> optionalEntityType() {
        return Optional.ofNullable(entityType);
    }

    public DocumentEntityTypeFilter entityType() {
        if (entityType == null) {
            setEntityType(new DocumentEntityTypeFilter());
        }
        return entityType;
    }

    public void setEntityType(DocumentEntityTypeFilter entityType) {
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

    public StringFilter getLabel() {
        return label;
    }

    public Optional<StringFilter> optionalLabel() {
        return Optional.ofNullable(label);
    }

    public StringFilter label() {
        if (label == null) {
            setLabel(new StringFilter());
        }
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public InstantFilter getLinkedAt() {
        return linkedAt;
    }

    public Optional<InstantFilter> optionalLinkedAt() {
        return Optional.ofNullable(linkedAt);
    }

    public InstantFilter linkedAt() {
        if (linkedAt == null) {
            setLinkedAt(new InstantFilter());
        }
        return linkedAt;
    }

    public void setLinkedAt(InstantFilter linkedAt) {
        this.linkedAt = linkedAt;
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

    public LongFilter getDocumentId() {
        return documentId;
    }

    public Optional<LongFilter> optionalDocumentId() {
        return Optional.ofNullable(documentId);
    }

    public LongFilter documentId() {
        if (documentId == null) {
            setDocumentId(new LongFilter());
        }
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
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
        final DocumentLinkCriteria that = (DocumentLinkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(label, that.label) &&
            Objects.equals(linkedAt, that.linkedAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            entityType,
            entityId,
            label,
            linkedAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            documentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentLinkCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEntityType().map(f -> "entityType=" + f + ", ").orElse("") +
            optionalEntityId().map(f -> "entityId=" + f + ", ").orElse("") +
            optionalLabel().map(f -> "label=" + f + ", ").orElse("") +
            optionalLinkedAt().map(f -> "linkedAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalDocumentId().map(f -> "documentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
