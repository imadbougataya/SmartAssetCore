package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.DocumentLinkRepository;
import com.ailab.smartasset.service.DocumentLinkQueryService;
import com.ailab.smartasset.service.DocumentLinkService;
import com.ailab.smartasset.service.criteria.DocumentLinkCriteria;
import com.ailab.smartasset.service.dto.DocumentLinkDTO;
import com.ailab.smartasset.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ailab.smartasset.domain.DocumentLink}.
 */
@RestController
@RequestMapping("/api/document-links")
public class DocumentLinkResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentLinkResource.class);

    private static final String ENTITY_NAME = "documentLink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentLinkService documentLinkService;

    private final DocumentLinkRepository documentLinkRepository;

    private final DocumentLinkQueryService documentLinkQueryService;

    public DocumentLinkResource(
        DocumentLinkService documentLinkService,
        DocumentLinkRepository documentLinkRepository,
        DocumentLinkQueryService documentLinkQueryService
    ) {
        this.documentLinkService = documentLinkService;
        this.documentLinkRepository = documentLinkRepository;
        this.documentLinkQueryService = documentLinkQueryService;
    }

    /**
     * {@code POST  /document-links} : Create a new documentLink.
     *
     * @param documentLinkDTO the documentLinkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentLinkDTO, or with status {@code 400 (Bad Request)} if the documentLink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentLinkDTO> createDocumentLink(@Valid @RequestBody DocumentLinkDTO documentLinkDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DocumentLink : {}", documentLinkDTO);
        if (documentLinkDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentLink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentLinkDTO = documentLinkService.save(documentLinkDTO);
        return ResponseEntity.created(new URI("/api/document-links/" + documentLinkDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentLinkDTO.getId().toString()))
            .body(documentLinkDTO);
    }

    /**
     * {@code PUT  /document-links/:id} : Updates an existing documentLink.
     *
     * @param id the id of the documentLinkDTO to save.
     * @param documentLinkDTO the documentLinkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentLinkDTO,
     * or with status {@code 400 (Bad Request)} if the documentLinkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentLinkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentLinkDTO> updateDocumentLink(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentLinkDTO documentLinkDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentLink : {}, {}", id, documentLinkDTO);
        if (documentLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentLinkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentLinkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentLinkDTO = documentLinkService.update(documentLinkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentLinkDTO.getId().toString()))
            .body(documentLinkDTO);
    }

    /**
     * {@code PATCH  /document-links/:id} : Partial updates given fields of an existing documentLink, field will ignore if it is null
     *
     * @param id the id of the documentLinkDTO to save.
     * @param documentLinkDTO the documentLinkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentLinkDTO,
     * or with status {@code 400 (Bad Request)} if the documentLinkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentLinkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentLinkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentLinkDTO> partialUpdateDocumentLink(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentLinkDTO documentLinkDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentLink partially : {}, {}", id, documentLinkDTO);
        if (documentLinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentLinkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentLinkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentLinkDTO> result = documentLinkService.partialUpdate(documentLinkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentLinkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /document-links} : get all the documentLinks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentLinks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentLinkDTO>> getAllDocumentLinks(DocumentLinkCriteria criteria) {
        LOG.debug("REST request to get DocumentLinks by criteria: {}", criteria);

        List<DocumentLinkDTO> entityList = documentLinkQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /document-links/count} : count all the documentLinks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDocumentLinks(DocumentLinkCriteria criteria) {
        LOG.debug("REST request to count DocumentLinks by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentLinkQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /document-links/:id} : get the "id" documentLink.
     *
     * @param id the id of the documentLinkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentLinkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentLinkDTO> getDocumentLink(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DocumentLink : {}", id);
        Optional<DocumentLinkDTO> documentLinkDTO = documentLinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentLinkDTO);
    }

    /**
     * {@code DELETE  /document-links/:id} : delete the "id" documentLink.
     *
     * @param id the id of the documentLinkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentLink(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DocumentLink : {}", id);
        documentLinkService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
