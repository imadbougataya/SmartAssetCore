package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.DocumentLink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentLink entity.
 */
@Repository
public interface DocumentLinkRepository extends JpaRepository<DocumentLink, Long>, JpaSpecificationExecutor<DocumentLink> {
    default Optional<DocumentLink> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocumentLink> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocumentLink> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select documentLink from DocumentLink documentLink left join fetch documentLink.document",
        countQuery = "select count(documentLink) from DocumentLink documentLink"
    )
    Page<DocumentLink> findAllWithToOneRelationships(Pageable pageable);

    @Query("select documentLink from DocumentLink documentLink left join fetch documentLink.document")
    List<DocumentLink> findAllWithToOneRelationships();

    @Query("select documentLink from DocumentLink documentLink left join fetch documentLink.document where documentLink.id =:id")
    Optional<DocumentLink> findOneWithToOneRelationships(@Param("id") Long id);
}
