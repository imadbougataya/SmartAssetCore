package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.SystemEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SystemEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemEventRepository extends JpaRepository<SystemEvent, Long>, JpaSpecificationExecutor<SystemEvent> {}
