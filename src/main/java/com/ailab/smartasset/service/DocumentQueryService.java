package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Document;
import com.ailab.smartasset.repository.DocumentRepository;
import com.ailab.smartasset.service.criteria.DocumentCriteria;
import com.ailab.smartasset.service.dto.DocumentDTO;
import com.ailab.smartasset.service.mapper.DocumentMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Document} entities in the database.
 * The main input is a {@link DocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentQueryService extends QueryService<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentQueryService.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    public DocumentQueryService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentDTO> findByCriteria(DocumentCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Document> specification = createSpecification(criteria);
        return documentMapper.toDto(documentRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Document> specification = createSpecification(criteria);
        return documentRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Document> createSpecification(DocumentCriteria criteria) {
        Specification<Document> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Document_.id),
                buildStringSpecification(criteria.getFileName(), Document_.fileName),
                buildStringSpecification(criteria.getMimeType(), Document_.mimeType),
                buildRangeSpecification(criteria.getSizeBytes(), Document_.sizeBytes),
                buildStringSpecification(criteria.getStorageRef(), Document_.storageRef),
                buildStringSpecification(criteria.getChecksumSha256(), Document_.checksumSha256),
                buildRangeSpecification(criteria.getUploadedAt(), Document_.uploadedAt),
                buildStringSpecification(criteria.getUploadedBy(), Document_.uploadedBy),
                buildStringSpecification(criteria.getCreatedBy(), Document_.createdBy),
                buildRangeSpecification(criteria.getCreatedDate(), Document_.createdDate),
                buildStringSpecification(criteria.getLastModifiedBy(), Document_.lastModifiedBy),
                buildRangeSpecification(criteria.getLastModifiedDate(), Document_.lastModifiedDate),
                buildSpecification(criteria.getLinksId(), root -> root.join(Document_.links, JoinType.LEFT).get(DocumentLink_.id))
            );
        }
        return specification;
    }
}
