package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.DocumentLink;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentLinkRepository extends JpaRepository<DocumentLink, Long>, JpaSpecificationExecutor<DocumentLink> {}
