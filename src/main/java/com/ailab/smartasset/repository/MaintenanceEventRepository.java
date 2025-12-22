package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.MaintenanceEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaintenanceEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaintenanceEventRepository extends JpaRepository<MaintenanceEvent, Long>, JpaSpecificationExecutor<MaintenanceEvent> {}
