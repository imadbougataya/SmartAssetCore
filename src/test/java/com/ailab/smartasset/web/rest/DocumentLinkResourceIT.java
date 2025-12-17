package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.DocumentLinkAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Document;
import com.ailab.smartasset.domain.DocumentLink;
import com.ailab.smartasset.domain.enumeration.DocumentEntityType;
import com.ailab.smartasset.repository.DocumentLinkRepository;
import com.ailab.smartasset.service.DocumentLinkService;
import com.ailab.smartasset.service.dto.DocumentLinkDTO;
import com.ailab.smartasset.service.mapper.DocumentLinkMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DocumentLinkResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocumentLinkResourceIT {

    private static final DocumentEntityType DEFAULT_ENTITY_TYPE = DocumentEntityType.ASSET;
    private static final DocumentEntityType UPDATED_ENTITY_TYPE = DocumentEntityType.MAINTENANCE_EVENT;

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_LINKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LINKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/document-links";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentLinkRepository documentLinkRepository;

    @Mock
    private DocumentLinkRepository documentLinkRepositoryMock;

    @Autowired
    private DocumentLinkMapper documentLinkMapper;

    @Mock
    private DocumentLinkService documentLinkServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentLinkMockMvc;

    private DocumentLink documentLink;

    private DocumentLink insertedDocumentLink;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentLink createEntity() {
        return new DocumentLink()
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .label(DEFAULT_LABEL)
            .linkedAt(DEFAULT_LINKED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentLink createUpdatedEntity() {
        return new DocumentLink()
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .label(UPDATED_LABEL)
            .linkedAt(UPDATED_LINKED_AT);
    }

    @BeforeEach
    void initTest() {
        documentLink = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentLink != null) {
            documentLinkRepository.delete(insertedDocumentLink);
            insertedDocumentLink = null;
        }
    }

    @Test
    @Transactional
    void createDocumentLink() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);
        var returnedDocumentLinkDTO = om.readValue(
            restDocumentLinkMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentLinkDTO.class
        );

        // Validate the DocumentLink in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentLink = documentLinkMapper.toEntity(returnedDocumentLinkDTO);
        assertDocumentLinkUpdatableFieldsEquals(returnedDocumentLink, getPersistedDocumentLink(returnedDocumentLink));

        insertedDocumentLink = returnedDocumentLink;
    }

    @Test
    @Transactional
    void createDocumentLinkWithExistingId() throws Exception {
        // Create the DocumentLink with an existing ID
        documentLink.setId(1L);
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentLink.setEntityType(null);

        // Create the DocumentLink, which fails.
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        restDocumentLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntityIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentLink.setEntityId(null);

        // Create the DocumentLink, which fails.
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        restDocumentLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentLink.setLinkedAt(null);

        // Create the DocumentLink, which fails.
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        restDocumentLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentLinks() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentLink.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].linkedAt").value(hasItem(DEFAULT_LINKED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentLinksWithEagerRelationshipsIsEnabled() throws Exception {
        when(documentLinkServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentLinkMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(documentLinkServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentLinksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(documentLinkServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentLinkMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(documentLinkRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocumentLink() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get the documentLink
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL_ID, documentLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentLink.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.linkedAt").value(DEFAULT_LINKED_AT.toString()));
    }

    @Test
    @Transactional
    void getDocumentLinksByIdFiltering() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        Long id = documentLink.getId();

        defaultDocumentLinkFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDocumentLinkFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDocumentLinkFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityType equals to
        defaultDocumentLinkFiltering("entityType.equals=" + DEFAULT_ENTITY_TYPE, "entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityType in
        defaultDocumentLinkFiltering(
            "entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE,
            "entityType.in=" + UPDATED_ENTITY_TYPE
        );
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityType is not null
        defaultDocumentLinkFiltering("entityType.specified=true", "entityType.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId equals to
        defaultDocumentLinkFiltering("entityId.equals=" + DEFAULT_ENTITY_ID, "entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId in
        defaultDocumentLinkFiltering("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID, "entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId is not null
        defaultDocumentLinkFiltering("entityId.specified=true", "entityId.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId is greater than or equal to
        defaultDocumentLinkFiltering(
            "entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID,
            "entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID
        );
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId is less than or equal to
        defaultDocumentLinkFiltering("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID, "entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId is less than
        defaultDocumentLinkFiltering("entityId.lessThan=" + UPDATED_ENTITY_ID, "entityId.lessThan=" + DEFAULT_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where entityId is greater than
        defaultDocumentLinkFiltering("entityId.greaterThan=" + SMALLER_ENTITY_ID, "entityId.greaterThan=" + DEFAULT_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where label equals to
        defaultDocumentLinkFiltering("label.equals=" + DEFAULT_LABEL, "label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where label in
        defaultDocumentLinkFiltering("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL, "label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where label is not null
        defaultDocumentLinkFiltering("label.specified=true", "label.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLabelContainsSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where label contains
        defaultDocumentLinkFiltering("label.contains=" + DEFAULT_LABEL, "label.contains=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLabelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where label does not contain
        defaultDocumentLinkFiltering("label.doesNotContain=" + UPDATED_LABEL, "label.doesNotContain=" + DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLinkedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where linkedAt equals to
        defaultDocumentLinkFiltering("linkedAt.equals=" + DEFAULT_LINKED_AT, "linkedAt.equals=" + UPDATED_LINKED_AT);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLinkedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where linkedAt in
        defaultDocumentLinkFiltering("linkedAt.in=" + DEFAULT_LINKED_AT + "," + UPDATED_LINKED_AT, "linkedAt.in=" + UPDATED_LINKED_AT);
    }

    @Test
    @Transactional
    void getAllDocumentLinksByLinkedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        // Get all the documentLinkList where linkedAt is not null
        defaultDocumentLinkFiltering("linkedAt.specified=true", "linkedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentLinksByDocumentIsEqualToSomething() throws Exception {
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            documentLinkRepository.saveAndFlush(documentLink);
            document = DocumentResourceIT.createEntity();
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        em.persist(document);
        em.flush();
        documentLink.setDocument(document);
        documentLinkRepository.saveAndFlush(documentLink);
        Long documentId = document.getId();
        // Get all the documentLinkList where document equals to documentId
        defaultDocumentLinkShouldBeFound("documentId.equals=" + documentId);

        // Get all the documentLinkList where document equals to (documentId + 1)
        defaultDocumentLinkShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    private void defaultDocumentLinkFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDocumentLinkShouldBeFound(shouldBeFound);
        defaultDocumentLinkShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentLinkShouldBeFound(String filter) throws Exception {
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentLink.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].linkedAt").value(hasItem(DEFAULT_LINKED_AT.toString())));

        // Check, that the count call also returns 1
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentLinkShouldNotBeFound(String filter) throws Exception {
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentLinkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocumentLink() throws Exception {
        // Get the documentLink
        restDocumentLinkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentLink() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentLink
        DocumentLink updatedDocumentLink = documentLinkRepository.findById(documentLink.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentLink are not directly saved in db
        em.detach(updatedDocumentLink);
        updatedDocumentLink.entityType(UPDATED_ENTITY_TYPE).entityId(UPDATED_ENTITY_ID).label(UPDATED_LABEL).linkedAt(UPDATED_LINKED_AT);
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(updatedDocumentLink);

        restDocumentLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentLinkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentLinkDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentLinkToMatchAllProperties(updatedDocumentLink);
    }

    @Test
    @Transactional
    void putNonExistingDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentLinkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentLinkWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentLink using partial update
        DocumentLink partialUpdatedDocumentLink = new DocumentLink();
        partialUpdatedDocumentLink.setId(documentLink.getId());

        partialUpdatedDocumentLink
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .label(UPDATED_LABEL)
            .linkedAt(UPDATED_LINKED_AT);

        restDocumentLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentLink))
            )
            .andExpect(status().isOk());

        // Validate the DocumentLink in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentLinkUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentLink, documentLink),
            getPersistedDocumentLink(documentLink)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentLinkWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentLink using partial update
        DocumentLink partialUpdatedDocumentLink = new DocumentLink();
        partialUpdatedDocumentLink.setId(documentLink.getId());

        partialUpdatedDocumentLink
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .label(UPDATED_LABEL)
            .linkedAt(UPDATED_LINKED_AT);

        restDocumentLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentLink))
            )
            .andExpect(status().isOk());

        // Validate the DocumentLink in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentLinkUpdatableFieldsEquals(partialUpdatedDocumentLink, getPersistedDocumentLink(partialUpdatedDocumentLink));
    }

    @Test
    @Transactional
    void patchNonExistingDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentLinkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentLinkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentLink() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentLink.setId(longCount.incrementAndGet());

        // Create the DocumentLink
        DocumentLinkDTO documentLinkDTO = documentLinkMapper.toDto(documentLink);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentLinkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentLinkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentLink in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentLink() throws Exception {
        // Initialize the database
        insertedDocumentLink = documentLinkRepository.saveAndFlush(documentLink);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentLink
        restDocumentLinkMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentLink.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentLinkRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected DocumentLink getPersistedDocumentLink(DocumentLink documentLink) {
        return documentLinkRepository.findById(documentLink.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentLinkToMatchAllProperties(DocumentLink expectedDocumentLink) {
        assertDocumentLinkAllPropertiesEquals(expectedDocumentLink, getPersistedDocumentLink(expectedDocumentLink));
    }

    protected void assertPersistedDocumentLinkToMatchUpdatableProperties(DocumentLink expectedDocumentLink) {
        assertDocumentLinkAllUpdatablePropertiesEquals(expectedDocumentLink, getPersistedDocumentLink(expectedDocumentLink));
    }
}
