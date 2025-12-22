package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.repository.ZoneRepository;
import com.ailab.smartasset.service.dto.ZoneDTO;
import com.ailab.smartasset.service.mapper.ZoneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.Zone}.
 */
@Service
@Transactional
public class ZoneService {

    private static final Logger LOG = LoggerFactory.getLogger(ZoneService.class);

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    public ZoneService(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
    }

    /**
     * Save a zone.
     *
     * @param zoneDTO the entity to save.
     * @return the persisted entity.
     */
    public ZoneDTO save(ZoneDTO zoneDTO) {
        LOG.debug("Request to save Zone : {}", zoneDTO);
        Zone zone = zoneMapper.toEntity(zoneDTO);
        zone = zoneRepository.save(zone);
        return zoneMapper.toDto(zone);
    }

    /**
     * Update a zone.
     *
     * @param zoneDTO the entity to save.
     * @return the persisted entity.
     */
    public ZoneDTO update(ZoneDTO zoneDTO) {
        LOG.debug("Request to update Zone : {}", zoneDTO);
        Zone zone = zoneMapper.toEntity(zoneDTO);
        zone = zoneRepository.save(zone);
        return zoneMapper.toDto(zone);
    }

    /**
     * Partially update a zone.
     *
     * @param zoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ZoneDTO> partialUpdate(ZoneDTO zoneDTO) {
        LOG.debug("Request to partially update Zone : {}", zoneDTO);

        return zoneRepository
            .findById(zoneDTO.getId())
            .map(existingZone -> {
                zoneMapper.partialUpdate(existingZone, zoneDTO);

                return existingZone;
            })
            .map(zoneRepository::save)
            .map(zoneMapper::toDto);
    }

    /**
     * Get one zone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ZoneDTO> findOne(Long id) {
        LOG.debug("Request to get Zone : {}", id);
        return zoneRepository.findById(id).map(zoneMapper::toDto);
    }

    /**
     * Delete the zone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Zone : {}", id);
        zoneRepository.deleteById(id);
    }
}
