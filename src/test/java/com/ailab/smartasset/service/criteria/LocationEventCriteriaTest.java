package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class LocationEventCriteriaTest {

    @Test
    void newLocationEventCriteriaHasAllFiltersNullTest() {
        var locationEventCriteria = new LocationEventCriteria();
        assertThat(locationEventCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void locationEventCriteriaFluentMethodsCreatesFiltersTest() {
        var locationEventCriteria = new LocationEventCriteria();

        setAllFilters(locationEventCriteria);

        assertThat(locationEventCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void locationEventCriteriaCopyCreatesNullFilterTest() {
        var locationEventCriteria = new LocationEventCriteria();
        var copy = locationEventCriteria.copy();

        assertThat(locationEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(locationEventCriteria)
        );
    }

    @Test
    void locationEventCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var locationEventCriteria = new LocationEventCriteria();
        setAllFilters(locationEventCriteria);

        var copy = locationEventCriteria.copy();

        assertThat(locationEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(locationEventCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var locationEventCriteria = new LocationEventCriteria();

        assertThat(locationEventCriteria).hasToString("LocationEventCriteria{}");
    }

    private static void setAllFilters(LocationEventCriteria locationEventCriteria) {
        locationEventCriteria.id();
        locationEventCriteria.source();
        locationEventCriteria.observedAt();
        locationEventCriteria.zoneConfidence();
        locationEventCriteria.rssi();
        locationEventCriteria.txPower();
        locationEventCriteria.latitude();
        locationEventCriteria.longitude();
        locationEventCriteria.accuracyMeters();
        locationEventCriteria.speedKmh();
        locationEventCriteria.rawPayload();
        locationEventCriteria.createdBy();
        locationEventCriteria.createdDate();
        locationEventCriteria.lastModifiedBy();
        locationEventCriteria.lastModifiedDate();
        locationEventCriteria.assetId();
        locationEventCriteria.zoneId();
        locationEventCriteria.gatewayId();
        locationEventCriteria.distinct();
    }

    private static Condition<LocationEventCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSource()) &&
                condition.apply(criteria.getObservedAt()) &&
                condition.apply(criteria.getZoneConfidence()) &&
                condition.apply(criteria.getRssi()) &&
                condition.apply(criteria.getTxPower()) &&
                condition.apply(criteria.getLatitude()) &&
                condition.apply(criteria.getLongitude()) &&
                condition.apply(criteria.getAccuracyMeters()) &&
                condition.apply(criteria.getSpeedKmh()) &&
                condition.apply(criteria.getRawPayload()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getAssetId()) &&
                condition.apply(criteria.getZoneId()) &&
                condition.apply(criteria.getGatewayId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<LocationEventCriteria> copyFiltersAre(
        LocationEventCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSource(), copy.getSource()) &&
                condition.apply(criteria.getObservedAt(), copy.getObservedAt()) &&
                condition.apply(criteria.getZoneConfidence(), copy.getZoneConfidence()) &&
                condition.apply(criteria.getRssi(), copy.getRssi()) &&
                condition.apply(criteria.getTxPower(), copy.getTxPower()) &&
                condition.apply(criteria.getLatitude(), copy.getLatitude()) &&
                condition.apply(criteria.getLongitude(), copy.getLongitude()) &&
                condition.apply(criteria.getAccuracyMeters(), copy.getAccuracyMeters()) &&
                condition.apply(criteria.getSpeedKmh(), copy.getSpeedKmh()) &&
                condition.apply(criteria.getRawPayload(), copy.getRawPayload()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getAssetId(), copy.getAssetId()) &&
                condition.apply(criteria.getZoneId(), copy.getZoneId()) &&
                condition.apply(criteria.getGatewayId(), copy.getGatewayId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
