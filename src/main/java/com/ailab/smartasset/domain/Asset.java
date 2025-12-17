package com.ailab.smartasset.domain;

import com.ailab.smartasset.domain.enumeration.AssetStatus;
import com.ailab.smartasset.domain.enumeration.AssetType;
import com.ailab.smartasset.domain.enumeration.Criticality;
import com.ailab.smartasset.domain.enumeration.MountingType;
import com.ailab.smartasset.domain.enumeration.TemperatureProbeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asset extends AbstractAuditingEntity<Long> implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private AssetType assetType;

    @NotNull
    @Size(max = 80)
    @Column(name = "asset_code", length = 80, nullable = false, unique = true)
    private String assetCode;

    @Size(max = 120)
    @Column(name = "reference", length = 120)
    private String reference;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AssetStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = false)
    private Criticality criticality;

    @Size(max = 120)
    @Column(name = "responsible_name", length = 120)
    private String responsibleName;

    @Size(max = 80)
    @Column(name = "cost_center", length = 80)
    private String costCenter;

    @Size(max = 80)
    @Column(name = "brand", length = 80)
    private String brand;

    @Size(max = 120)
    @Column(name = "model", length = 120)
    private String model;

    @Size(max = 120)
    @Column(name = "serial_number", length = 120)
    private String serialNumber;

    @Column(name = "power_kw", precision = 21, scale = 2)
    private BigDecimal powerKw;

    @Column(name = "voltage_v", precision = 21, scale = 2)
    private BigDecimal voltageV;

    @Column(name = "current_a", precision = 21, scale = 2)
    private BigDecimal currentA;

    @Column(name = "cos_phi", precision = 21, scale = 2)
    private BigDecimal cosPhi;

    @Column(name = "speed_rpm")
    private Integer speedRpm;

    @Size(max = 20)
    @Column(name = "ip_rating", length = 20)
    private String ipRating;

    @Size(max = 30)
    @Column(name = "insulation_class", length = 30)
    private String insulationClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "mounting_type")
    private MountingType mountingType;

    @Column(name = "shaft_diameter_mm", precision = 21, scale = 2)
    private BigDecimal shaftDiameterMm;

    @Column(name = "foot_distance_amm", precision = 21, scale = 2)
    private BigDecimal footDistanceAmm;

    @Column(name = "foot_distance_bmm", precision = 21, scale = 2)
    private BigDecimal footDistanceBmm;

    @Column(name = "front_flange_mm", precision = 21, scale = 2)
    private BigDecimal frontFlangeMm;

    @Column(name = "rear_flange_mm", precision = 21, scale = 2)
    private BigDecimal rearFlangeMm;

    @Column(name = "iec_axis_height_mm", precision = 21, scale = 2)
    private BigDecimal iecAxisHeightMm;

    @Size(max = 120)
    @Column(name = "dimensions_source", length = 120)
    private String dimensionsSource;

    @Column(name = "has_heating")
    private Boolean hasHeating;

    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_probe_type")
    private TemperatureProbeType temperatureProbeType;

    @Column(name = "last_commissioning_date")
    private LocalDate lastCommissioningDate;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    @Column(name = "maintenance_count")
    private Integer maintenanceCount;

    // Inherited createdBy definition
    // Inherited createdDate definition
    // Inherited lastModifiedBy definition
    // Inherited lastModifiedDate definition
    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "measurements", "asset" }, allowSetters = true)
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asset" }, allowSetters = true)
    private Set<MaintenanceEvent> maintenanceEvents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asset" }, allowSetters = true)
    private Set<AssetMovementRequest> movementRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "asset", "zone", "gateway" }, allowSetters = true)
    private Set<LocationEvent> locationEvents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "site" }, allowSetters = true)
    private ProductionLine productionLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "locationEvents", "site" }, allowSetters = true)
    private Zone currentZone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asset id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public Asset assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getAssetCode() {
        return this.assetCode;
    }

    public Asset assetCode(String assetCode) {
        this.setAssetCode(assetCode);
        return this;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getReference() {
        return this.reference;
    }

    public Asset reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return this.description;
    }

    public Asset description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssetStatus getStatus() {
        return this.status;
    }

    public Asset status(AssetStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public Criticality getCriticality() {
        return this.criticality;
    }

    public Asset criticality(Criticality criticality) {
        this.setCriticality(criticality);
        return this;
    }

    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public String getResponsibleName() {
        return this.responsibleName;
    }

    public Asset responsibleName(String responsibleName) {
        this.setResponsibleName(responsibleName);
        return this;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getCostCenter() {
        return this.costCenter;
    }

    public Asset costCenter(String costCenter) {
        this.setCostCenter(costCenter);
        return this;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getBrand() {
        return this.brand;
    }

    public Asset brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public Asset model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Asset serialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPowerKw() {
        return this.powerKw;
    }

    public Asset powerKw(BigDecimal powerKw) {
        this.setPowerKw(powerKw);
        return this;
    }

    public void setPowerKw(BigDecimal powerKw) {
        this.powerKw = powerKw;
    }

    public BigDecimal getVoltageV() {
        return this.voltageV;
    }

    public Asset voltageV(BigDecimal voltageV) {
        this.setVoltageV(voltageV);
        return this;
    }

    public void setVoltageV(BigDecimal voltageV) {
        this.voltageV = voltageV;
    }

    public BigDecimal getCurrentA() {
        return this.currentA;
    }

    public Asset currentA(BigDecimal currentA) {
        this.setCurrentA(currentA);
        return this;
    }

    public void setCurrentA(BigDecimal currentA) {
        this.currentA = currentA;
    }

    public BigDecimal getCosPhi() {
        return this.cosPhi;
    }

    public Asset cosPhi(BigDecimal cosPhi) {
        this.setCosPhi(cosPhi);
        return this;
    }

    public void setCosPhi(BigDecimal cosPhi) {
        this.cosPhi = cosPhi;
    }

    public Integer getSpeedRpm() {
        return this.speedRpm;
    }

    public Asset speedRpm(Integer speedRpm) {
        this.setSpeedRpm(speedRpm);
        return this;
    }

    public void setSpeedRpm(Integer speedRpm) {
        this.speedRpm = speedRpm;
    }

    public String getIpRating() {
        return this.ipRating;
    }

    public Asset ipRating(String ipRating) {
        this.setIpRating(ipRating);
        return this;
    }

    public void setIpRating(String ipRating) {
        this.ipRating = ipRating;
    }

    public String getInsulationClass() {
        return this.insulationClass;
    }

    public Asset insulationClass(String insulationClass) {
        this.setInsulationClass(insulationClass);
        return this;
    }

    public void setInsulationClass(String insulationClass) {
        this.insulationClass = insulationClass;
    }

    public MountingType getMountingType() {
        return this.mountingType;
    }

    public Asset mountingType(MountingType mountingType) {
        this.setMountingType(mountingType);
        return this;
    }

    public void setMountingType(MountingType mountingType) {
        this.mountingType = mountingType;
    }

    public BigDecimal getShaftDiameterMm() {
        return this.shaftDiameterMm;
    }

    public Asset shaftDiameterMm(BigDecimal shaftDiameterMm) {
        this.setShaftDiameterMm(shaftDiameterMm);
        return this;
    }

    public void setShaftDiameterMm(BigDecimal shaftDiameterMm) {
        this.shaftDiameterMm = shaftDiameterMm;
    }

    public BigDecimal getFootDistanceAmm() {
        return this.footDistanceAmm;
    }

    public Asset footDistanceAmm(BigDecimal footDistanceAmm) {
        this.setFootDistanceAmm(footDistanceAmm);
        return this;
    }

    public void setFootDistanceAmm(BigDecimal footDistanceAmm) {
        this.footDistanceAmm = footDistanceAmm;
    }

    public BigDecimal getFootDistanceBmm() {
        return this.footDistanceBmm;
    }

    public Asset footDistanceBmm(BigDecimal footDistanceBmm) {
        this.setFootDistanceBmm(footDistanceBmm);
        return this;
    }

    public void setFootDistanceBmm(BigDecimal footDistanceBmm) {
        this.footDistanceBmm = footDistanceBmm;
    }

    public BigDecimal getFrontFlangeMm() {
        return this.frontFlangeMm;
    }

    public Asset frontFlangeMm(BigDecimal frontFlangeMm) {
        this.setFrontFlangeMm(frontFlangeMm);
        return this;
    }

    public void setFrontFlangeMm(BigDecimal frontFlangeMm) {
        this.frontFlangeMm = frontFlangeMm;
    }

    public BigDecimal getRearFlangeMm() {
        return this.rearFlangeMm;
    }

    public Asset rearFlangeMm(BigDecimal rearFlangeMm) {
        this.setRearFlangeMm(rearFlangeMm);
        return this;
    }

    public void setRearFlangeMm(BigDecimal rearFlangeMm) {
        this.rearFlangeMm = rearFlangeMm;
    }

    public BigDecimal getIecAxisHeightMm() {
        return this.iecAxisHeightMm;
    }

    public Asset iecAxisHeightMm(BigDecimal iecAxisHeightMm) {
        this.setIecAxisHeightMm(iecAxisHeightMm);
        return this;
    }

    public void setIecAxisHeightMm(BigDecimal iecAxisHeightMm) {
        this.iecAxisHeightMm = iecAxisHeightMm;
    }

    public String getDimensionsSource() {
        return this.dimensionsSource;
    }

    public Asset dimensionsSource(String dimensionsSource) {
        this.setDimensionsSource(dimensionsSource);
        return this;
    }

    public void setDimensionsSource(String dimensionsSource) {
        this.dimensionsSource = dimensionsSource;
    }

    public Boolean getHasHeating() {
        return this.hasHeating;
    }

    public Asset hasHeating(Boolean hasHeating) {
        this.setHasHeating(hasHeating);
        return this;
    }

    public void setHasHeating(Boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    public TemperatureProbeType getTemperatureProbeType() {
        return this.temperatureProbeType;
    }

    public Asset temperatureProbeType(TemperatureProbeType temperatureProbeType) {
        this.setTemperatureProbeType(temperatureProbeType);
        return this;
    }

    public void setTemperatureProbeType(TemperatureProbeType temperatureProbeType) {
        this.temperatureProbeType = temperatureProbeType;
    }

    public LocalDate getLastCommissioningDate() {
        return this.lastCommissioningDate;
    }

    public Asset lastCommissioningDate(LocalDate lastCommissioningDate) {
        this.setLastCommissioningDate(lastCommissioningDate);
        return this;
    }

    public void setLastCommissioningDate(LocalDate lastCommissioningDate) {
        this.lastCommissioningDate = lastCommissioningDate;
    }

    public LocalDate getLastMaintenanceDate() {
        return this.lastMaintenanceDate;
    }

    public Asset lastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.setLastMaintenanceDate(lastMaintenanceDate);
        return this;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Integer getMaintenanceCount() {
        return this.maintenanceCount;
    }

    public Asset maintenanceCount(Integer maintenanceCount) {
        this.setMaintenanceCount(maintenanceCount);
        return this;
    }

    public void setMaintenanceCount(Integer maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    // Inherited createdBy methods
    public Asset createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    // Inherited createdDate methods
    public Asset createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    // Inherited lastModifiedBy methods
    public Asset lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    // Inherited lastModifiedDate methods
    public Asset lastModifiedDate(Instant lastModifiedDate) {
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

    public Asset setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        if (this.sensors != null) {
            this.sensors.forEach(i -> i.setAsset(null));
        }
        if (sensors != null) {
            sensors.forEach(i -> i.setAsset(this));
        }
        this.sensors = sensors;
    }

    public Asset sensors(Set<Sensor> sensors) {
        this.setSensors(sensors);
        return this;
    }

    public Asset addSensors(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setAsset(this);
        return this;
    }

    public Asset removeSensors(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setAsset(null);
        return this;
    }

    public Set<MaintenanceEvent> getMaintenanceEvents() {
        return this.maintenanceEvents;
    }

    public void setMaintenanceEvents(Set<MaintenanceEvent> maintenanceEvents) {
        if (this.maintenanceEvents != null) {
            this.maintenanceEvents.forEach(i -> i.setAsset(null));
        }
        if (maintenanceEvents != null) {
            maintenanceEvents.forEach(i -> i.setAsset(this));
        }
        this.maintenanceEvents = maintenanceEvents;
    }

    public Asset maintenanceEvents(Set<MaintenanceEvent> maintenanceEvents) {
        this.setMaintenanceEvents(maintenanceEvents);
        return this;
    }

    public Asset addMaintenanceEvents(MaintenanceEvent maintenanceEvent) {
        this.maintenanceEvents.add(maintenanceEvent);
        maintenanceEvent.setAsset(this);
        return this;
    }

    public Asset removeMaintenanceEvents(MaintenanceEvent maintenanceEvent) {
        this.maintenanceEvents.remove(maintenanceEvent);
        maintenanceEvent.setAsset(null);
        return this;
    }

    public Set<AssetMovementRequest> getMovementRequests() {
        return this.movementRequests;
    }

    public void setMovementRequests(Set<AssetMovementRequest> assetMovementRequests) {
        if (this.movementRequests != null) {
            this.movementRequests.forEach(i -> i.setAsset(null));
        }
        if (assetMovementRequests != null) {
            assetMovementRequests.forEach(i -> i.setAsset(this));
        }
        this.movementRequests = assetMovementRequests;
    }

    public Asset movementRequests(Set<AssetMovementRequest> assetMovementRequests) {
        this.setMovementRequests(assetMovementRequests);
        return this;
    }

    public Asset addMovementRequests(AssetMovementRequest assetMovementRequest) {
        this.movementRequests.add(assetMovementRequest);
        assetMovementRequest.setAsset(this);
        return this;
    }

    public Asset removeMovementRequests(AssetMovementRequest assetMovementRequest) {
        this.movementRequests.remove(assetMovementRequest);
        assetMovementRequest.setAsset(null);
        return this;
    }

    public Set<LocationEvent> getLocationEvents() {
        return this.locationEvents;
    }

    public void setLocationEvents(Set<LocationEvent> locationEvents) {
        if (this.locationEvents != null) {
            this.locationEvents.forEach(i -> i.setAsset(null));
        }
        if (locationEvents != null) {
            locationEvents.forEach(i -> i.setAsset(this));
        }
        this.locationEvents = locationEvents;
    }

    public Asset locationEvents(Set<LocationEvent> locationEvents) {
        this.setLocationEvents(locationEvents);
        return this;
    }

    public Asset addLocationEvents(LocationEvent locationEvent) {
        this.locationEvents.add(locationEvent);
        locationEvent.setAsset(this);
        return this;
    }

    public Asset removeLocationEvents(LocationEvent locationEvent) {
        this.locationEvents.remove(locationEvent);
        locationEvent.setAsset(null);
        return this;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Asset site(Site site) {
        this.setSite(site);
        return this;
    }

    public ProductionLine getProductionLine() {
        return this.productionLine;
    }

    public void setProductionLine(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    public Asset productionLine(ProductionLine productionLine) {
        this.setProductionLine(productionLine);
        return this;
    }

    public Zone getCurrentZone() {
        return this.currentZone;
    }

    public void setCurrentZone(Zone zone) {
        this.currentZone = zone;
    }

    public Asset currentZone(Zone zone) {
        this.setCurrentZone(zone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        return getId() != null && getId().equals(((Asset) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", assetType='" + getAssetType() + "'" +
            ", assetCode='" + getAssetCode() + "'" +
            ", reference='" + getReference() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", criticality='" + getCriticality() + "'" +
            ", responsibleName='" + getResponsibleName() + "'" +
            ", costCenter='" + getCostCenter() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", powerKw=" + getPowerKw() +
            ", voltageV=" + getVoltageV() +
            ", currentA=" + getCurrentA() +
            ", cosPhi=" + getCosPhi() +
            ", speedRpm=" + getSpeedRpm() +
            ", ipRating='" + getIpRating() + "'" +
            ", insulationClass='" + getInsulationClass() + "'" +
            ", mountingType='" + getMountingType() + "'" +
            ", shaftDiameterMm=" + getShaftDiameterMm() +
            ", footDistanceAmm=" + getFootDistanceAmm() +
            ", footDistanceBmm=" + getFootDistanceBmm() +
            ", frontFlangeMm=" + getFrontFlangeMm() +
            ", rearFlangeMm=" + getRearFlangeMm() +
            ", iecAxisHeightMm=" + getIecAxisHeightMm() +
            ", dimensionsSource='" + getDimensionsSource() + "'" +
            ", hasHeating='" + getHasHeating() + "'" +
            ", temperatureProbeType='" + getTemperatureProbeType() + "'" +
            ", lastCommissioningDate='" + getLastCommissioningDate() + "'" +
            ", lastMaintenanceDate='" + getLastMaintenanceDate() + "'" +
            ", maintenanceCount=" + getMaintenanceCount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
