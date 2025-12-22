package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.DocumentLink;
import com.ailab.smartasset.repository.DocumentLinkRepository;
import com.ailab.smartasset.service.dto.DocumentLinkDTO;
import com.ailab.smartasset.service.mapper.DocumentLinkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.DocumentLink}.
 */
@Service
@Transactional
public class DocumentLinkService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentLinkService.class);

    private final DocumentLinkRepository documentLinkRepository;

    private final DocumentLinkMapper documentLinkMapper;

    public DocumentLinkService(DocumentLinkRepository documentLinkRepository, DocumentLinkMapper documentLinkMapper) {
        this.documentLinkRepository = documentLinkRepository;
        this.documentLinkMapper = documentLinkMapper;
    }

    /**
     * Save a documentLink.
     *
     * @param documentLinkDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentLinkDTO save(DocumentLinkDTO documentLinkDTO) {
        LOG.debug("Request to save DocumentLink : {}", documentLinkDTO);
        DocumentLink documentLink = documentLinkMapper.toEntity(documentLinkDTO);
        documentLink = documentLinkRepository.save(documentLink);
        return documentLinkMapper.toDto(documentLink);
    }

    /**
     * Update a documentLink.
     *
     * @param documentLinkDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentLinkDTO update(DocumentLinkDTO documentLinkDTO) {
        LOG.debug("Request to update DocumentLink : {}", documentLinkDTO);
        DocumentLink documentLink = documentLinkMapper.toEntity(documentLinkDTO);
        documentLink = documentLinkRepository.save(documentLink);
        return documentLinkMapper.toDto(documentLink);
    }

    /**
     * Partially update a documentLink.
     *
     * @param documentLinkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentLinkDTO> partialUpdate(DocumentLinkDTO documentLinkDTO) {
        LOG.debug("Request to partially update DocumentLink : {}", documentLinkDTO);

        return documentLinkRepository
            .findById(documentLinkDTO.getId())
            .map(existingDocumentLink -> {
                documentLinkMapper.partialUpdate(existingDocumentLink, documentLinkDTO);

                return existingDocumentLink;
            })
            .map(documentLinkRepository::save)
            .map(documentLinkMapper::toDto);
    }

    /**
     * Get one documentLink by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentLinkDTO> findOne(Long id) {
        LOG.debug("Request to get DocumentLink : {}", id);
        return documentLinkRepository.findById(id).map(documentLinkMapper::toDto);
    }

    /**
     * Delete the documentLink by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DocumentLink : {}", id);
        documentLinkRepository.deleteById(id);
    }
}
