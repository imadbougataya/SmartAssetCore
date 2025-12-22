package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.SensorMeasurementAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ailab.smartasset.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Sensor;
import com.ailab.smartasset.domain.SensorMeasurement;
import com.ailab.smartasset.repository.SensorMeasurementRepository;
import com.ailab.smartasset.service.dto.SensorMeasurementDTO;
import com.ailab.smartasset.service.mapper.SensorMeasurementMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SensorMeasurementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SensorMeasurementResourceIT {

    private static final Instant DEFAULT_MEASURED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MEASURED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALUE = new BigDecimal(1 - 1);

    private static final String DEFAULT_QUALITY = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sensor-measurements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SensorMeasurementRepository sensorMeasurementRepository;

    @Autowired
    private SensorMeasurementMapper sensorMeasurementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorMeasurementMockMvc;

    private SensorMeasurement sensorMeasurement;

    private SensorMeasurement insertedSensorMeasurement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorMeasurement createEntity(EntityManager em) {
        SensorMeasurement sensorMeasurement = new SensorMeasurement()
            .measuredAt(DEFAULT_MEASURED_AT)
            .value(DEFAULT_VALUE)
            .quality(DEFAULT_QUALITY)
            .source(DEFAULT_SOURCE);
        // Add required entity
        Sensor sensor;
        if (TestUtil.findAll(em, Sensor.class).isEmpty()) {
            sensor = SensorResourceIT.createEntity(em);
            em.persist(sensor);
            em.flush();
        } else {
            sensor = TestUtil.findAll(em, Sensor.class).get(0);
        }
        sensorMeasurement.setSensor(sensor);
        return sensorMeasurement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorMeasurement createUpdatedEntity(EntityManager em) {
        SensorMeasurement updatedSensorMeasurement = new SensorMeasurement()
            .measuredAt(UPDATED_MEASURED_AT)
            .value(UPDATED_VALUE)
            .quality(UPDATED_QUALITY)
            .source(UPDATED_SOURCE);
        // Add required entity
        Sensor sensor;
        if (TestUtil.findAll(em, Sensor.class).isEmpty()) {
            sensor = SensorResourceIT.createUpdatedEntity(em);
            em.persist(sensor);
            em.flush();
        } else {
            sensor = TestUtil.findAll(em, Sensor.class).get(0);
        }
        updatedSensorMeasurement.setSensor(sensor);
        return updatedSensorMeasurement;
    }

    @BeforeEach
    void initTest() {
        sensorMeasurement = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSensorMeasurement != null) {
            sensorMeasurementRepository.delete(insertedSensorMeasurement);
            insertedSensorMeasurement = null;
        }
    }

    @Test
    @Transactional
    void createSensorMeasurement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);
        var returnedSensorMeasurementDTO = om.readValue(
            restSensorMeasurementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorMeasurementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SensorMeasurementDTO.class
        );

        // Validate the SensorMeasurement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSensorMeasurement = sensorMeasurementMapper.toEntity(returnedSensorMeasurementDTO);
        assertSensorMeasurementUpdatableFieldsEquals(returnedSensorMeasurement, getPersistedSensorMeasurement(returnedSensorMeasurement));

        insertedSensorMeasurement = returnedSensorMeasurement;
    }

    @Test
    @Transactional
    void createSensorMeasurementWithExistingId() throws Exception {
        // Create the SensorMeasurement with an existing ID
        sensorMeasurement.setId(1L);
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorMeasurementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorMeasurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMeasuredAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sensorMeasurement.setMeasuredAt(null);

        // Create the SensorMeasurement, which fails.
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        restSensorMeasurementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorMeasurementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sensorMeasurement.setValue(null);

        // Create the SensorMeasurement, which fails.
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        restSensorMeasurementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorMeasurementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSensorMeasurements() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].measuredAt").value(hasItem(DEFAULT_MEASURED_AT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)));
    }

    @Test
    @Transactional
    void getSensorMeasurement() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get the sensorMeasurement
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL_ID, sensorMeasurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensorMeasurement.getId().intValue()))
            .andExpect(jsonPath("$.measuredAt").value(DEFAULT_MEASURED_AT.toString()))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE));
    }

    @Test
    @Transactional
    void getSensorMeasurementsByIdFiltering() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        Long id = sensorMeasurement.getId();

        defaultSensorMeasurementFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSensorMeasurementFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSensorMeasurementFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByMeasuredAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where measuredAt equals to
        defaultSensorMeasurementFiltering("measuredAt.equals=" + DEFAULT_MEASURED_AT, "measuredAt.equals=" + UPDATED_MEASURED_AT);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByMeasuredAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where measuredAt in
        defaultSensorMeasurementFiltering(
            "measuredAt.in=" + DEFAULT_MEASURED_AT + "," + UPDATED_MEASURED_AT,
            "measuredAt.in=" + UPDATED_MEASURED_AT
        );
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByMeasuredAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where measuredAt is not null
        defaultSensorMeasurementFiltering("measuredAt.specified=true", "measuredAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value equals to
        defaultSensorMeasurementFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value in
        defaultSensorMeasurementFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value is not null
        defaultSensorMeasurementFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value is greater than or equal to
        defaultSensorMeasurementFiltering("value.greaterThanOrEqual=" + DEFAULT_VALUE, "value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value is less than or equal to
        defaultSensorMeasurementFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value is less than
        defaultSensorMeasurementFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where value is greater than
        defaultSensorMeasurementFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByQualityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where quality equals to
        defaultSensorMeasurementFiltering("quality.equals=" + DEFAULT_QUALITY, "quality.equals=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByQualityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where quality in
        defaultSensorMeasurementFiltering("quality.in=" + DEFAULT_QUALITY + "," + UPDATED_QUALITY, "quality.in=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByQualityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where quality is not null
        defaultSensorMeasurementFiltering("quality.specified=true", "quality.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByQualityContainsSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where quality contains
        defaultSensorMeasurementFiltering("quality.contains=" + DEFAULT_QUALITY, "quality.contains=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsByQualityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where quality does not contain
        defaultSensorMeasurementFiltering("quality.doesNotContain=" + UPDATED_QUALITY, "quality.doesNotContain=" + DEFAULT_QUALITY);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where source equals to
        defaultSensorMeasurementFiltering("source.equals=" + DEFAULT_SOURCE, "source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where source in
        defaultSensorMeasurementFiltering("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE, "source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where source is not null
        defaultSensorMeasurementFiltering("source.specified=true", "source.specified=false");
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySourceContainsSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where source contains
        defaultSensorMeasurementFiltering("source.contains=" + DEFAULT_SOURCE, "source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        // Get all the sensorMeasurementList where source does not contain
        defaultSensorMeasurementFiltering("source.doesNotContain=" + UPDATED_SOURCE, "source.doesNotContain=" + DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    void getAllSensorMeasurementsBySensorIsEqualToSomething() throws Exception {
        Sensor sensor;
        if (TestUtil.findAll(em, Sensor.class).isEmpty()) {
            sensorMeasurementRepository.saveAndFlush(sensorMeasurement);
            sensor = SensorResourceIT.createEntity(em);
        } else {
            sensor = TestUtil.findAll(em, Sensor.class).get(0);
        }
        em.persist(sensor);
        em.flush();
        sensorMeasurement.setSensor(sensor);
        sensorMeasurementRepository.saveAndFlush(sensorMeasurement);
        Long sensorId = sensor.getId();
        // Get all the sensorMeasurementList where sensor equals to sensorId
        defaultSensorMeasurementShouldBeFound("sensorId.equals=" + sensorId);

        // Get all the sensorMeasurementList where sensor equals to (sensorId + 1)
        defaultSensorMeasurementShouldNotBeFound("sensorId.equals=" + (sensorId + 1));
    }

    private void defaultSensorMeasurementFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSensorMeasurementShouldBeFound(shouldBeFound);
        defaultSensorMeasurementShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSensorMeasurementShouldBeFound(String filter) throws Exception {
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].measuredAt").value(hasItem(DEFAULT_MEASURED_AT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)));

        // Check, that the count call also returns 1
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSensorMeasurementShouldNotBeFound(String filter) throws Exception {
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSensorMeasurementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSensorMeasurement() throws Exception {
        // Get the sensorMeasurement
        restSensorMeasurementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSensorMeasurement() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensorMeasurement
        SensorMeasurement updatedSensorMeasurement = sensorMeasurementRepository.findById(sensorMeasurement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSensorMeasurement are not directly saved in db
        em.detach(updatedSensorMeasurement);
        updatedSensorMeasurement.measuredAt(UPDATED_MEASURED_AT).value(UPDATED_VALUE).quality(UPDATED_QUALITY).source(UPDATED_SOURCE);
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(updatedSensorMeasurement);

        restSensorMeasurementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensorMeasurementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensorMeasurementDTO))
            )
            .andExpect(status().isOk());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSensorMeasurementToMatchAllProperties(updatedSensorMeasurement);
    }

    @Test
    @Transactional
    void putNonExistingSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensorMeasurementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensorMeasurementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensorMeasurementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensorMeasurementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSensorMeasurementWithPatch() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensorMeasurement using partial update
        SensorMeasurement partialUpdatedSensorMeasurement = new SensorMeasurement();
        partialUpdatedSensorMeasurement.setId(sensorMeasurement.getId());

        partialUpdatedSensorMeasurement.measuredAt(UPDATED_MEASURED_AT).value(UPDATED_VALUE);

        restSensorMeasurementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensorMeasurement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensorMeasurement))
            )
            .andExpect(status().isOk());

        // Validate the SensorMeasurement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorMeasurementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSensorMeasurement, sensorMeasurement),
            getPersistedSensorMeasurement(sensorMeasurement)
        );
    }

    @Test
    @Transactional
    void fullUpdateSensorMeasurementWithPatch() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensorMeasurement using partial update
        SensorMeasurement partialUpdatedSensorMeasurement = new SensorMeasurement();
        partialUpdatedSensorMeasurement.setId(sensorMeasurement.getId());

        partialUpdatedSensorMeasurement
            .measuredAt(UPDATED_MEASURED_AT)
            .value(UPDATED_VALUE)
            .quality(UPDATED_QUALITY)
            .source(UPDATED_SOURCE);

        restSensorMeasurementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensorMeasurement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensorMeasurement))
            )
            .andExpect(status().isOk());

        // Validate the SensorMeasurement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorMeasurementUpdatableFieldsEquals(
            partialUpdatedSensorMeasurement,
            getPersistedSensorMeasurement(partialUpdatedSensorMeasurement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sensorMeasurementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensorMeasurementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensorMeasurementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSensorMeasurement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorMeasurement.setId(longCount.incrementAndGet());

        // Create the SensorMeasurement
        SensorMeasurementDTO sensorMeasurementDTO = sensorMeasurementMapper.toDto(sensorMeasurement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMeasurementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sensorMeasurementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SensorMeasurement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSensorMeasurement() throws Exception {
        // Initialize the database
        insertedSensorMeasurement = sensorMeasurementRepository.saveAndFlush(sensorMeasurement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sensorMeasurement
        restSensorMeasurementMockMvc
            .perform(delete(ENTITY_API_URL_ID, sensorMeasurement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sensorMeasurementRepository.count();
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

    protected SensorMeasurement getPersistedSensorMeasurement(SensorMeasurement sensorMeasurement) {
        return sensorMeasurementRepository.findById(sensorMeasurement.getId()).orElseThrow();
    }

    protected void assertPersistedSensorMeasurementToMatchAllProperties(SensorMeasurement expectedSensorMeasurement) {
        assertSensorMeasurementAllPropertiesEquals(expectedSensorMeasurement, getPersistedSensorMeasurement(expectedSensorMeasurement));
    }

    protected void assertPersistedSensorMeasurementToMatchUpdatableProperties(SensorMeasurement expectedSensorMeasurement) {
        assertSensorMeasurementAllUpdatablePropertiesEquals(
            expectedSensorMeasurement,
            getPersistedSensorMeasurement(expectedSensorMeasurement)
        );
    }
}
