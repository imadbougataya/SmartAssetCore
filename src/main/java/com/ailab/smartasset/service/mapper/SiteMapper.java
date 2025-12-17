package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.service.dto.SiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Site} and its DTO {@link SiteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteMapper extends EntityMapper<SiteDTO, Site> {}
