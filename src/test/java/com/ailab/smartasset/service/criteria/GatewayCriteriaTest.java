package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class GatewayCriteriaTest {

    @Test
    void newGatewayCriteriaHasAllFiltersNullTest() {
        var gatewayCriteria = new GatewayCriteria();
        assertThat(gatewayCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void gatewayCriteriaFluentMethodsCreatesFiltersTest() {
        var gatewayCriteria = new GatewayCriteria();

        setAllFilters(gatewayCriteria);

        assertThat(gatewayCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void gatewayCriteriaCopyCreatesNullFilterTest() {
        var gatewayCriteria = new GatewayCriteria();
        var copy = gatewayCriteria.copy();

        assertThat(gatewayCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(gatewayCriteria)
        );
    }

    @Test
    void gatewayCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var gatewayCriteria = new GatewayCriteria();
        setAllFilters(gatewayCriteria);

        var copy = gatewayCriteria.copy();

        assertThat(gatewayCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(gatewayCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var gatewayCriteria = new GatewayCriteria();

        assertThat(gatewayCriteria).hasToString("GatewayCriteria{}");
    }

    private static void setAllFilters(GatewayCriteria gatewayCriteria) {
        gatewayCriteria.id();
        gatewayCriteria.code();
        gatewayCriteria.name();
        gatewayCriteria.vendor();
        gatewayCriteria.model();
        gatewayCriteria.macAddress();
        gatewayCriteria.ipAddress();
        gatewayCriteria.installedAt();
        gatewayCriteria.active();
        gatewayCriteria.createdBy();
        gatewayCriteria.createdDate();
        gatewayCriteria.lastModifiedBy();
        gatewayCriteria.lastModifiedDate();
        gatewayCriteria.locationEventsId();
        gatewayCriteria.siteId();
        gatewayCriteria.zoneId();
        gatewayCriteria.distinct();
    }

    private static Condition<GatewayCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getVendor()) &&
                condition.apply(criteria.getModel()) &&
                condition.apply(criteria.getMacAddress()) &&
                condition.apply(criteria.getIpAddress()) &&
                condition.apply(criteria.getInstalledAt()) &&
                condition.apply(criteria.getActive()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId()) &&
                condition.apply(criteria.getZoneId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<GatewayCriteria> copyFiltersAre(GatewayCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getVendor(), copy.getVendor()) &&
                condition.apply(criteria.getModel(), copy.getModel()) &&
                condition.apply(criteria.getMacAddress(), copy.getMacAddress()) &&
                condition.apply(criteria.getIpAddress(), copy.getIpAddress()) &&
                condition.apply(criteria.getInstalledAt(), copy.getInstalledAt()) &&
                condition.apply(criteria.getActive(), copy.getActive()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getLocationEventsId(), copy.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId(), copy.getSiteId()) &&
                condition.apply(criteria.getZoneId(), copy.getZoneId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
