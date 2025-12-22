package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
import com.ailab.smartasset.service.dto.ZoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionLine} and its DTO {@link ProductionLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductionLineMapper extends EntityMapper<ProductionLineDTO, ProductionLine> {
    @Mapping(target = "zone", source = "zone", qualifiedByName = "zoneCode")
    ProductionLineDTO toDto(ProductionLine s);

    @Named("zoneCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ZoneDTO toDtoZoneCode(Zone zone);
}
