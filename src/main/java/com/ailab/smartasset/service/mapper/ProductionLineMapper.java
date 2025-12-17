package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
import com.ailab.smartasset.service.dto.SiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionLine} and its DTO {@link ProductionLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductionLineMapper extends EntityMapper<ProductionLineDTO, ProductionLine> {
    @Mapping(target = "site", source = "site", qualifiedByName = "siteCode")
    ProductionLineDTO toDto(ProductionLine s);

    @Named("siteCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    SiteDTO toDtoSiteCode(Site site);
}
