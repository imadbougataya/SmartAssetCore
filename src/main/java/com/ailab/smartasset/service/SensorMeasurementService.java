package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.SensorMeasurement;
import com.ailab.smartasset.repository.SensorMeasurementRepository;
import com.ailab.smartasset.service.dto.SensorMeasurementDTO;
import com.ailab.smartasset.service.mapper.SensorMeasurementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ailab.smartasset.domain.SensorMeasurement}.
 */
@Service
@Transactional
public class SensorMeasurementService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorMeasurementService.class);

    private final SensorMeasurementRepository sensorMeasurementRepository;

    private final SensorMeasurementMapper sensorMeasurementMapper;

    public SensorMeasurementService(
        SensorMeasurementRepository sensorMeasurementRepository,
        SensorMeasurementMapper sensorMeasurementMapper
    ) {
        this.sensorMeasurementRepository = sensorMeasurementRepository;
        this.sensorMeasurementMapper = sensorMeasurementMapper;
    }

    /**
     * Save a sensorMeasurement.
     *
     * @param sensorMeasurementDTO the entity to save.
     * @return the persisted entity.
     */
    public SensorMeasurementDTO save(SensorMeasurementDTO sensorMeasurementDTO) {
        LOG.debug("Request to save SensorMeasurement : {}", sensorMeasurementDTO);
        SensorMeasurement sensorMeasurement = sensorMeasurementMapper.toEntity(sensorMeasurementDTO);
        sensorMeasurement = sensorMeasurementRepository.save(sensorMeasurement);
        return sensorMeasurementMapper.toDto(sensorMeasurement);
    }

    /**
     * Update a sensorMeasurement.
     *
     * @param sensorMeasurementDTO the entity to save.
     * @return the persisted entity.
     */
    public SensorMeasurementDTO update(SensorMeasurementDTO sensorMeasurementDTO) {
        LOG.debug("Request to update SensorMeasurement : {}", sensorMeasurementDTO);
        SensorMeasurement sensorMeasurement = sensorMeasurementMapper.toEntity(sensorMeasurementDTO);
        sensorMeasurement = sensorMeasurementRepository.save(sensorMeasurement);
        return sensorMeasurementMapper.toDto(sensorMeasurement);
    }

    /**
     * Partially update a sensorMeasurement.
     *
     * @param sensorMeasurementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SensorMeasurementDTO> partialUpdate(SensorMeasurementDTO sensorMeasurementDTO) {
        LOG.debug("Request to partially update SensorMeasurement : {}", sensorMeasurementDTO);

        return sensorMeasurementRepository
            .findById(sensorMeasurementDTO.getId())
            .map(existingSensorMeasurement -> {
                sensorMeasurementMapper.partialUpdate(existingSensorMeasurement, sensorMeasurementDTO);

                return existingSensorMeasurement;
            })
            .map(sensorMeasurementRepository::save)
            .map(sensorMeasurementMapper::toDto);
    }

    /**
     * Get one sensorMeasurement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SensorMeasurementDTO> findOne(Long id) {
        LOG.debug("Request to get SensorMeasurement : {}", id);
        return sensorMeasurementRepository.findById(id).map(sensorMeasurementMapper::toDto);
    }

    /**
     * Delete the sensorMeasurement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SensorMeasurement : {}", id);
        sensorMeasurementRepository.deleteById(id);
    }
}
