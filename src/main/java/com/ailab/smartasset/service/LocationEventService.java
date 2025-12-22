package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.LocationEvent;
import com.ailab.smartasset.repository.LocationEventRepository;
import com.ailab.smartasset.service.dto.LocationEventDTO;
import com.ailab.smartasset.service.mapper.LocationEventMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.LocationEvent}.
 */
@Service
@Transactional
public class LocationEventService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationEventService.class);

    private final LocationEventRepository locationEventRepository;

    private final LocationEventMapper locationEventMapper;

    public LocationEventService(LocationEventRepository locationEventRepository, LocationEventMapper locationEventMapper) {
        this.locationEventRepository = locationEventRepository;
        this.locationEventMapper = locationEventMapper;
    }

    /**
     * Save a locationEvent.
     *
     * @param locationEventDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationEventDTO save(LocationEventDTO locationEventDTO) {
        LOG.debug("Request to save LocationEvent : {}", locationEventDTO);
        LocationEvent locationEvent = locationEventMapper.toEntity(locationEventDTO);
        locationEvent = locationEventRepository.save(locationEvent);
        return locationEventMapper.toDto(locationEvent);
    }

    /**
     * Update a locationEvent.
     *
     * @param locationEventDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationEventDTO update(LocationEventDTO locationEventDTO) {
        LOG.debug("Request to update LocationEvent : {}", locationEventDTO);
        LocationEvent locationEvent = locationEventMapper.toEntity(locationEventDTO);
        locationEvent = locationEventRepository.save(locationEvent);
        return locationEventMapper.toDto(locationEvent);
    }

    /**
     * Partially update a locationEvent.
     *
     * @param locationEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocationEventDTO> partialUpdate(LocationEventDTO locationEventDTO) {
        LOG.debug("Request to partially update LocationEvent : {}", locationEventDTO);

        return locationEventRepository
            .findById(locationEventDTO.getId())
            .map(existingLocationEvent -> {
                locationEventMapper.partialUpdate(existingLocationEvent, locationEventDTO);

                return existingLocationEvent;
            })
            .map(locationEventRepository::save)
            .map(locationEventMapper::toDto);
    }

    /**
     * Get all the locationEvents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LocationEventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return locationEventRepository.findAllWithEagerRelationships(pageable).map(locationEventMapper::toDto);
    }

    /**
     * Get one locationEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationEventDTO> findOne(Long id) {
        LOG.debug("Request to get LocationEvent : {}", id);
        return locationEventRepository.findOneWithEagerRelationships(id).map(locationEventMapper::toDto);
    }

    /**
     * Delete the locationEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete LocationEvent : {}", id);
        locationEventRepository.deleteById(id);
    }
}
