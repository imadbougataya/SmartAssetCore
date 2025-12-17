package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ZoneCriteriaTest {

    @Test
    void newZoneCriteriaHasAllFiltersNullTest() {
        var zoneCriteria = new ZoneCriteria();
        assertThat(zoneCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void zoneCriteriaFluentMethodsCreatesFiltersTest() {
        var zoneCriteria = new ZoneCriteria();

        setAllFilters(zoneCriteria);

        assertThat(zoneCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void zoneCriteriaCopyCreatesNullFilterTest() {
        var zoneCriteria = new ZoneCriteria();
        var copy = zoneCriteria.copy();

        assertThat(zoneCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(zoneCriteria)
        );
    }

    @Test
    void zoneCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var zoneCriteria = new ZoneCriteria();
        setAllFilters(zoneCriteria);

        var copy = zoneCriteria.copy();

        assertThat(zoneCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(zoneCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var zoneCriteria = new ZoneCriteria();

        assertThat(zoneCriteria).hasToString("ZoneCriteria{}");
    }

    private static void setAllFilters(ZoneCriteria zoneCriteria) {
        zoneCriteria.id();
        zoneCriteria.code();
        zoneCriteria.name();
        zoneCriteria.description();
        zoneCriteria.zoneType();
        zoneCriteria.centerLat();
        zoneCriteria.centerLon();
        zoneCriteria.radiusMeters();
        zoneCriteria.createdBy();
        zoneCriteria.createdDate();
        zoneCriteria.lastModifiedBy();
        zoneCriteria.lastModifiedDate();
        zoneCriteria.locationEventsId();
        zoneCriteria.siteId();
        zoneCriteria.distinct();
    }

    private static Condition<ZoneCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getZoneType()) &&
                condition.apply(criteria.getCenterLat()) &&
                condition.apply(criteria.getCenterLon()) &&
                condition.apply(criteria.getRadiusMeters()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ZoneCriteria> copyFiltersAre(ZoneCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getZoneType(), copy.getZoneType()) &&
                condition.apply(criteria.getCenterLat(), copy.getCenterLat()) &&
                condition.apply(criteria.getCenterLon(), copy.getCenterLon()) &&
                condition.apply(criteria.getRadiusMeters(), copy.getRadiusMeters()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getLocationEventsId(), copy.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId(), copy.getSiteId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
