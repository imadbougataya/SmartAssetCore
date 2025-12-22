package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.SystemEvent;
import com.ailab.smartasset.service.dto.SystemEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemEvent} and its DTO {@link SystemEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface SystemEventMapper extends EntityMapper<SystemEventDTO, SystemEvent> {}
