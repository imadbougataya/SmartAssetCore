package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.SensorMeasurement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SensorMeasurement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorMeasurementRepository extends JpaRepository<SensorMeasurement, Long>, JpaSpecificationExecutor<SensorMeasurement> {}
