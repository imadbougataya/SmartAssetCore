package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.DocumentLink;
import com.ailab.smartasset.repository.DocumentLinkRepository;
import com.ailab.smartasset.service.criteria.DocumentLinkCriteria;
import com.ailab.smartasset.service.dto.DocumentLinkDTO;
import com.ailab.smartasset.service.mapper.DocumentLinkMapper;
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
 * Service for executing complex queries for {@link DocumentLink} entities in the database.
 * The main input is a {@link DocumentLinkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DocumentLinkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentLinkQueryService extends QueryService<DocumentLink> {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentLinkQueryService.class);

    private final DocumentLinkRepository documentLinkRepository;

    private final DocumentLinkMapper documentLinkMapper;

    public DocumentLinkQueryService(DocumentLinkRepository documentLinkRepository, DocumentLinkMapper documentLinkMapper) {
        this.documentLinkRepository = documentLinkRepository;
        this.documentLinkMapper = documentLinkMapper;
    }

    /**
     * Return a {@link Page} of {@link DocumentLinkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentLinkDTO> findByCriteria(DocumentLinkCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentLink> specification = createSpecification(criteria);
        return documentLinkRepository.findAll(specification, page).map(documentLinkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentLinkCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<DocumentLink> specification = createSpecification(criteria);
        return documentLinkRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentLinkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentLink> createSpecification(DocumentLinkCriteria criteria) {
        Specification<DocumentLink> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), DocumentLink_.id),
                buildSpecification(criteria.getEntityType(), DocumentLink_.entityType),
                buildRangeSpecification(criteria.getEntityId(), DocumentLink_.entityId),
                buildStringSpecification(criteria.getLabel(), DocumentLink_.label),
                buildRangeSpecification(criteria.getLinkedAt(), DocumentLink_.linkedAt),
                buildStringSpecification(criteria.getCreatedBy(), DocumentLink_.createdBy),
                buildRangeSpecification(criteria.getCreatedDate(), DocumentLink_.createdDate),
                buildStringSpecification(criteria.getLastModifiedBy(), DocumentLink_.lastModifiedBy),
                buildRangeSpecification(criteria.getLastModifiedDate(), DocumentLink_.lastModifiedDate),
                buildSpecification(criteria.getDocumentId(), root -> root.join(DocumentLink_.document, JoinType.LEFT).get(Document_.id))
            );
        }
        return specification;
    }
}
