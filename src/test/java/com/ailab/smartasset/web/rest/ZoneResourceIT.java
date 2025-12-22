package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.ZoneAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.repository.ZoneRepository;
import com.ailab.smartasset.service.ZoneService;
import com.ailab.smartasset.service.dto.ZoneDTO;
import com.ailab.smartasset.service.mapper.ZoneMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link ZoneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ZoneResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_CENTER_LAT = 1D;
    private static final Double UPDATED_CENTER_LAT = 2D;
    private static final Double SMALLER_CENTER_LAT = 1D - 1D;

    private static final Double DEFAULT_CENTER_LON = 1D;
    private static final Double UPDATED_CENTER_LON = 2D;
    private static final Double SMALLER_CENTER_LON = 1D - 1D;

    private static final Integer DEFAULT_RADIUS_METERS = 1;
    private static final Integer UPDATED_RADIUS_METERS = 2;
    private static final Integer SMALLER_RADIUS_METERS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/zones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZoneRepository zoneRepository;

    @Mock
    private ZoneRepository zoneRepositoryMock;

    @Autowired
    private ZoneMapper zoneMapper;

    @Mock
    private ZoneService zoneServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZoneMockMvc;

    private Zone zone;

    private Zone insertedZone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createEntity() {
        return new Zone()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .centerLat(DEFAULT_CENTER_LAT)
            .centerLon(DEFAULT_CENTER_LON)
            .radiusMeters(DEFAULT_RADIUS_METERS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createUpdatedEntity() {
        return new Zone()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .centerLat(UPDATED_CENTER_LAT)
            .centerLon(UPDATED_CENTER_LON)
            .radiusMeters(UPDATED_RADIUS_METERS);
    }

    @BeforeEach
    void initTest() {
        zone = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedZone != null) {
            zoneRepository.delete(insertedZone);
            insertedZone = null;
        }
    }

    @Test
    @Transactional
    void createZone() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);
        var returnedZoneDTO = om.readValue(
            restZoneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ZoneDTO.class
        );

        // Validate the Zone in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedZone = zoneMapper.toEntity(returnedZoneDTO);
        assertZoneUpdatableFieldsEquals(returnedZone, getPersistedZone(returnedZone));

        insertedZone = returnedZone;
    }

    @Test
    @Transactional
    void createZoneWithExistingId() throws Exception {
        // Create the Zone with an existing ID
        zone.setId(1L);
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        zone.setCode(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        restZoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        zone.setName(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        restZoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllZones() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList
        restZoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].centerLat").value(hasItem(DEFAULT_CENTER_LAT)))
            .andExpect(jsonPath("$.[*].centerLon").value(hasItem(DEFAULT_CENTER_LON)))
            .andExpect(jsonPath("$.[*].radiusMeters").value(hasItem(DEFAULT_RADIUS_METERS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllZonesWithEagerRelationshipsIsEnabled() throws Exception {
        when(zoneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZoneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(zoneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllZonesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(zoneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restZoneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(zoneRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getZone() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get the zone
        restZoneMockMvc
            .perform(get(ENTITY_API_URL_ID, zone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.centerLat").value(DEFAULT_CENTER_LAT))
            .andExpect(jsonPath("$.centerLon").value(DEFAULT_CENTER_LON))
            .andExpect(jsonPath("$.radiusMeters").value(DEFAULT_RADIUS_METERS));
    }

    @Test
    @Transactional
    void getZonesByIdFiltering() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        Long id = zone.getId();

        defaultZoneFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultZoneFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultZoneFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllZonesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code equals to
        defaultZoneFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllZonesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code in
        defaultZoneFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllZonesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code is not null
        defaultZoneFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code contains
        defaultZoneFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllZonesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code does not contain
        defaultZoneFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllZonesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name equals to
        defaultZoneFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZonesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name in
        defaultZoneFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZonesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name is not null
        defaultZoneFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name contains
        defaultZoneFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllZonesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name does not contain
        defaultZoneFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllZonesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where description equals to
        defaultZoneFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllZonesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where description in
        defaultZoneFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllZonesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where description is not null
        defaultZoneFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where description contains
        defaultZoneFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllZonesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where description does not contain
        defaultZoneFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat equals to
        defaultZoneFiltering("centerLat.equals=" + DEFAULT_CENTER_LAT, "centerLat.equals=" + UPDATED_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat in
        defaultZoneFiltering("centerLat.in=" + DEFAULT_CENTER_LAT + "," + UPDATED_CENTER_LAT, "centerLat.in=" + UPDATED_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat is not null
        defaultZoneFiltering("centerLat.specified=true", "centerLat.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat is greater than or equal to
        defaultZoneFiltering("centerLat.greaterThanOrEqual=" + DEFAULT_CENTER_LAT, "centerLat.greaterThanOrEqual=" + UPDATED_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat is less than or equal to
        defaultZoneFiltering("centerLat.lessThanOrEqual=" + DEFAULT_CENTER_LAT, "centerLat.lessThanOrEqual=" + SMALLER_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat is less than
        defaultZoneFiltering("centerLat.lessThan=" + UPDATED_CENTER_LAT, "centerLat.lessThan=" + DEFAULT_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLat is greater than
        defaultZoneFiltering("centerLat.greaterThan=" + SMALLER_CENTER_LAT, "centerLat.greaterThan=" + DEFAULT_CENTER_LAT);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon equals to
        defaultZoneFiltering("centerLon.equals=" + DEFAULT_CENTER_LON, "centerLon.equals=" + UPDATED_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon in
        defaultZoneFiltering("centerLon.in=" + DEFAULT_CENTER_LON + "," + UPDATED_CENTER_LON, "centerLon.in=" + UPDATED_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon is not null
        defaultZoneFiltering("centerLon.specified=true", "centerLon.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon is greater than or equal to
        defaultZoneFiltering("centerLon.greaterThanOrEqual=" + DEFAULT_CENTER_LON, "centerLon.greaterThanOrEqual=" + UPDATED_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon is less than or equal to
        defaultZoneFiltering("centerLon.lessThanOrEqual=" + DEFAULT_CENTER_LON, "centerLon.lessThanOrEqual=" + SMALLER_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon is less than
        defaultZoneFiltering("centerLon.lessThan=" + UPDATED_CENTER_LON, "centerLon.lessThan=" + DEFAULT_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByCenterLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where centerLon is greater than
        defaultZoneFiltering("centerLon.greaterThan=" + SMALLER_CENTER_LON, "centerLon.greaterThan=" + DEFAULT_CENTER_LON);
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters equals to
        defaultZoneFiltering("radiusMeters.equals=" + DEFAULT_RADIUS_METERS, "radiusMeters.equals=" + UPDATED_RADIUS_METERS);
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsInShouldWork() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters in
        defaultZoneFiltering(
            "radiusMeters.in=" + DEFAULT_RADIUS_METERS + "," + UPDATED_RADIUS_METERS,
            "radiusMeters.in=" + UPDATED_RADIUS_METERS
        );
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters is not null
        defaultZoneFiltering("radiusMeters.specified=true", "radiusMeters.specified=false");
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters is greater than or equal to
        defaultZoneFiltering(
            "radiusMeters.greaterThanOrEqual=" + DEFAULT_RADIUS_METERS,
            "radiusMeters.greaterThanOrEqual=" + UPDATED_RADIUS_METERS
        );
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters is less than or equal to
        defaultZoneFiltering(
            "radiusMeters.lessThanOrEqual=" + DEFAULT_RADIUS_METERS,
            "radiusMeters.lessThanOrEqual=" + SMALLER_RADIUS_METERS
        );
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters is less than
        defaultZoneFiltering("radiusMeters.lessThan=" + UPDATED_RADIUS_METERS, "radiusMeters.lessThan=" + DEFAULT_RADIUS_METERS);
    }

    @Test
    @Transactional
    void getAllZonesByRadiusMetersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where radiusMeters is greater than
        defaultZoneFiltering("radiusMeters.greaterThan=" + SMALLER_RADIUS_METERS, "radiusMeters.greaterThan=" + DEFAULT_RADIUS_METERS);
    }

    @Test
    @Transactional
    void getAllZonesBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            zoneRepository.saveAndFlush(zone);
            site = SiteResourceIT.createEntity();
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        zone.setSite(site);
        zoneRepository.saveAndFlush(zone);
        Long siteId = site.getId();
        // Get all the zoneList where site equals to siteId
        defaultZoneShouldBeFound("siteId.equals=" + siteId);

        // Get all the zoneList where site equals to (siteId + 1)
        defaultZoneShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    private void defaultZoneFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultZoneShouldBeFound(shouldBeFound);
        defaultZoneShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZoneShouldBeFound(String filter) throws Exception {
        restZoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].centerLat").value(hasItem(DEFAULT_CENTER_LAT)))
            .andExpect(jsonPath("$.[*].centerLon").value(hasItem(DEFAULT_CENTER_LON)))
            .andExpect(jsonPath("$.[*].radiusMeters").value(hasItem(DEFAULT_RADIUS_METERS)));

        // Check, that the count call also returns 1
        restZoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZoneShouldNotBeFound(String filter) throws Exception {
        restZoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZoneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingZone() throws Exception {
        // Get the zone
        restZoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZone() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zone
        Zone updatedZone = zoneRepository.findById(zone.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZone are not directly saved in db
        em.detach(updatedZone);
        updatedZone
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .centerLat(UPDATED_CENTER_LAT)
            .centerLon(UPDATED_CENTER_LON)
            .radiusMeters(UPDATED_RADIUS_METERS);
        ZoneDTO zoneDTO = zoneMapper.toDto(updatedZone);

        restZoneMockMvc
            .perform(put(ENTITY_API_URL_ID, zoneDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isOk());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZoneToMatchAllProperties(updatedZone);
    }

    @Test
    @Transactional
    void putNonExistingZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(put(ENTITY_API_URL_ID, zoneDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZoneWithPatch() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zone using partial update
        Zone partialUpdatedZone = new Zone();
        partialUpdatedZone.setId(zone.getId());

        partialUpdatedZone.name(UPDATED_NAME).centerLat(UPDATED_CENTER_LAT);

        restZoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZone))
            )
            .andExpect(status().isOk());

        // Validate the Zone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZoneUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedZone, zone), getPersistedZone(zone));
    }

    @Test
    @Transactional
    void fullUpdateZoneWithPatch() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zone using partial update
        Zone partialUpdatedZone = new Zone();
        partialUpdatedZone.setId(zone.getId());

        partialUpdatedZone
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .centerLat(UPDATED_CENTER_LAT)
            .centerLon(UPDATED_CENTER_LON)
            .radiusMeters(UPDATED_RADIUS_METERS);

        restZoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZone))
            )
            .andExpect(status().isOk());

        // Validate the Zone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZoneUpdatableFieldsEquals(partialUpdatedZone, getPersistedZone(partialUpdatedZone));
    }

    @Test
    @Transactional
    void patchNonExistingZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zoneDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zone.setId(longCount.incrementAndGet());

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZoneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Zone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZone() throws Exception {
        // Initialize the database
        insertedZone = zoneRepository.saveAndFlush(zone);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zone
        restZoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, zone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zoneRepository.count();
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

    protected Zone getPersistedZone(Zone zone) {
        return zoneRepository.findById(zone.getId()).orElseThrow();
    }

    protected void assertPersistedZoneToMatchAllProperties(Zone expectedZone) {
        assertZoneAllPropertiesEquals(expectedZone, getPersistedZone(expectedZone));
    }

    protected void assertPersistedZoneToMatchUpdatableProperties(Zone expectedZone) {
        assertZoneAllUpdatablePropertiesEquals(expectedZone, getPersistedZone(expectedZone));
    }
}
