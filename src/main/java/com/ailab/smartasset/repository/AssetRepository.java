package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.Asset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Asset entity.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
    default Optional<Asset> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Asset> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Asset> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select asset from Asset asset left join fetch asset.site left join fetch asset.productionLine left join fetch asset.currentZone",
        countQuery = "select count(asset) from Asset asset"
    )
    Page<Asset> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select asset from Asset asset left join fetch asset.site left join fetch asset.productionLine left join fetch asset.currentZone"
    )
    List<Asset> findAllWithToOneRelationships();

    @Query(
        "select asset from Asset asset left join fetch asset.site left join fetch asset.productionLine left join fetch asset.currentZone where asset.id =:id"
    )
    Optional<Asset> findOneWithToOneRelationships(@Param("id") Long id);
}
