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
    @Mapping(target = "zone", source = "zone", qualifiedByName = "zoneId")
    ProductionLineDTO toDto(ProductionLine s);

    @Named("zoneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZoneDTO toDtoZoneId(Zone zone);
}
