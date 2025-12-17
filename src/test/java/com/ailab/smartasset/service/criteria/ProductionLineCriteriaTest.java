package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProductionLineCriteriaTest {

    @Test
    void newProductionLineCriteriaHasAllFiltersNullTest() {
        var productionLineCriteria = new ProductionLineCriteria();
        assertThat(productionLineCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void productionLineCriteriaFluentMethodsCreatesFiltersTest() {
        var productionLineCriteria = new ProductionLineCriteria();

        setAllFilters(productionLineCriteria);

        assertThat(productionLineCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void productionLineCriteriaCopyCreatesNullFilterTest() {
        var productionLineCriteria = new ProductionLineCriteria();
        var copy = productionLineCriteria.copy();

        assertThat(productionLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(productionLineCriteria)
        );
    }

    @Test
    void productionLineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var productionLineCriteria = new ProductionLineCriteria();
        setAllFilters(productionLineCriteria);

        var copy = productionLineCriteria.copy();

        assertThat(productionLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(productionLineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var productionLineCriteria = new ProductionLineCriteria();

        assertThat(productionLineCriteria).hasToString("ProductionLineCriteria{}");
    }

    private static void setAllFilters(ProductionLineCriteria productionLineCriteria) {
        productionLineCriteria.id();
        productionLineCriteria.code();
        productionLineCriteria.name();
        productionLineCriteria.description();
        productionLineCriteria.createdBy();
        productionLineCriteria.createdDate();
        productionLineCriteria.lastModifiedBy();
        productionLineCriteria.lastModifiedDate();
        productionLineCriteria.siteId();
        productionLineCriteria.distinct();
    }

    private static Condition<ProductionLineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getSiteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProductionLineCriteria> copyFiltersAre(
        ProductionLineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getSiteId(), copy.getSiteId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
