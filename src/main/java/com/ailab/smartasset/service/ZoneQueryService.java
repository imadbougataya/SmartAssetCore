package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.repository.ZoneRepository;
import com.ailab.smartasset.service.criteria.ZoneCriteria;
import com.ailab.smartasset.service.dto.ZoneDTO;
import com.ailab.smartasset.service.mapper.ZoneMapper;
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
 * Service for executing complex queries for {@link Zone} entities in the database.
 * The main input is a {@link ZoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ZoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZoneQueryService extends QueryService<Zone> {

    private static final Logger LOG = LoggerFactory.getLogger(ZoneQueryService.class);

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    public ZoneQueryService(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
    }

    /**
     * Return a {@link Page} of {@link ZoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZoneDTO> findByCriteria(ZoneCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Zone> specification = createSpecification(criteria);
        return zoneRepository.findAll(specification, page).map(zoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZoneCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Zone> specification = createSpecification(criteria);
        return zoneRepository.count(specification);
    }

    /**
     * Function to convert {@link ZoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Zone> createSpecification(ZoneCriteria criteria) {
        Specification<Zone> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Zone_.id),
                buildStringSpecification(criteria.getCode(), Zone_.code),
                buildStringSpecification(criteria.getName(), Zone_.name),
                buildStringSpecification(criteria.getDescription(), Zone_.description),
                buildStringSpecification(criteria.getZoneType(), Zone_.zoneType),
                buildRangeSpecification(criteria.getCenterLat(), Zone_.centerLat),
                buildRangeSpecification(criteria.getCenterLon(), Zone_.centerLon),
                buildRangeSpecification(criteria.getRadiusMeters(), Zone_.radiusMeters),
                buildStringSpecification(criteria.getCreatedBy(), Zone_.createdBy),
                buildRangeSpecification(criteria.getCreatedDate(), Zone_.createdDate),
                buildStringSpecification(criteria.getLastModifiedBy(), Zone_.lastModifiedBy),
                buildRangeSpecification(criteria.getLastModifiedDate(), Zone_.lastModifiedDate),
                buildSpecification(criteria.getLocationEventsId(), root ->
                    root.join(Zone_.locationEvents, JoinType.LEFT).get(LocationEvent_.id)
                ),
                buildSpecification(criteria.getSiteId(), root -> root.join(Zone_.site, JoinType.LEFT).get(Site_.id))
            );
        }
        return specification;
    }
}
