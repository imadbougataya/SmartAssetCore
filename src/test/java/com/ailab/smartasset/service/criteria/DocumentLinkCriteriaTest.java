package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DocumentLinkCriteriaTest {

    @Test
    void newDocumentLinkCriteriaHasAllFiltersNullTest() {
        var documentLinkCriteria = new DocumentLinkCriteria();
        assertThat(documentLinkCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void documentLinkCriteriaFluentMethodsCreatesFiltersTest() {
        var documentLinkCriteria = new DocumentLinkCriteria();

        setAllFilters(documentLinkCriteria);

        assertThat(documentLinkCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void documentLinkCriteriaCopyCreatesNullFilterTest() {
        var documentLinkCriteria = new DocumentLinkCriteria();
        var copy = documentLinkCriteria.copy();

        assertThat(documentLinkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(documentLinkCriteria)
        );
    }

    @Test
    void documentLinkCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var documentLinkCriteria = new DocumentLinkCriteria();
        setAllFilters(documentLinkCriteria);

        var copy = documentLinkCriteria.copy();

        assertThat(documentLinkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(documentLinkCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var documentLinkCriteria = new DocumentLinkCriteria();

        assertThat(documentLinkCriteria).hasToString("DocumentLinkCriteria{}");
    }

    private static void setAllFilters(DocumentLinkCriteria documentLinkCriteria) {
        documentLinkCriteria.id();
        documentLinkCriteria.entityType();
        documentLinkCriteria.entityId();
        documentLinkCriteria.label();
        documentLinkCriteria.linkedAt();
        documentLinkCriteria.createdBy();
        documentLinkCriteria.createdDate();
        documentLinkCriteria.lastModifiedBy();
        documentLinkCriteria.lastModifiedDate();
        documentLinkCriteria.documentId();
        documentLinkCriteria.distinct();
    }

    private static Condition<DocumentLinkCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEntityType()) &&
                condition.apply(criteria.getEntityId()) &&
                condition.apply(criteria.getLabel()) &&
                condition.apply(criteria.getLinkedAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getDocumentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DocumentLinkCriteria> copyFiltersAre(
        DocumentLinkCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEntityType(), copy.getEntityType()) &&
                condition.apply(criteria.getEntityId(), copy.getEntityId()) &&
                condition.apply(criteria.getLabel(), copy.getLabel()) &&
                condition.apply(criteria.getLinkedAt(), copy.getLinkedAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getDocumentId(), copy.getDocumentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
