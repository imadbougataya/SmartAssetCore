package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.AssetMovementRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetMovementRequest entity.
 */
@Repository
public interface AssetMovementRequestRepository
    extends JpaRepository<AssetMovementRequest, Long>, JpaSpecificationExecutor<AssetMovementRequest> {
    default Optional<AssetMovementRequest> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AssetMovementRequest> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AssetMovementRequest> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select assetMovementRequest from AssetMovementRequest assetMovementRequest left join fetch assetMovementRequest.asset",
        countQuery = "select count(assetMovementRequest) from AssetMovementRequest assetMovementRequest"
    )
    Page<AssetMovementRequest> findAllWithToOneRelationships(Pageable pageable);

    @Query("select assetMovementRequest from AssetMovementRequest assetMovementRequest left join fetch assetMovementRequest.asset")
    List<AssetMovementRequest> findAllWithToOneRelationships();

    @Query(
        "select assetMovementRequest from AssetMovementRequest assetMovementRequest left join fetch assetMovementRequest.asset where assetMovementRequest.id =:id"
    )
    Optional<AssetMovementRequest> findOneWithToOneRelationships(@Param("id") Long id);
}
