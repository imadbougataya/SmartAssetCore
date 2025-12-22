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
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    DocumentLinkDTO toDto(DocumentLink s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
