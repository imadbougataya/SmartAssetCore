package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.SystemEventRepository;
import com.ailab.smartasset.service.SystemEventQueryService;
import com.ailab.smartasset.service.SystemEventService;
import com.ailab.smartasset.service.criteria.SystemEventCriteria;
import com.ailab.smartasset.service.dto.SystemEventDTO;
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
 * REST controller for managing {@link com.ailab.smartasset.domain.SystemEvent}.
 */
@RestController
@RequestMapping("/api/system-events")
public class SystemEventResource {

    private static final Logger LOG = LoggerFactory.getLogger(SystemEventResource.class);

    private static final String ENTITY_NAME = "systemEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemEventService systemEventService;

    private final SystemEventRepository systemEventRepository;

    private final SystemEventQueryService systemEventQueryService;

    public SystemEventResource(
        SystemEventService systemEventService,
        SystemEventRepository systemEventRepository,
        SystemEventQueryService systemEventQueryService
    ) {
        this.systemEventService = systemEventService;
        this.systemEventRepository = systemEventRepository;
        this.systemEventQueryService = systemEventQueryService;
    }

    /**
     * {@code POST  /system-events} : Create a new systemEvent.
     *
     * @param systemEventDTO the systemEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemEventDTO, or with status {@code 400 (Bad Request)} if the systemEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SystemEventDTO> createSystemEvent(@Valid @RequestBody SystemEventDTO systemEventDTO) throws URISyntaxException {
        LOG.debug("REST request to save SystemEvent : {}", systemEventDTO);
        if (systemEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        systemEventDTO = systemEventService.save(systemEventDTO);
        return ResponseEntity.created(new URI("/api/system-events/" + systemEventDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, systemEventDTO.getId().toString()))
            .body(systemEventDTO);
    }

    /**
     * {@code PUT  /system-events/:id} : Updates an existing systemEvent.
     *
     * @param id the id of the systemEventDTO to save.
     * @param systemEventDTO the systemEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemEventDTO,
     * or with status {@code 400 (Bad Request)} if the systemEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SystemEventDTO> updateSystemEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SystemEventDTO systemEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SystemEvent : {}, {}", id, systemEventDTO);
        if (systemEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        systemEventDTO = systemEventService.update(systemEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemEventDTO.getId().toString()))
            .body(systemEventDTO);
    }

    /**
     * {@code PATCH  /system-events/:id} : Partial updates given fields of an existing systemEvent, field will ignore if it is null
     *
     * @param id the id of the systemEventDTO to save.
     * @param systemEventDTO the systemEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemEventDTO,
     * or with status {@code 400 (Bad Request)} if the systemEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the systemEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the systemEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SystemEventDTO> partialUpdateSystemEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SystemEventDTO systemEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SystemEvent partially : {}, {}", id, systemEventDTO);
        if (systemEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SystemEventDTO> result = systemEventService.partialUpdate(systemEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /system-events} : get all the systemEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemEvents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SystemEventDTO>> getAllSystemEvents(
        SystemEventCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SystemEvents by criteria: {}", criteria);

        Page<SystemEventDTO> page = systemEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-events/count} : count all the systemEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSystemEvents(SystemEventCriteria criteria) {
        LOG.debug("REST request to count SystemEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-events/:id} : get the "id" systemEvent.
     *
     * @param id the id of the systemEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SystemEventDTO> getSystemEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SystemEvent : {}", id);
        Optional<SystemEventDTO> systemEventDTO = systemEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemEventDTO);
    }

    /**
     * {@code DELETE  /system-events/:id} : delete the "id" systemEvent.
     *
     * @param id the id of the systemEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystemEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SystemEvent : {}", id);
        systemEventService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
