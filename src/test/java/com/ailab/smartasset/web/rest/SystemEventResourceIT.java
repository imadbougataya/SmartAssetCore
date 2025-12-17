package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.SystemEventAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.SystemEvent;
import com.ailab.smartasset.domain.enumeration.SystemEntityType;
import com.ailab.smartasset.domain.enumeration.SystemEventSeverity;
import com.ailab.smartasset.domain.enumeration.SystemEventSource;
import com.ailab.smartasset.repository.SystemEventRepository;
import com.ailab.smartasset.service.SystemEventService;
import com.ailab.smartasset.service.dto.SystemEventDTO;
import com.ailab.smartasset.service.mapper.SystemEventMapper;
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
 * Integration tests for the {@link SystemEventResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SystemEventResourceIT {

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final SystemEntityType DEFAULT_ENTITY_TYPE = SystemEntityType.ASSET;
    private static final SystemEntityType UPDATED_ENTITY_TYPE = SystemEntityType.LOCATION_EVENT;

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final SystemEventSeverity DEFAULT_SEVERITY = SystemEventSeverity.INFO;
    private static final SystemEventSeverity UPDATED_SEVERITY = SystemEventSeverity.WARNING;

    private static final SystemEventSource DEFAULT_SOURCE = SystemEventSource.UI;
    private static final SystemEventSource UPDATED_SOURCE = SystemEventSource.API;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/system-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SystemEventRepository systemEventRepository;

    @Mock
    private SystemEventRepository systemEventRepositoryMock;

    @Autowired
    private SystemEventMapper systemEventMapper;

    @Mock
    private SystemEventService systemEventServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemEventMockMvc;

    private SystemEvent systemEvent;

    private SystemEvent insertedSystemEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemEvent createEntity() {
        return new SystemEvent()
            .eventType(DEFAULT_EVENT_TYPE)
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .severity(DEFAULT_SEVERITY)
            .source(DEFAULT_SOURCE)
            .message(DEFAULT_MESSAGE)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .correlationId(DEFAULT_CORRELATION_ID)
            .payload(DEFAULT_PAYLOAD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemEvent createUpdatedEntity() {
        return new SystemEvent()
            .eventType(UPDATED_EVENT_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .severity(UPDATED_SEVERITY)
            .source(UPDATED_SOURCE)
            .message(UPDATED_MESSAGE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .correlationId(UPDATED_CORRELATION_ID)
            .payload(UPDATED_PAYLOAD);
    }

    @BeforeEach
    void initTest() {
        systemEvent = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSystemEvent != null) {
            systemEventRepository.delete(insertedSystemEvent);
            insertedSystemEvent = null;
        }
    }

    @Test
    @Transactional
    void createSystemEvent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);
        var returnedSystemEventDTO = om.readValue(
            restSystemEventMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SystemEventDTO.class
        );

        // Validate the SystemEvent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSystemEvent = systemEventMapper.toEntity(returnedSystemEventDTO);
        assertSystemEventUpdatableFieldsEquals(returnedSystemEvent, getPersistedSystemEvent(returnedSystemEvent));

        insertedSystemEvent = returnedSystemEvent;
    }

    @Test
    @Transactional
    void createSystemEventWithExistingId() throws Exception {
        // Create the SystemEvent with an existing ID
        systemEvent.setId(1L);
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEventTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        systemEvent.setEventType(null);

        // Create the SystemEvent, which fails.
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntityTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        systemEvent.setEntityType(null);

        // Create the SystemEvent, which fails.
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeverityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        systemEvent.setSeverity(null);

        // Create the SystemEvent, which fails.
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSourceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        systemEvent.setSource(null);

        // Create the SystemEvent, which fails.
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        systemEvent.setCreatedAt(null);

        // Create the SystemEvent, which fails.
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        restSystemEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSystemEvents() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSystemEventsWithEagerRelationshipsIsEnabled() throws Exception {
        when(systemEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSystemEventMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(systemEventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSystemEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(systemEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSystemEventMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(systemEventRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSystemEvent() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get the systemEvent
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL_ID, systemEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD));
    }

    @Test
    @Transactional
    void getSystemEventsByIdFiltering() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        Long id = systemEvent.getId();

        defaultSystemEventFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSystemEventFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSystemEventFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEventTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where eventType equals to
        defaultSystemEventFiltering("eventType.equals=" + DEFAULT_EVENT_TYPE, "eventType.equals=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEventTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where eventType in
        defaultSystemEventFiltering("eventType.in=" + DEFAULT_EVENT_TYPE + "," + UPDATED_EVENT_TYPE, "eventType.in=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEventTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where eventType is not null
        defaultSystemEventFiltering("eventType.specified=true", "eventType.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByEventTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where eventType contains
        defaultSystemEventFiltering("eventType.contains=" + DEFAULT_EVENT_TYPE, "eventType.contains=" + UPDATED_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEventTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where eventType does not contain
        defaultSystemEventFiltering("eventType.doesNotContain=" + UPDATED_EVENT_TYPE, "eventType.doesNotContain=" + DEFAULT_EVENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityType equals to
        defaultSystemEventFiltering("entityType.equals=" + DEFAULT_ENTITY_TYPE, "entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityType in
        defaultSystemEventFiltering(
            "entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE,
            "entityType.in=" + UPDATED_ENTITY_TYPE
        );
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityType is not null
        defaultSystemEventFiltering("entityType.specified=true", "entityType.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId equals to
        defaultSystemEventFiltering("entityId.equals=" + DEFAULT_ENTITY_ID, "entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId in
        defaultSystemEventFiltering("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID, "entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId is not null
        defaultSystemEventFiltering("entityId.specified=true", "entityId.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId is greater than or equal to
        defaultSystemEventFiltering("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID, "entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId is less than or equal to
        defaultSystemEventFiltering("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID, "entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId is less than
        defaultSystemEventFiltering("entityId.lessThan=" + UPDATED_ENTITY_ID, "entityId.lessThan=" + DEFAULT_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where entityId is greater than
        defaultSystemEventFiltering("entityId.greaterThan=" + SMALLER_ENTITY_ID, "entityId.greaterThan=" + DEFAULT_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsBySeverityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where severity equals to
        defaultSystemEventFiltering("severity.equals=" + DEFAULT_SEVERITY, "severity.equals=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllSystemEventsBySeverityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where severity in
        defaultSystemEventFiltering("severity.in=" + DEFAULT_SEVERITY + "," + UPDATED_SEVERITY, "severity.in=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllSystemEventsBySeverityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where severity is not null
        defaultSystemEventFiltering("severity.specified=true", "severity.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where source equals to
        defaultSystemEventFiltering("source.equals=" + DEFAULT_SOURCE, "source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllSystemEventsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where source in
        defaultSystemEventFiltering("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE, "source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllSystemEventsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where source is not null
        defaultSystemEventFiltering("source.specified=true", "source.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where message equals to
        defaultSystemEventFiltering("message.equals=" + DEFAULT_MESSAGE, "message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where message in
        defaultSystemEventFiltering("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE, "message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where message is not null
        defaultSystemEventFiltering("message.specified=true", "message.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByMessageContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where message contains
        defaultSystemEventFiltering("message.contains=" + DEFAULT_MESSAGE, "message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where message does not contain
        defaultSystemEventFiltering("message.doesNotContain=" + UPDATED_MESSAGE, "message.doesNotContain=" + DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdAt equals to
        defaultSystemEventFiltering("createdAt.equals=" + DEFAULT_CREATED_AT, "createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdAt in
        defaultSystemEventFiltering("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT, "createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdAt is not null
        defaultSystemEventFiltering("createdAt.specified=true", "createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdBy equals to
        defaultSystemEventFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdBy in
        defaultSystemEventFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdBy is not null
        defaultSystemEventFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdBy contains
        defaultSystemEventFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where createdBy does not contain
        defaultSystemEventFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCorrelationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where correlationId equals to
        defaultSystemEventFiltering("correlationId.equals=" + DEFAULT_CORRELATION_ID, "correlationId.equals=" + UPDATED_CORRELATION_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCorrelationIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where correlationId in
        defaultSystemEventFiltering(
            "correlationId.in=" + DEFAULT_CORRELATION_ID + "," + UPDATED_CORRELATION_ID,
            "correlationId.in=" + UPDATED_CORRELATION_ID
        );
    }

    @Test
    @Transactional
    void getAllSystemEventsByCorrelationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where correlationId is not null
        defaultSystemEventFiltering("correlationId.specified=true", "correlationId.specified=false");
    }

    @Test
    @Transactional
    void getAllSystemEventsByCorrelationIdContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where correlationId contains
        defaultSystemEventFiltering("correlationId.contains=" + DEFAULT_CORRELATION_ID, "correlationId.contains=" + UPDATED_CORRELATION_ID);
    }

    @Test
    @Transactional
    void getAllSystemEventsByCorrelationIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        // Get all the systemEventList where correlationId does not contain
        defaultSystemEventFiltering(
            "correlationId.doesNotContain=" + UPDATED_CORRELATION_ID,
            "correlationId.doesNotContain=" + DEFAULT_CORRELATION_ID
        );
    }

    @Test
    @Transactional
    void getAllSystemEventsByAssetIsEqualToSomething() throws Exception {
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            systemEventRepository.saveAndFlush(systemEvent);
            asset = AssetResourceIT.createEntity();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        em.persist(asset);
        em.flush();
        systemEvent.setAsset(asset);
        systemEventRepository.saveAndFlush(systemEvent);
        Long assetId = asset.getId();
        // Get all the systemEventList where asset equals to assetId
        defaultSystemEventShouldBeFound("assetId.equals=" + assetId);

        // Get all the systemEventList where asset equals to (assetId + 1)
        defaultSystemEventShouldNotBeFound("assetId.equals=" + (assetId + 1));
    }

    private void defaultSystemEventFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSystemEventShouldBeFound(shouldBeFound);
        defaultSystemEventShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemEventShouldBeFound(String filter) throws Exception {
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)));

        // Check, that the count call also returns 1
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemEventShouldNotBeFound(String filter) throws Exception {
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSystemEvent() throws Exception {
        // Get the systemEvent
        restSystemEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSystemEvent() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemEvent
        SystemEvent updatedSystemEvent = systemEventRepository.findById(systemEvent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSystemEvent are not directly saved in db
        em.detach(updatedSystemEvent);
        updatedSystemEvent
            .eventType(UPDATED_EVENT_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .severity(UPDATED_SEVERITY)
            .source(UPDATED_SOURCE)
            .message(UPDATED_MESSAGE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .correlationId(UPDATED_CORRELATION_ID)
            .payload(UPDATED_PAYLOAD);
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(updatedSystemEvent);

        restSystemEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(systemEventDTO))
            )
            .andExpect(status().isOk());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSystemEventToMatchAllProperties(updatedSystemEvent);
    }

    @Test
    @Transactional
    void putNonExistingSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(systemEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(systemEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSystemEventWithPatch() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemEvent using partial update
        SystemEvent partialUpdatedSystemEvent = new SystemEvent();
        partialUpdatedSystemEvent.setId(systemEvent.getId());

        partialUpdatedSystemEvent
            .entityType(UPDATED_ENTITY_TYPE)
            .message(UPDATED_MESSAGE)
            .createdBy(UPDATED_CREATED_BY)
            .payload(UPDATED_PAYLOAD);

        restSystemEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSystemEvent))
            )
            .andExpect(status().isOk());

        // Validate the SystemEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSystemEventUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSystemEvent, systemEvent),
            getPersistedSystemEvent(systemEvent)
        );
    }

    @Test
    @Transactional
    void fullUpdateSystemEventWithPatch() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemEvent using partial update
        SystemEvent partialUpdatedSystemEvent = new SystemEvent();
        partialUpdatedSystemEvent.setId(systemEvent.getId());

        partialUpdatedSystemEvent
            .eventType(UPDATED_EVENT_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .severity(UPDATED_SEVERITY)
            .source(UPDATED_SOURCE)
            .message(UPDATED_MESSAGE)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .correlationId(UPDATED_CORRELATION_ID)
            .payload(UPDATED_PAYLOAD);

        restSystemEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSystemEvent))
            )
            .andExpect(status().isOk());

        // Validate the SystemEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSystemEventUpdatableFieldsEquals(partialUpdatedSystemEvent, getPersistedSystemEvent(partialUpdatedSystemEvent));
    }

    @Test
    @Transactional
    void patchNonExistingSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, systemEventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(systemEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(systemEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSystemEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemEvent.setId(longCount.incrementAndGet());

        // Create the SystemEvent
        SystemEventDTO systemEventDTO = systemEventMapper.toDto(systemEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(systemEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSystemEvent() throws Exception {
        // Initialize the database
        insertedSystemEvent = systemEventRepository.saveAndFlush(systemEvent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the systemEvent
        restSystemEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, systemEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return systemEventRepository.count();
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

    protected SystemEvent getPersistedSystemEvent(SystemEvent systemEvent) {
        return systemEventRepository.findById(systemEvent.getId()).orElseThrow();
    }

    protected void assertPersistedSystemEventToMatchAllProperties(SystemEvent expectedSystemEvent) {
        assertSystemEventAllPropertiesEquals(expectedSystemEvent, getPersistedSystemEvent(expectedSystemEvent));
    }

    protected void assertPersistedSystemEventToMatchUpdatableProperties(SystemEvent expectedSystemEvent) {
        assertSystemEventAllUpdatablePropertiesEquals(expectedSystemEvent, getPersistedSystemEvent(expectedSystemEvent));
    }
}
