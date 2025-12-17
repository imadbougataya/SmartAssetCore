package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.Gateway;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gateway entity.
 */
@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long>, JpaSpecificationExecutor<Gateway> {
    default Optional<Gateway> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Gateway> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Gateway> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select gateway from Gateway gateway left join fetch gateway.site left join fetch gateway.zone",
        countQuery = "select count(gateway) from Gateway gateway"
    )
    Page<Gateway> findAllWithToOneRelationships(Pageable pageable);

    @Query("select gateway from Gateway gateway left join fetch gateway.site left join fetch gateway.zone")
    List<Gateway> findAllWithToOneRelationships();

    @Query("select gateway from Gateway gateway left join fetch gateway.site left join fetch gateway.zone where gateway.id =:id")
    Optional<Gateway> findOneWithToOneRelationships(@Param("id") Long id);
}
