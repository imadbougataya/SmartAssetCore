package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Gateway;
import com.ailab.smartasset.repository.GatewayRepository;
import com.ailab.smartasset.service.criteria.GatewayCriteria;
import com.ailab.smartasset.service.dto.GatewayDTO;
import com.ailab.smartasset.service.mapper.GatewayMapper;
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
 * Service for executing complex queries for {@link Gateway} entities in the database.
 * The main input is a {@link GatewayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link GatewayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GatewayQueryService extends QueryService<Gateway> {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayQueryService.class);

    private final GatewayRepository gatewayRepository;

    private final GatewayMapper gatewayMapper;

    public GatewayQueryService(GatewayRepository gatewayRepository, GatewayMapper gatewayMapper) {
        this.gatewayRepository = gatewayRepository;
        this.gatewayMapper = gatewayMapper;
    }

    /**
     * Return a {@link Page} of {@link GatewayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GatewayDTO> findByCriteria(GatewayCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Gateway> specification = createSpecification(criteria);
        return gatewayRepository.findAll(specification, page).map(gatewayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GatewayCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Gateway> specification = createSpecification(criteria);
        return gatewayRepository.count(specification);
    }

    /**
     * Function to convert {@link GatewayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Gateway> createSpecification(GatewayCriteria criteria) {
        Specification<Gateway> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Gateway_.id),
                buildStringSpecification(criteria.getCode(), Gateway_.code),
                buildStringSpecification(criteria.getName(), Gateway_.name),
                buildStringSpecification(criteria.getVendor(), Gateway_.vendor),
                buildStringSpecification(criteria.getModel(), Gateway_.model),
                buildStringSpecification(criteria.getMacAddress(), Gateway_.macAddress),
                buildStringSpecification(criteria.getIpAddress(), Gateway_.ipAddress),
                buildRangeSpecification(criteria.getInstalledAt(), Gateway_.installedAt),
                buildSpecification(criteria.getActive(), Gateway_.active),
                buildSpecification(criteria.getSiteId(), root -> root.join(Gateway_.site, JoinType.LEFT).get(Site_.id)),
                buildSpecification(criteria.getZoneId(), root -> root.join(Gateway_.zone, JoinType.LEFT).get(Zone_.id))
            );
        }
        return specification;
    }
}
