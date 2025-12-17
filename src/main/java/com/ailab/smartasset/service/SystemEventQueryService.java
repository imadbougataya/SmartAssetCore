package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.SystemEvent;
import com.ailab.smartasset.repository.SystemEventRepository;
import com.ailab.smartasset.service.criteria.SystemEventCriteria;
import com.ailab.smartasset.service.dto.SystemEventDTO;
import com.ailab.smartasset.service.mapper.SystemEventMapper;
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
 * Service for executing complex queries for {@link SystemEvent} entities in the
 * database.
 * The main input is a {@link SystemEventCriteria} which gets converted to
 * {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SystemEventDTO} which fulfills the
 * criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemEventQueryService extends QueryService<SystemEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(SystemEventQueryService.class);

    private final SystemEventRepository systemEventRepository;

    private final SystemEventMapper systemEventMapper;

    public SystemEventQueryService(SystemEventRepository systemEventRepository, SystemEventMapper systemEventMapper) {
        this.systemEventRepository = systemEventRepository;
        this.systemEventMapper = systemEventMapper;
    }

    /**
     * Return a {@link Page} of {@link SystemEventDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemEventDTO> findByCriteria(SystemEventCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemEvent> specification = createSpecification(criteria);
        return systemEventRepository.findAll(specification, page).map(systemEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemEventCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SystemEvent> specification = createSpecification(criteria);
        return systemEventRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemEventCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemEvent> createSpecification(SystemEventCriteria criteria) {
        Specification<SystemEvent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SystemEvent_.id),
                buildStringSpecification(criteria.getEventType(), SystemEvent_.eventType),
                buildSpecification(criteria.getEntityType(), SystemEvent_.entityType),
                buildRangeSpecification(criteria.getEntityId(), SystemEvent_.entityId),
                buildSpecification(criteria.getSeverity(), SystemEvent_.severity),
                buildSpecification(criteria.getSource(), SystemEvent_.source),
                buildStringSpecification(criteria.getMessage(), SystemEvent_.message),
                buildRangeSpecification(criteria.getCreatedAt(), SystemEvent_.createdAt),
                buildStringSpecification(criteria.getCreatedBy(), SystemEvent_.createdBy),
                buildStringSpecification(criteria.getCorrelationId(), SystemEvent_.correlationId),
                buildSpecification(criteria.getAssetId(), root -> root.join(SystemEvent_.asset, JoinType.LEFT).get(Asset_.id))
            );
        }
        return specification;
    }
}
