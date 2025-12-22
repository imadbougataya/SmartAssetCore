package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.SystemEvent;
import com.ailab.smartasset.repository.SystemEventRepository;
import com.ailab.smartasset.service.dto.SystemEventDTO;
import com.ailab.smartasset.service.mapper.SystemEventMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.SystemEvent}.
 */
@Service
@Transactional
public class SystemEventService {

    private static final Logger LOG = LoggerFactory.getLogger(SystemEventService.class);

    private final SystemEventRepository systemEventRepository;

    private final SystemEventMapper systemEventMapper;

    public SystemEventService(SystemEventRepository systemEventRepository, SystemEventMapper systemEventMapper) {
        this.systemEventRepository = systemEventRepository;
        this.systemEventMapper = systemEventMapper;
    }

    /**
     * Save a systemEvent.
     *
     * @param systemEventDTO the entity to save.
     * @return the persisted entity.
     */
    public SystemEventDTO save(SystemEventDTO systemEventDTO) {
        LOG.debug("Request to save SystemEvent : {}", systemEventDTO);
        SystemEvent systemEvent = systemEventMapper.toEntity(systemEventDTO);
        systemEvent = systemEventRepository.save(systemEvent);
        return systemEventMapper.toDto(systemEvent);
    }

    /**
     * Update a systemEvent.
     *
     * @param systemEventDTO the entity to save.
     * @return the persisted entity.
     */
    public SystemEventDTO update(SystemEventDTO systemEventDTO) {
        LOG.debug("Request to update SystemEvent : {}", systemEventDTO);
        SystemEvent systemEvent = systemEventMapper.toEntity(systemEventDTO);
        systemEvent = systemEventRepository.save(systemEvent);
        return systemEventMapper.toDto(systemEvent);
    }

    /**
     * Partially update a systemEvent.
     *
     * @param systemEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SystemEventDTO> partialUpdate(SystemEventDTO systemEventDTO) {
        LOG.debug("Request to partially update SystemEvent : {}", systemEventDTO);

        return systemEventRepository
            .findById(systemEventDTO.getId())
            .map(existingSystemEvent -> {
                systemEventMapper.partialUpdate(existingSystemEvent, systemEventDTO);

                return existingSystemEvent;
            })
            .map(systemEventRepository::save)
            .map(systemEventMapper::toDto);
    }

    /**
     * Get one systemEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SystemEventDTO> findOne(Long id) {
        LOG.debug("Request to get SystemEvent : {}", id);
        return systemEventRepository.findById(id).map(systemEventMapper::toDto);
    }

    /**
     * Delete the systemEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SystemEvent : {}", id);
        systemEventRepository.deleteById(id);
    }
}
