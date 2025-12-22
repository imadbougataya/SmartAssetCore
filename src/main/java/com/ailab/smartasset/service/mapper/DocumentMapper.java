package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.Document;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "asset", source = "asset", qualifiedByName = "assetAssetCode")
    DocumentDTO toDto(Document s);

    @Named("assetAssetCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assetCode", source = "assetCode")
    AssetDTO toDtoAssetAssetCode(Asset asset);
}
