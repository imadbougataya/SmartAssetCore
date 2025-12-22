package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.Gateway;
import com.ailab.smartasset.repository.GatewayRepository;
import com.ailab.smartasset.service.dto.GatewayDTO;
import com.ailab.smartasset.service.mapper.GatewayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.Gateway}.
 */
@Service
@Transactional
public class GatewayService {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayService.class);

    private final GatewayRepository gatewayRepository;

    private final GatewayMapper gatewayMapper;

    public GatewayService(GatewayRepository gatewayRepository, GatewayMapper gatewayMapper) {
        this.gatewayRepository = gatewayRepository;
        this.gatewayMapper = gatewayMapper;
    }

    /**
     * Save a gateway.
     *
     * @param gatewayDTO the entity to save.
     * @return the persisted entity.
     */
    public GatewayDTO save(GatewayDTO gatewayDTO) {
        LOG.debug("Request to save Gateway : {}", gatewayDTO);
        Gateway gateway = gatewayMapper.toEntity(gatewayDTO);
        gateway = gatewayRepository.save(gateway);
        return gatewayMapper.toDto(gateway);
    }

    /**
     * Update a gateway.
     *
     * @param gatewayDTO the entity to save.
     * @return the persisted entity.
     */
    public GatewayDTO update(GatewayDTO gatewayDTO) {
        LOG.debug("Request to update Gateway : {}", gatewayDTO);
        Gateway gateway = gatewayMapper.toEntity(gatewayDTO);
        gateway = gatewayRepository.save(gateway);
        return gatewayMapper.toDto(gateway);
    }

    /**
     * Partially update a gateway.
     *
     * @param gatewayDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GatewayDTO> partialUpdate(GatewayDTO gatewayDTO) {
        LOG.debug("Request to partially update Gateway : {}", gatewayDTO);

        return gatewayRepository
            .findById(gatewayDTO.getId())
            .map(existingGateway -> {
                gatewayMapper.partialUpdate(existingGateway, gatewayDTO);

                return existingGateway;
            })
            .map(gatewayRepository::save)
            .map(gatewayMapper::toDto);
    }

    /**
     * Get one gateway by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GatewayDTO> findOne(Long id) {
        LOG.debug("Request to get Gateway : {}", id);
        return gatewayRepository.findById(id).map(gatewayMapper::toDto);
    }

    /**
     * Delete the gateway by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Gateway : {}", id);
        gatewayRepository.deleteById(id);
    }
}
