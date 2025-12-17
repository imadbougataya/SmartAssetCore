package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AssetMovementRequestCriteriaTest {

    @Test
    void newAssetMovementRequestCriteriaHasAllFiltersNullTest() {
        var assetMovementRequestCriteria = new AssetMovementRequestCriteria();
        assertThat(assetMovementRequestCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void assetMovementRequestCriteriaFluentMethodsCreatesFiltersTest() {
        var assetMovementRequestCriteria = new AssetMovementRequestCriteria();

        setAllFilters(assetMovementRequestCriteria);

        assertThat(assetMovementRequestCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void assetMovementRequestCriteriaCopyCreatesNullFilterTest() {
        var assetMovementRequestCriteria = new AssetMovementRequestCriteria();
        var copy = assetMovementRequestCriteria.copy();

        assertThat(assetMovementRequestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(assetMovementRequestCriteria)
        );
    }

    @Test
    void assetMovementRequestCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var assetMovementRequestCriteria = new AssetMovementRequestCriteria();
        setAllFilters(assetMovementRequestCriteria);

        var copy = assetMovementRequestCriteria.copy();

        assertThat(assetMovementRequestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(assetMovementRequestCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var assetMovementRequestCriteria = new AssetMovementRequestCriteria();

        assertThat(assetMovementRequestCriteria).hasToString("AssetMovementRequestCriteria{}");
    }

    private static void setAllFilters(AssetMovementRequestCriteria assetMovementRequestCriteria) {
        assetMovementRequestCriteria.id();
        assetMovementRequestCriteria.status();
        assetMovementRequestCriteria.requestedAt();
        assetMovementRequestCriteria.reason();
        assetMovementRequestCriteria.fromLocationLabel();
        assetMovementRequestCriteria.toLocationLabel();
        assetMovementRequestCriteria.esignWorkflowId();
        assetMovementRequestCriteria.esignStatus();
        assetMovementRequestCriteria.esignLastUpdate();
        assetMovementRequestCriteria.signedAt();
        assetMovementRequestCriteria.executedAt();
        assetMovementRequestCriteria.requestedBy();
        assetMovementRequestCriteria.approvedBy();
        assetMovementRequestCriteria.createdBy();
        assetMovementRequestCriteria.createdDate();
        assetMovementRequestCriteria.lastModifiedBy();
        assetMovementRequestCriteria.lastModifiedDate();
        assetMovementRequestCriteria.assetId();
        assetMovementRequestCriteria.distinct();
    }

    private static Condition<AssetMovementRequestCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getRequestedAt()) &&
                condition.apply(criteria.getReason()) &&
                condition.apply(criteria.getFromLocationLabel()) &&
                condition.apply(criteria.getToLocationLabel()) &&
                condition.apply(criteria.getEsignWorkflowId()) &&
                condition.apply(criteria.getEsignStatus()) &&
                condition.apply(criteria.getEsignLastUpdate()) &&
                condition.apply(criteria.getSignedAt()) &&
                condition.apply(criteria.getExecutedAt()) &&
                condition.apply(criteria.getRequestedBy()) &&
                condition.apply(criteria.getApprovedBy()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getAssetId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AssetMovementRequestCriteria> copyFiltersAre(
        AssetMovementRequestCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getRequestedAt(), copy.getRequestedAt()) &&
                condition.apply(criteria.getReason(), copy.getReason()) &&
                condition.apply(criteria.getFromLocationLabel(), copy.getFromLocationLabel()) &&
                condition.apply(criteria.getToLocationLabel(), copy.getToLocationLabel()) &&
                condition.apply(criteria.getEsignWorkflowId(), copy.getEsignWorkflowId()) &&
                condition.apply(criteria.getEsignStatus(), copy.getEsignStatus()) &&
                condition.apply(criteria.getEsignLastUpdate(), copy.getEsignLastUpdate()) &&
                condition.apply(criteria.getSignedAt(), copy.getSignedAt()) &&
                condition.apply(criteria.getExecutedAt(), copy.getExecutedAt()) &&
                condition.apply(criteria.getRequestedBy(), copy.getRequestedBy()) &&
                condition.apply(criteria.getApprovedBy(), copy.getApprovedBy()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getAssetId(), copy.getAssetId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
