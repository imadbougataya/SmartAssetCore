package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.LocationEvent;
import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.LocationEventDTO;
import com.ailab.smartasset.service.dto.SensorDTO;
import com.ailab.smartasset.service.dto.SiteDTO;
import com.ailab.smartasset.service.dto.ZoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationEvent} and its DTO {@link LocationEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationEventMapper extends EntityMapper<LocationEventDTO, LocationEvent> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetId")
    @Mapping(target = "sensor", source = "sensor", qualifiedByName = "sensorId")
    @Mapping(target = "matchedSite", source = "matchedSite", qualifiedByName = "siteId")
    @Mapping(target = "matchedZone", source = "matchedZone", qualifiedByName = "zoneId")
    LocationEventDTO toDto(LocationEvent s);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);

    @Named("sensorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SensorDTO toDtoSensorId(Sensor sensor);

    @Named("siteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteDTO toDtoSiteId(Site site);

    @Named("zoneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZoneDTO toDtoZoneId(Zone zone);
}
