package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.AssetMovementRequestAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.domain.User;
import com.ailab.smartasset.domain.enumeration.EsignStatus;
import com.ailab.smartasset.domain.enumeration.MovementRequestStatus;
import com.ailab.smartasset.repository.AssetMovementRequestRepository;
import com.ailab.smartasset.repository.UserRepository;
import com.ailab.smartasset.service.dto.AssetMovementRequestDTO;
import com.ailab.smartasset.service.mapper.AssetMovementRequestMapper;
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
 * Integration tests for the {@link AssetMovementRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetMovementRequestResourceIT {

    private static final MovementRequestStatus DEFAULT_STATUS = MovementRequestStatus.DRAFT;
    private static final MovementRequestStatus UPDATED_STATUS = MovementRequestStatus.SUBMITTED;

    private static final Instant DEFAULT_REQUESTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUESTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_LOCATION_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FROM_LOCATION_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_TO_LOCATION_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_TO_LOCATION_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ESIGN_WORKFLOW_ID = "AAAAAAAAAA";
    private static final String UPDATED_ESIGN_WORKFLOW_ID = "BBBBBBBBBB";

    private static final EsignStatus DEFAULT_ESIGN_STATUS = EsignStatus.NOT_STARTED;
    private static final EsignStatus UPDATED_ESIGN_STATUS = EsignStatus.SENT;

    private static final Instant DEFAULT_ESIGN_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESIGN_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SIGNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SIGNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXECUTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/asset-movement-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetMovementRequestRepository assetMovementRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetMovementRequestMapper assetMovementRequestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetMovementRequestMockMvc;

    private AssetMovementRequest assetMovementRequest;

    private AssetMovementRequest insertedAssetMovementRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetMovementRequest createEntity(EntityManager em) {
        AssetMovementRequest assetMovementRequest = new AssetMovementRequest()
            .status(DEFAULT_STATUS)
            .requestedAt(DEFAULT_REQUESTED_AT)
            .reason(DEFAULT_REASON)
            .fromLocationLabel(DEFAULT_FROM_LOCATION_LABEL)
            .toLocationLabel(DEFAULT_TO_LOCATION_LABEL)
            .esignWorkflowId(DEFAULT_ESIGN_WORKFLOW_ID)
            .esignStatus(DEFAULT_ESIGN_STATUS)
            .esignLastUpdate(DEFAULT_ESIGN_LAST_UPDATE)
            .signedAt(DEFAULT_SIGNED_AT)
            .executedAt(DEFAULT_EXECUTED_AT);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        assetMovementRequest.setAsset(asset);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        assetMovementRequest.setRequestedBy(user);
        return assetMovementRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetMovementRequest createUpdatedEntity(EntityManager em) {
        AssetMovementRequest updatedAssetMovementRequest = new AssetMovementRequest()
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .reason(UPDATED_REASON)
            .fromLocationLabel(UPDATED_FROM_LOCATION_LABEL)
            .toLocationLabel(UPDATED_TO_LOCATION_LABEL)
            .esignWorkflowId(UPDATED_ESIGN_WORKFLOW_ID)
            .esignStatus(UPDATED_ESIGN_STATUS)
            .esignLastUpdate(UPDATED_ESIGN_LAST_UPDATE)
            .signedAt(UPDATED_SIGNED_AT)
            .executedAt(UPDATED_EXECUTED_AT);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createUpdatedEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        updatedAssetMovementRequest.setAsset(asset);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedAssetMovementRequest.setRequestedBy(user);
        return updatedAssetMovementRequest;
    }

    @BeforeEach
    void initTest() {
        assetMovementRequest = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAssetMovementRequest != null) {
            assetMovementRequestRepository.delete(insertedAssetMovementRequest);
            insertedAssetMovementRequest = null;
        }
    }

    @Test
    @Transactional
    void createAssetMovementRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);
        var returnedAssetMovementRequestDTO = om.readValue(
            restAssetMovementRequestMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetMovementRequestDTO.class
        );

        // Validate the AssetMovementRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAssetMovementRequest = assetMovementRequestMapper.toEntity(returnedAssetMovementRequestDTO);
        assertAssetMovementRequestUpdatableFieldsEquals(
            returnedAssetMovementRequest,
            getPersistedAssetMovementRequest(returnedAssetMovementRequest)
        );

        insertedAssetMovementRequest = returnedAssetMovementRequest;
    }

    @Test
    @Transactional
    void createAssetMovementRequestWithExistingId() throws Exception {
        // Create the AssetMovementRequest with an existing ID
        assetMovementRequest.setId(1L);
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMovementRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetMovementRequest.setStatus(null);

        // Create the AssetMovementRequest, which fails.
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        restAssetMovementRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetMovementRequest.setRequestedAt(null);

        // Create the AssetMovementRequest, which fails.
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        restAssetMovementRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEsignStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetMovementRequest.setEsignStatus(null);

        // Create the AssetMovementRequest, which fails.
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        restAssetMovementRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequests() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetMovementRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestedAt").value(hasItem(DEFAULT_REQUESTED_AT.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].fromLocationLabel").value(hasItem(DEFAULT_FROM_LOCATION_LABEL)))
            .andExpect(jsonPath("$.[*].toLocationLabel").value(hasItem(DEFAULT_TO_LOCATION_LABEL)))
            .andExpect(jsonPath("$.[*].esignWorkflowId").value(hasItem(DEFAULT_ESIGN_WORKFLOW_ID)))
            .andExpect(jsonPath("$.[*].esignStatus").value(hasItem(DEFAULT_ESIGN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].esignLastUpdate").value(hasItem(DEFAULT_ESIGN_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].signedAt").value(hasItem(DEFAULT_SIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].executedAt").value(hasItem(DEFAULT_EXECUTED_AT.toString())));
    }

    @Test
    @Transactional
    void getAssetMovementRequest() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get the assetMovementRequest
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, assetMovementRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetMovementRequest.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.requestedAt").value(DEFAULT_REQUESTED_AT.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.fromLocationLabel").value(DEFAULT_FROM_LOCATION_LABEL))
            .andExpect(jsonPath("$.toLocationLabel").value(DEFAULT_TO_LOCATION_LABEL))
            .andExpect(jsonPath("$.esignWorkflowId").value(DEFAULT_ESIGN_WORKFLOW_ID))
            .andExpect(jsonPath("$.esignStatus").value(DEFAULT_ESIGN_STATUS.toString()))
            .andExpect(jsonPath("$.esignLastUpdate").value(DEFAULT_ESIGN_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.signedAt").value(DEFAULT_SIGNED_AT.toString()))
            .andExpect(jsonPath("$.executedAt").value(DEFAULT_EXECUTED_AT.toString()));
    }

    @Test
    @Transactional
    void getAssetMovementRequestsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        Long id = assetMovementRequest.getId();

        defaultAssetMovementRequestFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAssetMovementRequestFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAssetMovementRequestFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where status equals to
        defaultAssetMovementRequestFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where status in
        defaultAssetMovementRequestFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where status is not null
        defaultAssetMovementRequestFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByRequestedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where requestedAt equals to
        defaultAssetMovementRequestFiltering("requestedAt.equals=" + DEFAULT_REQUESTED_AT, "requestedAt.equals=" + UPDATED_REQUESTED_AT);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByRequestedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where requestedAt in
        defaultAssetMovementRequestFiltering(
            "requestedAt.in=" + DEFAULT_REQUESTED_AT + "," + UPDATED_REQUESTED_AT,
            "requestedAt.in=" + UPDATED_REQUESTED_AT
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByRequestedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where requestedAt is not null
        defaultAssetMovementRequestFiltering("requestedAt.specified=true", "requestedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where reason equals to
        defaultAssetMovementRequestFiltering("reason.equals=" + DEFAULT_REASON, "reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where reason in
        defaultAssetMovementRequestFiltering("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON, "reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where reason is not null
        defaultAssetMovementRequestFiltering("reason.specified=true", "reason.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByReasonContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where reason contains
        defaultAssetMovementRequestFiltering("reason.contains=" + DEFAULT_REASON, "reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where reason does not contain
        defaultAssetMovementRequestFiltering("reason.doesNotContain=" + UPDATED_REASON, "reason.doesNotContain=" + DEFAULT_REASON);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByFromLocationLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where fromLocationLabel equals to
        defaultAssetMovementRequestFiltering(
            "fromLocationLabel.equals=" + DEFAULT_FROM_LOCATION_LABEL,
            "fromLocationLabel.equals=" + UPDATED_FROM_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByFromLocationLabelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where fromLocationLabel in
        defaultAssetMovementRequestFiltering(
            "fromLocationLabel.in=" + DEFAULT_FROM_LOCATION_LABEL + "," + UPDATED_FROM_LOCATION_LABEL,
            "fromLocationLabel.in=" + UPDATED_FROM_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByFromLocationLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where fromLocationLabel is not null
        defaultAssetMovementRequestFiltering("fromLocationLabel.specified=true", "fromLocationLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByFromLocationLabelContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where fromLocationLabel contains
        defaultAssetMovementRequestFiltering(
            "fromLocationLabel.contains=" + DEFAULT_FROM_LOCATION_LABEL,
            "fromLocationLabel.contains=" + UPDATED_FROM_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByFromLocationLabelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where fromLocationLabel does not contain
        defaultAssetMovementRequestFiltering(
            "fromLocationLabel.doesNotContain=" + UPDATED_FROM_LOCATION_LABEL,
            "fromLocationLabel.doesNotContain=" + DEFAULT_FROM_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByToLocationLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where toLocationLabel equals to
        defaultAssetMovementRequestFiltering(
            "toLocationLabel.equals=" + DEFAULT_TO_LOCATION_LABEL,
            "toLocationLabel.equals=" + UPDATED_TO_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByToLocationLabelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where toLocationLabel in
        defaultAssetMovementRequestFiltering(
            "toLocationLabel.in=" + DEFAULT_TO_LOCATION_LABEL + "," + UPDATED_TO_LOCATION_LABEL,
            "toLocationLabel.in=" + UPDATED_TO_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByToLocationLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where toLocationLabel is not null
        defaultAssetMovementRequestFiltering("toLocationLabel.specified=true", "toLocationLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByToLocationLabelContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where toLocationLabel contains
        defaultAssetMovementRequestFiltering(
            "toLocationLabel.contains=" + DEFAULT_TO_LOCATION_LABEL,
            "toLocationLabel.contains=" + UPDATED_TO_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByToLocationLabelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where toLocationLabel does not contain
        defaultAssetMovementRequestFiltering(
            "toLocationLabel.doesNotContain=" + UPDATED_TO_LOCATION_LABEL,
            "toLocationLabel.doesNotContain=" + DEFAULT_TO_LOCATION_LABEL
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignWorkflowIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignWorkflowId equals to
        defaultAssetMovementRequestFiltering(
            "esignWorkflowId.equals=" + DEFAULT_ESIGN_WORKFLOW_ID,
            "esignWorkflowId.equals=" + UPDATED_ESIGN_WORKFLOW_ID
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignWorkflowIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignWorkflowId in
        defaultAssetMovementRequestFiltering(
            "esignWorkflowId.in=" + DEFAULT_ESIGN_WORKFLOW_ID + "," + UPDATED_ESIGN_WORKFLOW_ID,
            "esignWorkflowId.in=" + UPDATED_ESIGN_WORKFLOW_ID
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignWorkflowIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignWorkflowId is not null
        defaultAssetMovementRequestFiltering("esignWorkflowId.specified=true", "esignWorkflowId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignWorkflowIdContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignWorkflowId contains
        defaultAssetMovementRequestFiltering(
            "esignWorkflowId.contains=" + DEFAULT_ESIGN_WORKFLOW_ID,
            "esignWorkflowId.contains=" + UPDATED_ESIGN_WORKFLOW_ID
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignWorkflowIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignWorkflowId does not contain
        defaultAssetMovementRequestFiltering(
            "esignWorkflowId.doesNotContain=" + UPDATED_ESIGN_WORKFLOW_ID,
            "esignWorkflowId.doesNotContain=" + DEFAULT_ESIGN_WORKFLOW_ID
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignStatus equals to
        defaultAssetMovementRequestFiltering("esignStatus.equals=" + DEFAULT_ESIGN_STATUS, "esignStatus.equals=" + UPDATED_ESIGN_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignStatus in
        defaultAssetMovementRequestFiltering(
            "esignStatus.in=" + DEFAULT_ESIGN_STATUS + "," + UPDATED_ESIGN_STATUS,
            "esignStatus.in=" + UPDATED_ESIGN_STATUS
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignStatus is not null
        defaultAssetMovementRequestFiltering("esignStatus.specified=true", "esignStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignLastUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignLastUpdate equals to
        defaultAssetMovementRequestFiltering(
            "esignLastUpdate.equals=" + DEFAULT_ESIGN_LAST_UPDATE,
            "esignLastUpdate.equals=" + UPDATED_ESIGN_LAST_UPDATE
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignLastUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignLastUpdate in
        defaultAssetMovementRequestFiltering(
            "esignLastUpdate.in=" + DEFAULT_ESIGN_LAST_UPDATE + "," + UPDATED_ESIGN_LAST_UPDATE,
            "esignLastUpdate.in=" + UPDATED_ESIGN_LAST_UPDATE
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByEsignLastUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where esignLastUpdate is not null
        defaultAssetMovementRequestFiltering("esignLastUpdate.specified=true", "esignLastUpdate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsBySignedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where signedAt equals to
        defaultAssetMovementRequestFiltering("signedAt.equals=" + DEFAULT_SIGNED_AT, "signedAt.equals=" + UPDATED_SIGNED_AT);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsBySignedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where signedAt in
        defaultAssetMovementRequestFiltering(
            "signedAt.in=" + DEFAULT_SIGNED_AT + "," + UPDATED_SIGNED_AT,
            "signedAt.in=" + UPDATED_SIGNED_AT
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsBySignedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where signedAt is not null
        defaultAssetMovementRequestFiltering("signedAt.specified=true", "signedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByExecutedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where executedAt equals to
        defaultAssetMovementRequestFiltering("executedAt.equals=" + DEFAULT_EXECUTED_AT, "executedAt.equals=" + UPDATED_EXECUTED_AT);
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByExecutedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where executedAt in
        defaultAssetMovementRequestFiltering(
            "executedAt.in=" + DEFAULT_EXECUTED_AT + "," + UPDATED_EXECUTED_AT,
            "executedAt.in=" + UPDATED_EXECUTED_AT
        );
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByExecutedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        // Get all the assetMovementRequestList where executedAt is not null
        defaultAssetMovementRequestFiltering("executedAt.specified=true", "executedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByAssetIsEqualToSomething() throws Exception {
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
            asset = AssetResourceIT.createEntity(em);
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        em.persist(asset);
        em.flush();
        assetMovementRequest.setAsset(asset);
        assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
        Long assetId = asset.getId();
        // Get all the assetMovementRequestList where asset equals to assetId
        defaultAssetMovementRequestShouldBeFound("assetId.equals=" + assetId);

        // Get all the assetMovementRequestList where asset equals to (assetId + 1)
        defaultAssetMovementRequestShouldNotBeFound("assetId.equals=" + (assetId + 1));
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByRequestedByIsEqualToSomething() throws Exception {
        User requestedBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
            requestedBy = UserResourceIT.createEntity();
        } else {
            requestedBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(requestedBy);
        em.flush();
        assetMovementRequest.setRequestedBy(requestedBy);
        assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
        Long requestedById = requestedBy.getId();
        // Get all the assetMovementRequestList where requestedBy equals to requestedById
        defaultAssetMovementRequestShouldBeFound("requestedById.equals=" + requestedById);

        // Get all the assetMovementRequestList where requestedBy equals to (requestedById + 1)
        defaultAssetMovementRequestShouldNotBeFound("requestedById.equals=" + (requestedById + 1));
    }

    @Test
    @Transactional
    void getAllAssetMovementRequestsByApprovedByIsEqualToSomething() throws Exception {
        User approvedBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
            approvedBy = UserResourceIT.createEntity();
        } else {
            approvedBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(approvedBy);
        em.flush();
        assetMovementRequest.setApprovedBy(approvedBy);
        assetMovementRequestRepository.saveAndFlush(assetMovementRequest);
        Long approvedById = approvedBy.getId();
        // Get all the assetMovementRequestList where approvedBy equals to approvedById
        defaultAssetMovementRequestShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the assetMovementRequestList where approvedBy equals to (approvedById + 1)
        defaultAssetMovementRequestShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    private void defaultAssetMovementRequestFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAssetMovementRequestShouldBeFound(shouldBeFound);
        defaultAssetMovementRequestShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetMovementRequestShouldBeFound(String filter) throws Exception {
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetMovementRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestedAt").value(hasItem(DEFAULT_REQUESTED_AT.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].fromLocationLabel").value(hasItem(DEFAULT_FROM_LOCATION_LABEL)))
            .andExpect(jsonPath("$.[*].toLocationLabel").value(hasItem(DEFAULT_TO_LOCATION_LABEL)))
            .andExpect(jsonPath("$.[*].esignWorkflowId").value(hasItem(DEFAULT_ESIGN_WORKFLOW_ID)))
            .andExpect(jsonPath("$.[*].esignStatus").value(hasItem(DEFAULT_ESIGN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].esignLastUpdate").value(hasItem(DEFAULT_ESIGN_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].signedAt").value(hasItem(DEFAULT_SIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].executedAt").value(hasItem(DEFAULT_EXECUTED_AT.toString())));

        // Check, that the count call also returns 1
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetMovementRequestShouldNotBeFound(String filter) throws Exception {
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetMovementRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssetMovementRequest() throws Exception {
        // Get the assetMovementRequest
        restAssetMovementRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetMovementRequest() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMovementRequest
        AssetMovementRequest updatedAssetMovementRequest = assetMovementRequestRepository
            .findById(assetMovementRequest.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAssetMovementRequest are not directly saved in db
        em.detach(updatedAssetMovementRequest);
        updatedAssetMovementRequest
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .reason(UPDATED_REASON)
            .fromLocationLabel(UPDATED_FROM_LOCATION_LABEL)
            .toLocationLabel(UPDATED_TO_LOCATION_LABEL)
            .esignWorkflowId(UPDATED_ESIGN_WORKFLOW_ID)
            .esignStatus(UPDATED_ESIGN_STATUS)
            .esignLastUpdate(UPDATED_ESIGN_LAST_UPDATE)
            .signedAt(UPDATED_SIGNED_AT)
            .executedAt(UPDATED_EXECUTED_AT);
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(updatedAssetMovementRequest);

        restAssetMovementRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetMovementRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetMovementRequestToMatchAllProperties(updatedAssetMovementRequest);
    }

    @Test
    @Transactional
    void putNonExistingAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetMovementRequestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMovementRequestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetMovementRequestWithPatch() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMovementRequest using partial update
        AssetMovementRequest partialUpdatedAssetMovementRequest = new AssetMovementRequest();
        partialUpdatedAssetMovementRequest.setId(assetMovementRequest.getId());

        partialUpdatedAssetMovementRequest.fromLocationLabel(UPDATED_FROM_LOCATION_LABEL).esignWorkflowId(UPDATED_ESIGN_WORKFLOW_ID);

        restAssetMovementRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetMovementRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetMovementRequest))
            )
            .andExpect(status().isOk());

        // Validate the AssetMovementRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetMovementRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetMovementRequest, assetMovementRequest),
            getPersistedAssetMovementRequest(assetMovementRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetMovementRequestWithPatch() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMovementRequest using partial update
        AssetMovementRequest partialUpdatedAssetMovementRequest = new AssetMovementRequest();
        partialUpdatedAssetMovementRequest.setId(assetMovementRequest.getId());

        partialUpdatedAssetMovementRequest
            .status(UPDATED_STATUS)
            .requestedAt(UPDATED_REQUESTED_AT)
            .reason(UPDATED_REASON)
            .fromLocationLabel(UPDATED_FROM_LOCATION_LABEL)
            .toLocationLabel(UPDATED_TO_LOCATION_LABEL)
            .esignWorkflowId(UPDATED_ESIGN_WORKFLOW_ID)
            .esignStatus(UPDATED_ESIGN_STATUS)
            .esignLastUpdate(UPDATED_ESIGN_LAST_UPDATE)
            .signedAt(UPDATED_SIGNED_AT)
            .executedAt(UPDATED_EXECUTED_AT);

        restAssetMovementRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetMovementRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetMovementRequest))
            )
            .andExpect(status().isOk());

        // Validate the AssetMovementRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetMovementRequestUpdatableFieldsEquals(
            partialUpdatedAssetMovementRequest,
            getPersistedAssetMovementRequest(partialUpdatedAssetMovementRequest)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetMovementRequestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetMovementRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMovementRequest.setId(longCount.incrementAndGet());

        // Create the AssetMovementRequest
        AssetMovementRequestDTO assetMovementRequestDTO = assetMovementRequestMapper.toDto(assetMovementRequest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMovementRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetMovementRequestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetMovementRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetMovementRequest() throws Exception {
        // Initialize the database
        insertedAssetMovementRequest = assetMovementRequestRepository.saveAndFlush(assetMovementRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetMovementRequest
        restAssetMovementRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetMovementRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetMovementRequestRepository.count();
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

    protected AssetMovementRequest getPersistedAssetMovementRequest(AssetMovementRequest assetMovementRequest) {
        return assetMovementRequestRepository.findById(assetMovementRequest.getId()).orElseThrow();
    }

    protected void assertPersistedAssetMovementRequestToMatchAllProperties(AssetMovementRequest expectedAssetMovementRequest) {
        assertAssetMovementRequestAllPropertiesEquals(
            expectedAssetMovementRequest,
            getPersistedAssetMovementRequest(expectedAssetMovementRequest)
        );
    }

    protected void assertPersistedAssetMovementRequestToMatchUpdatableProperties(AssetMovementRequest expectedAssetMovementRequest) {
        assertAssetMovementRequestAllUpdatablePropertiesEquals(
            expectedAssetMovementRequest,
            getPersistedAssetMovementRequest(expectedAssetMovementRequest)
        );
    }
}
