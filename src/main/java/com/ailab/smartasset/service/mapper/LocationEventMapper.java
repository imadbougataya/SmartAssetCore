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
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetAssetCode")
    @Mapping(target = "sensor", source = "sensor", qualifiedByName = "sensorExternalId")
    @Mapping(target = "matchedSite", source = "matchedSite", qualifiedByName = "siteCode")
    @Mapping(target = "matchedZone", source = "matchedZone", qualifiedByName = "zoneCode")
    LocationEventDTO toDto(LocationEvent s);

    @Named("assetAssetCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assetCode", source = "assetCode")
    AssetDTO toDtoAssetAssetCode(Asset asset);

    @Named("sensorExternalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "externalId", source = "externalId")
    SensorDTO toDtoSensorExternalId(Sensor sensor);

    @Named("siteCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    SiteDTO toDtoSiteCode(Site site);

    @Named("zoneCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ZoneDTO toDtoZoneCode(Zone zone);
}
