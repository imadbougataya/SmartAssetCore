package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.MaintenanceEventAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ailab.smartasset.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.MaintenanceEvent;
import com.ailab.smartasset.domain.enumeration.MaintenanceStatus;
import com.ailab.smartasset.domain.enumeration.MaintenanceType;
import com.ailab.smartasset.repository.MaintenanceEventRepository;
import com.ailab.smartasset.service.MaintenanceEventService;
import com.ailab.smartasset.service.dto.MaintenanceEventDTO;
import com.ailab.smartasset.service.mapper.MaintenanceEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link MaintenanceEventResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MaintenanceEventResourceIT {

    private static final MaintenanceType DEFAULT_MAINTENANCE_TYPE = MaintenanceType.PREVENTIVE;
    private static final MaintenanceType UPDATED_MAINTENANCE_TYPE = MaintenanceType.CORRECTIVE;

    private static final MaintenanceStatus DEFAULT_STATUS = MaintenanceStatus.REQUESTED;
    private static final MaintenanceStatus UPDATED_STATUS = MaintenanceStatus.PLANNED;

    private static final Instant DEFAULT_REQUESTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUESTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PLANNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLANNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_STARTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICIAN = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICIAN = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOWNTIME_MINUTES = 1;
    private static final Integer UPDATED_DOWNTIME_MINUTES = 2;
    private static final Integer SMALLER_DOWNTIME_MINUTES = 1 - 1;

    private static final BigDecimal DEFAULT_COST_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_COST_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/maintenance-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaintenanceEventRepository maintenanceEventRepository;

    @Mock
    private MaintenanceEventRepository maintenanceEventRepositoryMock;

    @Autowired
    private MaintenanceEventMapper maintenanceEventMapper;

    @Mock
    private MaintenanceEventService maintenanceEventServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaintenanceEventMockMvc;

    private MaintenanceEvent maintenanceEvent;

    private MaintenanceEvent insertedMaintenanceEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaintenanceEvent createEntity() {
        return new MaintenanceEvent()
            .maintenanceType(DEFAULT_MAINTENANCE_TYPE)
            .status(DEFAULT_STATUS)
            .requestedAt(DEFAULT_REQUESTED_AT)
            .plannedAt(DEFAULT_PLANNED_AT)
            .startedAt(DEFAULT_STARTED_AT)
            .finishedAt(DEFAULT_FINISHED_AT)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .technician(DEFAULT_TECHNICIAN)
            .downtimeMinutes(DEFAULT_DOWNTIME_MINUTES)
            .costAmount(DEFAULT_COST_AMOUNT)
            .notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaintenanceEvent createUpdatedEntity() {
        return new MaintenanceEvent()
            .maintenanceType(UPDATED_MAINTENANCE_TYPE)
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .plannedAt(UPDATED_PLANNED_AT)
            .startedAt(UPDATED_STARTED_AT)
            .finishedAt(UPDATED_FINISHED_AT)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .technician(UPDATED_TECHNICIAN)
            .downtimeMinutes(UPDATED_DOWNTIME_MINUTES)
            .costAmount(UPDATED_COST_AMOUNT)
            .notes(UPDATED_NOTES);
    }

    @BeforeEach
    void initTest() {
        maintenanceEvent = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMaintenanceEvent != null) {
            maintenanceEventRepository.delete(insertedMaintenanceEvent);
            insertedMaintenanceEvent = null;
        }
    }

    @Test
    @Transactional
    void createMaintenanceEvent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);
        var returnedMaintenanceEventDTO = om.readValue(
            restMaintenanceEventMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MaintenanceEventDTO.class
        );

        // Validate the MaintenanceEvent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMaintenanceEvent = maintenanceEventMapper.toEntity(returnedMaintenanceEventDTO);
        assertMaintenanceEventUpdatableFieldsEquals(returnedMaintenanceEvent, getPersistedMaintenanceEvent(returnedMaintenanceEvent));

        insertedMaintenanceEvent = returnedMaintenanceEvent;
    }

    @Test
    @Transactional
    void createMaintenanceEventWithExistingId() throws Exception {
        // Create the MaintenanceEvent with an existing ID
        maintenanceEvent.setId(1L);
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaintenanceEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaintenanceTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        maintenanceEvent.setMaintenanceType(null);

        // Create the MaintenanceEvent, which fails.
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        restMaintenanceEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        maintenanceEvent.setStatus(null);

        // Create the MaintenanceEvent, which fails.
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        restMaintenanceEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        maintenanceEvent.setRequestedAt(null);

        // Create the MaintenanceEvent, which fails.
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        restMaintenanceEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaintenanceEvents() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenanceEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].maintenanceType").value(hasItem(DEFAULT_MAINTENANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestedAt").value(hasItem(DEFAULT_REQUESTED_AT.toString())))
            .andExpect(jsonPath("$.[*].plannedAt").value(hasItem(DEFAULT_PLANNED_AT.toString())))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN)))
            .andExpect(jsonPath("$.[*].downtimeMinutes").value(hasItem(DEFAULT_DOWNTIME_MINUTES)))
            .andExpect(jsonPath("$.[*].costAmount").value(hasItem(sameNumber(DEFAULT_COST_AMOUNT))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMaintenanceEventsWithEagerRelationshipsIsEnabled() throws Exception {
        when(maintenanceEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMaintenanceEventMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(maintenanceEventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMaintenanceEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(maintenanceEventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMaintenanceEventMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(maintenanceEventRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMaintenanceEvent() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get the maintenanceEvent
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL_ID, maintenanceEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maintenanceEvent.getId().intValue()))
            .andExpect(jsonPath("$.maintenanceType").value(DEFAULT_MAINTENANCE_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.requestedAt").value(DEFAULT_REQUESTED_AT.toString()))
            .andExpect(jsonPath("$.plannedAt").value(DEFAULT_PLANNED_AT.toString()))
            .andExpect(jsonPath("$.startedAt").value(DEFAULT_STARTED_AT.toString()))
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.technician").value(DEFAULT_TECHNICIAN))
            .andExpect(jsonPath("$.downtimeMinutes").value(DEFAULT_DOWNTIME_MINUTES))
            .andExpect(jsonPath("$.costAmount").value(sameNumber(DEFAULT_COST_AMOUNT)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getMaintenanceEventsByIdFiltering() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        Long id = maintenanceEvent.getId();

        defaultMaintenanceEventFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMaintenanceEventFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMaintenanceEventFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByMaintenanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where maintenanceType equals to
        defaultMaintenanceEventFiltering(
            "maintenanceType.equals=" + DEFAULT_MAINTENANCE_TYPE,
            "maintenanceType.equals=" + UPDATED_MAINTENANCE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByMaintenanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where maintenanceType in
        defaultMaintenanceEventFiltering(
            "maintenanceType.in=" + DEFAULT_MAINTENANCE_TYPE + "," + UPDATED_MAINTENANCE_TYPE,
            "maintenanceType.in=" + UPDATED_MAINTENANCE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByMaintenanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where maintenanceType is not null
        defaultMaintenanceEventFiltering("maintenanceType.specified=true", "maintenanceType.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where status equals to
        defaultMaintenanceEventFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where status in
        defaultMaintenanceEventFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where status is not null
        defaultMaintenanceEventFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByRequestedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where requestedAt equals to
        defaultMaintenanceEventFiltering("requestedAt.equals=" + DEFAULT_REQUESTED_AT, "requestedAt.equals=" + UPDATED_REQUESTED_AT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByRequestedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where requestedAt in
        defaultMaintenanceEventFiltering(
            "requestedAt.in=" + DEFAULT_REQUESTED_AT + "," + UPDATED_REQUESTED_AT,
            "requestedAt.in=" + UPDATED_REQUESTED_AT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByRequestedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where requestedAt is not null
        defaultMaintenanceEventFiltering("requestedAt.specified=true", "requestedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByPlannedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where plannedAt equals to
        defaultMaintenanceEventFiltering("plannedAt.equals=" + DEFAULT_PLANNED_AT, "plannedAt.equals=" + UPDATED_PLANNED_AT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByPlannedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where plannedAt in
        defaultMaintenanceEventFiltering(
            "plannedAt.in=" + DEFAULT_PLANNED_AT + "," + UPDATED_PLANNED_AT,
            "plannedAt.in=" + UPDATED_PLANNED_AT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByPlannedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where plannedAt is not null
        defaultMaintenanceEventFiltering("plannedAt.specified=true", "plannedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStartedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where startedAt equals to
        defaultMaintenanceEventFiltering("startedAt.equals=" + DEFAULT_STARTED_AT, "startedAt.equals=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStartedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where startedAt in
        defaultMaintenanceEventFiltering(
            "startedAt.in=" + DEFAULT_STARTED_AT + "," + UPDATED_STARTED_AT,
            "startedAt.in=" + UPDATED_STARTED_AT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByStartedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where startedAt is not null
        defaultMaintenanceEventFiltering("startedAt.specified=true", "startedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByFinishedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where finishedAt equals to
        defaultMaintenanceEventFiltering("finishedAt.equals=" + DEFAULT_FINISHED_AT, "finishedAt.equals=" + UPDATED_FINISHED_AT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByFinishedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where finishedAt in
        defaultMaintenanceEventFiltering(
            "finishedAt.in=" + DEFAULT_FINISHED_AT + "," + UPDATED_FINISHED_AT,
            "finishedAt.in=" + UPDATED_FINISHED_AT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByFinishedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where finishedAt is not null
        defaultMaintenanceEventFiltering("finishedAt.specified=true", "finishedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where title equals to
        defaultMaintenanceEventFiltering("title.equals=" + DEFAULT_TITLE, "title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where title in
        defaultMaintenanceEventFiltering("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE, "title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where title is not null
        defaultMaintenanceEventFiltering("title.specified=true", "title.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTitleContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where title contains
        defaultMaintenanceEventFiltering("title.contains=" + DEFAULT_TITLE, "title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where title does not contain
        defaultMaintenanceEventFiltering("title.doesNotContain=" + UPDATED_TITLE, "title.doesNotContain=" + DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where description equals to
        defaultMaintenanceEventFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where description in
        defaultMaintenanceEventFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where description is not null
        defaultMaintenanceEventFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where description contains
        defaultMaintenanceEventFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where description does not contain
        defaultMaintenanceEventFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTechnicianIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where technician equals to
        defaultMaintenanceEventFiltering("technician.equals=" + DEFAULT_TECHNICIAN, "technician.equals=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTechnicianIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where technician in
        defaultMaintenanceEventFiltering(
            "technician.in=" + DEFAULT_TECHNICIAN + "," + UPDATED_TECHNICIAN,
            "technician.in=" + UPDATED_TECHNICIAN
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTechnicianIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where technician is not null
        defaultMaintenanceEventFiltering("technician.specified=true", "technician.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTechnicianContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where technician contains
        defaultMaintenanceEventFiltering("technician.contains=" + DEFAULT_TECHNICIAN, "technician.contains=" + UPDATED_TECHNICIAN);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByTechnicianNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where technician does not contain
        defaultMaintenanceEventFiltering(
            "technician.doesNotContain=" + UPDATED_TECHNICIAN,
            "technician.doesNotContain=" + DEFAULT_TECHNICIAN
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes equals to
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.equals=" + DEFAULT_DOWNTIME_MINUTES,
            "downtimeMinutes.equals=" + UPDATED_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes in
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.in=" + DEFAULT_DOWNTIME_MINUTES + "," + UPDATED_DOWNTIME_MINUTES,
            "downtimeMinutes.in=" + UPDATED_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes is not null
        defaultMaintenanceEventFiltering("downtimeMinutes.specified=true", "downtimeMinutes.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes is greater than or equal to
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.greaterThanOrEqual=" + DEFAULT_DOWNTIME_MINUTES,
            "downtimeMinutes.greaterThanOrEqual=" + UPDATED_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes is less than or equal to
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.lessThanOrEqual=" + DEFAULT_DOWNTIME_MINUTES,
            "downtimeMinutes.lessThanOrEqual=" + SMALLER_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes is less than
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.lessThan=" + UPDATED_DOWNTIME_MINUTES,
            "downtimeMinutes.lessThan=" + DEFAULT_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByDowntimeMinutesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where downtimeMinutes is greater than
        defaultMaintenanceEventFiltering(
            "downtimeMinutes.greaterThan=" + SMALLER_DOWNTIME_MINUTES,
            "downtimeMinutes.greaterThan=" + DEFAULT_DOWNTIME_MINUTES
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount equals to
        defaultMaintenanceEventFiltering("costAmount.equals=" + DEFAULT_COST_AMOUNT, "costAmount.equals=" + UPDATED_COST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount in
        defaultMaintenanceEventFiltering(
            "costAmount.in=" + DEFAULT_COST_AMOUNT + "," + UPDATED_COST_AMOUNT,
            "costAmount.in=" + UPDATED_COST_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount is not null
        defaultMaintenanceEventFiltering("costAmount.specified=true", "costAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount is greater than or equal to
        defaultMaintenanceEventFiltering(
            "costAmount.greaterThanOrEqual=" + DEFAULT_COST_AMOUNT,
            "costAmount.greaterThanOrEqual=" + UPDATED_COST_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount is less than or equal to
        defaultMaintenanceEventFiltering(
            "costAmount.lessThanOrEqual=" + DEFAULT_COST_AMOUNT,
            "costAmount.lessThanOrEqual=" + SMALLER_COST_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount is less than
        defaultMaintenanceEventFiltering("costAmount.lessThan=" + UPDATED_COST_AMOUNT, "costAmount.lessThan=" + DEFAULT_COST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByCostAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where costAmount is greater than
        defaultMaintenanceEventFiltering("costAmount.greaterThan=" + SMALLER_COST_AMOUNT, "costAmount.greaterThan=" + DEFAULT_COST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where notes equals to
        defaultMaintenanceEventFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where notes in
        defaultMaintenanceEventFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where notes is not null
        defaultMaintenanceEventFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where notes contains
        defaultMaintenanceEventFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        // Get all the maintenanceEventList where notes does not contain
        defaultMaintenanceEventFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllMaintenanceEventsByAssetIsEqualToSomething() throws Exception {
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            maintenanceEventRepository.saveAndFlush(maintenanceEvent);
            asset = AssetResourceIT.createEntity(em);
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        em.persist(asset);
        em.flush();
        maintenanceEvent.setAsset(asset);
        maintenanceEventRepository.saveAndFlush(maintenanceEvent);
        Long assetId = asset.getId();
        // Get all the maintenanceEventList where asset equals to assetId
        defaultMaintenanceEventShouldBeFound("assetId.equals=" + assetId);

        // Get all the maintenanceEventList where asset equals to (assetId + 1)
        defaultMaintenanceEventShouldNotBeFound("assetId.equals=" + (assetId + 1));
    }

    private void defaultMaintenanceEventFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMaintenanceEventShouldBeFound(shouldBeFound);
        defaultMaintenanceEventShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaintenanceEventShouldBeFound(String filter) throws Exception {
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenanceEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].maintenanceType").value(hasItem(DEFAULT_MAINTENANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestedAt").value(hasItem(DEFAULT_REQUESTED_AT.toString())))
            .andExpect(jsonPath("$.[*].plannedAt").value(hasItem(DEFAULT_PLANNED_AT.toString())))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].technician").value(hasItem(DEFAULT_TECHNICIAN)))
            .andExpect(jsonPath("$.[*].downtimeMinutes").value(hasItem(DEFAULT_DOWNTIME_MINUTES)))
            .andExpect(jsonPath("$.[*].costAmount").value(hasItem(sameNumber(DEFAULT_COST_AMOUNT))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaintenanceEventShouldNotBeFound(String filter) throws Exception {
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaintenanceEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaintenanceEvent() throws Exception {
        // Get the maintenanceEvent
        restMaintenanceEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaintenanceEvent() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maintenanceEvent
        MaintenanceEvent updatedMaintenanceEvent = maintenanceEventRepository.findById(maintenanceEvent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaintenanceEvent are not directly saved in db
        em.detach(updatedMaintenanceEvent);
        updatedMaintenanceEvent
            .maintenanceType(UPDATED_MAINTENANCE_TYPE)
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .plannedAt(UPDATED_PLANNED_AT)
            .startedAt(UPDATED_STARTED_AT)
            .finishedAt(UPDATED_FINISHED_AT)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .technician(UPDATED_TECHNICIAN)
            .downtimeMinutes(UPDATED_DOWNTIME_MINUTES)
            .costAmount(UPDATED_COST_AMOUNT)
            .notes(UPDATED_NOTES);
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(updatedMaintenanceEvent);

        restMaintenanceEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maintenanceEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maintenanceEventDTO))
            )
            .andExpect(status().isOk());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaintenanceEventToMatchAllProperties(updatedMaintenanceEvent);
    }

    @Test
    @Transactional
    void putNonExistingMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maintenanceEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maintenanceEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(maintenanceEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaintenanceEventWithPatch() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maintenanceEvent using partial update
        MaintenanceEvent partialUpdatedMaintenanceEvent = new MaintenanceEvent();
        partialUpdatedMaintenanceEvent.setId(maintenanceEvent.getId());

        partialUpdatedMaintenanceEvent
            .maintenanceType(UPDATED_MAINTENANCE_TYPE)
            .requestedAt(UPDATED_REQUESTED_AT)
            .plannedAt(UPDATED_PLANNED_AT)
            .startedAt(UPDATED_STARTED_AT)
            .finishedAt(UPDATED_FINISHED_AT)
            .title(UPDATED_TITLE)
            .technician(UPDATED_TECHNICIAN)
            .downtimeMinutes(UPDATED_DOWNTIME_MINUTES)
            .costAmount(UPDATED_COST_AMOUNT);

        restMaintenanceEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaintenanceEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaintenanceEvent))
            )
            .andExpect(status().isOk());

        // Validate the MaintenanceEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaintenanceEventUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaintenanceEvent, maintenanceEvent),
            getPersistedMaintenanceEvent(maintenanceEvent)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaintenanceEventWithPatch() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the maintenanceEvent using partial update
        MaintenanceEvent partialUpdatedMaintenanceEvent = new MaintenanceEvent();
        partialUpdatedMaintenanceEvent.setId(maintenanceEvent.getId());

        partialUpdatedMaintenanceEvent
            .maintenanceType(UPDATED_MAINTENANCE_TYPE)
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .plannedAt(UPDATED_PLANNED_AT)
            .startedAt(UPDATED_STARTED_AT)
            .finishedAt(UPDATED_FINISHED_AT)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .technician(UPDATED_TECHNICIAN)
            .downtimeMinutes(UPDATED_DOWNTIME_MINUTES)
            .costAmount(UPDATED_COST_AMOUNT)
            .notes(UPDATED_NOTES);

        restMaintenanceEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaintenanceEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaintenanceEvent))
            )
            .andExpect(status().isOk());

        // Validate the MaintenanceEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaintenanceEventUpdatableFieldsEquals(
            partialUpdatedMaintenanceEvent,
            getPersistedMaintenanceEvent(partialUpdatedMaintenanceEvent)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maintenanceEventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maintenanceEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(maintenanceEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaintenanceEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        maintenanceEvent.setId(longCount.incrementAndGet());

        // Create the MaintenanceEvent
        MaintenanceEventDTO maintenanceEventDTO = maintenanceEventMapper.toDto(maintenanceEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaintenanceEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(maintenanceEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaintenanceEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaintenanceEvent() throws Exception {
        // Initialize the database
        insertedMaintenanceEvent = maintenanceEventRepository.saveAndFlush(maintenanceEvent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the maintenanceEvent
        restMaintenanceEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, maintenanceEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return maintenanceEventRepository.count();
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

    protected MaintenanceEvent getPersistedMaintenanceEvent(MaintenanceEvent maintenanceEvent) {
        return maintenanceEventRepository.findById(maintenanceEvent.getId()).orElseThrow();
    }

    protected void assertPersistedMaintenanceEventToMatchAllProperties(MaintenanceEvent expectedMaintenanceEvent) {
        assertMaintenanceEventAllPropertiesEquals(expectedMaintenanceEvent, getPersistedMaintenanceEvent(expectedMaintenanceEvent));
    }

    protected void assertPersistedMaintenanceEventToMatchUpdatableProperties(MaintenanceEvent expectedMaintenanceEvent) {
        assertMaintenanceEventAllUpdatablePropertiesEquals(
            expectedMaintenanceEvent,
            getPersistedMaintenanceEvent(expectedMaintenanceEvent)
        );
    }
}
