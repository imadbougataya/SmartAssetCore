package com.ailab.smartasset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gateway.
 */
@Entity
@Table(name = "gateway")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gateway implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "code", length = 80, nullable = false, unique = true)
    private String code;

    @Size(max = 150)
    @Column(name = "name", length = 150)
    private String name;

    @Size(max = 80)
    @Column(name = "vendor", length = 80)
    private String vendor;

    @Size(max = 80)
    @Column(name = "model", length = 80)
    private String model;

    @Size(max = 32)
    @Column(name = "mac_address", length = 32)
    private String macAddress;

    @Size(max = 64)
    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Column(name = "installed_at")
    private Instant installedAt;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "site" }, allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gateway id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Gateway code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Gateway name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return this.vendor;
    }

    public Gateway vendor(String vendor) {
        this.setVendor(vendor);
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return this.model;
    }

    public Gateway model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public Gateway macAddress(String macAddress) {
        this.setMacAddress(macAddress);
        return this;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public Gateway ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Instant getInstalledAt() {
        return this.installedAt;
    }

    public Gateway installedAt(Instant installedAt) {
        this.setInstalledAt(installedAt);
        return this;
    }

    public void setInstalledAt(Instant installedAt) {
        this.installedAt = installedAt;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Gateway active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Gateway site(Site site) {
        this.setSite(site);
        return this;
    }

    public Zone getZone() {
        return this.zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Gateway zone(Zone zone) {
        this.setZone(zone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gateway)) {
            return false;
        }
        return getId() != null && getId().equals(((Gateway) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gateway{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", model='" + getModel() + "'" +
            ", macAddress='" + getMacAddress() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", installedAt='" + getInstalledAt() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
