package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.MaintenanceEventRepository;
import com.ailab.smartasset.service.MaintenanceEventQueryService;
import com.ailab.smartasset.service.MaintenanceEventService;
import com.ailab.smartasset.service.criteria.MaintenanceEventCriteria;
import com.ailab.smartasset.service.dto.MaintenanceEventDTO;
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
 * REST controller for managing {@link com.ailab.smartasset.domain.MaintenanceEvent}.
 */
@RestController
@RequestMapping("/api/maintenance-events")
public class MaintenanceEventResource {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceEventResource.class);

    private static final String ENTITY_NAME = "maintenanceEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaintenanceEventService maintenanceEventService;

    private final MaintenanceEventRepository maintenanceEventRepository;

    private final MaintenanceEventQueryService maintenanceEventQueryService;

    public MaintenanceEventResource(
        MaintenanceEventService maintenanceEventService,
        MaintenanceEventRepository maintenanceEventRepository,
        MaintenanceEventQueryService maintenanceEventQueryService
    ) {
        this.maintenanceEventService = maintenanceEventService;
        this.maintenanceEventRepository = maintenanceEventRepository;
        this.maintenanceEventQueryService = maintenanceEventQueryService;
    }

    /**
     * {@code POST  /maintenance-events} : Create a new maintenanceEvent.
     *
     * @param maintenanceEventDTO the maintenanceEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maintenanceEventDTO, or with status {@code 400 (Bad Request)} if the maintenanceEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MaintenanceEventDTO> createMaintenanceEvent(@Valid @RequestBody MaintenanceEventDTO maintenanceEventDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MaintenanceEvent : {}", maintenanceEventDTO);
        if (maintenanceEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new maintenanceEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        maintenanceEventDTO = maintenanceEventService.save(maintenanceEventDTO);
        return ResponseEntity.created(new URI("/api/maintenance-events/" + maintenanceEventDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, maintenanceEventDTO.getId().toString()))
            .body(maintenanceEventDTO);
    }

    /**
     * {@code PUT  /maintenance-events/:id} : Updates an existing maintenanceEvent.
     *
     * @param id the id of the maintenanceEventDTO to save.
     * @param maintenanceEventDTO the maintenanceEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maintenanceEventDTO,
     * or with status {@code 400 (Bad Request)} if the maintenanceEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maintenanceEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceEventDTO> updateMaintenanceEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaintenanceEventDTO maintenanceEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MaintenanceEvent : {}, {}", id, maintenanceEventDTO);
        if (maintenanceEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maintenanceEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maintenanceEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        maintenanceEventDTO = maintenanceEventService.update(maintenanceEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maintenanceEventDTO.getId().toString()))
            .body(maintenanceEventDTO);
    }

    /**
     * {@code PATCH  /maintenance-events/:id} : Partial updates given fields of an existing maintenanceEvent, field will ignore if it is null
     *
     * @param id the id of the maintenanceEventDTO to save.
     * @param maintenanceEventDTO the maintenanceEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maintenanceEventDTO,
     * or with status {@code 400 (Bad Request)} if the maintenanceEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the maintenanceEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the maintenanceEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaintenanceEventDTO> partialUpdateMaintenanceEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaintenanceEventDTO maintenanceEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MaintenanceEvent partially : {}, {}", id, maintenanceEventDTO);
        if (maintenanceEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maintenanceEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maintenanceEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaintenanceEventDTO> result = maintenanceEventService.partialUpdate(maintenanceEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maintenanceEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /maintenance-events} : get all the maintenanceEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maintenanceEvents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MaintenanceEventDTO>> getAllMaintenanceEvents(
        MaintenanceEventCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get MaintenanceEvents by criteria: {}", criteria);

        Page<MaintenanceEventDTO> page = maintenanceEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /maintenance-events/count} : count all the maintenanceEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMaintenanceEvents(MaintenanceEventCriteria criteria) {
        LOG.debug("REST request to count MaintenanceEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(maintenanceEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /maintenance-events/:id} : get the "id" maintenanceEvent.
     *
     * @param id the id of the maintenanceEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maintenanceEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceEventDTO> getMaintenanceEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MaintenanceEvent : {}", id);
        Optional<MaintenanceEventDTO> maintenanceEventDTO = maintenanceEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maintenanceEventDTO);
    }

    /**
     * {@code DELETE  /maintenance-events/:id} : delete the "id" maintenanceEvent.
     *
     * @param id the id of the maintenanceEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MaintenanceEvent : {}", id);
        maintenanceEventService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
