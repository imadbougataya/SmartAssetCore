package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.MaintenanceEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaintenanceEvent entity.
 */
@Repository
public interface MaintenanceEventRepository extends JpaRepository<MaintenanceEvent, Long>, JpaSpecificationExecutor<MaintenanceEvent> {
    default Optional<MaintenanceEvent> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<MaintenanceEvent> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<MaintenanceEvent> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select maintenanceEvent from MaintenanceEvent maintenanceEvent left join fetch maintenanceEvent.asset",
        countQuery = "select count(maintenanceEvent) from MaintenanceEvent maintenanceEvent"
    )
    Page<MaintenanceEvent> findAllWithToOneRelationships(Pageable pageable);

    @Query("select maintenanceEvent from MaintenanceEvent maintenanceEvent left join fetch maintenanceEvent.asset")
    List<MaintenanceEvent> findAllWithToOneRelationships();

    @Query(
        "select maintenanceEvent from MaintenanceEvent maintenanceEvent left join fetch maintenanceEvent.asset where maintenanceEvent.id =:id"
    )
    Optional<MaintenanceEvent> findOneWithToOneRelationships(@Param("id") Long id);
}
