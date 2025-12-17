package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.SensorMeasurementRepository;
import com.ailab.smartasset.service.SensorMeasurementQueryService;
import com.ailab.smartasset.service.SensorMeasurementService;
import com.ailab.smartasset.service.criteria.SensorMeasurementCriteria;
import com.ailab.smartasset.service.dto.SensorMeasurementDTO;
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
 * REST controller for managing {@link com.ailab.smartasset.domain.SensorMeasurement}.
 */
@RestController
@RequestMapping("/api/sensor-measurements")
public class SensorMeasurementResource {

    private static final Logger LOG = LoggerFactory.getLogger(SensorMeasurementResource.class);

    private static final String ENTITY_NAME = "sensorMeasurement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorMeasurementService sensorMeasurementService;

    private final SensorMeasurementRepository sensorMeasurementRepository;

    private final SensorMeasurementQueryService sensorMeasurementQueryService;

    public SensorMeasurementResource(
        SensorMeasurementService sensorMeasurementService,
        SensorMeasurementRepository sensorMeasurementRepository,
        SensorMeasurementQueryService sensorMeasurementQueryService
    ) {
        this.sensorMeasurementService = sensorMeasurementService;
        this.sensorMeasurementRepository = sensorMeasurementRepository;
        this.sensorMeasurementQueryService = sensorMeasurementQueryService;
    }

    /**
     * {@code POST  /sensor-measurements} : Create a new sensorMeasurement.
     *
     * @param sensorMeasurementDTO the sensorMeasurementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensorMeasurementDTO, or with status {@code 400 (Bad Request)} if the sensorMeasurement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SensorMeasurementDTO> createSensorMeasurement(@Valid @RequestBody SensorMeasurementDTO sensorMeasurementDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SensorMeasurement : {}", sensorMeasurementDTO);
        if (sensorMeasurementDTO.getId() != null) {
            throw new BadRequestAlertException("A new sensorMeasurement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sensorMeasurementDTO = sensorMeasurementService.save(sensorMeasurementDTO);
        return ResponseEntity.created(new URI("/api/sensor-measurements/" + sensorMeasurementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sensorMeasurementDTO.getId().toString()))
            .body(sensorMeasurementDTO);
    }

    /**
     * {@code PUT  /sensor-measurements/:id} : Updates an existing sensorMeasurement.
     *
     * @param id the id of the sensorMeasurementDTO to save.
     * @param sensorMeasurementDTO the sensorMeasurementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorMeasurementDTO,
     * or with status {@code 400 (Bad Request)} if the sensorMeasurementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensorMeasurementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SensorMeasurementDTO> updateSensorMeasurement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SensorMeasurementDTO sensorMeasurementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SensorMeasurement : {}, {}", id, sensorMeasurementDTO);
        if (sensorMeasurementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensorMeasurementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorMeasurementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sensorMeasurementDTO = sensorMeasurementService.update(sensorMeasurementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensorMeasurementDTO.getId().toString()))
            .body(sensorMeasurementDTO);
    }

    /**
     * {@code PATCH  /sensor-measurements/:id} : Partial updates given fields of an existing sensorMeasurement, field will ignore if it is null
     *
     * @param id the id of the sensorMeasurementDTO to save.
     * @param sensorMeasurementDTO the sensorMeasurementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorMeasurementDTO,
     * or with status {@code 400 (Bad Request)} if the sensorMeasurementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sensorMeasurementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sensorMeasurementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SensorMeasurementDTO> partialUpdateSensorMeasurement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SensorMeasurementDTO sensorMeasurementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SensorMeasurement partially : {}, {}", id, sensorMeasurementDTO);
        if (sensorMeasurementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensorMeasurementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorMeasurementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SensorMeasurementDTO> result = sensorMeasurementService.partialUpdate(sensorMeasurementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensorMeasurementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sensor-measurements} : get all the sensorMeasurements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensorMeasurements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SensorMeasurementDTO>> getAllSensorMeasurements(
        SensorMeasurementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SensorMeasurements by criteria: {}", criteria);

        Page<SensorMeasurementDTO> page = sensorMeasurementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sensor-measurements/count} : count all the sensorMeasurements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSensorMeasurements(SensorMeasurementCriteria criteria) {
        LOG.debug("REST request to count SensorMeasurements by criteria: {}", criteria);
        return ResponseEntity.ok().body(sensorMeasurementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sensor-measurements/:id} : get the "id" sensorMeasurement.
     *
     * @param id the id of the sensorMeasurementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensorMeasurementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SensorMeasurementDTO> getSensorMeasurement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SensorMeasurement : {}", id);
        Optional<SensorMeasurementDTO> sensorMeasurementDTO = sensorMeasurementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sensorMeasurementDTO);
    }

    /**
     * {@code DELETE  /sensor-measurements/:id} : delete the "id" sensorMeasurement.
     *
     * @param id the id of the sensorMeasurementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensorMeasurement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SensorMeasurement : {}", id);
        sensorMeasurementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
