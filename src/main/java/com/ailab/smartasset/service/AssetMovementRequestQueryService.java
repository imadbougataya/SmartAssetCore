package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.repository.AssetMovementRequestRepository;
import com.ailab.smartasset.service.criteria.AssetMovementRequestCriteria;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
import com.ailab.smartasset.service.mapper.AssetMovementRequestMapper;
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
 * Service for executing complex queries for {@link AssetMovementRequest} entities in the database.
 * The main input is a {@link AssetMovementRequestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AssetMovementRequestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetMovementRequestQueryService extends QueryService<AssetMovementRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(AssetMovementRequestQueryService.class);

    private final AssetMovementRequestRepository assetMovementRequestRepository;

    private final AssetMovementRequestMapper assetMovementRequestMapper;

    public AssetMovementRequestQueryService(
        AssetMovementRequestRepository assetMovementRequestRepository,
        AssetMovementRequestMapper assetMovementRequestMapper
    ) {
        this.assetMovementRequestRepository = assetMovementRequestRepository;
        this.assetMovementRequestMapper = assetMovementRequestMapper;
    }

    /**
     * Return a {@link Page} of {@link AssetMovementRequestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetMovementRequestDTO> findByCriteria(AssetMovementRequestCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssetMovementRequest> specification = createSpecification(criteria);
        return assetMovementRequestRepository.findAll(specification, page).map(assetMovementRequestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetMovementRequestCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<AssetMovementRequest> specification = createSpecification(criteria);
        return assetMovementRequestRepository.count(specification);
    }

    /**
     * Function to convert {@link AssetMovementRequestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AssetMovementRequest> createSpecification(AssetMovementRequestCriteria criteria) {
        Specification<AssetMovementRequest> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), AssetMovementRequest_.id),
                buildSpecification(criteria.getStatus(), AssetMovementRequest_.status),
                buildRangeSpecification(criteria.getRequestedAt(), AssetMovementRequest_.requestedAt),
                buildStringSpecification(criteria.getReason(), AssetMovementRequest_.reason),
                buildStringSpecification(criteria.getFromLocationLabel(), AssetMovementRequest_.fromLocationLabel),
                buildStringSpecification(criteria.getToLocationLabel(), AssetMovementRequest_.toLocationLabel),
                buildStringSpecification(criteria.getEsignWorkflowId(), AssetMovementRequest_.esignWorkflowId),
                buildSpecification(criteria.getEsignStatus(), AssetMovementRequest_.esignStatus),
                buildRangeSpecification(criteria.getEsignLastUpdate(), AssetMovementRequest_.esignLastUpdate),
                buildRangeSpecification(criteria.getSignedAt(), AssetMovementRequest_.signedAt),
                buildRangeSpecification(criteria.getExecutedAt(), AssetMovementRequest_.executedAt),
                buildSpecification(criteria.getAssetId(), root -> root.join(AssetMovementRequest_.asset, JoinType.LEFT).get(Asset_.id)),
                buildSpecification(criteria.getRequestedById(), root ->
                    root.join(AssetMovementRequest_.requestedBy, JoinType.LEFT).get(User_.id)
                ),
                buildSpecification(criteria.getApprovedById(), root ->
                    root.join(AssetMovementRequest_.approvedBy, JoinType.LEFT).get(User_.id)
                )
            );
        }
        return specification;
    }
}
