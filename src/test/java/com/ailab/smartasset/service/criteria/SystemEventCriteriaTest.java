package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SystemEventCriteriaTest {

    @Test
    void newSystemEventCriteriaHasAllFiltersNullTest() {
        var systemEventCriteria = new SystemEventCriteria();
        assertThat(systemEventCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void systemEventCriteriaFluentMethodsCreatesFiltersTest() {
        var systemEventCriteria = new SystemEventCriteria();

        setAllFilters(systemEventCriteria);

        assertThat(systemEventCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void systemEventCriteriaCopyCreatesNullFilterTest() {
        var systemEventCriteria = new SystemEventCriteria();
        var copy = systemEventCriteria.copy();

        assertThat(systemEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(systemEventCriteria)
        );
    }

    @Test
    void systemEventCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var systemEventCriteria = new SystemEventCriteria();
        setAllFilters(systemEventCriteria);

        var copy = systemEventCriteria.copy();

        assertThat(systemEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(systemEventCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var systemEventCriteria = new SystemEventCriteria();

        assertThat(systemEventCriteria).hasToString("SystemEventCriteria{}");
    }

    private static void setAllFilters(SystemEventCriteria systemEventCriteria) {
        systemEventCriteria.id();
        systemEventCriteria.eventType();
        systemEventCriteria.severity();
        systemEventCriteria.source();
        systemEventCriteria.message();
        systemEventCriteria.createdAt();
        systemEventCriteria.createdBy();
        systemEventCriteria.correlationId();
        systemEventCriteria.distinct();
    }

    private static Condition<SystemEventCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEventType()) &&
                condition.apply(criteria.getSeverity()) &&
                condition.apply(criteria.getSource()) &&
                condition.apply(criteria.getMessage()) &&
                condition.apply(criteria.getCreatedAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCorrelationId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SystemEventCriteria> copyFiltersAre(SystemEventCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEventType(), copy.getEventType()) &&
                condition.apply(criteria.getSeverity(), copy.getSeverity()) &&
                condition.apply(criteria.getSource(), copy.getSource()) &&
                condition.apply(criteria.getMessage(), copy.getMessage()) &&
                condition.apply(criteria.getCreatedAt(), copy.getCreatedAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCorrelationId(), copy.getCorrelationId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
