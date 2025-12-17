package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.MaintenanceEvent;
import com.ailab.smartasset.repository.MaintenanceEventRepository;
import com.ailab.smartasset.service.dto.MaintenanceEventDTO;
import com.ailab.smartasset.service.mapper.MaintenanceEventMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.MaintenanceEvent}.
 */
@Service
@Transactional
public class MaintenanceEventService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceEventService.class);

    private final MaintenanceEventRepository maintenanceEventRepository;

    private final MaintenanceEventMapper maintenanceEventMapper;

    public MaintenanceEventService(MaintenanceEventRepository maintenanceEventRepository, MaintenanceEventMapper maintenanceEventMapper) {
        this.maintenanceEventRepository = maintenanceEventRepository;
        this.maintenanceEventMapper = maintenanceEventMapper;
    }

    /**
     * Save a maintenanceEvent.
     *
     * @param maintenanceEventDTO the entity to save.
     * @return the persisted entity.
     */
    public MaintenanceEventDTO save(MaintenanceEventDTO maintenanceEventDTO) {
        LOG.debug("Request to save MaintenanceEvent : {}", maintenanceEventDTO);
        MaintenanceEvent maintenanceEvent = maintenanceEventMapper.toEntity(maintenanceEventDTO);
        maintenanceEvent = maintenanceEventRepository.save(maintenanceEvent);
        return maintenanceEventMapper.toDto(maintenanceEvent);
    }

    /**
     * Update a maintenanceEvent.
     *
     * @param maintenanceEventDTO the entity to save.
     * @return the persisted entity.
     */
    public MaintenanceEventDTO update(MaintenanceEventDTO maintenanceEventDTO) {
        LOG.debug("Request to update MaintenanceEvent : {}", maintenanceEventDTO);
        MaintenanceEvent maintenanceEvent = maintenanceEventMapper.toEntity(maintenanceEventDTO);
        maintenanceEvent.setIsPersisted();
        maintenanceEvent = maintenanceEventRepository.save(maintenanceEvent);
        return maintenanceEventMapper.toDto(maintenanceEvent);
    }

    /**
     * Partially update a maintenanceEvent.
     *
     * @param maintenanceEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MaintenanceEventDTO> partialUpdate(MaintenanceEventDTO maintenanceEventDTO) {
        LOG.debug("Request to partially update MaintenanceEvent : {}", maintenanceEventDTO);

        return maintenanceEventRepository
            .findById(maintenanceEventDTO.getId())
            .map(existingMaintenanceEvent -> {
                maintenanceEventMapper.partialUpdate(existingMaintenanceEvent, maintenanceEventDTO);

                return existingMaintenanceEvent;
            })
            .map(maintenanceEventRepository::save)
            .map(maintenanceEventMapper::toDto);
    }

    /**
     * Get all the maintenanceEvents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MaintenanceEventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return maintenanceEventRepository.findAllWithEagerRelationships(pageable).map(maintenanceEventMapper::toDto);
    }

    /**
     * Get one maintenanceEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MaintenanceEventDTO> findOne(Long id) {
        LOG.debug("Request to get MaintenanceEvent : {}", id);
        return maintenanceEventRepository.findOneWithEagerRelationships(id).map(maintenanceEventMapper::toDto);
    }

    /**
     * Delete the maintenanceEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MaintenanceEvent : {}", id);
        maintenanceEventRepository.deleteById(id);
    }
}
