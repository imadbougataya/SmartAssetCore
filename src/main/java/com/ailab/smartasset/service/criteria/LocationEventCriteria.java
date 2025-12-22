package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.LocationSource;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.LocationEvent} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.LocationEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationEventCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LocationSource
     */
    public static class LocationSourceFilter extends Filter<LocationSource> {

        public LocationSourceFilter() {}

        public LocationSourceFilter(LocationSourceFilter filter) {
            super(filter);
        }

        @Override
        public LocationSourceFilter copy() {
            return new LocationSourceFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocationSourceFilter source;

    private InstantFilter observedAt;

    private IntegerFilter zoneConfidence;

    private IntegerFilter rssi;

    private IntegerFilter txPower;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private DoubleFilter accuracyMeters;

    private DoubleFilter speedKmh;

    private StringFilter gnssConstellation;

    private StringFilter rawPayload;

    private LongFilter assetId;

    private LongFilter sensorId;

    private LongFilter matchedSiteId;

    private LongFilter matchedZoneId;

    private Boolean distinct;

    public LocationEventCriteria() {}

    public LocationEventCriteria(LocationEventCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.source = other.optionalSource().map(LocationSourceFilter::copy).orElse(null);
        this.observedAt = other.optionalObservedAt().map(InstantFilter::copy).orElse(null);
        this.zoneConfidence = other.optionalZoneConfidence().map(IntegerFilter::copy).orElse(null);
        this.rssi = other.optionalRssi().map(IntegerFilter::copy).orElse(null);
        this.txPower = other.optionalTxPower().map(IntegerFilter::copy).orElse(null);
        this.latitude = other.optionalLatitude().map(DoubleFilter::copy).orElse(null);
        this.longitude = other.optionalLongitude().map(DoubleFilter::copy).orElse(null);
        this.accuracyMeters = other.optionalAccuracyMeters().map(DoubleFilter::copy).orElse(null);
        this.speedKmh = other.optionalSpeedKmh().map(DoubleFilter::copy).orElse(null);
        this.gnssConstellation = other.optionalGnssConstellation().map(StringFilter::copy).orElse(null);
        this.rawPayload = other.optionalRawPayload().map(StringFilter::copy).orElse(null);
        this.assetId = other.optionalAssetId().map(LongFilter::copy).orElse(null);
        this.sensorId = other.optionalSensorId().map(LongFilter::copy).orElse(null);
        this.matchedSiteId = other.optionalMatchedSiteId().map(LongFilter::copy).orElse(null);
        this.matchedZoneId = other.optionalMatchedZoneId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public LocationEventCriteria copy() {
        return new LocationEventCriteria(this);
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

    public LocationSourceFilter getSource() {
        return source;
    }

    public Optional<LocationSourceFilter> optionalSource() {
        return Optional.ofNullable(source);
    }

    public LocationSourceFilter source() {
        if (source == null) {
            setSource(new LocationSourceFilter());
        }
        return source;
    }

    public void setSource(LocationSourceFilter source) {
        this.source = source;
    }

    public InstantFilter getObservedAt() {
        return observedAt;
    }

    public Optional<InstantFilter> optionalObservedAt() {
        return Optional.ofNullable(observedAt);
    }

    public InstantFilter observedAt() {
        if (observedAt == null) {
            setObservedAt(new InstantFilter());
        }
        return observedAt;
    }

    public void setObservedAt(InstantFilter observedAt) {
        this.observedAt = observedAt;
    }

    public IntegerFilter getZoneConfidence() {
        return zoneConfidence;
    }

    public Optional<IntegerFilter> optionalZoneConfidence() {
        return Optional.ofNullable(zoneConfidence);
    }

    public IntegerFilter zoneConfidence() {
        if (zoneConfidence == null) {
            setZoneConfidence(new IntegerFilter());
        }
        return zoneConfidence;
    }

    public void setZoneConfidence(IntegerFilter zoneConfidence) {
        this.zoneConfidence = zoneConfidence;
    }

    public IntegerFilter getRssi() {
        return rssi;
    }

    public Optional<IntegerFilter> optionalRssi() {
        return Optional.ofNullable(rssi);
    }

    public IntegerFilter rssi() {
        if (rssi == null) {
            setRssi(new IntegerFilter());
        }
        return rssi;
    }

    public void setRssi(IntegerFilter rssi) {
        this.rssi = rssi;
    }

    public IntegerFilter getTxPower() {
        return txPower;
    }

    public Optional<IntegerFilter> optionalTxPower() {
        return Optional.ofNullable(txPower);
    }

    public IntegerFilter txPower() {
        if (txPower == null) {
            setTxPower(new IntegerFilter());
        }
        return txPower;
    }

    public void setTxPower(IntegerFilter txPower) {
        this.txPower = txPower;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public Optional<DoubleFilter> optionalLatitude() {
        return Optional.ofNullable(latitude);
    }

    public DoubleFilter latitude() {
        if (latitude == null) {
            setLatitude(new DoubleFilter());
        }
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public Optional<DoubleFilter> optionalLongitude() {
        return Optional.ofNullable(longitude);
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            setLongitude(new DoubleFilter());
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public DoubleFilter getAccuracyMeters() {
        return accuracyMeters;
    }

    public Optional<DoubleFilter> optionalAccuracyMeters() {
        return Optional.ofNullable(accuracyMeters);
    }

    public DoubleFilter accuracyMeters() {
        if (accuracyMeters == null) {
            setAccuracyMeters(new DoubleFilter());
        }
        return accuracyMeters;
    }

    public void setAccuracyMeters(DoubleFilter accuracyMeters) {
        this.accuracyMeters = accuracyMeters;
    }

    public DoubleFilter getSpeedKmh() {
        return speedKmh;
    }

    public Optional<DoubleFilter> optionalSpeedKmh() {
        return Optional.ofNullable(speedKmh);
    }

    public DoubleFilter speedKmh() {
        if (speedKmh == null) {
            setSpeedKmh(new DoubleFilter());
        }
        return speedKmh;
    }

    public void setSpeedKmh(DoubleFilter speedKmh) {
        this.speedKmh = speedKmh;
    }

    public StringFilter getGnssConstellation() {
        return gnssConstellation;
    }

    public Optional<StringFilter> optionalGnssConstellation() {
        return Optional.ofNullable(gnssConstellation);
    }

    public StringFilter gnssConstellation() {
        if (gnssConstellation == null) {
            setGnssConstellation(new StringFilter());
        }
        return gnssConstellation;
    }

    public void setGnssConstellation(StringFilter gnssConstellation) {
        this.gnssConstellation = gnssConstellation;
    }

    public StringFilter getRawPayload() {
        return rawPayload;
    }

    public Optional<StringFilter> optionalRawPayload() {
        return Optional.ofNullable(rawPayload);
    }

    public StringFilter rawPayload() {
        if (rawPayload == null) {
            setRawPayload(new StringFilter());
        }
        return rawPayload;
    }

    public void setRawPayload(StringFilter rawPayload) {
        this.rawPayload = rawPayload;
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

    public LongFilter getSensorId() {
        return sensorId;
    }

    public Optional<LongFilter> optionalSensorId() {
        return Optional.ofNullable(sensorId);
    }

    public LongFilter sensorId() {
        if (sensorId == null) {
            setSensorId(new LongFilter());
        }
        return sensorId;
    }

    public void setSensorId(LongFilter sensorId) {
        this.sensorId = sensorId;
    }

    public LongFilter getMatchedSiteId() {
        return matchedSiteId;
    }

    public Optional<LongFilter> optionalMatchedSiteId() {
        return Optional.ofNullable(matchedSiteId);
    }

    public LongFilter matchedSiteId() {
        if (matchedSiteId == null) {
            setMatchedSiteId(new LongFilter());
        }
        return matchedSiteId;
    }

    public void setMatchedSiteId(LongFilter matchedSiteId) {
        this.matchedSiteId = matchedSiteId;
    }

    public LongFilter getMatchedZoneId() {
        return matchedZoneId;
    }

    public Optional<LongFilter> optionalMatchedZoneId() {
        return Optional.ofNullable(matchedZoneId);
    }

    public LongFilter matchedZoneId() {
        if (matchedZoneId == null) {
            setMatchedZoneId(new LongFilter());
        }
        return matchedZoneId;
    }

    public void setMatchedZoneId(LongFilter matchedZoneId) {
        this.matchedZoneId = matchedZoneId;
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
        final LocationEventCriteria that = (LocationEventCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(source, that.source) &&
            Objects.equals(observedAt, that.observedAt) &&
            Objects.equals(zoneConfidence, that.zoneConfidence) &&
            Objects.equals(rssi, that.rssi) &&
            Objects.equals(txPower, that.txPower) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(accuracyMeters, that.accuracyMeters) &&
            Objects.equals(speedKmh, that.speedKmh) &&
            Objects.equals(gnssConstellation, that.gnssConstellation) &&
            Objects.equals(rawPayload, that.rawPayload) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(sensorId, that.sensorId) &&
            Objects.equals(matchedSiteId, that.matchedSiteId) &&
            Objects.equals(matchedZoneId, that.matchedZoneId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            source,
            observedAt,
            zoneConfidence,
            rssi,
            txPower,
            latitude,
            longitude,
            accuracyMeters,
            speedKmh,
            gnssConstellation,
            rawPayload,
            assetId,
            sensorId,
            matchedSiteId,
            matchedZoneId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationEventCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSource().map(f -> "source=" + f + ", ").orElse("") +
            optionalObservedAt().map(f -> "observedAt=" + f + ", ").orElse("") +
            optionalZoneConfidence().map(f -> "zoneConfidence=" + f + ", ").orElse("") +
            optionalRssi().map(f -> "rssi=" + f + ", ").orElse("") +
            optionalTxPower().map(f -> "txPower=" + f + ", ").orElse("") +
            optionalLatitude().map(f -> "latitude=" + f + ", ").orElse("") +
            optionalLongitude().map(f -> "longitude=" + f + ", ").orElse("") +
            optionalAccuracyMeters().map(f -> "accuracyMeters=" + f + ", ").orElse("") +
            optionalSpeedKmh().map(f -> "speedKmh=" + f + ", ").orElse("") +
            optionalGnssConstellation().map(f -> "gnssConstellation=" + f + ", ").orElse("") +
            optionalRawPayload().map(f -> "rawPayload=" + f + ", ").orElse("") +
            optionalAssetId().map(f -> "assetId=" + f + ", ").orElse("") +
            optionalSensorId().map(f -> "sensorId=" + f + ", ").orElse("") +
            optionalMatchedSiteId().map(f -> "matchedSiteId=" + f + ", ").orElse("") +
            optionalMatchedZoneId().map(f -> "matchedZoneId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
