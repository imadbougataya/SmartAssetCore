package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.LocationEvent;
import com.ailab.smartasset.repository.LocationEventRepository;
import com.ailab.smartasset.service.criteria.LocationEventCriteria;
import com.ailab.smartasset.service.dto.LocationEventDTO;
import com.ailab.smartasset.service.mapper.LocationEventMapper;
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
 * Service for executing complex queries for {@link LocationEvent} entities in
 * the database.
 * The main input is a {@link LocationEventCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link LocationEventDTO} which fulfills the
 * criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationEventQueryService extends QueryService<LocationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(LocationEventQueryService.class);

    private final LocationEventRepository locationEventRepository;

    private final LocationEventMapper locationEventMapper;

    public LocationEventQueryService(LocationEventRepository locationEventRepository, LocationEventMapper locationEventMapper) {
        this.locationEventRepository = locationEventRepository;
        this.locationEventMapper = locationEventMapper;
    }

    /**
     * Return a {@link Page} of {@link LocationEventDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationEventDTO> findByCriteria(LocationEventCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationEvent> specification = createSpecification(criteria);
        return locationEventRepository.findAll(specification, page).map(locationEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationEventCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<LocationEvent> specification = createSpecification(criteria);
        return locationEventRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationEventCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LocationEvent> createSpecification(LocationEventCriteria criteria) {
        Specification<LocationEvent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), LocationEvent_.id),
                buildSpecification(criteria.getSource(), LocationEvent_.source),
                buildRangeSpecification(criteria.getObservedAt(), LocationEvent_.observedAt),
                buildRangeSpecification(criteria.getZoneConfidence(), LocationEvent_.zoneConfidence),
                buildRangeSpecification(criteria.getRssi(), LocationEvent_.rssi),
                buildRangeSpecification(criteria.getTxPower(), LocationEvent_.txPower),
                buildRangeSpecification(criteria.getLatitude(), LocationEvent_.latitude),
                buildRangeSpecification(criteria.getLongitude(), LocationEvent_.longitude),
                buildRangeSpecification(criteria.getAccuracyMeters(), LocationEvent_.accuracyMeters),
                buildRangeSpecification(criteria.getSpeedKmh(), LocationEvent_.speedKmh),
                buildStringSpecification(criteria.getRawPayload(), LocationEvent_.rawPayload),
                buildSpecification(criteria.getAssetId(), root -> root.join(LocationEvent_.asset, JoinType.LEFT).get(Asset_.id)),
                buildSpecification(criteria.getZoneId(), root -> root.join(LocationEvent_.zone, JoinType.LEFT).get(Zone_.id)),
                buildSpecification(criteria.getGatewayId(), root -> root.join(LocationEvent_.gateway, JoinType.LEFT).get(Gateway_.id))
            );
        }
        return specification;
    }
}
