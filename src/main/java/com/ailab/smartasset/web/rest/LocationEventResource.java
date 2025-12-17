package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.LocationEventRepository;
import com.ailab.smartasset.service.LocationEventQueryService;
import com.ailab.smartasset.service.LocationEventService;
import com.ailab.smartasset.service.criteria.LocationEventCriteria;
import com.ailab.smartasset.service.dto.LocationEventDTO;
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
 * REST controller for managing {@link com.ailab.smartasset.domain.LocationEvent}.
 */
@RestController
@RequestMapping("/api/location-events")
public class LocationEventResource {

    private static final Logger LOG = LoggerFactory.getLogger(LocationEventResource.class);

    private static final String ENTITY_NAME = "locationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationEventService locationEventService;

    private final LocationEventRepository locationEventRepository;

    private final LocationEventQueryService locationEventQueryService;

    public LocationEventResource(
        LocationEventService locationEventService,
        LocationEventRepository locationEventRepository,
        LocationEventQueryService locationEventQueryService
    ) {
        this.locationEventService = locationEventService;
        this.locationEventRepository = locationEventRepository;
        this.locationEventQueryService = locationEventQueryService;
    }

    /**
     * {@code POST  /location-events} : Create a new locationEvent.
     *
     * @param locationEventDTO the locationEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationEventDTO, or with status {@code 400 (Bad Request)} if the locationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationEventDTO> createLocationEvent(@Valid @RequestBody LocationEventDTO locationEventDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save LocationEvent : {}", locationEventDTO);
        if (locationEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locationEventDTO = locationEventService.save(locationEventDTO);
        return ResponseEntity.created(new URI("/api/location-events/" + locationEventDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, locationEventDTO.getId().toString()))
            .body(locationEventDTO);
    }

    /**
     * {@code PUT  /location-events/:id} : Updates an existing locationEvent.
     *
     * @param id the id of the locationEventDTO to save.
     * @param locationEventDTO the locationEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationEventDTO,
     * or with status {@code 400 (Bad Request)} if the locationEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocationEventDTO> updateLocationEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocationEventDTO locationEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update LocationEvent : {}, {}", id, locationEventDTO);
        if (locationEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locationEventDTO = locationEventService.update(locationEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationEventDTO.getId().toString()))
            .body(locationEventDTO);
    }

    /**
     * {@code PATCH  /location-events/:id} : Partial updates given fields of an existing locationEvent, field will ignore if it is null
     *
     * @param id the id of the locationEventDTO to save.
     * @param locationEventDTO the locationEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationEventDTO,
     * or with status {@code 400 (Bad Request)} if the locationEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locationEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationEventDTO> partialUpdateLocationEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocationEventDTO locationEventDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LocationEvent partially : {}, {}", id, locationEventDTO);
        if (locationEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationEventDTO> result = locationEventService.partialUpdate(locationEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /location-events} : get all the locationEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationEvents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LocationEventDTO>> getAllLocationEvents(
        LocationEventCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get LocationEvents by criteria: {}", criteria);

        Page<LocationEventDTO> page = locationEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-events/count} : count all the locationEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countLocationEvents(LocationEventCriteria criteria) {
        LOG.debug("REST request to count LocationEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-events/:id} : get the "id" locationEvent.
     *
     * @param id the id of the locationEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationEventDTO> getLocationEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LocationEvent : {}", id);
        Optional<LocationEventDTO> locationEventDTO = locationEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationEventDTO);
    }

    /**
     * {@code DELETE  /location-events/:id} : delete the "id" locationEvent.
     *
     * @param id the id of the locationEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationEvent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LocationEvent : {}", id);
        locationEventService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
