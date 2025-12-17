package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SensorMeasurementCriteriaTest {

    @Test
    void newSensorMeasurementCriteriaHasAllFiltersNullTest() {
        var sensorMeasurementCriteria = new SensorMeasurementCriteria();
        assertThat(sensorMeasurementCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sensorMeasurementCriteriaFluentMethodsCreatesFiltersTest() {
        var sensorMeasurementCriteria = new SensorMeasurementCriteria();

        setAllFilters(sensorMeasurementCriteria);

        assertThat(sensorMeasurementCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sensorMeasurementCriteriaCopyCreatesNullFilterTest() {
        var sensorMeasurementCriteria = new SensorMeasurementCriteria();
        var copy = sensorMeasurementCriteria.copy();

        assertThat(sensorMeasurementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sensorMeasurementCriteria)
        );
    }

    @Test
    void sensorMeasurementCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sensorMeasurementCriteria = new SensorMeasurementCriteria();
        setAllFilters(sensorMeasurementCriteria);

        var copy = sensorMeasurementCriteria.copy();

        assertThat(sensorMeasurementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sensorMeasurementCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sensorMeasurementCriteria = new SensorMeasurementCriteria();

        assertThat(sensorMeasurementCriteria).hasToString("SensorMeasurementCriteria{}");
    }

    private static void setAllFilters(SensorMeasurementCriteria sensorMeasurementCriteria) {
        sensorMeasurementCriteria.id();
        sensorMeasurementCriteria.measuredAt();
        sensorMeasurementCriteria.value();
        sensorMeasurementCriteria.quality();
        sensorMeasurementCriteria.source();
        sensorMeasurementCriteria.createdBy();
        sensorMeasurementCriteria.createdDate();
        sensorMeasurementCriteria.lastModifiedBy();
        sensorMeasurementCriteria.lastModifiedDate();
        sensorMeasurementCriteria.sensorId();
        sensorMeasurementCriteria.distinct();
    }

    private static Condition<SensorMeasurementCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMeasuredAt()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getQuality()) &&
                condition.apply(criteria.getSource()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getSensorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SensorMeasurementCriteria> copyFiltersAre(
        SensorMeasurementCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMeasuredAt(), copy.getMeasuredAt()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getQuality(), copy.getQuality()) &&
                condition.apply(criteria.getSource(), copy.getSource()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getSensorId(), copy.getSensorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
