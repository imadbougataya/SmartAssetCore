package com.ailab.smartasset.web.rest;

import com.ailab.smartasset.repository.ProductionLineRepository;
import com.ailab.smartasset.service.ProductionLineQueryService;
import com.ailab.smartasset.service.ProductionLineService;
import com.ailab.smartasset.service.criteria.ProductionLineCriteria;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
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
 * REST controller for managing {@link com.ailab.smartasset.domain.ProductionLine}.
 */
@RestController
@RequestMapping("/api/production-lines")
public class ProductionLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProductionLineResource.class);

    private static final String ENTITY_NAME = "productionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionLineService productionLineService;

    private final ProductionLineRepository productionLineRepository;

    private final ProductionLineQueryService productionLineQueryService;

    public ProductionLineResource(
        ProductionLineService productionLineService,
        ProductionLineRepository productionLineRepository,
        ProductionLineQueryService productionLineQueryService
    ) {
        this.productionLineService = productionLineService;
        this.productionLineRepository = productionLineRepository;
        this.productionLineQueryService = productionLineQueryService;
    }

    /**
     * {@code POST  /production-lines} : Create a new productionLine.
     *
     * @param productionLineDTO the productionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionLineDTO, or with status {@code 400 (Bad Request)} if the productionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductionLineDTO> createProductionLine(@Valid @RequestBody ProductionLineDTO productionLineDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ProductionLine : {}", productionLineDTO);
        if (productionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productionLineDTO = productionLineService.save(productionLineDTO);
        return ResponseEntity.created(new URI("/api/production-lines/" + productionLineDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productionLineDTO.getId().toString()))
            .body(productionLineDTO);
    }

    /**
     * {@code PUT  /production-lines/:id} : Updates an existing productionLine.
     *
     * @param id the id of the productionLineDTO to save.
     * @param productionLineDTO the productionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductionLineDTO> updateProductionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductionLineDTO productionLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ProductionLine : {}, {}", id, productionLineDTO);
        if (productionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productionLineDTO = productionLineService.update(productionLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionLineDTO.getId().toString()))
            .body(productionLineDTO);
    }

    /**
     * {@code PATCH  /production-lines/:id} : Partial updates given fields of an existing productionLine, field will ignore if it is null
     *
     * @param id the id of the productionLineDTO to save.
     * @param productionLineDTO the productionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productionLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductionLineDTO> partialUpdateProductionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductionLineDTO productionLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ProductionLine partially : {}, {}", id, productionLineDTO);
        if (productionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionLineDTO> result = productionLineService.partialUpdate(productionLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /production-lines} : get all the productionLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProductionLineDTO>> getAllProductionLines(
        ProductionLineCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ProductionLines by criteria: {}", criteria);

        Page<ProductionLineDTO> page = productionLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-lines/count} : count all the productionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProductionLines(ProductionLineCriteria criteria) {
        LOG.debug("REST request to count ProductionLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(productionLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /production-lines/:id} : get the "id" productionLine.
     *
     * @param id the id of the productionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductionLineDTO> getProductionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ProductionLine : {}", id);
        Optional<ProductionLineDTO> productionLineDTO = productionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionLineDTO);
    }

    /**
     * {@code DELETE  /production-lines/:id} : delete the "id" productionLine.
     *
     * @param id the id of the productionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ProductionLine : {}", id);
        productionLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
