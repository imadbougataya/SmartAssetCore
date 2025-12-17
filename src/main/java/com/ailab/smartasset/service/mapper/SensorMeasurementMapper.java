package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.domain.SensorMeasurement;
import com.ailab.smartasset.service.dto.SensorDTO;
import com.ailab.smartasset.service.dto.SensorMeasurementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SensorMeasurement} and its DTO {@link SensorMeasurementDTO}.
 */
@Mapper(componentModel = "spring")
public interface SensorMeasurementMapper extends EntityMapper<SensorMeasurementDTO, SensorMeasurement> {
    @Mapping(target = "sensor", source = "sensor", qualifiedByName = "sensorName")
    SensorMeasurementDTO toDto(SensorMeasurement s);

    @Named("sensorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SensorDTO toDtoSensorName(Sensor sensor);
}
