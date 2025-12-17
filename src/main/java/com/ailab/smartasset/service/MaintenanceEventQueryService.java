package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.MaintenanceEvent;
import com.ailab.smartasset.repository.MaintenanceEventRepository;
import com.ailab.smartasset.service.criteria.MaintenanceEventCriteria;
import com.ailab.smartasset.service.dto.MaintenanceEventDTO;
import com.ailab.smartasset.service.mapper.MaintenanceEventMapper;
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
 * Service for executing complex queries for {@link MaintenanceEvent} entities in the database.
 * The main input is a {@link MaintenanceEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MaintenanceEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaintenanceEventQueryService extends QueryService<MaintenanceEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceEventQueryService.class);

    private final MaintenanceEventRepository maintenanceEventRepository;

    private final MaintenanceEventMapper maintenanceEventMapper;

    public MaintenanceEventQueryService(
        MaintenanceEventRepository maintenanceEventRepository,
        MaintenanceEventMapper maintenanceEventMapper
    ) {
        this.maintenanceEventRepository = maintenanceEventRepository;
        this.maintenanceEventMapper = maintenanceEventMapper;
    }

    /**
     * Return a {@link Page} of {@link MaintenanceEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaintenanceEventDTO> findByCriteria(MaintenanceEventCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaintenanceEvent> specification = createSpecification(criteria);
        return maintenanceEventRepository.findAll(specification, page).map(maintenanceEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaintenanceEventCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<MaintenanceEvent> specification = createSpecification(criteria);
        return maintenanceEventRepository.count(specification);
    }

    /**
     * Function to convert {@link MaintenanceEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaintenanceEvent> createSpecification(MaintenanceEventCriteria criteria) {
        Specification<MaintenanceEvent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), MaintenanceEvent_.id),
                buildSpecification(criteria.getMaintenanceType(), MaintenanceEvent_.maintenanceType),
                buildSpecification(criteria.getStatus(), MaintenanceEvent_.status),
                buildRangeSpecification(criteria.getRequestedAt(), MaintenanceEvent_.requestedAt),
                buildRangeSpecification(criteria.getPlannedAt(), MaintenanceEvent_.plannedAt),
                buildRangeSpecification(criteria.getStartedAt(), MaintenanceEvent_.startedAt),
                buildRangeSpecification(criteria.getFinishedAt(), MaintenanceEvent_.finishedAt),
                buildStringSpecification(criteria.getTitle(), MaintenanceEvent_.title),
                buildStringSpecification(criteria.getDescription(), MaintenanceEvent_.description),
                buildStringSpecification(criteria.getTechnician(), MaintenanceEvent_.technician),
                buildRangeSpecification(criteria.getDowntimeMinutes(), MaintenanceEvent_.downtimeMinutes),
                buildRangeSpecification(criteria.getCostAmount(), MaintenanceEvent_.costAmount),
                buildStringSpecification(criteria.getNotes(), MaintenanceEvent_.notes),
                buildStringSpecification(criteria.getCreatedBy(), MaintenanceEvent_.createdBy),
                buildRangeSpecification(criteria.getCreatedDate(), MaintenanceEvent_.createdDate),
                buildStringSpecification(criteria.getLastModifiedBy(), MaintenanceEvent_.lastModifiedBy),
                buildRangeSpecification(criteria.getLastModifiedDate(), MaintenanceEvent_.lastModifiedDate),
                buildSpecification(criteria.getAssetId(), root -> root.join(MaintenanceEvent_.asset, JoinType.LEFT).get(Asset_.id))
            );
        }
        return specification;
    }
}
