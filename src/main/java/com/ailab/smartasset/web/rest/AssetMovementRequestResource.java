package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.AssetMovementRequestRepository;
import com.ailab.smartasset.service.AssetMovementRequestQueryService;
import com.ailab.smartasset.service.AssetMovementRequestService;
import com.ailab.smartasset.service.criteria.AssetMovementRequestCriteria;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ailab.smartasset.domain.AssetMovementRequest}.
 */
@RestController
@RequestMapping("/api/asset-movement-requests")
public class AssetMovementRequestResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssetMovementRequestResource.class);

    private static final String ENTITY_NAME = "assetMovementRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetMovementRequestService assetMovementRequestService;

    private final AssetMovementRequestRepository assetMovementRequestRepository;

    private final AssetMovementRequestQueryService assetMovementRequestQueryService;

    public AssetMovementRequestResource(
        AssetMovementRequestService assetMovementRequestService,
        AssetMovementRequestRepository assetMovementRequestRepository,
        AssetMovementRequestQueryService assetMovementRequestQueryService
    ) {
        this.assetMovementRequestService = assetMovementRequestService;
        this.assetMovementRequestRepository = assetMovementRequestRepository;
        this.assetMovementRequestQueryService = assetMovementRequestQueryService;
    }

    /**
     * {@code POST  /asset-movement-requests} : Create a new assetMovementRequest.
     *
     * @param assetMovementRequestDTO the assetMovementRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetMovementRequestDTO, or with status {@code 400 (Bad Request)} if the assetMovementRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssetMovementRequestDTO> createAssetMovementRequest(
        @Valid @RequestBody AssetMovementRequestDTO assetMovementRequestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AssetMovementRequest : {}", assetMovementRequestDTO);
        if (assetMovementRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetMovementRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assetMovementRequestDTO = assetMovementRequestService.save(assetMovementRequestDTO);
        return ResponseEntity.created(new URI("/api/asset-movement-requests/" + assetMovementRequestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetMovementRequestDTO.getId().toString()))
            .body(assetMovementRequestDTO);
    }

    /**
     * {@code PUT  /asset-movement-requests/:id} : Updates an existing assetMovementRequest.
     *
     * @param id the id of the assetMovementRequestDTO to save.
     * @param assetMovementRequestDTO the assetMovementRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetMovementRequestDTO,
     * or with status {@code 400 (Bad Request)} if the assetMovementRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetMovementRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssetMovementRequestDTO> updateAssetMovementRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetMovementRequestDTO assetMovementRequestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AssetMovementRequest : {}, {}", id, assetMovementRequestDTO);
        if (assetMovementRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetMovementRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetMovementRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assetMovementRequestDTO = assetMovementRequestService.update(assetMovementRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetMovementRequestDTO.getId().toString()))
            .body(assetMovementRequestDTO);
    }

    /**
     * {@code PATCH  /asset-movement-requests/:id} : Partial updates given fields of an existing assetMovementRequest, field will ignore if it is null
     *
     * @param id the id of the assetMovementRequestDTO to save.
     * @param assetMovementRequestDTO the assetMovementRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetMovementRequestDTO,
     * or with status {@code 400 (Bad Request)} if the assetMovementRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetMovementRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetMovementRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetMovementRequestDTO> partialUpdateAssetMovementRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetMovementRequestDTO assetMovementRequestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AssetMovementRequest partially : {}, {}", id, assetMovementRequestDTO);
        if (assetMovementRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetMovementRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetMovementRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetMovementRequestDTO> result = assetMovementRequestService.partialUpdate(assetMovementRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetMovementRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-movement-requests} : get all the assetMovementRequests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetMovementRequests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssetMovementRequestDTO>> getAllAssetMovementRequests(
        AssetMovementRequestCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get AssetMovementRequests by criteria: {}", criteria);

        Page<AssetMovementRequestDTO> page = assetMovementRequestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-movement-requests/count} : count all the assetMovementRequests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAssetMovementRequests(AssetMovementRequestCriteria criteria) {
        LOG.debug("REST request to count AssetMovementRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetMovementRequestQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-movement-requests/:id} : get the "id" assetMovementRequest.
     *
     * @param id the id of the assetMovementRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetMovementRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssetMovementRequestDTO> getAssetMovementRequest(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AssetMovementRequest : {}", id);
        Optional<AssetMovementRequestDTO> assetMovementRequestDTO = assetMovementRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetMovementRequestDTO);
    }

    /**
     * {@code DELETE  /asset-movement-requests/:id} : delete the "id" assetMovementRequest.
     *
     * @param id the id of the assetMovementRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetMovementRequest(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AssetMovementRequest : {}", id);
        assetMovementRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
