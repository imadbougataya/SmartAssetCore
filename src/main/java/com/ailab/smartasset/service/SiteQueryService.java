package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.repository.SiteRepository;
import com.ailab.smartasset.service.criteria.SiteCriteria;
import com.ailab.smartasset.service.dto.SiteDTO;
import com.ailab.smartasset.service.mapper.SiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Site} entities in the database.
 * The main input is a {@link SiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SiteQueryService extends QueryService<Site> {

    private static final Logger LOG = LoggerFactory.getLogger(SiteQueryService.class);

    private final SiteRepository siteRepository;

    private final SiteMapper siteMapper;

    public SiteQueryService(SiteRepository siteRepository, SiteMapper siteMapper) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
    }

    /**
     * Return a {@link Page} of {@link SiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteDTO> findByCriteria(SiteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.findAll(specification, page).map(siteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SiteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Site> specification = createSpecification(criteria);
        return siteRepository.count(specification);
    }

    /**
     * Function to convert {@link SiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Site> createSpecification(SiteCriteria criteria) {
        Specification<Site> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Site_.id),
                buildStringSpecification(criteria.getCode(), Site_.code),
                buildStringSpecification(criteria.getName(), Site_.name),
                buildStringSpecification(criteria.getDescription(), Site_.description),
                buildRangeSpecification(criteria.getCenterLat(), Site_.centerLat),
                buildRangeSpecification(criteria.getCenterLon(), Site_.centerLon),
                buildRangeSpecification(criteria.getRadiusMeters(), Site_.radiusMeters)
            );
        }
        return specification;
    }
}
