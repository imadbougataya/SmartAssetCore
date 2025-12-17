package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.GatewayAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Gateway;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.repository.GatewayRepository;
import com.ailab.smartasset.service.GatewayService;
import com.ailab.smartasset.service.dto.GatewayDTO;
import com.ailab.smartasset.service.mapper.GatewayMapper;
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
 * Integration tests for the {@link GatewayResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GatewayResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MAC_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAC_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSTALLED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTALLED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/gateways";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Mock
    private GatewayRepository gatewayRepositoryMock;

    @Autowired
    private GatewayMapper gatewayMapper;

    @Mock
    private GatewayService gatewayServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGatewayMockMvc;

    private Gateway gateway;

    private Gateway insertedGateway;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gateway createEntity() {
        return new Gateway()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .vendor(DEFAULT_VENDOR)
            .model(DEFAULT_MODEL)
            .macAddress(DEFAULT_MAC_ADDRESS)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .installedAt(DEFAULT_INSTALLED_AT)
            .active(DEFAULT_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gateway createUpdatedEntity() {
        return new Gateway()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .vendor(UPDATED_VENDOR)
            .model(UPDATED_MODEL)
            .macAddress(UPDATED_MAC_ADDRESS)
            .ipAddress(UPDATED_IP_ADDRESS)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE);
    }

    @BeforeEach
    void initTest() {
        gateway = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedGateway != null) {
            gatewayRepository.delete(insertedGateway);
            insertedGateway = null;
        }
    }

    @Test
    @Transactional
    void createGateway() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);
        var returnedGatewayDTO = om.readValue(
            restGatewayMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GatewayDTO.class
        );

        // Validate the Gateway in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGateway = gatewayMapper.toEntity(returnedGatewayDTO);
        assertGatewayUpdatableFieldsEquals(returnedGateway, getPersistedGateway(returnedGateway));

        insertedGateway = returnedGateway;
    }

    @Test
    @Transactional
    void createGatewayWithExistingId() throws Exception {
        // Create the Gateway with an existing ID
        gateway.setId(1L);
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGatewayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gateway.setCode(null);

        // Create the Gateway, which fails.
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        restGatewayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gateway.setActive(null);

        // Create the Gateway, which fails.
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        restGatewayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGateways() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gateway.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].macAddress").value(hasItem(DEFAULT_MAC_ADDRESS)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].installedAt").value(hasItem(DEFAULT_INSTALLED_AT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGatewaysWithEagerRelationshipsIsEnabled() throws Exception {
        when(gatewayServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGatewayMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(gatewayServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGatewaysWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(gatewayServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGatewayMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(gatewayRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGateway() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get the gateway
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL_ID, gateway.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gateway.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.macAddress").value(DEFAULT_MAC_ADDRESS))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.installedAt").value(DEFAULT_INSTALLED_AT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE));
    }

    @Test
    @Transactional
    void getGatewaysByIdFiltering() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        Long id = gateway.getId();

        defaultGatewayFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultGatewayFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultGatewayFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGatewaysByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where code equals to
        defaultGatewayFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllGatewaysByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where code in
        defaultGatewayFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllGatewaysByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where code is not null
        defaultGatewayFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where code contains
        defaultGatewayFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllGatewaysByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where code does not contain
        defaultGatewayFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllGatewaysByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where name equals to
        defaultGatewayFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGatewaysByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where name in
        defaultGatewayFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGatewaysByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where name is not null
        defaultGatewayFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where name contains
        defaultGatewayFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllGatewaysByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where name does not contain
        defaultGatewayFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllGatewaysByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where vendor equals to
        defaultGatewayFiltering("vendor.equals=" + DEFAULT_VENDOR, "vendor.equals=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void getAllGatewaysByVendorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where vendor in
        defaultGatewayFiltering("vendor.in=" + DEFAULT_VENDOR + "," + UPDATED_VENDOR, "vendor.in=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void getAllGatewaysByVendorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where vendor is not null
        defaultGatewayFiltering("vendor.specified=true", "vendor.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByVendorContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where vendor contains
        defaultGatewayFiltering("vendor.contains=" + DEFAULT_VENDOR, "vendor.contains=" + UPDATED_VENDOR);
    }

    @Test
    @Transactional
    void getAllGatewaysByVendorNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where vendor does not contain
        defaultGatewayFiltering("vendor.doesNotContain=" + UPDATED_VENDOR, "vendor.doesNotContain=" + DEFAULT_VENDOR);
    }

    @Test
    @Transactional
    void getAllGatewaysByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where model equals to
        defaultGatewayFiltering("model.equals=" + DEFAULT_MODEL, "model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllGatewaysByModelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where model in
        defaultGatewayFiltering("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL, "model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllGatewaysByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where model is not null
        defaultGatewayFiltering("model.specified=true", "model.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByModelContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where model contains
        defaultGatewayFiltering("model.contains=" + DEFAULT_MODEL, "model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllGatewaysByModelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where model does not contain
        defaultGatewayFiltering("model.doesNotContain=" + UPDATED_MODEL, "model.doesNotContain=" + DEFAULT_MODEL);
    }

    @Test
    @Transactional
    void getAllGatewaysByMacAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where macAddress equals to
        defaultGatewayFiltering("macAddress.equals=" + DEFAULT_MAC_ADDRESS, "macAddress.equals=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByMacAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where macAddress in
        defaultGatewayFiltering("macAddress.in=" + DEFAULT_MAC_ADDRESS + "," + UPDATED_MAC_ADDRESS, "macAddress.in=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByMacAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where macAddress is not null
        defaultGatewayFiltering("macAddress.specified=true", "macAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByMacAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where macAddress contains
        defaultGatewayFiltering("macAddress.contains=" + DEFAULT_MAC_ADDRESS, "macAddress.contains=" + UPDATED_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByMacAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where macAddress does not contain
        defaultGatewayFiltering("macAddress.doesNotContain=" + UPDATED_MAC_ADDRESS, "macAddress.doesNotContain=" + DEFAULT_MAC_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByIpAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where ipAddress equals to
        defaultGatewayFiltering("ipAddress.equals=" + DEFAULT_IP_ADDRESS, "ipAddress.equals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByIpAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where ipAddress in
        defaultGatewayFiltering("ipAddress.in=" + DEFAULT_IP_ADDRESS + "," + UPDATED_IP_ADDRESS, "ipAddress.in=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByIpAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where ipAddress is not null
        defaultGatewayFiltering("ipAddress.specified=true", "ipAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByIpAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where ipAddress contains
        defaultGatewayFiltering("ipAddress.contains=" + DEFAULT_IP_ADDRESS, "ipAddress.contains=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByIpAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where ipAddress does not contain
        defaultGatewayFiltering("ipAddress.doesNotContain=" + UPDATED_IP_ADDRESS, "ipAddress.doesNotContain=" + DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllGatewaysByInstalledAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where installedAt equals to
        defaultGatewayFiltering("installedAt.equals=" + DEFAULT_INSTALLED_AT, "installedAt.equals=" + UPDATED_INSTALLED_AT);
    }

    @Test
    @Transactional
    void getAllGatewaysByInstalledAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where installedAt in
        defaultGatewayFiltering(
            "installedAt.in=" + DEFAULT_INSTALLED_AT + "," + UPDATED_INSTALLED_AT,
            "installedAt.in=" + UPDATED_INSTALLED_AT
        );
    }

    @Test
    @Transactional
    void getAllGatewaysByInstalledAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where installedAt is not null
        defaultGatewayFiltering("installedAt.specified=true", "installedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where active equals to
        defaultGatewayFiltering("active.equals=" + DEFAULT_ACTIVE, "active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGatewaysByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where active in
        defaultGatewayFiltering("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE, "active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGatewaysByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        // Get all the gatewayList where active is not null
        defaultGatewayFiltering("active.specified=true", "active.specified=false");
    }

    @Test
    @Transactional
    void getAllGatewaysBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            gatewayRepository.saveAndFlush(gateway);
            site = SiteResourceIT.createEntity();
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        gateway.setSite(site);
        gatewayRepository.saveAndFlush(gateway);
        Long siteId = site.getId();
        // Get all the gatewayList where site equals to siteId
        defaultGatewayShouldBeFound("siteId.equals=" + siteId);

        // Get all the gatewayList where site equals to (siteId + 1)
        defaultGatewayShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    @Test
    @Transactional
    void getAllGatewaysByZoneIsEqualToSomething() throws Exception {
        Zone zone;
        if (TestUtil.findAll(em, Zone.class).isEmpty()) {
            gatewayRepository.saveAndFlush(gateway);
            zone = ZoneResourceIT.createEntity();
        } else {
            zone = TestUtil.findAll(em, Zone.class).get(0);
        }
        em.persist(zone);
        em.flush();
        gateway.setZone(zone);
        gatewayRepository.saveAndFlush(gateway);
        Long zoneId = zone.getId();
        // Get all the gatewayList where zone equals to zoneId
        defaultGatewayShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the gatewayList where zone equals to (zoneId + 1)
        defaultGatewayShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
    }

    private void defaultGatewayFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultGatewayShouldBeFound(shouldBeFound);
        defaultGatewayShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGatewayShouldBeFound(String filter) throws Exception {
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gateway.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].macAddress").value(hasItem(DEFAULT_MAC_ADDRESS)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].installedAt").value(hasItem(DEFAULT_INSTALLED_AT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));

        // Check, that the count call also returns 1
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGatewayShouldNotBeFound(String filter) throws Exception {
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGatewayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGateway() throws Exception {
        // Get the gateway
        restGatewayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGateway() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gateway
        Gateway updatedGateway = gatewayRepository.findById(gateway.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGateway are not directly saved in db
        em.detach(updatedGateway);
        updatedGateway
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .vendor(UPDATED_VENDOR)
            .model(UPDATED_MODEL)
            .macAddress(UPDATED_MAC_ADDRESS)
            .ipAddress(UPDATED_IP_ADDRESS)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE);
        GatewayDTO gatewayDTO = gatewayMapper.toDto(updatedGateway);

        restGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gatewayDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO))
            )
            .andExpect(status().isOk());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGatewayToMatchAllProperties(updatedGateway);
    }

    @Test
    @Transactional
    void putNonExistingGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gatewayDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGatewayWithPatch() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gateway using partial update
        Gateway partialUpdatedGateway = new Gateway();
        partialUpdatedGateway.setId(gateway.getId());

        partialUpdatedGateway.name(UPDATED_NAME).vendor(UPDATED_VENDOR).macAddress(UPDATED_MAC_ADDRESS).ipAddress(UPDATED_IP_ADDRESS);

        restGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGateway.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGateway))
            )
            .andExpect(status().isOk());

        // Validate the Gateway in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGateway, gateway), getPersistedGateway(gateway));
    }

    @Test
    @Transactional
    void fullUpdateGatewayWithPatch() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gateway using partial update
        Gateway partialUpdatedGateway = new Gateway();
        partialUpdatedGateway.setId(gateway.getId());

        partialUpdatedGateway
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .vendor(UPDATED_VENDOR)
            .model(UPDATED_MODEL)
            .macAddress(UPDATED_MAC_ADDRESS)
            .ipAddress(UPDATED_IP_ADDRESS)
            .installedAt(UPDATED_INSTALLED_AT)
            .active(UPDATED_ACTIVE);

        restGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGateway.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGateway))
            )
            .andExpect(status().isOk());

        // Validate the Gateway in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayUpdatableFieldsEquals(partialUpdatedGateway, getPersistedGateway(partialUpdatedGateway));
    }

    @Test
    @Transactional
    void patchNonExistingGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gatewayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gateway.setId(longCount.incrementAndGet());

        // Create the Gateway
        GatewayDTO gatewayDTO = gatewayMapper.toDto(gateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gatewayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGateway() throws Exception {
        // Initialize the database
        insertedGateway = gatewayRepository.saveAndFlush(gateway);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gateway
        restGatewayMockMvc
            .perform(delete(ENTITY_API_URL_ID, gateway.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gatewayRepository.count();
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

    protected Gateway getPersistedGateway(Gateway gateway) {
        return gatewayRepository.findById(gateway.getId()).orElseThrow();
    }

    protected void assertPersistedGatewayToMatchAllProperties(Gateway expectedGateway) {
        assertGatewayAllPropertiesEquals(expectedGateway, getPersistedGateway(expectedGateway));
    }

    protected void assertPersistedGatewayToMatchUpdatableProperties(Gateway expectedGateway) {
        assertGatewayAllUpdatablePropertiesEquals(expectedGateway, getPersistedGateway(expectedGateway));
    }
}
