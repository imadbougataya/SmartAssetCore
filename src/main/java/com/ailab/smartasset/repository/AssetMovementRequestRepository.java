package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.AssetMovementRequest;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetMovementRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetMovementRequestRepository
    extends JpaRepository<AssetMovementRequest, Long>, JpaSpecificationExecutor<AssetMovementRequest> {
    @Query(
        "select assetMovementRequest from AssetMovementRequest assetMovementRequest where assetMovementRequest.requestedBy.login = ?#{authentication.name}"
    )
    List<AssetMovementRequest> findByRequestedByIsCurrentUser();

    @Query(
        "select assetMovementRequest from AssetMovementRequest assetMovementRequest where assetMovementRequest.approvedBy.login = ?#{authentication.name}"
    )
    List<AssetMovementRequest> findByApprovedByIsCurrentUser();
}
