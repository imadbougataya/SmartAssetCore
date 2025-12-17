package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.ProductionLine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductionLine entity.
 */
@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine, Long>, JpaSpecificationExecutor<ProductionLine> {
    default Optional<ProductionLine> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProductionLine> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProductionLine> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select productionLine from ProductionLine productionLine left join fetch productionLine.site",
        countQuery = "select count(productionLine) from ProductionLine productionLine"
    )
    Page<ProductionLine> findAllWithToOneRelationships(Pageable pageable);

    @Query("select productionLine from ProductionLine productionLine left join fetch productionLine.site")
    List<ProductionLine> findAllWithToOneRelationships();

    @Query("select productionLine from ProductionLine productionLine left join fetch productionLine.site where productionLine.id =:id")
    Optional<ProductionLine> findOneWithToOneRelationships(@Param("id") Long id);
}
