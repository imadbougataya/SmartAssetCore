package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.LocationEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationEvent entity.
 */
@Repository
public interface LocationEventRepository extends JpaRepository<LocationEvent, Long>, JpaSpecificationExecutor<LocationEvent> {
    default Optional<LocationEvent> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LocationEvent> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LocationEvent> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select locationEvent from LocationEvent locationEvent left join fetch locationEvent.asset left join fetch locationEvent.zone left join fetch locationEvent.gateway",
        countQuery = "select count(locationEvent) from LocationEvent locationEvent"
    )
    Page<LocationEvent> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select locationEvent from LocationEvent locationEvent left join fetch locationEvent.asset left join fetch locationEvent.zone left join fetch locationEvent.gateway"
    )
    List<LocationEvent> findAllWithToOneRelationships();

    @Query(
        "select locationEvent from LocationEvent locationEvent left join fetch locationEvent.asset left join fetch locationEvent.zone left join fetch locationEvent.gateway where locationEvent.id =:id"
    )
    Optional<LocationEvent> findOneWithToOneRelationships(@Param("id") Long id);
}
