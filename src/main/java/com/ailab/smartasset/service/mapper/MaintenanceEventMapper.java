package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.MaintenanceEvent;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.MaintenanceEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MaintenanceEvent} and its DTO {@link MaintenanceEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface MaintenanceEventMapper extends EntityMapper<MaintenanceEventDTO, MaintenanceEvent> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetId")
    MaintenanceEventDTO toDto(MaintenanceEvent s);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);
}
