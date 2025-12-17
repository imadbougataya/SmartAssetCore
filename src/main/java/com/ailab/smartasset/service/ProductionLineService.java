package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.repository.ProductionLineRepository;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
import com.ailab.smartasset.service.mapper.ProductionLineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.ProductionLine}.
 */
@Service
@Transactional
public class ProductionLineService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductionLineService.class);

    private final ProductionLineRepository productionLineRepository;

    private final ProductionLineMapper productionLineMapper;

    public ProductionLineService(ProductionLineRepository productionLineRepository, ProductionLineMapper productionLineMapper) {
        this.productionLineRepository = productionLineRepository;
        this.productionLineMapper = productionLineMapper;
    }

    /**
     * Save a productionLine.
     *
     * @param productionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductionLineDTO save(ProductionLineDTO productionLineDTO) {
        LOG.debug("Request to save ProductionLine : {}", productionLineDTO);
        ProductionLine productionLine = productionLineMapper.toEntity(productionLineDTO);
        productionLine = productionLineRepository.save(productionLine);
        return productionLineMapper.toDto(productionLine);
    }

    /**
     * Update a productionLine.
     *
     * @param productionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductionLineDTO update(ProductionLineDTO productionLineDTO) {
        LOG.debug("Request to update ProductionLine : {}", productionLineDTO);
        ProductionLine productionLine = productionLineMapper.toEntity(productionLineDTO);
        productionLine.setIsPersisted();
        productionLine = productionLineRepository.save(productionLine);
        return productionLineMapper.toDto(productionLine);
    }

    /**
     * Partially update a productionLine.
     *
     * @param productionLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductionLineDTO> partialUpdate(ProductionLineDTO productionLineDTO) {
        LOG.debug("Request to partially update ProductionLine : {}", productionLineDTO);

        return productionLineRepository
            .findById(productionLineDTO.getId())
            .map(existingProductionLine -> {
                productionLineMapper.partialUpdate(existingProductionLine, productionLineDTO);

                return existingProductionLine;
            })
            .map(productionLineRepository::save)
            .map(productionLineMapper::toDto);
    }

    /**
     * Get all the productionLines with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductionLineDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productionLineRepository.findAllWithEagerRelationships(pageable).map(productionLineMapper::toDto);
    }

    /**
     * Get one productionLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductionLineDTO> findOne(Long id) {
        LOG.debug("Request to get ProductionLine : {}", id);
        return productionLineRepository.findOneWithEagerRelationships(id).map(productionLineMapper::toDto);
    }

    /**
     * Delete the productionLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ProductionLine : {}", id);
        productionLineRepository.deleteById(id);
    }
}
