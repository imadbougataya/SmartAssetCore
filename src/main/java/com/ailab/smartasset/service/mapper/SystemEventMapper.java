package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.SystemEvent;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.SystemEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemEvent} and its DTO {@link SystemEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface SystemEventMapper extends EntityMapper<SystemEventDTO, SystemEvent> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetAssetCode")
    SystemEventDTO toDto(SystemEvent s);

    @Named("assetAssetCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assetCode", source = "assetCode")
    AssetDTO toDtoAssetAssetCode(Asset asset);
}
