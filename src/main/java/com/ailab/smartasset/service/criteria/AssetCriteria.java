package com.ailab.smartasset.service.criteria;

import com.ailab.smartasset.domain.enumeration.AssetGeofencePolicy;
import com.ailab.smartasset.domain.enumeration.AssetStatus;
import com.ailab.smartasset.domain.enumeration.AssetType;
import com.ailab.smartasset.domain.enumeration.Criticality;
import com.ailab.smartasset.domain.enumeration.MountingType;
import com.ailab.smartasset.domain.enumeration.TemperatureProbeType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ailab.smartasset.domain.Asset} entity. This class is used
 * in {@link com.ailab.smartasset.web.rest.AssetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /assets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AssetType
     */
    public static class AssetTypeFilter extends Filter<AssetType> {

        public AssetTypeFilter() {}

        public AssetTypeFilter(AssetTypeFilter filter) {
            super(filter);
        }

        @Override
        public AssetTypeFilter copy() {
            return new AssetTypeFilter(this);
        }
    }

    /**
     * Class for filtering AssetStatus
     */
    public static class AssetStatusFilter extends Filter<AssetStatus> {

        public AssetStatusFilter() {}

        public AssetStatusFilter(AssetStatusFilter filter) {
            super(filter);
        }

        @Override
        public AssetStatusFilter copy() {
            return new AssetStatusFilter(this);
        }
    }

    /**
     * Class for filtering Criticality
     */
    public static class CriticalityFilter extends Filter<Criticality> {

        public CriticalityFilter() {}

        public CriticalityFilter(CriticalityFilter filter) {
            super(filter);
        }

        @Override
        public CriticalityFilter copy() {
            return new CriticalityFilter(this);
        }
    }

    /**
     * Class for filtering AssetGeofencePolicy
     */
    public static class AssetGeofencePolicyFilter extends Filter<AssetGeofencePolicy> {

        public AssetGeofencePolicyFilter() {}

        public AssetGeofencePolicyFilter(AssetGeofencePolicyFilter filter) {
            super(filter);
        }

        @Override
        public AssetGeofencePolicyFilter copy() {
            return new AssetGeofencePolicyFilter(this);
        }
    }

    /**
     * Class for filtering MountingType
     */
    public static class MountingTypeFilter extends Filter<MountingType> {

        public MountingTypeFilter() {}

        public MountingTypeFilter(MountingTypeFilter filter) {
            super(filter);
        }

        @Override
        public MountingTypeFilter copy() {
            return new MountingTypeFilter(this);
        }
    }

    /**
     * Class for filtering TemperatureProbeType
     */
    public static class TemperatureProbeTypeFilter extends Filter<TemperatureProbeType> {

        public TemperatureProbeTypeFilter() {}

        public TemperatureProbeTypeFilter(TemperatureProbeTypeFilter filter) {
            super(filter);
        }

        @Override
        public TemperatureProbeTypeFilter copy() {
            return new TemperatureProbeTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AssetTypeFilter assetType;

    private StringFilter assetCode;

    private StringFilter reference;

    private StringFilter description;

    private AssetStatusFilter status;

    private CriticalityFilter criticality;

    private AssetGeofencePolicyFilter geofencePolicy;

    private StringFilter responsibleName;

    private StringFilter costCenter;

    private StringFilter brand;

    private StringFilter model;

    private StringFilter serialNumber;

    private BigDecimalFilter powerKw;

    private BigDecimalFilter voltageV;

    private BigDecimalFilter currentA;

    private BigDecimalFilter cosPhi;

    private IntegerFilter speedRpm;

    private StringFilter ipRating;

    private StringFilter insulationClass;

    private MountingTypeFilter mountingType;

    private BigDecimalFilter shaftDiameterMm;

    private BigDecimalFilter footDistanceAmm;

    private BigDecimalFilter footDistanceBmm;

    private BigDecimalFilter frontFlangeMm;

    private BigDecimalFilter rearFlangeMm;

    private BigDecimalFilter iecAxisHeightMm;

    private StringFilter dimensionsSource;

    private BooleanFilter hasHeating;

    private TemperatureProbeTypeFilter temperatureProbeType;

    private LocalDateFilter lastCommissioningDate;

    private LocalDateFilter lastMaintenanceDate;

    private IntegerFilter maintenanceCount;

    private LongFilter productionLineId;

    private LongFilter allowedSiteId;

    private LongFilter allowedZoneId;

    private Boolean distinct;

    public AssetCriteria() {}

    public AssetCriteria(AssetCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.assetType = other.optionalAssetType().map(AssetTypeFilter::copy).orElse(null);
        this.assetCode = other.optionalAssetCode().map(StringFilter::copy).orElse(null);
        this.reference = other.optionalReference().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(AssetStatusFilter::copy).orElse(null);
        this.criticality = other.optionalCriticality().map(CriticalityFilter::copy).orElse(null);
        this.geofencePolicy = other.optionalGeofencePolicy().map(AssetGeofencePolicyFilter::copy).orElse(null);
        this.responsibleName = other.optionalResponsibleName().map(StringFilter::copy).orElse(null);
        this.costCenter = other.optionalCostCenter().map(StringFilter::copy).orElse(null);
        this.brand = other.optionalBrand().map(StringFilter::copy).orElse(null);
        this.model = other.optionalModel().map(StringFilter::copy).orElse(null);
        this.serialNumber = other.optionalSerialNumber().map(StringFilter::copy).orElse(null);
        this.powerKw = other.optionalPowerKw().map(BigDecimalFilter::copy).orElse(null);
        this.voltageV = other.optionalVoltageV().map(BigDecimalFilter::copy).orElse(null);
        this.currentA = other.optionalCurrentA().map(BigDecimalFilter::copy).orElse(null);
        this.cosPhi = other.optionalCosPhi().map(BigDecimalFilter::copy).orElse(null);
        this.speedRpm = other.optionalSpeedRpm().map(IntegerFilter::copy).orElse(null);
        this.ipRating = other.optionalIpRating().map(StringFilter::copy).orElse(null);
        this.insulationClass = other.optionalInsulationClass().map(StringFilter::copy).orElse(null);
        this.mountingType = other.optionalMountingType().map(MountingTypeFilter::copy).orElse(null);
        this.shaftDiameterMm = other.optionalShaftDiameterMm().map(BigDecimalFilter::copy).orElse(null);
        this.footDistanceAmm = other.optionalFootDistanceAmm().map(BigDecimalFilter::copy).orElse(null);
        this.footDistanceBmm = other.optionalFootDistanceBmm().map(BigDecimalFilter::copy).orElse(null);
        this.frontFlangeMm = other.optionalFrontFlangeMm().map(BigDecimalFilter::copy).orElse(null);
        this.rearFlangeMm = other.optionalRearFlangeMm().map(BigDecimalFilter::copy).orElse(null);
        this.iecAxisHeightMm = other.optionalIecAxisHeightMm().map(BigDecimalFilter::copy).orElse(null);
        this.dimensionsSource = other.optionalDimensionsSource().map(StringFilter::copy).orElse(null);
        this.hasHeating = other.optionalHasHeating().map(BooleanFilter::copy).orElse(null);
        this.temperatureProbeType = other.optionalTemperatureProbeType().map(TemperatureProbeTypeFilter::copy).orElse(null);
        this.lastCommissioningDate = other.optionalLastCommissioningDate().map(LocalDateFilter::copy).orElse(null);
        this.lastMaintenanceDate = other.optionalLastMaintenanceDate().map(LocalDateFilter::copy).orElse(null);
        this.maintenanceCount = other.optionalMaintenanceCount().map(IntegerFilter::copy).orElse(null);
        this.productionLineId = other.optionalProductionLineId().map(LongFilter::copy).orElse(null);
        this.allowedSiteId = other.optionalAllowedSiteId().map(LongFilter::copy).orElse(null);
        this.allowedZoneId = other.optionalAllowedZoneId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AssetCriteria copy() {
        return new AssetCriteria(this);
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

    public AssetTypeFilter getAssetType() {
        return assetType;
    }

    public Optional<AssetTypeFilter> optionalAssetType() {
        return Optional.ofNullable(assetType);
    }

    public AssetTypeFilter assetType() {
        if (assetType == null) {
            setAssetType(new AssetTypeFilter());
        }
        return assetType;
    }

    public void setAssetType(AssetTypeFilter assetType) {
        this.assetType = assetType;
    }

    public StringFilter getAssetCode() {
        return assetCode;
    }

    public Optional<StringFilter> optionalAssetCode() {
        return Optional.ofNullable(assetCode);
    }

    public StringFilter assetCode() {
        if (assetCode == null) {
            setAssetCode(new StringFilter());
        }
        return assetCode;
    }

    public void setAssetCode(StringFilter assetCode) {
        this.assetCode = assetCode;
    }

    public StringFilter getReference() {
        return reference;
    }

    public Optional<StringFilter> optionalReference() {
        return Optional.ofNullable(reference);
    }

    public StringFilter reference() {
        if (reference == null) {
            setReference(new StringFilter());
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public AssetStatusFilter getStatus() {
        return status;
    }

    public Optional<AssetStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public AssetStatusFilter status() {
        if (status == null) {
            setStatus(new AssetStatusFilter());
        }
        return status;
    }

    public void setStatus(AssetStatusFilter status) {
        this.status = status;
    }

    public CriticalityFilter getCriticality() {
        return criticality;
    }

    public Optional<CriticalityFilter> optionalCriticality() {
        return Optional.ofNullable(criticality);
    }

    public CriticalityFilter criticality() {
        if (criticality == null) {
            setCriticality(new CriticalityFilter());
        }
        return criticality;
    }

    public void setCriticality(CriticalityFilter criticality) {
        this.criticality = criticality;
    }

    public AssetGeofencePolicyFilter getGeofencePolicy() {
        return geofencePolicy;
    }

    public Optional<AssetGeofencePolicyFilter> optionalGeofencePolicy() {
        return Optional.ofNullable(geofencePolicy);
    }

    public AssetGeofencePolicyFilter geofencePolicy() {
        if (geofencePolicy == null) {
            setGeofencePolicy(new AssetGeofencePolicyFilter());
        }
        return geofencePolicy;
    }

    public void setGeofencePolicy(AssetGeofencePolicyFilter geofencePolicy) {
        this.geofencePolicy = geofencePolicy;
    }

    public StringFilter getResponsibleName() {
        return responsibleName;
    }

    public Optional<StringFilter> optionalResponsibleName() {
        return Optional.ofNullable(responsibleName);
    }

    public StringFilter responsibleName() {
        if (responsibleName == null) {
            setResponsibleName(new StringFilter());
        }
        return responsibleName;
    }

    public void setResponsibleName(StringFilter responsibleName) {
        this.responsibleName = responsibleName;
    }

    public StringFilter getCostCenter() {
        return costCenter;
    }

    public Optional<StringFilter> optionalCostCenter() {
        return Optional.ofNullable(costCenter);
    }

    public StringFilter costCenter() {
        if (costCenter == null) {
            setCostCenter(new StringFilter());
        }
        return costCenter;
    }

    public void setCostCenter(StringFilter costCenter) {
        this.costCenter = costCenter;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public Optional<StringFilter> optionalBrand() {
        return Optional.ofNullable(brand);
    }

    public StringFilter brand() {
        if (brand == null) {
            setBrand(new StringFilter());
        }
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
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

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public Optional<StringFilter> optionalSerialNumber() {
        return Optional.ofNullable(serialNumber);
    }

    public StringFilter serialNumber() {
        if (serialNumber == null) {
            setSerialNumber(new StringFilter());
        }
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimalFilter getPowerKw() {
        return powerKw;
    }

    public Optional<BigDecimalFilter> optionalPowerKw() {
        return Optional.ofNullable(powerKw);
    }

    public BigDecimalFilter powerKw() {
        if (powerKw == null) {
            setPowerKw(new BigDecimalFilter());
        }
        return powerKw;
    }

    public void setPowerKw(BigDecimalFilter powerKw) {
        this.powerKw = powerKw;
    }

    public BigDecimalFilter getVoltageV() {
        return voltageV;
    }

    public Optional<BigDecimalFilter> optionalVoltageV() {
        return Optional.ofNullable(voltageV);
    }

    public BigDecimalFilter voltageV() {
        if (voltageV == null) {
            setVoltageV(new BigDecimalFilter());
        }
        return voltageV;
    }

    public void setVoltageV(BigDecimalFilter voltageV) {
        this.voltageV = voltageV;
    }

    public BigDecimalFilter getCurrentA() {
        return currentA;
    }

    public Optional<BigDecimalFilter> optionalCurrentA() {
        return Optional.ofNullable(currentA);
    }

    public BigDecimalFilter currentA() {
        if (currentA == null) {
            setCurrentA(new BigDecimalFilter());
        }
        return currentA;
    }

    public void setCurrentA(BigDecimalFilter currentA) {
        this.currentA = currentA;
    }

    public BigDecimalFilter getCosPhi() {
        return cosPhi;
    }

    public Optional<BigDecimalFilter> optionalCosPhi() {
        return Optional.ofNullable(cosPhi);
    }

    public BigDecimalFilter cosPhi() {
        if (cosPhi == null) {
            setCosPhi(new BigDecimalFilter());
        }
        return cosPhi;
    }

    public void setCosPhi(BigDecimalFilter cosPhi) {
        this.cosPhi = cosPhi;
    }

    public IntegerFilter getSpeedRpm() {
        return speedRpm;
    }

    public Optional<IntegerFilter> optionalSpeedRpm() {
        return Optional.ofNullable(speedRpm);
    }

    public IntegerFilter speedRpm() {
        if (speedRpm == null) {
            setSpeedRpm(new IntegerFilter());
        }
        return speedRpm;
    }

    public void setSpeedRpm(IntegerFilter speedRpm) {
        this.speedRpm = speedRpm;
    }

    public StringFilter getIpRating() {
        return ipRating;
    }

    public Optional<StringFilter> optionalIpRating() {
        return Optional.ofNullable(ipRating);
    }

    public StringFilter ipRating() {
        if (ipRating == null) {
            setIpRating(new StringFilter());
        }
        return ipRating;
    }

    public void setIpRating(StringFilter ipRating) {
        this.ipRating = ipRating;
    }

    public StringFilter getInsulationClass() {
        return insulationClass;
    }

    public Optional<StringFilter> optionalInsulationClass() {
        return Optional.ofNullable(insulationClass);
    }

    public StringFilter insulationClass() {
        if (insulationClass == null) {
            setInsulationClass(new StringFilter());
        }
        return insulationClass;
    }

    public void setInsulationClass(StringFilter insulationClass) {
        this.insulationClass = insulationClass;
    }

    public MountingTypeFilter getMountingType() {
        return mountingType;
    }

    public Optional<MountingTypeFilter> optionalMountingType() {
        return Optional.ofNullable(mountingType);
    }

    public MountingTypeFilter mountingType() {
        if (mountingType == null) {
            setMountingType(new MountingTypeFilter());
        }
        return mountingType;
    }

    public void setMountingType(MountingTypeFilter mountingType) {
        this.mountingType = mountingType;
    }

    public BigDecimalFilter getShaftDiameterMm() {
        return shaftDiameterMm;
    }

    public Optional<BigDecimalFilter> optionalShaftDiameterMm() {
        return Optional.ofNullable(shaftDiameterMm);
    }

    public BigDecimalFilter shaftDiameterMm() {
        if (shaftDiameterMm == null) {
            setShaftDiameterMm(new BigDecimalFilter());
        }
        return shaftDiameterMm;
    }

    public void setShaftDiameterMm(BigDecimalFilter shaftDiameterMm) {
        this.shaftDiameterMm = shaftDiameterMm;
    }

    public BigDecimalFilter getFootDistanceAmm() {
        return footDistanceAmm;
    }

    public Optional<BigDecimalFilter> optionalFootDistanceAmm() {
        return Optional.ofNullable(footDistanceAmm);
    }

    public BigDecimalFilter footDistanceAmm() {
        if (footDistanceAmm == null) {
            setFootDistanceAmm(new BigDecimalFilter());
        }
        return footDistanceAmm;
    }

    public void setFootDistanceAmm(BigDecimalFilter footDistanceAmm) {
        this.footDistanceAmm = footDistanceAmm;
    }

    public BigDecimalFilter getFootDistanceBmm() {
        return footDistanceBmm;
    }

    public Optional<BigDecimalFilter> optionalFootDistanceBmm() {
        return Optional.ofNullable(footDistanceBmm);
    }

    public BigDecimalFilter footDistanceBmm() {
        if (footDistanceBmm == null) {
            setFootDistanceBmm(new BigDecimalFilter());
        }
        return footDistanceBmm;
    }

    public void setFootDistanceBmm(BigDecimalFilter footDistanceBmm) {
        this.footDistanceBmm = footDistanceBmm;
    }

    public BigDecimalFilter getFrontFlangeMm() {
        return frontFlangeMm;
    }

    public Optional<BigDecimalFilter> optionalFrontFlangeMm() {
        return Optional.ofNullable(frontFlangeMm);
    }

    public BigDecimalFilter frontFlangeMm() {
        if (frontFlangeMm == null) {
            setFrontFlangeMm(new BigDecimalFilter());
        }
        return frontFlangeMm;
    }

    public void setFrontFlangeMm(BigDecimalFilter frontFlangeMm) {
        this.frontFlangeMm = frontFlangeMm;
    }

    public BigDecimalFilter getRearFlangeMm() {
        return rearFlangeMm;
    }

    public Optional<BigDecimalFilter> optionalRearFlangeMm() {
        return Optional.ofNullable(rearFlangeMm);
    }

    public BigDecimalFilter rearFlangeMm() {
        if (rearFlangeMm == null) {
            setRearFlangeMm(new BigDecimalFilter());
        }
        return rearFlangeMm;
    }

    public void setRearFlangeMm(BigDecimalFilter rearFlangeMm) {
        this.rearFlangeMm = rearFlangeMm;
    }

    public BigDecimalFilter getIecAxisHeightMm() {
        return iecAxisHeightMm;
    }

    public Optional<BigDecimalFilter> optionalIecAxisHeightMm() {
        return Optional.ofNullable(iecAxisHeightMm);
    }

    public BigDecimalFilter iecAxisHeightMm() {
        if (iecAxisHeightMm == null) {
            setIecAxisHeightMm(new BigDecimalFilter());
        }
        return iecAxisHeightMm;
    }

    public void setIecAxisHeightMm(BigDecimalFilter iecAxisHeightMm) {
        this.iecAxisHeightMm = iecAxisHeightMm;
    }

    public StringFilter getDimensionsSource() {
        return dimensionsSource;
    }

    public Optional<StringFilter> optionalDimensionsSource() {
        return Optional.ofNullable(dimensionsSource);
    }

    public StringFilter dimensionsSource() {
        if (dimensionsSource == null) {
            setDimensionsSource(new StringFilter());
        }
        return dimensionsSource;
    }

    public void setDimensionsSource(StringFilter dimensionsSource) {
        this.dimensionsSource = dimensionsSource;
    }

    public BooleanFilter getHasHeating() {
        return hasHeating;
    }

    public Optional<BooleanFilter> optionalHasHeating() {
        return Optional.ofNullable(hasHeating);
    }

    public BooleanFilter hasHeating() {
        if (hasHeating == null) {
            setHasHeating(new BooleanFilter());
        }
        return hasHeating;
    }

    public void setHasHeating(BooleanFilter hasHeating) {
        this.hasHeating = hasHeating;
    }

    public TemperatureProbeTypeFilter getTemperatureProbeType() {
        return temperatureProbeType;
    }

    public Optional<TemperatureProbeTypeFilter> optionalTemperatureProbeType() {
        return Optional.ofNullable(temperatureProbeType);
    }

    public TemperatureProbeTypeFilter temperatureProbeType() {
        if (temperatureProbeType == null) {
            setTemperatureProbeType(new TemperatureProbeTypeFilter());
        }
        return temperatureProbeType;
    }

    public void setTemperatureProbeType(TemperatureProbeTypeFilter temperatureProbeType) {
        this.temperatureProbeType = temperatureProbeType;
    }

    public LocalDateFilter getLastCommissioningDate() {
        return lastCommissioningDate;
    }

    public Optional<LocalDateFilter> optionalLastCommissioningDate() {
        return Optional.ofNullable(lastCommissioningDate);
    }

    public LocalDateFilter lastCommissioningDate() {
        if (lastCommissioningDate == null) {
            setLastCommissioningDate(new LocalDateFilter());
        }
        return lastCommissioningDate;
    }

    public void setLastCommissioningDate(LocalDateFilter lastCommissioningDate) {
        this.lastCommissioningDate = lastCommissioningDate;
    }

    public LocalDateFilter getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public Optional<LocalDateFilter> optionalLastMaintenanceDate() {
        return Optional.ofNullable(lastMaintenanceDate);
    }

    public LocalDateFilter lastMaintenanceDate() {
        if (lastMaintenanceDate == null) {
            setLastMaintenanceDate(new LocalDateFilter());
        }
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDateFilter lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public IntegerFilter getMaintenanceCount() {
        return maintenanceCount;
    }

    public Optional<IntegerFilter> optionalMaintenanceCount() {
        return Optional.ofNullable(maintenanceCount);
    }

    public IntegerFilter maintenanceCount() {
        if (maintenanceCount == null) {
            setMaintenanceCount(new IntegerFilter());
        }
        return maintenanceCount;
    }

    public void setMaintenanceCount(IntegerFilter maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    public LongFilter getProductionLineId() {
        return productionLineId;
    }

    public Optional<LongFilter> optionalProductionLineId() {
        return Optional.ofNullable(productionLineId);
    }

    public LongFilter productionLineId() {
        if (productionLineId == null) {
            setProductionLineId(new LongFilter());
        }
        return productionLineId;
    }

    public void setProductionLineId(LongFilter productionLineId) {
        this.productionLineId = productionLineId;
    }

    public LongFilter getAllowedSiteId() {
        return allowedSiteId;
    }

    public Optional<LongFilter> optionalAllowedSiteId() {
        return Optional.ofNullable(allowedSiteId);
    }

    public LongFilter allowedSiteId() {
        if (allowedSiteId == null) {
            setAllowedSiteId(new LongFilter());
        }
        return allowedSiteId;
    }

    public void setAllowedSiteId(LongFilter allowedSiteId) {
        this.allowedSiteId = allowedSiteId;
    }

    public LongFilter getAllowedZoneId() {
        return allowedZoneId;
    }

    public Optional<LongFilter> optionalAllowedZoneId() {
        return Optional.ofNullable(allowedZoneId);
    }

    public LongFilter allowedZoneId() {
        if (allowedZoneId == null) {
            setAllowedZoneId(new LongFilter());
        }
        return allowedZoneId;
    }

    public void setAllowedZoneId(LongFilter allowedZoneId) {
        this.allowedZoneId = allowedZoneId;
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
        final AssetCriteria that = (AssetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assetType, that.assetType) &&
            Objects.equals(assetCode, that.assetCode) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(description, that.description) &&
            Objects.equals(status, that.status) &&
            Objects.equals(criticality, that.criticality) &&
            Objects.equals(geofencePolicy, that.geofencePolicy) &&
            Objects.equals(responsibleName, that.responsibleName) &&
            Objects.equals(costCenter, that.costCenter) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(model, that.model) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(powerKw, that.powerKw) &&
            Objects.equals(voltageV, that.voltageV) &&
            Objects.equals(currentA, that.currentA) &&
            Objects.equals(cosPhi, that.cosPhi) &&
            Objects.equals(speedRpm, that.speedRpm) &&
            Objects.equals(ipRating, that.ipRating) &&
            Objects.equals(insulationClass, that.insulationClass) &&
            Objects.equals(mountingType, that.mountingType) &&
            Objects.equals(shaftDiameterMm, that.shaftDiameterMm) &&
            Objects.equals(footDistanceAmm, that.footDistanceAmm) &&
            Objects.equals(footDistanceBmm, that.footDistanceBmm) &&
            Objects.equals(frontFlangeMm, that.frontFlangeMm) &&
            Objects.equals(rearFlangeMm, that.rearFlangeMm) &&
            Objects.equals(iecAxisHeightMm, that.iecAxisHeightMm) &&
            Objects.equals(dimensionsSource, that.dimensionsSource) &&
            Objects.equals(hasHeating, that.hasHeating) &&
            Objects.equals(temperatureProbeType, that.temperatureProbeType) &&
            Objects.equals(lastCommissioningDate, that.lastCommissioningDate) &&
            Objects.equals(lastMaintenanceDate, that.lastMaintenanceDate) &&
            Objects.equals(maintenanceCount, that.maintenanceCount) &&
            Objects.equals(productionLineId, that.productionLineId) &&
            Objects.equals(allowedSiteId, that.allowedSiteId) &&
            Objects.equals(allowedZoneId, that.allowedZoneId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assetType,
            assetCode,
            reference,
            description,
            status,
            criticality,
            geofencePolicy,
            responsibleName,
            costCenter,
            brand,
            model,
            serialNumber,
            powerKw,
            voltageV,
            currentA,
            cosPhi,
            speedRpm,
            ipRating,
            insulationClass,
            mountingType,
            shaftDiameterMm,
            footDistanceAmm,
            footDistanceBmm,
            frontFlangeMm,
            rearFlangeMm,
            iecAxisHeightMm,
            dimensionsSource,
            hasHeating,
            temperatureProbeType,
            lastCommissioningDate,
            lastMaintenanceDate,
            maintenanceCount,
            productionLineId,
            allowedSiteId,
            allowedZoneId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAssetType().map(f -> "assetType=" + f + ", ").orElse("") +
            optionalAssetCode().map(f -> "assetCode=" + f + ", ").orElse("") +
            optionalReference().map(f -> "reference=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalCriticality().map(f -> "criticality=" + f + ", ").orElse("") +
            optionalGeofencePolicy().map(f -> "geofencePolicy=" + f + ", ").orElse("") +
            optionalResponsibleName().map(f -> "responsibleName=" + f + ", ").orElse("") +
            optionalCostCenter().map(f -> "costCenter=" + f + ", ").orElse("") +
            optionalBrand().map(f -> "brand=" + f + ", ").orElse("") +
            optionalModel().map(f -> "model=" + f + ", ").orElse("") +
            optionalSerialNumber().map(f -> "serialNumber=" + f + ", ").orElse("") +
            optionalPowerKw().map(f -> "powerKw=" + f + ", ").orElse("") +
            optionalVoltageV().map(f -> "voltageV=" + f + ", ").orElse("") +
            optionalCurrentA().map(f -> "currentA=" + f + ", ").orElse("") +
            optionalCosPhi().map(f -> "cosPhi=" + f + ", ").orElse("") +
            optionalSpeedRpm().map(f -> "speedRpm=" + f + ", ").orElse("") +
            optionalIpRating().map(f -> "ipRating=" + f + ", ").orElse("") +
            optionalInsulationClass().map(f -> "insulationClass=" + f + ", ").orElse("") +
            optionalMountingType().map(f -> "mountingType=" + f + ", ").orElse("") +
            optionalShaftDiameterMm().map(f -> "shaftDiameterMm=" + f + ", ").orElse("") +
            optionalFootDistanceAmm().map(f -> "footDistanceAmm=" + f + ", ").orElse("") +
            optionalFootDistanceBmm().map(f -> "footDistanceBmm=" + f + ", ").orElse("") +
            optionalFrontFlangeMm().map(f -> "frontFlangeMm=" + f + ", ").orElse("") +
            optionalRearFlangeMm().map(f -> "rearFlangeMm=" + f + ", ").orElse("") +
            optionalIecAxisHeightMm().map(f -> "iecAxisHeightMm=" + f + ", ").orElse("") +
            optionalDimensionsSource().map(f -> "dimensionsSource=" + f + ", ").orElse("") +
            optionalHasHeating().map(f -> "hasHeating=" + f + ", ").orElse("") +
            optionalTemperatureProbeType().map(f -> "temperatureProbeType=" + f + ", ").orElse("") +
            optionalLastCommissioningDate().map(f -> "lastCommissioningDate=" + f + ", ").orElse("") +
            optionalLastMaintenanceDate().map(f -> "lastMaintenanceDate=" + f + ", ").orElse("") +
            optionalMaintenanceCount().map(f -> "maintenanceCount=" + f + ", ").orElse("") +
            optionalProductionLineId().map(f -> "productionLineId=" + f + ", ").orElse("") +
            optionalAllowedSiteId().map(f -> "allowedSiteId=" + f + ", ").orElse("") +
            optionalAllowedZoneId().map(f -> "allowedZoneId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
