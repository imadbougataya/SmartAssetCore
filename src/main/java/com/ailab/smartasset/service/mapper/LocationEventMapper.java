package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.Gateway;
import com.ailab.smartasset.domain.LocationEvent;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.GatewayDTO;
import com.ailab.smartasset.service.dto.LocationEventDTO;
import com.ailab.smartasset.service.dto.ZoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationEvent} and its DTO {@link LocationEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationEventMapper extends EntityMapper<LocationEventDTO, LocationEvent> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetAssetCode")
    @Mapping(target = "zone", source = "zone", qualifiedByName = "zoneCode")
    @Mapping(target = "gateway", source = "gateway", qualifiedByName = "gatewayCode")
    LocationEventDTO toDto(LocationEvent s);

    @Named("assetAssetCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assetCode", source = "assetCode")
    AssetDTO toDtoAssetAssetCode(Asset asset);

    @Named("zoneCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ZoneDTO toDtoZoneCode(Zone zone);

    @Named("gatewayCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    GatewayDTO toDtoGatewayCode(Gateway gateway);
}
