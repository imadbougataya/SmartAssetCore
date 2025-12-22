package com.ailab.smartasset.repository;

import com.ailab.smartasset.domain.Document;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Document entity.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    default Optional<Document> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Document> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Document> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select document from Document document left join fetch document.asset",
        countQuery = "select count(document) from Document document"
    )
    Page<Document> findAllWithToOneRelationships(Pageable pageable);

    @Query("select document from Document document left join fetch document.asset")
    List<Document> findAllWithToOneRelationships();

    @Query("select document from Document document left join fetch document.asset where document.id =:id")
    Optional<Document> findOneWithToOneRelationships(@Param("id") Long id);
}
