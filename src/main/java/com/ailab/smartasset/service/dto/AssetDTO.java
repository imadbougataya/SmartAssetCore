package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.enumeration.AssetStatus;
import com.ailab.smartasset.domain.enumeration.AssetType;
import com.ailab.smartasset.domain.enumeration.Criticality;
import com.ailab.smartasset.domain.enumeration.MountingType;
import com.ailab.smartasset.domain.enumeration.TemperatureProbeType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.Asset} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetDTO implements Serializable {

    private Long id;

    @NotNull
    private AssetType assetType;

    @NotNull
    @Size(max = 80)
    private String assetCode;

    @Size(max = 120)
    private String reference;

    @Size(max = 500)
    private String description;

    @NotNull
    private AssetStatus status;

    @NotNull
    private Criticality criticality;

    @Size(max = 120)
    private String responsibleName;

    @Size(max = 80)
    private String costCenter;

    @Size(max = 80)
    private String brand;

    @Size(max = 120)
    private String model;

    @Size(max = 120)
    private String serialNumber;

    private BigDecimal powerKw;

    private BigDecimal voltageV;

    private BigDecimal currentA;

    private BigDecimal cosPhi;

    private Integer speedRpm;

    @Size(max = 20)
    private String ipRating;

    @Size(max = 30)
    private String insulationClass;

    private MountingType mountingType;

    private BigDecimal shaftDiameterMm;

    private BigDecimal footDistanceAmm;

    private BigDecimal footDistanceBmm;

    private BigDecimal frontFlangeMm;

    private BigDecimal rearFlangeMm;

    private BigDecimal iecAxisHeightMm;

    @Size(max = 120)
    private String dimensionsSource;

    private Boolean hasHeating;

    private TemperatureProbeType temperatureProbeType;

    private LocalDate lastCommissioningDate;

    private LocalDate lastMaintenanceDate;

    private Integer maintenanceCount;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private SiteDTO site;

    private ProductionLineDTO productionLine;

    private ZoneDTO currentZone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public Criticality getCriticality() {
        return criticality;
    }

    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPowerKw() {
        return powerKw;
    }

    public void setPowerKw(BigDecimal powerKw) {
        this.powerKw = powerKw;
    }

    public BigDecimal getVoltageV() {
        return voltageV;
    }

    public void setVoltageV(BigDecimal voltageV) {
        this.voltageV = voltageV;
    }

    public BigDecimal getCurrentA() {
        return currentA;
    }

    public void setCurrentA(BigDecimal currentA) {
        this.currentA = currentA;
    }

    public BigDecimal getCosPhi() {
        return cosPhi;
    }

    public void setCosPhi(BigDecimal cosPhi) {
        this.cosPhi = cosPhi;
    }

    public Integer getSpeedRpm() {
        return speedRpm;
    }

    public void setSpeedRpm(Integer speedRpm) {
        this.speedRpm = speedRpm;
    }

    public String getIpRating() {
        return ipRating;
    }

    public void setIpRating(String ipRating) {
        this.ipRating = ipRating;
    }

    public String getInsulationClass() {
        return insulationClass;
    }

    public void setInsulationClass(String insulationClass) {
        this.insulationClass = insulationClass;
    }

    public MountingType getMountingType() {
        return mountingType;
    }

    public void setMountingType(MountingType mountingType) {
        this.mountingType = mountingType;
    }

    public BigDecimal getShaftDiameterMm() {
        return shaftDiameterMm;
    }

    public void setShaftDiameterMm(BigDecimal shaftDiameterMm) {
        this.shaftDiameterMm = shaftDiameterMm;
    }

    public BigDecimal getFootDistanceAmm() {
        return footDistanceAmm;
    }

    public void setFootDistanceAmm(BigDecimal footDistanceAmm) {
        this.footDistanceAmm = footDistanceAmm;
    }

    public BigDecimal getFootDistanceBmm() {
        return footDistanceBmm;
    }

    public void setFootDistanceBmm(BigDecimal footDistanceBmm) {
        this.footDistanceBmm = footDistanceBmm;
    }

    public BigDecimal getFrontFlangeMm() {
        return frontFlangeMm;
    }

    public void setFrontFlangeMm(BigDecimal frontFlangeMm) {
        this.frontFlangeMm = frontFlangeMm;
    }

    public BigDecimal getRearFlangeMm() {
        return rearFlangeMm;
    }

    public void setRearFlangeMm(BigDecimal rearFlangeMm) {
        this.rearFlangeMm = rearFlangeMm;
    }

    public BigDecimal getIecAxisHeightMm() {
        return iecAxisHeightMm;
    }

    public void setIecAxisHeightMm(BigDecimal iecAxisHeightMm) {
        this.iecAxisHeightMm = iecAxisHeightMm;
    }

    public String getDimensionsSource() {
        return dimensionsSource;
    }

    public void setDimensionsSource(String dimensionsSource) {
        this.dimensionsSource = dimensionsSource;
    }

    public Boolean getHasHeating() {
        return hasHeating;
    }

    public void setHasHeating(Boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    public TemperatureProbeType getTemperatureProbeType() {
        return temperatureProbeType;
    }

    public void setTemperatureProbeType(TemperatureProbeType temperatureProbeType) {
        this.temperatureProbeType = temperatureProbeType;
    }

    public LocalDate getLastCommissioningDate() {
        return lastCommissioningDate;
    }

    public void setLastCommissioningDate(LocalDate lastCommissioningDate) {
        this.lastCommissioningDate = lastCommissioningDate;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Integer getMaintenanceCount() {
        return maintenanceCount;
    }

    public void setMaintenanceCount(Integer maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    public ProductionLineDTO getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(ProductionLineDTO productionLine) {
        this.productionLine = productionLine;
    }

    public ZoneDTO getCurrentZone() {
        return currentZone;
    }

    public void setCurrentZone(ZoneDTO currentZone) {
        this.currentZone = currentZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetDTO)) {
            return false;
        }

        AssetDTO assetDTO = (AssetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetDTO{" +
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
            ", site=" + getSite() +
            ", productionLine=" + getProductionLine() +
            ", currentZone=" + getCurrentZone() +
            "}";
    }
}
