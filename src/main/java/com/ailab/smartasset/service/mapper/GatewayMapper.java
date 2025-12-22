package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Gateway;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.service.dto.GatewayDTO;
import com.ailab.smartasset.service.dto.SiteDTO;
import com.ailab.smartasset.service.dto.ZoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gateway} and its DTO {@link GatewayDTO}.
 */
@Mapper(componentModel = "spring")
public interface GatewayMapper extends EntityMapper<GatewayDTO, Gateway> {
    @Mapping(target = "site", source = "site", qualifiedByName = "siteId")
    @Mapping(target = "zone", source = "zone", qualifiedByName = "zoneId")
    GatewayDTO toDto(Gateway s);

    @Named("siteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SiteDTO toDtoSiteId(Site site);

    @Named("zoneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZoneDTO toDtoZoneId(Zone zone);
}
