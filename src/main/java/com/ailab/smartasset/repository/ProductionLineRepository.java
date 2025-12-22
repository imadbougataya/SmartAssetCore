package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.ProductionLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine, Long>, JpaSpecificationExecutor<ProductionLine> {}
