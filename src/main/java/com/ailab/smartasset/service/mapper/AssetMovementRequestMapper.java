package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetMovementRequest} and its DTO {@link AssetMovementRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetMovementRequestMapper extends EntityMapper<AssetMovementRequestDTO, AssetMovementRequest> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetAssetCode")
    AssetMovementRequestDTO toDto(AssetMovementRequest s);

    @Named("assetAssetCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assetCode", source = "assetCode")
    AssetDTO toDtoAssetAssetCode(Asset asset);
}
