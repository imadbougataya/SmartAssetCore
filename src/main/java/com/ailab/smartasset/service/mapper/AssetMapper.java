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
@Mapper(componentModel = "spring")
public interface AssetMapper extends EntityMapper<AssetDTO, Asset> {
    @Mapping(target = "productionLine", source = "productionLine", qualifiedByName = "productionLineCode")
    @Mapping(target = "allowedSite", source = "allowedSite", qualifiedByName = "siteCode")
    @Mapping(target = "allowedZone", source = "allowedZone", qualifiedByName = "zoneCode")
    AssetDTO toDto(Asset s);

    @Named("productionLineCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ProductionLineDTO toDtoProductionLineCode(ProductionLine productionLine);

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
