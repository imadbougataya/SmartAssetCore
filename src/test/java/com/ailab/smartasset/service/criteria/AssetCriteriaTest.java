package com.ailab.smartasset.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AssetCriteriaTest {

    @Test
    void newAssetCriteriaHasAllFiltersNullTest() {
        var assetCriteria = new AssetCriteria();
        assertThat(assetCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void assetCriteriaFluentMethodsCreatesFiltersTest() {
        var assetCriteria = new AssetCriteria();

        setAllFilters(assetCriteria);

        assertThat(assetCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void assetCriteriaCopyCreatesNullFilterTest() {
        var assetCriteria = new AssetCriteria();
        var copy = assetCriteria.copy();

        assertThat(assetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(assetCriteria)
        );
    }

    @Test
    void assetCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var assetCriteria = new AssetCriteria();
        setAllFilters(assetCriteria);

        var copy = assetCriteria.copy();

        assertThat(assetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(assetCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var assetCriteria = new AssetCriteria();

        assertThat(assetCriteria).hasToString("AssetCriteria{}");
    }

    private static void setAllFilters(AssetCriteria assetCriteria) {
        assetCriteria.id();
        assetCriteria.assetType();
        assetCriteria.assetCode();
        assetCriteria.reference();
        assetCriteria.description();
        assetCriteria.status();
        assetCriteria.criticality();
        assetCriteria.responsibleName();
        assetCriteria.costCenter();
        assetCriteria.brand();
        assetCriteria.model();
        assetCriteria.serialNumber();
        assetCriteria.powerKw();
        assetCriteria.voltageV();
        assetCriteria.currentA();
        assetCriteria.cosPhi();
        assetCriteria.speedRpm();
        assetCriteria.ipRating();
        assetCriteria.insulationClass();
        assetCriteria.mountingType();
        assetCriteria.shaftDiameterMm();
        assetCriteria.footDistanceAmm();
        assetCriteria.footDistanceBmm();
        assetCriteria.frontFlangeMm();
        assetCriteria.rearFlangeMm();
        assetCriteria.iecAxisHeightMm();
        assetCriteria.dimensionsSource();
        assetCriteria.hasHeating();
        assetCriteria.temperatureProbeType();
        assetCriteria.lastCommissioningDate();
        assetCriteria.lastMaintenanceDate();
        assetCriteria.maintenanceCount();
        assetCriteria.createdBy();
        assetCriteria.createdDate();
        assetCriteria.lastModifiedBy();
        assetCriteria.lastModifiedDate();
        assetCriteria.sensorsId();
        assetCriteria.maintenanceEventsId();
        assetCriteria.movementRequestsId();
        assetCriteria.locationEventsId();
        assetCriteria.siteId();
        assetCriteria.productionLineId();
        assetCriteria.currentZoneId();
        assetCriteria.distinct();
    }

    private static Condition<AssetCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAssetType()) &&
                condition.apply(criteria.getAssetCode()) &&
                condition.apply(criteria.getReference()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCriticality()) &&
                condition.apply(criteria.getResponsibleName()) &&
                condition.apply(criteria.getCostCenter()) &&
                condition.apply(criteria.getBrand()) &&
                condition.apply(criteria.getModel()) &&
                condition.apply(criteria.getSerialNumber()) &&
                condition.apply(criteria.getPowerKw()) &&
                condition.apply(criteria.getVoltageV()) &&
                condition.apply(criteria.getCurrentA()) &&
                condition.apply(criteria.getCosPhi()) &&
                condition.apply(criteria.getSpeedRpm()) &&
                condition.apply(criteria.getIpRating()) &&
                condition.apply(criteria.getInsulationClass()) &&
                condition.apply(criteria.getMountingType()) &&
                condition.apply(criteria.getShaftDiameterMm()) &&
                condition.apply(criteria.getFootDistanceAmm()) &&
                condition.apply(criteria.getFootDistanceBmm()) &&
                condition.apply(criteria.getFrontFlangeMm()) &&
                condition.apply(criteria.getRearFlangeMm()) &&
                condition.apply(criteria.getIecAxisHeightMm()) &&
                condition.apply(criteria.getDimensionsSource()) &&
                condition.apply(criteria.getHasHeating()) &&
                condition.apply(criteria.getTemperatureProbeType()) &&
                condition.apply(criteria.getLastCommissioningDate()) &&
                condition.apply(criteria.getLastMaintenanceDate()) &&
                condition.apply(criteria.getMaintenanceCount()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getSensorsId()) &&
                condition.apply(criteria.getMaintenanceEventsId()) &&
                condition.apply(criteria.getMovementRequestsId()) &&
                condition.apply(criteria.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId()) &&
                condition.apply(criteria.getProductionLineId()) &&
                condition.apply(criteria.getCurrentZoneId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AssetCriteria> copyFiltersAre(AssetCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAssetType(), copy.getAssetType()) &&
                condition.apply(criteria.getAssetCode(), copy.getAssetCode()) &&
                condition.apply(criteria.getReference(), copy.getReference()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCriticality(), copy.getCriticality()) &&
                condition.apply(criteria.getResponsibleName(), copy.getResponsibleName()) &&
                condition.apply(criteria.getCostCenter(), copy.getCostCenter()) &&
                condition.apply(criteria.getBrand(), copy.getBrand()) &&
                condition.apply(criteria.getModel(), copy.getModel()) &&
                condition.apply(criteria.getSerialNumber(), copy.getSerialNumber()) &&
                condition.apply(criteria.getPowerKw(), copy.getPowerKw()) &&
                condition.apply(criteria.getVoltageV(), copy.getVoltageV()) &&
                condition.apply(criteria.getCurrentA(), copy.getCurrentA()) &&
                condition.apply(criteria.getCosPhi(), copy.getCosPhi()) &&
                condition.apply(criteria.getSpeedRpm(), copy.getSpeedRpm()) &&
                condition.apply(criteria.getIpRating(), copy.getIpRating()) &&
                condition.apply(criteria.getInsulationClass(), copy.getInsulationClass()) &&
                condition.apply(criteria.getMountingType(), copy.getMountingType()) &&
                condition.apply(criteria.getShaftDiameterMm(), copy.getShaftDiameterMm()) &&
                condition.apply(criteria.getFootDistanceAmm(), copy.getFootDistanceAmm()) &&
                condition.apply(criteria.getFootDistanceBmm(), copy.getFootDistanceBmm()) &&
                condition.apply(criteria.getFrontFlangeMm(), copy.getFrontFlangeMm()) &&
                condition.apply(criteria.getRearFlangeMm(), copy.getRearFlangeMm()) &&
                condition.apply(criteria.getIecAxisHeightMm(), copy.getIecAxisHeightMm()) &&
                condition.apply(criteria.getDimensionsSource(), copy.getDimensionsSource()) &&
                condition.apply(criteria.getHasHeating(), copy.getHasHeating()) &&
                condition.apply(criteria.getTemperatureProbeType(), copy.getTemperatureProbeType()) &&
                condition.apply(criteria.getLastCommissioningDate(), copy.getLastCommissioningDate()) &&
                condition.apply(criteria.getLastMaintenanceDate(), copy.getLastMaintenanceDate()) &&
                condition.apply(criteria.getMaintenanceCount(), copy.getMaintenanceCount()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getSensorsId(), copy.getSensorsId()) &&
                condition.apply(criteria.getMaintenanceEventsId(), copy.getMaintenanceEventsId()) &&
                condition.apply(criteria.getMovementRequestsId(), copy.getMovementRequestsId()) &&
                condition.apply(criteria.getLocationEventsId(), copy.getLocationEventsId()) &&
                condition.apply(criteria.getSiteId(), copy.getSiteId()) &&
                condition.apply(criteria.getProductionLineId(), copy.getProductionLineId()) &&
                condition.apply(criteria.getCurrentZoneId(), copy.getCurrentZoneId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
