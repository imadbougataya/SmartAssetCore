package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.SensorMeasurement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SensorMeasurement entity.
 */
@Repository
public interface SensorMeasurementRepository extends JpaRepository<SensorMeasurement, Long>, JpaSpecificationExecutor<SensorMeasurement> {
    default Optional<SensorMeasurement> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SensorMeasurement> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SensorMeasurement> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select sensorMeasurement from SensorMeasurement sensorMeasurement left join fetch sensorMeasurement.sensor",
        countQuery = "select count(sensorMeasurement) from SensorMeasurement sensorMeasurement"
    )
    Page<SensorMeasurement> findAllWithToOneRelationships(Pageable pageable);

    @Query("select sensorMeasurement from SensorMeasurement sensorMeasurement left join fetch sensorMeasurement.sensor")
    List<SensorMeasurement> findAllWithToOneRelationships();

    @Query(
        "select sensorMeasurement from SensorMeasurement sensorMeasurement left join fetch sensorMeasurement.sensor where sensorMeasurement.id =:id"
    )
    Optional<SensorMeasurement> findOneWithToOneRelationships(@Param("id") Long id);
}
