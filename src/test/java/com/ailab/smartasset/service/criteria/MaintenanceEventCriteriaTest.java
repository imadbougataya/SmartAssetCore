package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MaintenanceEventCriteriaTest {

    @Test
    void newMaintenanceEventCriteriaHasAllFiltersNullTest() {
        var maintenanceEventCriteria = new MaintenanceEventCriteria();
        assertThat(maintenanceEventCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void maintenanceEventCriteriaFluentMethodsCreatesFiltersTest() {
        var maintenanceEventCriteria = new MaintenanceEventCriteria();

        setAllFilters(maintenanceEventCriteria);

        assertThat(maintenanceEventCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void maintenanceEventCriteriaCopyCreatesNullFilterTest() {
        var maintenanceEventCriteria = new MaintenanceEventCriteria();
        var copy = maintenanceEventCriteria.copy();

        assertThat(maintenanceEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(maintenanceEventCriteria)
        );
    }

    @Test
    void maintenanceEventCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var maintenanceEventCriteria = new MaintenanceEventCriteria();
        setAllFilters(maintenanceEventCriteria);

        var copy = maintenanceEventCriteria.copy();

        assertThat(maintenanceEventCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(maintenanceEventCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var maintenanceEventCriteria = new MaintenanceEventCriteria();

        assertThat(maintenanceEventCriteria).hasToString("MaintenanceEventCriteria{}");
    }

    private static void setAllFilters(MaintenanceEventCriteria maintenanceEventCriteria) {
        maintenanceEventCriteria.id();
        maintenanceEventCriteria.maintenanceType();
        maintenanceEventCriteria.status();
        maintenanceEventCriteria.requestedAt();
        maintenanceEventCriteria.plannedAt();
        maintenanceEventCriteria.startedAt();
        maintenanceEventCriteria.finishedAt();
        maintenanceEventCriteria.title();
        maintenanceEventCriteria.description();
        maintenanceEventCriteria.technician();
        maintenanceEventCriteria.downtimeMinutes();
        maintenanceEventCriteria.costAmount();
        maintenanceEventCriteria.notes();
        maintenanceEventCriteria.createdBy();
        maintenanceEventCriteria.createdDate();
        maintenanceEventCriteria.lastModifiedBy();
        maintenanceEventCriteria.lastModifiedDate();
        maintenanceEventCriteria.assetId();
        maintenanceEventCriteria.distinct();
    }

    private static Condition<MaintenanceEventCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMaintenanceType()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getRequestedAt()) &&
                condition.apply(criteria.getPlannedAt()) &&
                condition.apply(criteria.getStartedAt()) &&
                condition.apply(criteria.getFinishedAt()) &&
                condition.apply(criteria.getTitle()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getTechnician()) &&
                condition.apply(criteria.getDowntimeMinutes()) &&
                condition.apply(criteria.getCostAmount()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getAssetId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MaintenanceEventCriteria> copyFiltersAre(
        MaintenanceEventCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMaintenanceType(), copy.getMaintenanceType()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getRequestedAt(), copy.getRequestedAt()) &&
                condition.apply(criteria.getPlannedAt(), copy.getPlannedAt()) &&
                condition.apply(criteria.getStartedAt(), copy.getStartedAt()) &&
                condition.apply(criteria.getFinishedAt(), copy.getFinishedAt()) &&
                condition.apply(criteria.getTitle(), copy.getTitle()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getTechnician(), copy.getTechnician()) &&
                condition.apply(criteria.getDowntimeMinutes(), copy.getDowntimeMinutes()) &&
                condition.apply(criteria.getCostAmount(), copy.getCostAmount()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
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
