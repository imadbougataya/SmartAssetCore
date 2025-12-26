package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.domain.enumeration.EsignStatus;
import com.ailab.smartasset.repository.AssetMovementRequestRepository;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
import com.ailab.smartasset.service.esign.EsignWorkflowService;
import com.ailab.smartasset.service.mapper.AssetMovementRequestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.ailab.smartasset.domain.AssetMovementRequest}.
 */
@Service
@Transactional
public class AssetMovementRequestService {

    private static final Logger LOG = LoggerFactory.getLogger(AssetMovementRequestService.class);

    private final AssetMovementRequestRepository assetMovementRequestRepository;

    private final AssetMovementRequestMapper assetMovementRequestMapper;
    private final EsignWorkflowService esignWorkflowService;

    public AssetMovementRequestService(
        AssetMovementRequestRepository assetMovementRequestRepository,
        AssetMovementRequestMapper assetMovementRequestMapper,
        EsignWorkflowService esignWorkflowService
    ) {
        this.assetMovementRequestRepository = assetMovementRequestRepository;
        this.assetMovementRequestMapper = assetMovementRequestMapper;
        this.esignWorkflowService = esignWorkflowService;
    }

    /**
     * Save a assetMovementRequest.
     *
     * @param assetMovementRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetMovementRequestDTO save(AssetMovementRequestDTO assetMovementRequestDTO) {
        LOG.debug("Request to save AssetMovementRequest : {}", assetMovementRequestDTO);
        AssetMovementRequest assetMovementRequest = assetMovementRequestMapper.toEntity(assetMovementRequestDTO);
        assetMovementRequest.setEsignStatus(EsignStatus.NOT_STARTED);
        assetMovementRequest.setRequestedAt(java.time.Instant.now());
        assetMovementRequest = assetMovementRequestRepository.save(assetMovementRequest);
        // 🔹 Lancement eSign après persistance
        esignWorkflowService.startWorkflow(assetMovementRequest);
        return assetMovementRequestMapper.toDto(assetMovementRequest);
    }

    /**
     * Update a assetMovementRequest.
     *
     * @param assetMovementRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetMovementRequestDTO update(AssetMovementRequestDTO assetMovementRequestDTO) {
        LOG.debug("Request to update AssetMovementRequest : {}", assetMovementRequestDTO);
        AssetMovementRequest assetMovementRequest = assetMovementRequestMapper.toEntity(assetMovementRequestDTO);
        assetMovementRequest = assetMovementRequestRepository.save(assetMovementRequest);
        return assetMovementRequestMapper.toDto(assetMovementRequest);
    }

    /**
     * Partially update a assetMovementRequest.
     *
     * @param assetMovementRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetMovementRequestDTO> partialUpdate(AssetMovementRequestDTO assetMovementRequestDTO) {
        LOG.debug("Request to partially update AssetMovementRequest : {}", assetMovementRequestDTO);

        return assetMovementRequestRepository
            .findById(assetMovementRequestDTO.getId())
            .map(existingAssetMovementRequest -> {
                assetMovementRequestMapper.partialUpdate(existingAssetMovementRequest, assetMovementRequestDTO);

                return existingAssetMovementRequest;
            })
            .map(assetMovementRequestRepository::save)
            .map(assetMovementRequestMapper::toDto);
    }

    /**
     * Get one assetMovementRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetMovementRequestDTO> findOne(Long id) {
        LOG.debug("Request to get AssetMovementRequest : {}", id);
        return assetMovementRequestRepository.findById(id).map(assetMovementRequestMapper::toDto);
    }

    /**
     * Delete the assetMovementRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AssetMovementRequest : {}", id);
        assetMovementRequestRepository.deleteById(id);
    }
}
