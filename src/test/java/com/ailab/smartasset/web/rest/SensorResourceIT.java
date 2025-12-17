package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.SensorAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ailab.smartasset.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.domain.enumeration.SensorType;
import com.ailab.smartasset.repository.SensorRepository;
import com.ailab.smartasset.service.SensorService;
import com.ailab.smartasset.service.dto.SensorDTO;
import com.ailab.smartasset.service.mapper.SensorMapper;
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
 * Integration tests for the {@link SensorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SensorResourceIT {

    private static final SensorType DEFAULT_SENSOR_TYPE = SensorType.TEMPERATURE;
    private static final SensorType UPDATED_SENSOR_TYPE = SensorType.VIBRATION;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MIN_THRESHOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_THRESHOLD = new BigDecimal(2);
    private static final BigDecimal SMALLER_MIN_THRESHOLD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MAX_THRESHOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_MAX_THRESHOLD = new BigDecimal(2);
    private static final BigDecimal SMALLER_MAX_THRESHOLD = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_INSTALLED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTALLED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sensors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SensorRepository sensorRepository;

    @Mock
    private SensorRepository sensorRepositoryMock;

    @Autowired
    private SensorMapper sensorMapper;

    @Mock
    private SensorService sensorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorMockMvc;

    private Sensor sensor;

    private Sensor insertedSensor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createEntity() {
        return new Sensor()
            .sensorType(DEFAULT_SENSOR_TYPE)
            .name(DEFAULT_NAME)
            .unit(DEFAULT_UNIT)
            .minThreshold(DEFAULT_MIN_THRESHOLD)
            .maxThreshold(DEFAULT_MAX_THRESHOLD)
            .installedAt(DEFAULT_INSTALLED_AT)
            .active(DEFAULT_ACTIVE)
            .externalId(DEFAULT_EXTERNAL_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createUpdatedEntity() {
        return new Sensor()
            .sensorType(UPDATED_SENSOR_TYPE)
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .minThreshold(UPDATED_MIN_THRESHOLD)
            .maxThreshold(UPDATED_MAX_THRESHOLD)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE)
            .externalId(UPDATED_EXTERNAL_ID);
    }

    @BeforeEach
    void initTest() {
        sensor = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSensor != null) {
            sensorRepository.delete(insertedSensor);
            insertedSensor = null;
        }
    }

    @Test
    @Transactional
    void createSensor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);
        var returnedSensorDTO = om.readValue(
            restSensorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SensorDTO.class
        );

        // Validate the Sensor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSensor = sensorMapper.toEntity(returnedSensorDTO);
        assertSensorUpdatableFieldsEquals(returnedSensor, getPersistedSensor(returnedSensor));

        insertedSensor = returnedSensor;
    }

    @Test
    @Transactional
    void createSensorWithExistingId() throws Exception {
        // Create the Sensor with an existing ID
        sensor.setId(1L);
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSensorTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sensor.setSensorType(null);

        // Create the Sensor, which fails.
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        restSensorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sensor.setActive(null);

        // Create the Sensor, which fails.
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        restSensorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSensors() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].sensorType").value(hasItem(DEFAULT_SENSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].minThreshold").value(hasItem(sameNumber(DEFAULT_MIN_THRESHOLD))))
            .andExpect(jsonPath("$.[*].maxThreshold").value(hasItem(sameNumber(DEFAULT_MAX_THRESHOLD))))
            .andExpect(jsonPath("$.[*].installedAt").value(hasItem(DEFAULT_INSTALLED_AT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSensorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(sensorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSensorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sensorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSensorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sensorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSensorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sensorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get the sensor
        restSensorMockMvc
            .perform(get(ENTITY_API_URL_ID, sensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensor.getId().intValue()))
            .andExpect(jsonPath("$.sensorType").value(DEFAULT_SENSOR_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.minThreshold").value(sameNumber(DEFAULT_MIN_THRESHOLD)))
            .andExpect(jsonPath("$.maxThreshold").value(sameNumber(DEFAULT_MAX_THRESHOLD)))
            .andExpect(jsonPath("$.installedAt").value(DEFAULT_INSTALLED_AT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID));
    }

    @Test
    @Transactional
    void getSensorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        Long id = sensor.getId();

        defaultSensorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSensorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSensorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSensorsBySensorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where sensorType equals to
        defaultSensorFiltering("sensorType.equals=" + DEFAULT_SENSOR_TYPE, "sensorType.equals=" + UPDATED_SENSOR_TYPE);
    }

    @Test
    @Transactional
    void getAllSensorsBySensorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where sensorType in
        defaultSensorFiltering("sensorType.in=" + DEFAULT_SENSOR_TYPE + "," + UPDATED_SENSOR_TYPE, "sensorType.in=" + UPDATED_SENSOR_TYPE);
    }

    @Test
    @Transactional
    void getAllSensorsBySensorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where sensorType is not null
        defaultSensorFiltering("sensorType.specified=true", "sensorType.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where name equals to
        defaultSensorFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSensorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where name in
        defaultSensorFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSensorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where name is not null
        defaultSensorFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where name contains
        defaultSensorFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSensorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where name does not contain
        defaultSensorFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllSensorsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where unit equals to
        defaultSensorFiltering("unit.equals=" + DEFAULT_UNIT, "unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllSensorsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where unit in
        defaultSensorFiltering("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT, "unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllSensorsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where unit is not null
        defaultSensorFiltering("unit.specified=true", "unit.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByUnitContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where unit contains
        defaultSensorFiltering("unit.contains=" + DEFAULT_UNIT, "unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllSensorsByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where unit does not contain
        defaultSensorFiltering("unit.doesNotContain=" + UPDATED_UNIT, "unit.doesNotContain=" + DEFAULT_UNIT);
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold equals to
        defaultSensorFiltering("minThreshold.equals=" + DEFAULT_MIN_THRESHOLD, "minThreshold.equals=" + UPDATED_MIN_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold in
        defaultSensorFiltering(
            "minThreshold.in=" + DEFAULT_MIN_THRESHOLD + "," + UPDATED_MIN_THRESHOLD,
            "minThreshold.in=" + UPDATED_MIN_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold is not null
        defaultSensorFiltering("minThreshold.specified=true", "minThreshold.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold is greater than or equal to
        defaultSensorFiltering(
            "minThreshold.greaterThanOrEqual=" + DEFAULT_MIN_THRESHOLD,
            "minThreshold.greaterThanOrEqual=" + UPDATED_MIN_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold is less than or equal to
        defaultSensorFiltering(
            "minThreshold.lessThanOrEqual=" + DEFAULT_MIN_THRESHOLD,
            "minThreshold.lessThanOrEqual=" + SMALLER_MIN_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold is less than
        defaultSensorFiltering("minThreshold.lessThan=" + UPDATED_MIN_THRESHOLD, "minThreshold.lessThan=" + DEFAULT_MIN_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByMinThresholdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where minThreshold is greater than
        defaultSensorFiltering("minThreshold.greaterThan=" + SMALLER_MIN_THRESHOLD, "minThreshold.greaterThan=" + DEFAULT_MIN_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold equals to
        defaultSensorFiltering("maxThreshold.equals=" + DEFAULT_MAX_THRESHOLD, "maxThreshold.equals=" + UPDATED_MAX_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold in
        defaultSensorFiltering(
            "maxThreshold.in=" + DEFAULT_MAX_THRESHOLD + "," + UPDATED_MAX_THRESHOLD,
            "maxThreshold.in=" + UPDATED_MAX_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold is not null
        defaultSensorFiltering("maxThreshold.specified=true", "maxThreshold.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold is greater than or equal to
        defaultSensorFiltering(
            "maxThreshold.greaterThanOrEqual=" + DEFAULT_MAX_THRESHOLD,
            "maxThreshold.greaterThanOrEqual=" + UPDATED_MAX_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold is less than or equal to
        defaultSensorFiltering(
            "maxThreshold.lessThanOrEqual=" + DEFAULT_MAX_THRESHOLD,
            "maxThreshold.lessThanOrEqual=" + SMALLER_MAX_THRESHOLD
        );
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold is less than
        defaultSensorFiltering("maxThreshold.lessThan=" + UPDATED_MAX_THRESHOLD, "maxThreshold.lessThan=" + DEFAULT_MAX_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByMaxThresholdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where maxThreshold is greater than
        defaultSensorFiltering("maxThreshold.greaterThan=" + SMALLER_MAX_THRESHOLD, "maxThreshold.greaterThan=" + DEFAULT_MAX_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllSensorsByInstalledAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where installedAt equals to
        defaultSensorFiltering("installedAt.equals=" + DEFAULT_INSTALLED_AT, "installedAt.equals=" + UPDATED_INSTALLED_AT);
    }

    @Test
    @Transactional
    void getAllSensorsByInstalledAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where installedAt in
        defaultSensorFiltering(
            "installedAt.in=" + DEFAULT_INSTALLED_AT + "," + UPDATED_INSTALLED_AT,
            "installedAt.in=" + UPDATED_INSTALLED_AT
        );
    }

    @Test
    @Transactional
    void getAllSensorsByInstalledAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where installedAt is not null
        defaultSensorFiltering("installedAt.specified=true", "installedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where active equals to
        defaultSensorFiltering("active.equals=" + DEFAULT_ACTIVE, "active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSensorsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where active in
        defaultSensorFiltering("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE, "active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSensorsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where active is not null
        defaultSensorFiltering("active.specified=true", "active.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByExternalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where externalId equals to
        defaultSensorFiltering("externalId.equals=" + DEFAULT_EXTERNAL_ID, "externalId.equals=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllSensorsByExternalIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where externalId in
        defaultSensorFiltering("externalId.in=" + DEFAULT_EXTERNAL_ID + "," + UPDATED_EXTERNAL_ID, "externalId.in=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllSensorsByExternalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where externalId is not null
        defaultSensorFiltering("externalId.specified=true", "externalId.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorsByExternalIdContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where externalId contains
        defaultSensorFiltering("externalId.contains=" + DEFAULT_EXTERNAL_ID, "externalId.contains=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllSensorsByExternalIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList where externalId does not contain
        defaultSensorFiltering("externalId.doesNotContain=" + UPDATED_EXTERNAL_ID, "externalId.doesNotContain=" + DEFAULT_EXTERNAL_ID);
    }

    @Test
    @Transactional
    void getAllSensorsByAssetIsEqualToSomething() throws Exception {
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            sensorRepository.saveAndFlush(sensor);
            asset = AssetResourceIT.createEntity();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        em.persist(asset);
        em.flush();
        sensor.setAsset(asset);
        sensorRepository.saveAndFlush(sensor);
        Long assetId = asset.getId();
        // Get all the sensorList where asset equals to assetId
        defaultSensorShouldBeFound("assetId.equals=" + assetId);

        // Get all the sensorList where asset equals to (assetId + 1)
        defaultSensorShouldNotBeFound("assetId.equals=" + (assetId + 1));
    }

    private void defaultSensorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSensorShouldBeFound(shouldBeFound);
        defaultSensorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSensorShouldBeFound(String filter) throws Exception {
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].sensorType").value(hasItem(DEFAULT_SENSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].minThreshold").value(hasItem(sameNumber(DEFAULT_MIN_THRESHOLD))))
            .andExpect(jsonPath("$.[*].maxThreshold").value(hasItem(sameNumber(DEFAULT_MAX_THRESHOLD))))
            .andExpect(jsonPath("$.[*].installedAt").value(hasItem(DEFAULT_INSTALLED_AT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)));

        // Check, that the count call also returns 1
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSensorShouldNotBeFound(String filter) throws Exception {
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSensor() throws Exception {
        // Get the sensor
        restSensorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor
        Sensor updatedSensor = sensorRepository.findById(sensor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSensor are not directly saved in db
        em.detach(updatedSensor);
        updatedSensor
            .sensorType(UPDATED_SENSOR_TYPE)
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .minThreshold(UPDATED_MIN_THRESHOLD)
            .maxThreshold(UPDATED_MAX_THRESHOLD)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE)
            .externalId(UPDATED_EXTERNAL_ID);
        SensorDTO sensorDTO = sensorMapper.toDto(updatedSensor);

        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSensorToMatchAllProperties(updatedSensor);
    }

    @Test
    @Transactional
    void putNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor.maxThreshold(UPDATED_MAX_THRESHOLD).installedAt(UPDATED_INSTALLED_AT);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSensor, sensor), getPersistedSensor(sensor));
    }

    @Test
    @Transactional
    void fullUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor
            .sensorType(UPDATED_SENSOR_TYPE)
            .name(UPDATED_NAME)
            .unit(UPDATED_UNIT)
            .minThreshold(UPDATED_MIN_THRESHOLD)
            .maxThreshold(UPDATED_MAX_THRESHOLD)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE)
            .externalId(UPDATED_EXTERNAL_ID);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(partialUpdatedSensor, getPersistedSensor(partialUpdatedSensor));
    }

    @Test
    @Transactional
    void patchNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sensorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensor.setId(longCount.incrementAndGet());

        // Create the Sensor
        SensorDTO sensorDTO = sensorMapper.toDto(sensor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sensorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sensor
        restSensorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sensor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sensorRepository.count();
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

    protected Sensor getPersistedSensor(Sensor sensor) {
        return sensorRepository.findById(sensor.getId()).orElseThrow();
    }

    protected void assertPersistedSensorToMatchAllProperties(Sensor expectedSensor) {
        assertSensorAllPropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }

    protected void assertPersistedSensorToMatchUpdatableProperties(Sensor expectedSensor) {
        assertSensorAllUpdatablePropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }
}
