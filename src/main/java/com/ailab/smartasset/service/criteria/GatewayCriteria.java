package com.ailab.smartasset.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Gateway} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.GatewayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gateways?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GatewayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter vendor;

    private StringFilter model;

    private StringFilter macAddress;

    private StringFilter ipAddress;

    private InstantFilter installedAt;

    private BooleanFilter active;

    private LongFilter siteId;

    private LongFilter zoneId;

    private Boolean distinct;

    public GatewayCriteria() {}

    public GatewayCriteria(GatewayCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.vendor = other.optionalVendor().map(StringFilter::copy).orElse(null);
        this.model = other.optionalModel().map(StringFilter::copy).orElse(null);
        this.macAddress = other.optionalMacAddress().map(StringFilter::copy).orElse(null);
        this.ipAddress = other.optionalIpAddress().map(StringFilter::copy).orElse(null);
        this.installedAt = other.optionalInstalledAt().map(InstantFilter::copy).orElse(null);
        this.active = other.optionalActive().map(BooleanFilter::copy).orElse(null);
        this.siteId = other.optionalSiteId().map(LongFilter::copy).orElse(null);
        this.zoneId = other.optionalZoneId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public GatewayCriteria copy() {
        return new GatewayCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getVendor() {
        return vendor;
    }

    public Optional<StringFilter> optionalVendor() {
        return Optional.ofNullable(vendor);
    }

    public StringFilter vendor() {
        if (vendor == null) {
            setVendor(new StringFilter());
        }
        return vendor;
    }

    public void setVendor(StringFilter vendor) {
        this.vendor = vendor;
    }

    public StringFilter getModel() {
        return model;
    }

    public Optional<StringFilter> optionalModel() {
        return Optional.ofNullable(model);
    }

    public StringFilter model() {
        if (model == null) {
            setModel(new StringFilter());
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public StringFilter getMacAddress() {
        return macAddress;
    }

    public Optional<StringFilter> optionalMacAddress() {
        return Optional.ofNullable(macAddress);
    }

    public StringFilter macAddress() {
        if (macAddress == null) {
            setMacAddress(new StringFilter());
        }
        return macAddress;
    }

    public void setMacAddress(StringFilter macAddress) {
        this.macAddress = macAddress;
    }

    public StringFilter getIpAddress() {
        return ipAddress;
    }

    public Optional<StringFilter> optionalIpAddress() {
        return Optional.ofNullable(ipAddress);
    }

    public StringFilter ipAddress() {
        if (ipAddress == null) {
            setIpAddress(new StringFilter());
        }
        return ipAddress;
    }

    public void setIpAddress(StringFilter ipAddress) {
        this.ipAddress = ipAddress;
    }

    public InstantFilter getInstalledAt() {
        return installedAt;
    }

    public Optional<InstantFilter> optionalInstalledAt() {
        return Optional.ofNullable(installedAt);
    }

    public InstantFilter installedAt() {
        if (installedAt == null) {
            setInstalledAt(new InstantFilter());
        }
        return installedAt;
    }

    public void setInstalledAt(InstantFilter installedAt) {
        this.installedAt = installedAt;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public Optional<BooleanFilter> optionalActive() {
        return Optional.ofNullable(active);
    }

    public BooleanFilter active() {
        if (active == null) {
            setActive(new BooleanFilter());
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public Optional<LongFilter> optionalSiteId() {
        return Optional.ofNullable(siteId);
    }

    public LongFilter siteId() {
        if (siteId == null) {
            setSiteId(new LongFilter());
        }
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public Optional<LongFilter> optionalZoneId() {
        return Optional.ofNullable(zoneId);
    }

    public LongFilter zoneId() {
        if (zoneId == null) {
            setZoneId(new LongFilter());
        }
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
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
        final GatewayCriteria that = (GatewayCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(vendor, that.vendor) &&
            Objects.equals(model, that.model) &&
            Objects.equals(macAddress, that.macAddress) &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(installedAt, that.installedAt) &&
            Objects.equals(active, that.active) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(zoneId, that.zoneId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, vendor, model, macAddress, ipAddress, installedAt, active, siteId, zoneId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GatewayCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalVendor().map(f -> "vendor=" + f + ", ").orElse("") +
            optionalModel().map(f -> "model=" + f + ", ").orElse("") +
            optionalMacAddress().map(f -> "macAddress=" + f + ", ").orElse("") +
            optionalIpAddress().map(f -> "ipAddress=" + f + ", ").orElse("") +
            optionalInstalledAt().map(f -> "installedAt=" + f + ", ").orElse("") +
            optionalActive().map(f -> "active=" + f + ", ").orElse("") +
            optionalSiteId().map(f -> "siteId=" + f + ", ").orElse("") +
            optionalZoneId().map(f -> "zoneId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
