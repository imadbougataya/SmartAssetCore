package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.LocationEventAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.LocationEvent;
import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.domain.enumeration.LocationSource;
import com.ailab.smartasset.repository.LocationEventRepository;
import com.ailab.smartasset.service.dto.LocationEventDTO;
import com.ailab.smartasset.service.mapper.LocationEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocationEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationEventResourceIT {

    private static final LocationSource DEFAULT_SOURCE = LocationSource.BLE;
    private static final LocationSource UPDATED_SOURCE = LocationSource.GNSS;

    private static final Instant DEFAULT_OBSERVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OBSERVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ZONE_CONFIDENCE = 1;
    private static final Integer UPDATED_ZONE_CONFIDENCE = 2;
    private static final Integer SMALLER_ZONE_CONFIDENCE = 1 - 1;

    private static final Integer DEFAULT_RSSI = 1;
    private static final Integer UPDATED_RSSI = 2;
    private static final Integer SMALLER_RSSI = 1 - 1;

    private static final Integer DEFAULT_TX_POWER = 1;
    private static final Integer UPDATED_TX_POWER = 2;
    private static final Integer SMALLER_TX_POWER = 1 - 1;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Double DEFAULT_ACCURACY_METERS = 1D;
    private static final Double UPDATED_ACCURACY_METERS = 2D;
    private static final Double SMALLER_ACCURACY_METERS = 1D - 1D;

    private static final Double DEFAULT_SPEED_KMH = 1D;
    private static final Double UPDATED_SPEED_KMH = 2D;
    private static final Double SMALLER_SPEED_KMH = 1D - 1D;

    private static final String DEFAULT_GNSS_CONSTELLATION = "AAAAAAAAAA";
    private static final String UPDATED_GNSS_CONSTELLATION = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_RAW_PAYLOAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/location-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationEventRepository locationEventRepository;

    @Autowired
    private LocationEventMapper locationEventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationEventMockMvc;

    private LocationEvent locationEvent;

    private LocationEvent insertedLocationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationEvent createEntity(EntityManager em) {
        LocationEvent locationEvent = new LocationEvent()
            .source(DEFAULT_SOURCE)
            .observedAt(DEFAULT_OBSERVED_AT)
            .zoneConfidence(DEFAULT_ZONE_CONFIDENCE)
            .rssi(DEFAULT_RSSI)
            .txPower(DEFAULT_TX_POWER)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .accuracyMeters(DEFAULT_ACCURACY_METERS)
            .speedKmh(DEFAULT_SPEED_KMH)
            .gnssConstellation(DEFAULT_GNSS_CONSTELLATION)
            .rawPayload(DEFAULT_RAW_PAYLOAD);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        locationEvent.setAsset(asset);
        return locationEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationEvent createUpdatedEntity(EntityManager em) {
        LocationEvent updatedLocationEvent = new LocationEvent()
            .source(UPDATED_SOURCE)
            .observedAt(UPDATED_OBSERVED_AT)
            .zoneConfidence(UPDATED_ZONE_CONFIDENCE)
            .rssi(UPDATED_RSSI)
            .txPower(UPDATED_TX_POWER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .accuracyMeters(UPDATED_ACCURACY_METERS)
            .speedKmh(UPDATED_SPEED_KMH)
            .gnssConstellation(UPDATED_GNSS_CONSTELLATION)
            .rawPayload(UPDATED_RAW_PAYLOAD);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createUpdatedEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        updatedLocationEvent.setAsset(asset);
        return updatedLocationEvent;
    }

    @BeforeEach
    void initTest() {
        locationEvent = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedLocationEvent != null) {
            locationEventRepository.delete(insertedLocationEvent);
            insertedLocationEvent = null;
        }
    }

    @Test
    @Transactional
    void createLocationEvent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);
        var returnedLocationEventDTO = om.readValue(
            restLocationEventMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationEventDTO.class
        );

        // Validate the LocationEvent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocationEvent = locationEventMapper.toEntity(returnedLocationEventDTO);
        assertLocationEventUpdatableFieldsEquals(returnedLocationEvent, getPersistedLocationEvent(returnedLocationEvent));

        insertedLocationEvent = returnedLocationEvent;
    }

    @Test
    @Transactional
    void createLocationEventWithExistingId() throws Exception {
        // Create the LocationEvent with an existing ID
        locationEvent.setId(1L);
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSourceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationEvent.setSource(null);

        // Create the LocationEvent, which fails.
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        restLocationEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObservedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationEvent.setObservedAt(null);

        // Create the LocationEvent, which fails.
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        restLocationEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationEvent.setLatitude(null);

        // Create the LocationEvent, which fails.
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        restLocationEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationEvent.setLongitude(null);

        // Create the LocationEvent, which fails.
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        restLocationEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocationEvents() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].observedAt").value(hasItem(DEFAULT_OBSERVED_AT.toString())))
            .andExpect(jsonPath("$.[*].zoneConfidence").value(hasItem(DEFAULT_ZONE_CONFIDENCE)))
            .andExpect(jsonPath("$.[*].rssi").value(hasItem(DEFAULT_RSSI)))
            .andExpect(jsonPath("$.[*].txPower").value(hasItem(DEFAULT_TX_POWER)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].accuracyMeters").value(hasItem(DEFAULT_ACCURACY_METERS)))
            .andExpect(jsonPath("$.[*].speedKmh").value(hasItem(DEFAULT_SPEED_KMH)))
            .andExpect(jsonPath("$.[*].gnssConstellation").value(hasItem(DEFAULT_GNSS_CONSTELLATION)))
            .andExpect(jsonPath("$.[*].rawPayload").value(hasItem(DEFAULT_RAW_PAYLOAD)));
    }

    @Test
    @Transactional
    void getLocationEvent() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get the locationEvent
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL_ID, locationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationEvent.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.observedAt").value(DEFAULT_OBSERVED_AT.toString()))
            .andExpect(jsonPath("$.zoneConfidence").value(DEFAULT_ZONE_CONFIDENCE))
            .andExpect(jsonPath("$.rssi").value(DEFAULT_RSSI))
            .andExpect(jsonPath("$.txPower").value(DEFAULT_TX_POWER))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.accuracyMeters").value(DEFAULT_ACCURACY_METERS))
            .andExpect(jsonPath("$.speedKmh").value(DEFAULT_SPEED_KMH))
            .andExpect(jsonPath("$.gnssConstellation").value(DEFAULT_GNSS_CONSTELLATION))
            .andExpect(jsonPath("$.rawPayload").value(DEFAULT_RAW_PAYLOAD));
    }

    @Test
    @Transactional
    void getLocationEventsByIdFiltering() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        Long id = locationEvent.getId();

        defaultLocationEventFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultLocationEventFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultLocationEventFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where source equals to
        defaultLocationEventFiltering("source.equals=" + DEFAULT_SOURCE, "source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where source in
        defaultLocationEventFiltering("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE, "source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where source is not null
        defaultLocationEventFiltering("source.specified=true", "source.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByObservedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where observedAt equals to
        defaultLocationEventFiltering("observedAt.equals=" + DEFAULT_OBSERVED_AT, "observedAt.equals=" + UPDATED_OBSERVED_AT);
    }

    @Test
    @Transactional
    void getAllLocationEventsByObservedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where observedAt in
        defaultLocationEventFiltering(
            "observedAt.in=" + DEFAULT_OBSERVED_AT + "," + UPDATED_OBSERVED_AT,
            "observedAt.in=" + UPDATED_OBSERVED_AT
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByObservedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where observedAt is not null
        defaultLocationEventFiltering("observedAt.specified=true", "observedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence equals to
        defaultLocationEventFiltering(
            "zoneConfidence.equals=" + DEFAULT_ZONE_CONFIDENCE,
            "zoneConfidence.equals=" + UPDATED_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence in
        defaultLocationEventFiltering(
            "zoneConfidence.in=" + DEFAULT_ZONE_CONFIDENCE + "," + UPDATED_ZONE_CONFIDENCE,
            "zoneConfidence.in=" + UPDATED_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence is not null
        defaultLocationEventFiltering("zoneConfidence.specified=true", "zoneConfidence.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence is greater than or equal to
        defaultLocationEventFiltering(
            "zoneConfidence.greaterThanOrEqual=" + DEFAULT_ZONE_CONFIDENCE,
            "zoneConfidence.greaterThanOrEqual=" + UPDATED_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence is less than or equal to
        defaultLocationEventFiltering(
            "zoneConfidence.lessThanOrEqual=" + DEFAULT_ZONE_CONFIDENCE,
            "zoneConfidence.lessThanOrEqual=" + SMALLER_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence is less than
        defaultLocationEventFiltering(
            "zoneConfidence.lessThan=" + UPDATED_ZONE_CONFIDENCE,
            "zoneConfidence.lessThan=" + DEFAULT_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByZoneConfidenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where zoneConfidence is greater than
        defaultLocationEventFiltering(
            "zoneConfidence.greaterThan=" + SMALLER_ZONE_CONFIDENCE,
            "zoneConfidence.greaterThan=" + DEFAULT_ZONE_CONFIDENCE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi equals to
        defaultLocationEventFiltering("rssi.equals=" + DEFAULT_RSSI, "rssi.equals=" + UPDATED_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi in
        defaultLocationEventFiltering("rssi.in=" + DEFAULT_RSSI + "," + UPDATED_RSSI, "rssi.in=" + UPDATED_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi is not null
        defaultLocationEventFiltering("rssi.specified=true", "rssi.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi is greater than or equal to
        defaultLocationEventFiltering("rssi.greaterThanOrEqual=" + DEFAULT_RSSI, "rssi.greaterThanOrEqual=" + UPDATED_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi is less than or equal to
        defaultLocationEventFiltering("rssi.lessThanOrEqual=" + DEFAULT_RSSI, "rssi.lessThanOrEqual=" + SMALLER_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi is less than
        defaultLocationEventFiltering("rssi.lessThan=" + UPDATED_RSSI, "rssi.lessThan=" + DEFAULT_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRssiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rssi is greater than
        defaultLocationEventFiltering("rssi.greaterThan=" + SMALLER_RSSI, "rssi.greaterThan=" + DEFAULT_RSSI);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower equals to
        defaultLocationEventFiltering("txPower.equals=" + DEFAULT_TX_POWER, "txPower.equals=" + UPDATED_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower in
        defaultLocationEventFiltering("txPower.in=" + DEFAULT_TX_POWER + "," + UPDATED_TX_POWER, "txPower.in=" + UPDATED_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower is not null
        defaultLocationEventFiltering("txPower.specified=true", "txPower.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower is greater than or equal to
        defaultLocationEventFiltering("txPower.greaterThanOrEqual=" + DEFAULT_TX_POWER, "txPower.greaterThanOrEqual=" + UPDATED_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower is less than or equal to
        defaultLocationEventFiltering("txPower.lessThanOrEqual=" + DEFAULT_TX_POWER, "txPower.lessThanOrEqual=" + SMALLER_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower is less than
        defaultLocationEventFiltering("txPower.lessThan=" + UPDATED_TX_POWER, "txPower.lessThan=" + DEFAULT_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByTxPowerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where txPower is greater than
        defaultLocationEventFiltering("txPower.greaterThan=" + SMALLER_TX_POWER, "txPower.greaterThan=" + DEFAULT_TX_POWER);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude equals to
        defaultLocationEventFiltering("latitude.equals=" + DEFAULT_LATITUDE, "latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude in
        defaultLocationEventFiltering("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE, "latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude is not null
        defaultLocationEventFiltering("latitude.specified=true", "latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude is greater than or equal to
        defaultLocationEventFiltering("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE, "latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude is less than or equal to
        defaultLocationEventFiltering("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE, "latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude is less than
        defaultLocationEventFiltering("latitude.lessThan=" + UPDATED_LATITUDE, "latitude.lessThan=" + DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where latitude is greater than
        defaultLocationEventFiltering("latitude.greaterThan=" + SMALLER_LATITUDE, "latitude.greaterThan=" + DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude equals to
        defaultLocationEventFiltering("longitude.equals=" + DEFAULT_LONGITUDE, "longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude in
        defaultLocationEventFiltering("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE, "longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude is not null
        defaultLocationEventFiltering("longitude.specified=true", "longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude is greater than or equal to
        defaultLocationEventFiltering(
            "longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE,
            "longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude is less than or equal to
        defaultLocationEventFiltering("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE, "longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude is less than
        defaultLocationEventFiltering("longitude.lessThan=" + UPDATED_LONGITUDE, "longitude.lessThan=" + DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where longitude is greater than
        defaultLocationEventFiltering("longitude.greaterThan=" + SMALLER_LONGITUDE, "longitude.greaterThan=" + DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters equals to
        defaultLocationEventFiltering(
            "accuracyMeters.equals=" + DEFAULT_ACCURACY_METERS,
            "accuracyMeters.equals=" + UPDATED_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters in
        defaultLocationEventFiltering(
            "accuracyMeters.in=" + DEFAULT_ACCURACY_METERS + "," + UPDATED_ACCURACY_METERS,
            "accuracyMeters.in=" + UPDATED_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters is not null
        defaultLocationEventFiltering("accuracyMeters.specified=true", "accuracyMeters.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters is greater than or equal to
        defaultLocationEventFiltering(
            "accuracyMeters.greaterThanOrEqual=" + DEFAULT_ACCURACY_METERS,
            "accuracyMeters.greaterThanOrEqual=" + UPDATED_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters is less than or equal to
        defaultLocationEventFiltering(
            "accuracyMeters.lessThanOrEqual=" + DEFAULT_ACCURACY_METERS,
            "accuracyMeters.lessThanOrEqual=" + SMALLER_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters is less than
        defaultLocationEventFiltering(
            "accuracyMeters.lessThan=" + UPDATED_ACCURACY_METERS,
            "accuracyMeters.lessThan=" + DEFAULT_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAccuracyMetersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where accuracyMeters is greater than
        defaultLocationEventFiltering(
            "accuracyMeters.greaterThan=" + SMALLER_ACCURACY_METERS,
            "accuracyMeters.greaterThan=" + DEFAULT_ACCURACY_METERS
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh equals to
        defaultLocationEventFiltering("speedKmh.equals=" + DEFAULT_SPEED_KMH, "speedKmh.equals=" + UPDATED_SPEED_KMH);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh in
        defaultLocationEventFiltering("speedKmh.in=" + DEFAULT_SPEED_KMH + "," + UPDATED_SPEED_KMH, "speedKmh.in=" + UPDATED_SPEED_KMH);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh is not null
        defaultLocationEventFiltering("speedKmh.specified=true", "speedKmh.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh is greater than or equal to
        defaultLocationEventFiltering(
            "speedKmh.greaterThanOrEqual=" + DEFAULT_SPEED_KMH,
            "speedKmh.greaterThanOrEqual=" + UPDATED_SPEED_KMH
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh is less than or equal to
        defaultLocationEventFiltering("speedKmh.lessThanOrEqual=" + DEFAULT_SPEED_KMH, "speedKmh.lessThanOrEqual=" + SMALLER_SPEED_KMH);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh is less than
        defaultLocationEventFiltering("speedKmh.lessThan=" + UPDATED_SPEED_KMH, "speedKmh.lessThan=" + DEFAULT_SPEED_KMH);
    }

    @Test
    @Transactional
    void getAllLocationEventsBySpeedKmhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where speedKmh is greater than
        defaultLocationEventFiltering("speedKmh.greaterThan=" + SMALLER_SPEED_KMH, "speedKmh.greaterThan=" + DEFAULT_SPEED_KMH);
    }

    @Test
    @Transactional
    void getAllLocationEventsByGnssConstellationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where gnssConstellation equals to
        defaultLocationEventFiltering(
            "gnssConstellation.equals=" + DEFAULT_GNSS_CONSTELLATION,
            "gnssConstellation.equals=" + UPDATED_GNSS_CONSTELLATION
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByGnssConstellationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where gnssConstellation in
        defaultLocationEventFiltering(
            "gnssConstellation.in=" + DEFAULT_GNSS_CONSTELLATION + "," + UPDATED_GNSS_CONSTELLATION,
            "gnssConstellation.in=" + UPDATED_GNSS_CONSTELLATION
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByGnssConstellationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where gnssConstellation is not null
        defaultLocationEventFiltering("gnssConstellation.specified=true", "gnssConstellation.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByGnssConstellationContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where gnssConstellation contains
        defaultLocationEventFiltering(
            "gnssConstellation.contains=" + DEFAULT_GNSS_CONSTELLATION,
            "gnssConstellation.contains=" + UPDATED_GNSS_CONSTELLATION
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByGnssConstellationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where gnssConstellation does not contain
        defaultLocationEventFiltering(
            "gnssConstellation.doesNotContain=" + UPDATED_GNSS_CONSTELLATION,
            "gnssConstellation.doesNotContain=" + DEFAULT_GNSS_CONSTELLATION
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByRawPayloadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rawPayload equals to
        defaultLocationEventFiltering("rawPayload.equals=" + DEFAULT_RAW_PAYLOAD, "rawPayload.equals=" + UPDATED_RAW_PAYLOAD);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRawPayloadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rawPayload in
        defaultLocationEventFiltering(
            "rawPayload.in=" + DEFAULT_RAW_PAYLOAD + "," + UPDATED_RAW_PAYLOAD,
            "rawPayload.in=" + UPDATED_RAW_PAYLOAD
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByRawPayloadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rawPayload is not null
        defaultLocationEventFiltering("rawPayload.specified=true", "rawPayload.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationEventsByRawPayloadContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rawPayload contains
        defaultLocationEventFiltering("rawPayload.contains=" + DEFAULT_RAW_PAYLOAD, "rawPayload.contains=" + UPDATED_RAW_PAYLOAD);
    }

    @Test
    @Transactional
    void getAllLocationEventsByRawPayloadNotContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        // Get all the locationEventList where rawPayload does not contain
        defaultLocationEventFiltering(
            "rawPayload.doesNotContain=" + UPDATED_RAW_PAYLOAD,
            "rawPayload.doesNotContain=" + DEFAULT_RAW_PAYLOAD
        );
    }

    @Test
    @Transactional
    void getAllLocationEventsByAssetIsEqualToSomething() throws Exception {
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            locationEventRepository.saveAndFlush(locationEvent);
            asset = AssetResourceIT.createEntity(em);
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        em.persist(asset);
        em.flush();
        locationEvent.setAsset(asset);
        locationEventRepository.saveAndFlush(locationEvent);
        Long assetId = asset.getId();
        // Get all the locationEventList where asset equals to assetId
        defaultLocationEventShouldBeFound("assetId.equals=" + assetId);

        // Get all the locationEventList where asset equals to (assetId + 1)
        defaultLocationEventShouldNotBeFound("assetId.equals=" + (assetId + 1));
    }

    @Test
    @Transactional
    void getAllLocationEventsBySensorIsEqualToSomething() throws Exception {
        Sensor sensor;
        if (TestUtil.findAll(em, Sensor.class).isEmpty()) {
            locationEventRepository.saveAndFlush(locationEvent);
            sensor = SensorResourceIT.createEntity(em);
        } else {
            sensor = TestUtil.findAll(em, Sensor.class).get(0);
        }
        em.persist(sensor);
        em.flush();
        locationEvent.setSensor(sensor);
        locationEventRepository.saveAndFlush(locationEvent);
        Long sensorId = sensor.getId();
        // Get all the locationEventList where sensor equals to sensorId
        defaultLocationEventShouldBeFound("sensorId.equals=" + sensorId);

        // Get all the locationEventList where sensor equals to (sensorId + 1)
        defaultLocationEventShouldNotBeFound("sensorId.equals=" + (sensorId + 1));
    }

    @Test
    @Transactional
    void getAllLocationEventsByMatchedSiteIsEqualToSomething() throws Exception {
        Site matchedSite;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            locationEventRepository.saveAndFlush(locationEvent);
            matchedSite = SiteResourceIT.createEntity();
        } else {
            matchedSite = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(matchedSite);
        em.flush();
        locationEvent.setMatchedSite(matchedSite);
        locationEventRepository.saveAndFlush(locationEvent);
        Long matchedSiteId = matchedSite.getId();
        // Get all the locationEventList where matchedSite equals to matchedSiteId
        defaultLocationEventShouldBeFound("matchedSiteId.equals=" + matchedSiteId);

        // Get all the locationEventList where matchedSite equals to (matchedSiteId + 1)
        defaultLocationEventShouldNotBeFound("matchedSiteId.equals=" + (matchedSiteId + 1));
    }

    @Test
    @Transactional
    void getAllLocationEventsByMatchedZoneIsEqualToSomething() throws Exception {
        Zone matchedZone;
        if (TestUtil.findAll(em, Zone.class).isEmpty()) {
            locationEventRepository.saveAndFlush(locationEvent);
            matchedZone = ZoneResourceIT.createEntity(em);
        } else {
            matchedZone = TestUtil.findAll(em, Zone.class).get(0);
        }
        em.persist(matchedZone);
        em.flush();
        locationEvent.setMatchedZone(matchedZone);
        locationEventRepository.saveAndFlush(locationEvent);
        Long matchedZoneId = matchedZone.getId();
        // Get all the locationEventList where matchedZone equals to matchedZoneId
        defaultLocationEventShouldBeFound("matchedZoneId.equals=" + matchedZoneId);

        // Get all the locationEventList where matchedZone equals to (matchedZoneId + 1)
        defaultLocationEventShouldNotBeFound("matchedZoneId.equals=" + (matchedZoneId + 1));
    }

    private void defaultLocationEventFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultLocationEventShouldBeFound(shouldBeFound);
        defaultLocationEventShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationEventShouldBeFound(String filter) throws Exception {
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].observedAt").value(hasItem(DEFAULT_OBSERVED_AT.toString())))
            .andExpect(jsonPath("$.[*].zoneConfidence").value(hasItem(DEFAULT_ZONE_CONFIDENCE)))
            .andExpect(jsonPath("$.[*].rssi").value(hasItem(DEFAULT_RSSI)))
            .andExpect(jsonPath("$.[*].txPower").value(hasItem(DEFAULT_TX_POWER)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].accuracyMeters").value(hasItem(DEFAULT_ACCURACY_METERS)))
            .andExpect(jsonPath("$.[*].speedKmh").value(hasItem(DEFAULT_SPEED_KMH)))
            .andExpect(jsonPath("$.[*].gnssConstellation").value(hasItem(DEFAULT_GNSS_CONSTELLATION)))
            .andExpect(jsonPath("$.[*].rawPayload").value(hasItem(DEFAULT_RAW_PAYLOAD)));

        // Check, that the count call also returns 1
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationEventShouldNotBeFound(String filter) throws Exception {
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocationEvent() throws Exception {
        // Get the locationEvent
        restLocationEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocationEvent() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationEvent
        LocationEvent updatedLocationEvent = locationEventRepository.findById(locationEvent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocationEvent are not directly saved in db
        em.detach(updatedLocationEvent);
        updatedLocationEvent
            .source(UPDATED_SOURCE)
            .observedAt(UPDATED_OBSERVED_AT)
            .zoneConfidence(UPDATED_ZONE_CONFIDENCE)
            .rssi(UPDATED_RSSI)
            .txPower(UPDATED_TX_POWER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .accuracyMeters(UPDATED_ACCURACY_METERS)
            .speedKmh(UPDATED_SPEED_KMH)
            .gnssConstellation(UPDATED_GNSS_CONSTELLATION)
            .rawPayload(UPDATED_RAW_PAYLOAD);
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(updatedLocationEvent);

        restLocationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationEventDTO))
            )
            .andExpect(status().isOk());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationEventToMatchAllProperties(updatedLocationEvent);
    }

    @Test
    @Transactional
    void putNonExistingLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationEventWithPatch() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationEvent using partial update
        LocationEvent partialUpdatedLocationEvent = new LocationEvent();
        partialUpdatedLocationEvent.setId(locationEvent.getId());

        partialUpdatedLocationEvent
            .source(UPDATED_SOURCE)
            .zoneConfidence(UPDATED_ZONE_CONFIDENCE)
            .rssi(UPDATED_RSSI)
            .longitude(UPDATED_LONGITUDE)
            .gnssConstellation(UPDATED_GNSS_CONSTELLATION)
            .rawPayload(UPDATED_RAW_PAYLOAD);

        restLocationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationEvent))
            )
            .andExpect(status().isOk());

        // Validate the LocationEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationEventUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocationEvent, locationEvent),
            getPersistedLocationEvent(locationEvent)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocationEventWithPatch() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationEvent using partial update
        LocationEvent partialUpdatedLocationEvent = new LocationEvent();
        partialUpdatedLocationEvent.setId(locationEvent.getId());

        partialUpdatedLocationEvent
            .source(UPDATED_SOURCE)
            .observedAt(UPDATED_OBSERVED_AT)
            .zoneConfidence(UPDATED_ZONE_CONFIDENCE)
            .rssi(UPDATED_RSSI)
            .txPower(UPDATED_TX_POWER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .accuracyMeters(UPDATED_ACCURACY_METERS)
            .speedKmh(UPDATED_SPEED_KMH)
            .gnssConstellation(UPDATED_GNSS_CONSTELLATION)
            .rawPayload(UPDATED_RAW_PAYLOAD);

        restLocationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationEvent))
            )
            .andExpect(status().isOk());

        // Validate the LocationEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationEventUpdatableFieldsEquals(partialUpdatedLocationEvent, getPersistedLocationEvent(partialUpdatedLocationEvent));
    }

    @Test
    @Transactional
    void patchNonExistingLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationEventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationEvent.setId(longCount.incrementAndGet());

        // Create the LocationEvent
        LocationEventDTO locationEventDTO = locationEventMapper.toDto(locationEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationEvent() throws Exception {
        // Initialize the database
        insertedLocationEvent = locationEventRepository.saveAndFlush(locationEvent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locationEvent
        restLocationEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationEventRepository.count();
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

    protected LocationEvent getPersistedLocationEvent(LocationEvent locationEvent) {
        return locationEventRepository.findById(locationEvent.getId()).orElseThrow();
    }

    protected void assertPersistedLocationEventToMatchAllProperties(LocationEvent expectedLocationEvent) {
        assertLocationEventAllPropertiesEquals(expectedLocationEvent, getPersistedLocationEvent(expectedLocationEvent));
    }

    protected void assertPersistedLocationEventToMatchUpdatableProperties(LocationEvent expectedLocationEvent) {
        assertLocationEventAllUpdatablePropertiesEquals(expectedLocationEvent, getPersistedLocationEvent(expectedLocationEvent));
    }
}
