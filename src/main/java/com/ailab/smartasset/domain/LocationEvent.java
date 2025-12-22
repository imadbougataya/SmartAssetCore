package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.LocationSource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LocationEvent.
 */
@Entity
@Table(name = "location_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private LocationSource source;

    @NotNull
    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Column(name = "zone_confidence")
    private Integer zoneConfidence;

    @Column(name = "rssi")
    private Integer rssi;

    @Column(name = "tx_power")
    private Integer txPower;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "accuracy_meters")
    private Double accuracyMeters;

    @Column(name = "speed_kmh")
    private Double speedKmh;

    @Size(max = 50)
    @Column(name = "gnss_constellation", length = 50)
    private String gnssConstellation;

    @Size(max = 4000)
    @Column(name = "raw_payload", length = 4000)
    private String rawPayload;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productionLine", "allowedSite", "allowedZone" }, allowSetters = true)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "asset" }, allowSetters = true)
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Site matchedSite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "site" }, allowSetters = true)
    private Zone matchedZone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LocationEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationSource getSource() {
        return this.source;
    }

    public LocationEvent source(LocationSource source) {
        this.setSource(source);
        return this;
    }

    public void setSource(LocationSource source) {
        this.source = source;
    }

    public Instant getObservedAt() {
        return this.observedAt;
    }

    public LocationEvent observedAt(Instant observedAt) {
        this.setObservedAt(observedAt);
        return this;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public Integer getZoneConfidence() {
        return this.zoneConfidence;
    }

    public LocationEvent zoneConfidence(Integer zoneConfidence) {
        this.setZoneConfidence(zoneConfidence);
        return this;
    }

    public void setZoneConfidence(Integer zoneConfidence) {
        this.zoneConfidence = zoneConfidence;
    }

    public Integer getRssi() {
        return this.rssi;
    }

    public LocationEvent rssi(Integer rssi) {
        this.setRssi(rssi);
        return this;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Integer getTxPower() {
        return this.txPower;
    }

    public LocationEvent txPower(Integer txPower) {
        this.setTxPower(txPower);
        return this;
    }

    public void setTxPower(Integer txPower) {
        this.txPower = txPower;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public LocationEvent latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public LocationEvent longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAccuracyMeters() {
        return this.accuracyMeters;
    }

    public LocationEvent accuracyMeters(Double accuracyMeters) {
        this.setAccuracyMeters(accuracyMeters);
        return this;
    }

    public void setAccuracyMeters(Double accuracyMeters) {
        this.accuracyMeters = accuracyMeters;
    }

    public Double getSpeedKmh() {
        return this.speedKmh;
    }

    public LocationEvent speedKmh(Double speedKmh) {
        this.setSpeedKmh(speedKmh);
        return this;
    }

    public void setSpeedKmh(Double speedKmh) {
        this.speedKmh = speedKmh;
    }

    public String getGnssConstellation() {
        return this.gnssConstellation;
    }

    public LocationEvent gnssConstellation(String gnssConstellation) {
        this.setGnssConstellation(gnssConstellation);
        return this;
    }

    public void setGnssConstellation(String gnssConstellation) {
        this.gnssConstellation = gnssConstellation;
    }

    public String getRawPayload() {
        return this.rawPayload;
    }

    public LocationEvent rawPayload(String rawPayload) {
        this.setRawPayload(rawPayload);
        return this;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public LocationEvent asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocationEvent sensor(Sensor sensor) {
        this.setSensor(sensor);
        return this;
    }

    public Site getMatchedSite() {
        return this.matchedSite;
    }

    public void setMatchedSite(Site site) {
        this.matchedSite = site;
    }

    public LocationEvent matchedSite(Site site) {
        this.setMatchedSite(site);
        return this;
    }

    public Zone getMatchedZone() {
        return this.matchedZone;
    }

    public void setMatchedZone(Zone zone) {
        this.matchedZone = zone;
    }

    public LocationEvent matchedZone(Zone zone) {
        this.setMatchedZone(zone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationEvent)) {
            return false;
        }
        return getId() != null && getId().equals(((LocationEvent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEvent{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", observedAt='" + getObservedAt() + "'" +
            ", zoneConfidence=" + getZoneConfidence() +
            ", rssi=" + getRssi() +
            ", txPower=" + getTxPower() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", accuracyMeters=" + getAccuracyMeters() +
            ", speedKmh=" + getSpeedKmh() +
            ", gnssConstellation='" + getGnssConstellation() + "'" +
            ", rawPayload='" + getRawPayload() + "'" +
            "}";
    }
}
