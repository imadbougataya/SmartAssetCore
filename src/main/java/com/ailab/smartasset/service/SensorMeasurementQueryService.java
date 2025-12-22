package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.SensorMeasurement;
import com.ailab.smartasset.repository.SensorMeasurementRepository;
import com.ailab.smartasset.service.criteria.SensorMeasurementCriteria;
import com.ailab.smartasset.service.dto.SensorMeasurementDTO;
import com.ailab.smartasset.service.mapper.SensorMeasurementMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SensorMeasurement} entities in the database.
 * The main input is a {@link SensorMeasurementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SensorMeasurementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SensorMeasurementQueryService extends QueryService<SensorMeasurement> {

    private static final Logger LOG = LoggerFactory.getLogger(SensorMeasurementQueryService.class);

    private final SensorMeasurementRepository sensorMeasurementRepository;

    private final SensorMeasurementMapper sensorMeasurementMapper;

    public SensorMeasurementQueryService(
        SensorMeasurementRepository sensorMeasurementRepository,
        SensorMeasurementMapper sensorMeasurementMapper
    ) {
        this.sensorMeasurementRepository = sensorMeasurementRepository;
        this.sensorMeasurementMapper = sensorMeasurementMapper;
    }

    /**
     * Return a {@link Page} of {@link SensorMeasurementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SensorMeasurementDTO> findByCriteria(SensorMeasurementCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SensorMeasurement> specification = createSpecification(criteria);
        return sensorMeasurementRepository.findAll(specification, page).map(sensorMeasurementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SensorMeasurementCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SensorMeasurement> specification = createSpecification(criteria);
        return sensorMeasurementRepository.count(specification);
    }

    /**
     * Function to convert {@link SensorMeasurementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SensorMeasurement> createSpecification(SensorMeasurementCriteria criteria) {
        Specification<SensorMeasurement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SensorMeasurement_.id),
                buildRangeSpecification(criteria.getMeasuredAt(), SensorMeasurement_.measuredAt),
                buildRangeSpecification(criteria.getValue(), SensorMeasurement_.value),
                buildStringSpecification(criteria.getQuality(), SensorMeasurement_.quality),
                buildStringSpecification(criteria.getSource(), SensorMeasurement_.source),
                buildSpecification(criteria.getSensorId(), root -> root.join(SensorMeasurement_.sensor, JoinType.LEFT).get(Sensor_.id))
            );
        }
        return specification;
    }
}
