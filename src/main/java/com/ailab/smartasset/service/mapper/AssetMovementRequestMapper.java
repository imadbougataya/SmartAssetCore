package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.domain.User;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
import com.ailab.smartasset.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetMovementRequest} and its DTO {@link AssetMovementRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetMovementRequestMapper extends EntityMapper<AssetMovementRequestDTO, AssetMovementRequest> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetId")
    @Mapping(target = "requestedBy", source = "requestedBy", qualifiedByName = "userId")
    @Mapping(target = "approvedBy", source = "approvedBy", qualifiedByName = "userId")
    AssetMovementRequestDTO toDto(AssetMovementRequest s);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
