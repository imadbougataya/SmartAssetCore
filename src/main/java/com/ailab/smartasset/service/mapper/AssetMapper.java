package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
import com.ailab.smartasset.service.dto.SiteDTO;
import com.ailab.smartasset.service.dto.ZoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asset} and its DTO {@link AssetDTO}.
 */
@Mapper(componentModel = "spring") //, uses = {ZoneMapper.class, SiteMapper.class, ProductionLineMapper.class})
public interface AssetMapper extends EntityMapper<AssetDTO, Asset> {
    @Mapping(target = "productionLine", source = "productionLine")
    @Mapping(target = "allowedSite", source = "allowedSite")
    @Mapping(target = "allowedZone", source = "allowedZone")
    AssetDTO toDto(Asset asset);

    @Mapping(target = "productionLine", source = "productionLine")
    @Mapping(target = "allowedZone", source = "allowedZone")
    @Mapping(target = "allowedSite", source = "allowedSite")
    Asset toEntity(AssetDTO assetDTO);
    //    @Named("productionLineId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    ProductionLineDTO toDtoProductionLineId(ProductionLine productionLine);
    //
    //    @Named("siteId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    SiteDTO toDtoSiteId(Site site);
    //
    //    @Named("zoneId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    ZoneDTO toDtoZoneId(Zone zone);
}
