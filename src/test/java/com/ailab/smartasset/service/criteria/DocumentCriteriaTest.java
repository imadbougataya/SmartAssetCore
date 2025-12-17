package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DocumentCriteriaTest {

    @Test
    void newDocumentCriteriaHasAllFiltersNullTest() {
        var documentCriteria = new DocumentCriteria();
        assertThat(documentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void documentCriteriaFluentMethodsCreatesFiltersTest() {
        var documentCriteria = new DocumentCriteria();

        setAllFilters(documentCriteria);

        assertThat(documentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void documentCriteriaCopyCreatesNullFilterTest() {
        var documentCriteria = new DocumentCriteria();
        var copy = documentCriteria.copy();

        assertThat(documentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(documentCriteria)
        );
    }

    @Test
    void documentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var documentCriteria = new DocumentCriteria();
        setAllFilters(documentCriteria);

        var copy = documentCriteria.copy();

        assertThat(documentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(documentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var documentCriteria = new DocumentCriteria();

        assertThat(documentCriteria).hasToString("DocumentCriteria{}");
    }

    private static void setAllFilters(DocumentCriteria documentCriteria) {
        documentCriteria.id();
        documentCriteria.fileName();
        documentCriteria.mimeType();
        documentCriteria.sizeBytes();
        documentCriteria.storageRef();
        documentCriteria.checksumSha256();
        documentCriteria.uploadedAt();
        documentCriteria.uploadedBy();
        documentCriteria.createdBy();
        documentCriteria.createdDate();
        documentCriteria.lastModifiedBy();
        documentCriteria.lastModifiedDate();
        documentCriteria.linksId();
        documentCriteria.distinct();
    }

    private static Condition<DocumentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFileName()) &&
                condition.apply(criteria.getMimeType()) &&
                condition.apply(criteria.getSizeBytes()) &&
                condition.apply(criteria.getStorageRef()) &&
                condition.apply(criteria.getChecksumSha256()) &&
                condition.apply(criteria.getUploadedAt()) &&
                condition.apply(criteria.getUploadedBy()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getLinksId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DocumentCriteria> copyFiltersAre(DocumentCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFileName(), copy.getFileName()) &&
                condition.apply(criteria.getMimeType(), copy.getMimeType()) &&
                condition.apply(criteria.getSizeBytes(), copy.getSizeBytes()) &&
                condition.apply(criteria.getStorageRef(), copy.getStorageRef()) &&
                condition.apply(criteria.getChecksumSha256(), copy.getChecksumSha256()) &&
                condition.apply(criteria.getUploadedAt(), copy.getUploadedAt()) &&
                condition.apply(criteria.getUploadedBy(), copy.getUploadedBy()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getLinksId(), copy.getLinksId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
