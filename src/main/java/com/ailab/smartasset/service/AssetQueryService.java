package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.repository.AssetRepository;
import com.ailab.smartasset.service.criteria.AssetCriteria;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.mapper.AssetMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Asset} entities in the database.
 * The main input is a {@link AssetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AssetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetQueryService extends QueryService<Asset> {

    private static final Logger LOG = LoggerFactory.getLogger(AssetQueryService.class);

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    public AssetQueryService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    /**
     * Return a {@link Page} of {@link AssetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetDTO> findByCriteria(AssetCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Asset> specification = createSpecification(criteria);
        return assetRepository.findAll(specification, page).map(assetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Asset> specification = createSpecification(criteria);
        return assetRepository.count(specification);
    }

    /**
     * Function to convert {@link AssetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Asset> createSpecification(AssetCriteria criteria) {
        Specification<Asset> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Asset_.id),
                buildSpecification(criteria.getAssetType(), Asset_.assetType),
                buildStringSpecification(criteria.getAssetCode(), Asset_.assetCode),
                buildStringSpecification(criteria.getReference(), Asset_.reference),
                buildStringSpecification(criteria.getDescription(), Asset_.description),
                buildSpecification(criteria.getStatus(), Asset_.status),
                buildSpecification(criteria.getCriticality(), Asset_.criticality),
                buildSpecification(criteria.getGeofencePolicy(), Asset_.geofencePolicy),
                buildStringSpecification(criteria.getResponsibleName(), Asset_.responsibleName),
                buildStringSpecification(criteria.getCostCenter(), Asset_.costCenter),
                buildStringSpecification(criteria.getBrand(), Asset_.brand),
                buildStringSpecification(criteria.getModel(), Asset_.model),
                buildStringSpecification(criteria.getSerialNumber(), Asset_.serialNumber),
                buildRangeSpecification(criteria.getPowerKw(), Asset_.powerKw),
                buildRangeSpecification(criteria.getVoltageV(), Asset_.voltageV),
                buildRangeSpecification(criteria.getCurrentA(), Asset_.currentA),
                buildRangeSpecification(criteria.getCosPhi(), Asset_.cosPhi),
                buildRangeSpecification(criteria.getSpeedRpm(), Asset_.speedRpm),
                buildStringSpecification(criteria.getIpRating(), Asset_.ipRating),
                buildStringSpecification(criteria.getInsulationClass(), Asset_.insulationClass),
                buildSpecification(criteria.getMountingType(), Asset_.mountingType),
                buildRangeSpecification(criteria.getShaftDiameterMm(), Asset_.shaftDiameterMm),
                buildRangeSpecification(criteria.getFootDistanceAmm(), Asset_.footDistanceAmm),
                buildRangeSpecification(criteria.getFootDistanceBmm(), Asset_.footDistanceBmm),
                buildRangeSpecification(criteria.getFrontFlangeMm(), Asset_.frontFlangeMm),
                buildRangeSpecification(criteria.getRearFlangeMm(), Asset_.rearFlangeMm),
                buildRangeSpecification(criteria.getIecAxisHeightMm(), Asset_.iecAxisHeightMm),
                buildStringSpecification(criteria.getDimensionsSource(), Asset_.dimensionsSource),
                buildSpecification(criteria.getHasHeating(), Asset_.hasHeating),
                buildSpecification(criteria.getTemperatureProbeType(), Asset_.temperatureProbeType),
                buildRangeSpecification(criteria.getLastCommissioningDate(), Asset_.lastCommissioningDate),
                buildRangeSpecification(criteria.getLastMaintenanceDate(), Asset_.lastMaintenanceDate),
                buildRangeSpecification(criteria.getMaintenanceCount(), Asset_.maintenanceCount),
                buildSpecification(criteria.getProductionLineId(), root ->
                    root.join(Asset_.productionLine, JoinType.LEFT).get(ProductionLine_.id)
                ),
                buildSpecification(criteria.getAllowedSiteId(), root -> root.join(Asset_.allowedSite, JoinType.LEFT).get(Site_.id)),
                buildSpecification(criteria.getAllowedZoneId(), root -> root.join(Asset_.allowedZone, JoinType.LEFT).get(Zone_.id))
            );
        }
        return specification;
    }
}
