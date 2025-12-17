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
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zone extends AbstractAuditingEntity<Long> implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "code", length = 80, nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 80)
    @Column(name = "zone_type", length = 80)
    private String zoneType;

    @Column(name = "center_lat")
    private Double centerLat;

    @Column(name = "center_lon")
    private Double centerLon;

    @Column(name = "radius_meters")
    private Double radiusMeters;

    // Inherited createdBy definition
    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asset", "zone", "gateway" }, allowSetters = true)
    private Set<LocationEvent> locationEvents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Zone code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Zone name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Zone description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZoneType() {
        return this.zoneType;
    }

    public Zone zoneType(String zoneType) {
        this.setZoneType(zoneType);
        return this;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

    public Double getCenterLat() {
        return this.centerLat;
    }

    public Zone centerLat(Double centerLat) {
        this.setCenterLat(centerLat);
        return this;
    }

    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }

    public Double getCenterLon() {
        return this.centerLon;
    }

    public Zone centerLon(Double centerLon) {
        this.setCenterLon(centerLon);
        return this;
    }

    public void setCenterLon(Double centerLon) {
        this.centerLon = centerLon;
    }

    public Double getRadiusMeters() {
        return this.radiusMeters;
    }

    public Zone radiusMeters(Double radiusMeters) {
        this.setRadiusMeters(radiusMeters);
        return this;
    }

    public void setRadiusMeters(Double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    // Inherited createdBy methods
    public Zone createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    // Inherited createdDate methods
    public Zone createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    // Inherited lastModifiedBy methods
    public Zone lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // Inherited lastModifiedDate methods
    public Zone lastModifiedDate(Instant lastModifiedDate) {
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

    public Zone setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<LocationEvent> getLocationEvents() {
        return this.locationEvents;
    }

    public void setLocationEvents(Set<LocationEvent> locationEvents) {
        if (this.locationEvents != null) {
            this.locationEvents.forEach(i -> i.setZone(null));
        }
        if (locationEvents != null) {
            locationEvents.forEach(i -> i.setZone(this));
        }
        this.locationEvents = locationEvents;
    }

    public Zone locationEvents(Set<LocationEvent> locationEvents) {
        this.setLocationEvents(locationEvents);
        return this;
    }

    public Zone addLocationEvents(LocationEvent locationEvent) {
        this.locationEvents.add(locationEvent);
        locationEvent.setZone(this);
        return this;
    }

    public Zone removeLocationEvents(LocationEvent locationEvent) {
        this.locationEvents.remove(locationEvent);
        locationEvent.setZone(null);
        return this;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Zone site(Site site) {
        this.setSite(site);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return getId() != null && getId().equals(((Zone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", zoneType='" + getZoneType() + "'" +
            ", centerLat=" + getCenterLat() +
            ", centerLon=" + getCenterLon() +
            ", radiusMeters=" + getRadiusMeters() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
