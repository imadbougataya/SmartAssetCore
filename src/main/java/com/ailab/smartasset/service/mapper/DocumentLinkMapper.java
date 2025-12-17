package com.ailab.smartasset.service.mapper;

import com.ailab.smartasset.domain.Document;
import com.ailab.smartasset.domain.DocumentLink;
import com.ailab.smartasset.service.dto.DocumentDTO;
import com.ailab.smartasset.service.dto.DocumentLinkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentLink} and its DTO {@link DocumentLinkDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentLinkMapper extends EntityMapper<DocumentLinkDTO, DocumentLink> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentFileName")
    DocumentLinkDTO toDto(DocumentLink s);

    @Named("documentFileName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    DocumentDTO toDtoDocumentFileName(Document document);
}
