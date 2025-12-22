package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.LocationSource;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.LocationEvent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationEventDTO implements Serializable {

    private Long id;

    @NotNull
    private LocationSource source;

    @NotNull
    private Instant observedAt;

    private Integer zoneConfidence;

    private Integer rssi;

    private Integer txPower;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Double accuracyMeters;

    private Double speedKmh;

    @Size(max = 50)
    private String gnssConstellation;

    @Size(max = 4000)
    private String rawPayload;

    @NotNull
    private AssetDTO asset;

    private SensorDTO sensor;

    private SiteDTO matchedSite;

    private ZoneDTO matchedZone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationSource getSource() {
        return source;
    }

    public void setSource(LocationSource source) {
        this.source = source;
    }

    public Instant getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public Integer getZoneConfidence() {
        return zoneConfidence;
    }

    public void setZoneConfidence(Integer zoneConfidence) {
        this.zoneConfidence = zoneConfidence;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Integer getTxPower() {
        return txPower;
    }

    public void setTxPower(Integer txPower) {
        this.txPower = txPower;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAccuracyMeters() {
        return accuracyMeters;
    }

    public void setAccuracyMeters(Double accuracyMeters) {
        this.accuracyMeters = accuracyMeters;
    }

    public Double getSpeedKmh() {
        return speedKmh;
    }

    public void setSpeedKmh(Double speedKmh) {
        this.speedKmh = speedKmh;
    }

    public String getGnssConstellation() {
        return gnssConstellation;
    }

    public void setGnssConstellation(String gnssConstellation) {
        this.gnssConstellation = gnssConstellation;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
        this.asset = asset;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public SiteDTO getMatchedSite() {
        return matchedSite;
    }

    public void setMatchedSite(SiteDTO matchedSite) {
        this.matchedSite = matchedSite;
    }

    public ZoneDTO getMatchedZone() {
        return matchedZone;
    }

    public void setMatchedZone(ZoneDTO matchedZone) {
        this.matchedZone = matchedZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationEventDTO)) {
            return false;
        }

        LocationEventDTO locationEventDTO = (LocationEventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationEventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEventDTO{" +
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
            ", asset=" + getAsset() +
            ", sensor=" + getSensor() +
            ", matchedSite=" + getMatchedSite() +
            ", matchedZone=" + getMatchedZone() +
            "}";
    }
}
