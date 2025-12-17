package com.ailab.smartasset.service;

import com.ailab.smartasset.domain.*; // for static metamodels
import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.repository.SensorRepository;
import com.ailab.smartasset.service.criteria.SensorCriteria;
import com.ailab.smartasset.service.dto.SensorDTO;
import com.ailab.smartasset.service.mapper.SensorMapper;
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
 * Service for executing complex queries for {@link Sensor} entities in the database.
 * The main input is a {@link SensorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SensorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SensorQueryService extends QueryService<Sensor> {

    private static final Logger LOG = LoggerFactory.getLogger(SensorQueryService.class);

    private final SensorRepository sensorRepository;

    private final SensorMapper sensorMapper;

    public SensorQueryService(SensorRepository sensorRepository, SensorMapper sensorMapper) {
        this.sensorRepository = sensorRepository;
        this.sensorMapper = sensorMapper;
    }

    /**
     * Return a {@link Page} of {@link SensorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SensorDTO> findByCriteria(SensorCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sensor> specification = createSpecification(criteria);
        return sensorRepository.findAll(specification, page).map(sensorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SensorCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Sensor> specification = createSpecification(criteria);
        return sensorRepository.count(specification);
    }

    /**
     * Function to convert {@link SensorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sensor> createSpecification(SensorCriteria criteria) {
        Specification<Sensor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Sensor_.id),
                buildSpecification(criteria.getSensorType(), Sensor_.sensorType),
                buildStringSpecification(criteria.getName(), Sensor_.name),
                buildStringSpecification(criteria.getUnit(), Sensor_.unit),
                buildRangeSpecification(criteria.getMinThreshold(), Sensor_.minThreshold),
                buildRangeSpecification(criteria.getMaxThreshold(), Sensor_.maxThreshold),
                buildRangeSpecification(criteria.getInstalledAt(), Sensor_.installedAt),
                buildSpecification(criteria.getActive(), Sensor_.active),
                buildStringSpecification(criteria.getExternalId(), Sensor_.externalId),
                buildStringSpecification(criteria.getCreatedBy(), Sensor_.createdBy),
                buildRangeSpecification(criteria.getCreatedDate(), Sensor_.createdDate),
                buildStringSpecification(criteria.getLastModifiedBy(), Sensor_.lastModifiedBy),
                buildRangeSpecification(criteria.getLastModifiedDate(), Sensor_.lastModifiedDate),
                buildSpecification(criteria.getMeasurementsId(), root ->
                    root.join(Sensor_.measurements, JoinType.LEFT).get(SensorMeasurement_.id)
                ),
                buildSpecification(criteria.getAssetId(), root -> root.join(Sensor_.asset, JoinType.LEFT).get(Asset_.id))
            );
        }
        return specification;
    }
}
