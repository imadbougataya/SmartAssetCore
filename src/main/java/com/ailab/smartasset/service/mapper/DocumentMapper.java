package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Document;
import com.ailab.smartasset.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {}
