package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.LocationEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationEventRepository extends JpaRepository<LocationEvent, Long>, JpaSpecificationExecutor<LocationEvent> {}
