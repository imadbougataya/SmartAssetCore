package com.ailab.smartasset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document extends AbstractAuditingEntity<Long> implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @NotNull
    @Size(max = 120)
    @Column(name = "mime_type", length = 120, nullable = false)
    private String mimeType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @NotNull
    @Size(max = 500)
    @Column(name = "storage_ref", length = 500, nullable = false)
    private String storageRef;

    @Size(max = 128)
    @Column(name = "checksum_sha_256", length = 128)
    private String checksumSha256;

    @NotNull
    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    @Size(max = 120)
    @Column(name = "uploaded_by", length = 120)
    private String uploadedBy;

    // Inherited createdBy definition
    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<DocumentLink> links = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Document fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Document mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSizeBytes() {
        return this.sizeBytes;
    }

    public Document sizeBytes(Long sizeBytes) {
        this.setSizeBytes(sizeBytes);
        return this;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getStorageRef() {
        return this.storageRef;
    }

    public Document storageRef(String storageRef) {
        this.setStorageRef(storageRef);
        return this;
    }

    public void setStorageRef(String storageRef) {
        this.storageRef = storageRef;
    }

    public String getChecksumSha256() {
        return this.checksumSha256;
    }

    public Document checksumSha256(String checksumSha256) {
        this.setChecksumSha256(checksumSha256);
        return this;
    }

    public void setChecksumSha256(String checksumSha256) {
        this.checksumSha256 = checksumSha256;
    }

    public Instant getUploadedAt() {
        return this.uploadedAt;
    }

    public Document uploadedAt(Instant uploadedAt) {
        this.setUploadedAt(uploadedAt);
        return this;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploadedBy() {
        return this.uploadedBy;
    }

    public Document uploadedBy(String uploadedBy) {
        this.setUploadedBy(uploadedBy);
        return this;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    // Inherited createdBy methods
    public Document createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    // Inherited createdDate methods
    public Document createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    // Inherited lastModifiedBy methods
    public Document lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // Inherited lastModifiedDate methods
    public Document lastModifiedDate(Instant lastModifiedDate) {
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

    public Document setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<DocumentLink> getLinks() {
        return this.links;
    }

    public void setLinks(Set<DocumentLink> documentLinks) {
        if (this.links != null) {
            this.links.forEach(i -> i.setDocument(null));
        }
        if (documentLinks != null) {
            documentLinks.forEach(i -> i.setDocument(this));
        }
        this.links = documentLinks;
    }

    public Document links(Set<DocumentLink> documentLinks) {
        this.setLinks(documentLinks);
        return this;
    }

    public Document addLinks(DocumentLink documentLink) {
        this.links.add(documentLink);
        documentLink.setDocument(this);
        return this;
    }

    public Document removeLinks(DocumentLink documentLink) {
        this.links.remove(documentLink);
        documentLink.setDocument(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return getId() != null && getId().equals(((Document) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", sizeBytes=" + getSizeBytes() +
            ", storageRef='" + getStorageRef() + "'" +
            ", checksumSha256='" + getChecksumSha256() + "'" +
            ", uploadedAt='" + getUploadedAt() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
