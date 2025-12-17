package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SiteCriteriaTest {

    @Test
    void newSiteCriteriaHasAllFiltersNullTest() {
        var siteCriteria = new SiteCriteria();
        assertThat(siteCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void siteCriteriaFluentMethodsCreatesFiltersTest() {
        var siteCriteria = new SiteCriteria();

        setAllFilters(siteCriteria);

        assertThat(siteCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void siteCriteriaCopyCreatesNullFilterTest() {
        var siteCriteria = new SiteCriteria();
        var copy = siteCriteria.copy();

        assertThat(siteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(siteCriteria)
        );
    }

    @Test
    void siteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var siteCriteria = new SiteCriteria();
        setAllFilters(siteCriteria);

        var copy = siteCriteria.copy();

        assertThat(siteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(siteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var siteCriteria = new SiteCriteria();

        assertThat(siteCriteria).hasToString("SiteCriteria{}");
    }

    private static void setAllFilters(SiteCriteria siteCriteria) {
        siteCriteria.id();
        siteCriteria.code();
        siteCriteria.name();
        siteCriteria.description();
        siteCriteria.createdBy();
        siteCriteria.createdDate();
        siteCriteria.lastModifiedBy();
        siteCriteria.lastModifiedDate();
        siteCriteria.distinct();
    }

    private static Condition<SiteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SiteCriteria> copyFiltersAre(SiteCriteria copy, BiFunction<Object, Object, Boolean> condition) {
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
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
