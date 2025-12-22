package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SensorCriteriaTest {

    @Test
    void newSensorCriteriaHasAllFiltersNullTest() {
        var sensorCriteria = new SensorCriteria();
        assertThat(sensorCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sensorCriteriaFluentMethodsCreatesFiltersTest() {
        var sensorCriteria = new SensorCriteria();

        setAllFilters(sensorCriteria);

        assertThat(sensorCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sensorCriteriaCopyCreatesNullFilterTest() {
        var sensorCriteria = new SensorCriteria();
        var copy = sensorCriteria.copy();

        assertThat(sensorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sensorCriteria)
        );
    }

    @Test
    void sensorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sensorCriteria = new SensorCriteria();
        setAllFilters(sensorCriteria);

        var copy = sensorCriteria.copy();

        assertThat(sensorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sensorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sensorCriteria = new SensorCriteria();

        assertThat(sensorCriteria).hasToString("SensorCriteria{}");
    }

    private static void setAllFilters(SensorCriteria sensorCriteria) {
        sensorCriteria.id();
        sensorCriteria.sensorType();
        sensorCriteria.externalId();
        sensorCriteria.name();
        sensorCriteria.unit();
        sensorCriteria.minThreshold();
        sensorCriteria.maxThreshold();
        sensorCriteria.installedAt();
        sensorCriteria.active();
        sensorCriteria.assetId();
        sensorCriteria.distinct();
    }

    private static Condition<SensorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSensorType()) &&
                condition.apply(criteria.getExternalId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getUnit()) &&
                condition.apply(criteria.getMinThreshold()) &&
                condition.apply(criteria.getMaxThreshold()) &&
                condition.apply(criteria.getInstalledAt()) &&
                condition.apply(criteria.getActive()) &&
                condition.apply(criteria.getAssetId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SensorCriteria> copyFiltersAre(SensorCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSensorType(), copy.getSensorType()) &&
                condition.apply(criteria.getExternalId(), copy.getExternalId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getUnit(), copy.getUnit()) &&
                condition.apply(criteria.getMinThreshold(), copy.getMinThreshold()) &&
                condition.apply(criteria.getMaxThreshold(), copy.getMaxThreshold()) &&
                condition.apply(criteria.getInstalledAt(), copy.getInstalledAt()) &&
                condition.apply(criteria.getActive(), copy.getActive()) &&
                condition.apply(criteria.getAssetId(), copy.getAssetId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
