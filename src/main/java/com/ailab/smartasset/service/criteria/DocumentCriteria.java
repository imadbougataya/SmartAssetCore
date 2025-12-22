package com.ailab.smartasset.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Document} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.DocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fileName;

    private StringFilter mimeType;

    private LongFilter sizeBytes;

    private StringFilter storageRef;

    private StringFilter checksumSha256;

    private InstantFilter uploadedAt;

    private StringFilter uploadedBy;

    private LongFilter assetId;

    private Boolean distinct;

    public DocumentCriteria() {}

    public DocumentCriteria(DocumentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fileName = other.optionalFileName().map(StringFilter::copy).orElse(null);
        this.mimeType = other.optionalMimeType().map(StringFilter::copy).orElse(null);
        this.sizeBytes = other.optionalSizeBytes().map(LongFilter::copy).orElse(null);
        this.storageRef = other.optionalStorageRef().map(StringFilter::copy).orElse(null);
        this.checksumSha256 = other.optionalChecksumSha256().map(StringFilter::copy).orElse(null);
        this.uploadedAt = other.optionalUploadedAt().map(InstantFilter::copy).orElse(null);
        this.uploadedBy = other.optionalUploadedBy().map(StringFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DocumentCriteria copy() {
        return new DocumentCriteria(this);
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

    public StringFilter getFileName() {
        return fileName;
    }

    public Optional<StringFilter> optionalFileName() {
        return Optional.ofNullable(fileName);
    }

    public StringFilter fileName() {
        if (fileName == null) {
            setFileName(new StringFilter());
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getMimeType() {
        return mimeType;
    }

    public Optional<StringFilter> optionalMimeType() {
        return Optional.ofNullable(mimeType);
    }

    public StringFilter mimeType() {
        if (mimeType == null) {
            setMimeType(new StringFilter());
        }
        return mimeType;
    }

    public void setMimeType(StringFilter mimeType) {
        this.mimeType = mimeType;
    }

    public LongFilter getSizeBytes() {
        return sizeBytes;
    }

    public Optional<LongFilter> optionalSizeBytes() {
        return Optional.ofNullable(sizeBytes);
    }

    public LongFilter sizeBytes() {
        if (sizeBytes == null) {
            setSizeBytes(new LongFilter());
        }
        return sizeBytes;
    }

    public void setSizeBytes(LongFilter sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public StringFilter getStorageRef() {
        return storageRef;
    }

    public Optional<StringFilter> optionalStorageRef() {
        return Optional.ofNullable(storageRef);
    }

    public StringFilter storageRef() {
        if (storageRef == null) {
            setStorageRef(new StringFilter());
        }
        return storageRef;
    }

    public void setStorageRef(StringFilter storageRef) {
        this.storageRef = storageRef;
    }

    public StringFilter getChecksumSha256() {
        return checksumSha256;
    }

    public Optional<StringFilter> optionalChecksumSha256() {
        return Optional.ofNullable(checksumSha256);
    }

    public StringFilter checksumSha256() {
        if (checksumSha256 == null) {
            setChecksumSha256(new StringFilter());
        }
        return checksumSha256;
    }

    public void setChecksumSha256(StringFilter checksumSha256) {
        this.checksumSha256 = checksumSha256;
    }

    public InstantFilter getUploadedAt() {
        return uploadedAt;
    }

    public Optional<InstantFilter> optionalUploadedAt() {
        return Optional.ofNullable(uploadedAt);
    }

    public InstantFilter uploadedAt() {
        if (uploadedAt == null) {
            setUploadedAt(new InstantFilter());
        }
        return uploadedAt;
    }

    public void setUploadedAt(InstantFilter uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public StringFilter getUploadedBy() {
        return uploadedBy;
    }

    public Optional<StringFilter> optionalUploadedBy() {
        return Optional.ofNullable(uploadedBy);
    }

    public StringFilter uploadedBy() {
        if (uploadedBy == null) {
            setUploadedBy(new StringFilter());
        }
        return uploadedBy;
    }

    public void setUploadedBy(StringFilter uploadedBy) {
        this.uploadedBy = uploadedBy;
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
        final DocumentCriteria that = (DocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(mimeType, that.mimeType) &&
            Objects.equals(sizeBytes, that.sizeBytes) &&
            Objects.equals(storageRef, that.storageRef) &&
            Objects.equals(checksumSha256, that.checksumSha256) &&
            Objects.equals(uploadedAt, that.uploadedAt) &&
            Objects.equals(uploadedBy, that.uploadedBy) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, mimeType, sizeBytes, storageRef, checksumSha256, uploadedAt, uploadedBy, assetId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFileName().map(f -> "fileName=" + f + ", ").orElse("") +
            optionalMimeType().map(f -> "mimeType=" + f + ", ").orElse("") +
            optionalSizeBytes().map(f -> "sizeBytes=" + f + ", ").orElse("") +
            optionalStorageRef().map(f -> "storageRef=" + f + ", ").orElse("") +
            optionalChecksumSha256().map(f -> "checksumSha256=" + f + ", ").orElse("") +
            optionalUploadedAt().map(f -> "uploadedAt=" + f + ", ").orElse("") +
            optionalUploadedBy().map(f -> "uploadedBy=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
