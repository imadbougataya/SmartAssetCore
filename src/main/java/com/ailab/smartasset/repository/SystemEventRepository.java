package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.SystemEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SystemEvent entity.
 */
@Repository
public interface SystemEventRepository extends JpaRepository<SystemEvent, Long>, JpaSpecificationExecutor<SystemEvent> {
    default Optional<SystemEvent> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SystemEvent> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SystemEvent> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select systemEvent from SystemEvent systemEvent left join fetch systemEvent.asset",
        countQuery = "select count(systemEvent) from SystemEvent systemEvent"
    )
    Page<SystemEvent> findAllWithToOneRelationships(Pageable pageable);

    @Query("select systemEvent from SystemEvent systemEvent left join fetch systemEvent.asset")
    List<SystemEvent> findAllWithToOneRelationships();

    @Query("select systemEvent from SystemEvent systemEvent left join fetch systemEvent.asset where systemEvent.id =:id")
    Optional<SystemEvent> findOneWithToOneRelationships(@Param("id") Long id);
}
