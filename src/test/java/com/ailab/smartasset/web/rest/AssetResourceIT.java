package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.AssetAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ailab.smartasset.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Asset;
import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.domain.Zone;
import com.ailab.smartasset.domain.enumeration.AssetGeofencePolicy;
import com.ailab.smartasset.domain.enumeration.AssetStatus;
import com.ailab.smartasset.domain.enumeration.AssetType;
import com.ailab.smartasset.domain.enumeration.Criticality;
import com.ailab.smartasset.domain.enumeration.MountingType;
import com.ailab.smartasset.domain.enumeration.TemperatureProbeType;
import com.ailab.smartasset.repository.AssetRepository;
import com.ailab.smartasset.service.AssetService;
import com.ailab.smartasset.service.dto.AssetDTO;
import com.ailab.smartasset.service.mapper.AssetMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AssetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssetResourceIT {

    private static final AssetType DEFAULT_ASSET_TYPE = AssetType.INDUSTRIAL_ASSET;
    private static final AssetType UPDATED_ASSET_TYPE = AssetType.NON_INDUSTRIAL_ASSET;

    private static final String DEFAULT_ASSET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AssetStatus DEFAULT_STATUS = AssetStatus.ACTIVE;
    private static final AssetStatus UPDATED_STATUS = AssetStatus.INACTIVE;

    private static final Criticality DEFAULT_CRITICALITY = Criticality.LOW;
    private static final Criticality UPDATED_CRITICALITY = Criticality.MEDIUM;

    private static final AssetGeofencePolicy DEFAULT_GEOFENCE_POLICY = AssetGeofencePolicy.NONE;
    private static final AssetGeofencePolicy UPDATED_GEOFENCE_POLICY = AssetGeofencePolicy.ZONE_ONLY;

    private static final String DEFAULT_RESPONSIBLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COST_CENTER = "AAAAAAAAAA";
    private static final String UPDATED_COST_CENTER = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_POWER_KW = new BigDecimal(1);
    private static final BigDecimal UPDATED_POWER_KW = new BigDecimal(2);
    private static final BigDecimal SMALLER_POWER_KW = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VOLTAGE_V = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLTAGE_V = new BigDecimal(2);
    private static final BigDecimal SMALLER_VOLTAGE_V = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CURRENT_A = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_A = new BigDecimal(2);
    private static final BigDecimal SMALLER_CURRENT_A = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_COS_PHI = new BigDecimal(1);
    private static final BigDecimal UPDATED_COS_PHI = new BigDecimal(2);
    private static final BigDecimal SMALLER_COS_PHI = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_SPEED_RPM = 1;
    private static final Integer UPDATED_SPEED_RPM = 2;
    private static final Integer SMALLER_SPEED_RPM = 1 - 1;

    private static final String DEFAULT_IP_RATING = "AAAAAAAAAA";
    private static final String UPDATED_IP_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_INSULATION_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_INSULATION_CLASS = "BBBBBBBBBB";

    private static final MountingType DEFAULT_MOUNTING_TYPE = MountingType.B3;
    private static final MountingType UPDATED_MOUNTING_TYPE = MountingType.B5;

    private static final BigDecimal DEFAULT_SHAFT_DIAMETER_MM = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHAFT_DIAMETER_MM = new BigDecimal(2);
    private static final BigDecimal SMALLER_SHAFT_DIAMETER_MM = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOT_DISTANCE_AMM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOT_DISTANCE_AMM = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOT_DISTANCE_AMM = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOT_DISTANCE_BMM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOT_DISTANCE_BMM = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOT_DISTANCE_BMM = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FRONT_FLANGE_MM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FRONT_FLANGE_MM = new BigDecimal(2);
    private static final BigDecimal SMALLER_FRONT_FLANGE_MM = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_REAR_FLANGE_MM = new BigDecimal(1);
    private static final BigDecimal UPDATED_REAR_FLANGE_MM = new BigDecimal(2);
    private static final BigDecimal SMALLER_REAR_FLANGE_MM = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IEC_AXIS_HEIGHT_MM = new BigDecimal(1);
    private static final BigDecimal UPDATED_IEC_AXIS_HEIGHT_MM = new BigDecimal(2);
    private static final BigDecimal SMALLER_IEC_AXIS_HEIGHT_MM = new BigDecimal(1 - 1);

    private static final String DEFAULT_DIMENSIONS_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSIONS_SOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_HEATING = false;
    private static final Boolean UPDATED_HAS_HEATING = true;

    private static final TemperatureProbeType DEFAULT_TEMPERATURE_PROBE_TYPE = TemperatureProbeType.NONE;
    private static final TemperatureProbeType UPDATED_TEMPERATURE_PROBE_TYPE = TemperatureProbeType.PTC;

    private static final LocalDate DEFAULT_LAST_COMMISSIONING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_COMMISSIONING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_COMMISSIONING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_LAST_MAINTENANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MAINTENANCE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_MAINTENANCE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_MAINTENANCE_COUNT = 1;
    private static final Integer UPDATED_MAINTENANCE_COUNT = 2;
    private static final Integer SMALLER_MAINTENANCE_COUNT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetRepository assetRepository;

    @Mock
    private AssetRepository assetRepositoryMock;

    @Autowired
    private AssetMapper assetMapper;

    @Mock
    private AssetService assetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetMockMvc;

    private Asset asset;

    private Asset insertedAsset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .assetType(DEFAULT_ASSET_TYPE)
            .assetCode(DEFAULT_ASSET_CODE)
            .reference(DEFAULT_REFERENCE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .criticality(DEFAULT_CRITICALITY)
            .geofencePolicy(DEFAULT_GEOFENCE_POLICY)
            .responsibleName(DEFAULT_RESPONSIBLE_NAME)
            .costCenter(DEFAULT_COST_CENTER)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .powerKw(DEFAULT_POWER_KW)
            .voltageV(DEFAULT_VOLTAGE_V)
            .currentA(DEFAULT_CURRENT_A)
            .cosPhi(DEFAULT_COS_PHI)
            .speedRpm(DEFAULT_SPEED_RPM)
            .ipRating(DEFAULT_IP_RATING)
            .insulationClass(DEFAULT_INSULATION_CLASS)
            .mountingType(DEFAULT_MOUNTING_TYPE)
            .shaftDiameterMm(DEFAULT_SHAFT_DIAMETER_MM)
            .footDistanceAmm(DEFAULT_FOOT_DISTANCE_AMM)
            .footDistanceBmm(DEFAULT_FOOT_DISTANCE_BMM)
            .frontFlangeMm(DEFAULT_FRONT_FLANGE_MM)
            .rearFlangeMm(DEFAULT_REAR_FLANGE_MM)
            .iecAxisHeightMm(DEFAULT_IEC_AXIS_HEIGHT_MM)
            .dimensionsSource(DEFAULT_DIMENSIONS_SOURCE)
            .hasHeating(DEFAULT_HAS_HEATING)
            .temperatureProbeType(DEFAULT_TEMPERATURE_PROBE_TYPE)
            .lastCommissioningDate(DEFAULT_LAST_COMMISSIONING_DATE)
            .lastMaintenanceDate(DEFAULT_LAST_MAINTENANCE_DATE)
            .maintenanceCount(DEFAULT_MAINTENANCE_COUNT);
        // Add required entity
        ProductionLine productionLine;
        if (TestUtil.findAll(em, ProductionLine.class).isEmpty()) {
            productionLine = ProductionLineResourceIT.createEntity();
            em.persist(productionLine);
            em.flush();
        } else {
            productionLine = TestUtil.findAll(em, ProductionLine.class).get(0);
        }
        asset.setProductionLine(productionLine);
        return asset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createUpdatedEntity(EntityManager em) {
        Asset updatedAsset = new Asset()
            .assetType(UPDATED_ASSET_TYPE)
            .assetCode(UPDATED_ASSET_CODE)
            .reference(UPDATED_REFERENCE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .criticality(UPDATED_CRITICALITY)
            .geofencePolicy(UPDATED_GEOFENCE_POLICY)
            .responsibleName(UPDATED_RESPONSIBLE_NAME)
            .costCenter(UPDATED_COST_CENTER)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .powerKw(UPDATED_POWER_KW)
            .voltageV(UPDATED_VOLTAGE_V)
            .currentA(UPDATED_CURRENT_A)
            .cosPhi(UPDATED_COS_PHI)
            .speedRpm(UPDATED_SPEED_RPM)
            .ipRating(UPDATED_IP_RATING)
            .insulationClass(UPDATED_INSULATION_CLASS)
            .mountingType(UPDATED_MOUNTING_TYPE)
            .shaftDiameterMm(UPDATED_SHAFT_DIAMETER_MM)
            .footDistanceAmm(UPDATED_FOOT_DISTANCE_AMM)
            .footDistanceBmm(UPDATED_FOOT_DISTANCE_BMM)
            .frontFlangeMm(UPDATED_FRONT_FLANGE_MM)
            .rearFlangeMm(UPDATED_REAR_FLANGE_MM)
            .iecAxisHeightMm(UPDATED_IEC_AXIS_HEIGHT_MM)
            .dimensionsSource(UPDATED_DIMENSIONS_SOURCE)
            .hasHeating(UPDATED_HAS_HEATING)
            .temperatureProbeType(UPDATED_TEMPERATURE_PROBE_TYPE)
            .lastCommissioningDate(UPDATED_LAST_COMMISSIONING_DATE)
            .lastMaintenanceDate(UPDATED_LAST_MAINTENANCE_DATE)
            .maintenanceCount(UPDATED_MAINTENANCE_COUNT);
        // Add required entity
        ProductionLine productionLine;
        if (TestUtil.findAll(em, ProductionLine.class).isEmpty()) {
            productionLine = ProductionLineResourceIT.createUpdatedEntity();
            em.persist(productionLine);
            em.flush();
        } else {
            productionLine = TestUtil.findAll(em, ProductionLine.class).get(0);
        }
        updatedAsset.setProductionLine(productionLine);
        return updatedAsset;
    }

    @BeforeEach
    void initTest() {
        asset = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAsset != null) {
            assetRepository.delete(insertedAsset);
            insertedAsset = null;
        }
    }

    @Test
    @Transactional
    void createAsset() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);
        var returnedAssetDTO = om.readValue(
            restAssetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetDTO.class
        );

        // Validate the Asset in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAsset = assetMapper.toEntity(returnedAssetDTO);
        assertAssetUpdatableFieldsEquals(returnedAsset, getPersistedAsset(returnedAsset));

        insertedAsset = returnedAsset;
    }

    @Test
    @Transactional
    void createAssetWithExistingId() throws Exception {
        // Create the Asset with an existing ID
        asset.setId(1L);
        AssetDTO assetDTO = assetMapper.toDto(asset);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssetTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setAssetType(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssetCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setAssetCode(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setStatus(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCriticalityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setCriticality(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGeofencePolicyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setGeofencePolicy(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssets() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].criticality").value(hasItem(DEFAULT_CRITICALITY.toString())))
            .andExpect(jsonPath("$.[*].geofencePolicy").value(hasItem(DEFAULT_GEOFENCE_POLICY.toString())))
            .andExpect(jsonPath("$.[*].responsibleName").value(hasItem(DEFAULT_RESPONSIBLE_NAME)))
            .andExpect(jsonPath("$.[*].costCenter").value(hasItem(DEFAULT_COST_CENTER)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].powerKw").value(hasItem(sameNumber(DEFAULT_POWER_KW))))
            .andExpect(jsonPath("$.[*].voltageV").value(hasItem(sameNumber(DEFAULT_VOLTAGE_V))))
            .andExpect(jsonPath("$.[*].currentA").value(hasItem(sameNumber(DEFAULT_CURRENT_A))))
            .andExpect(jsonPath("$.[*].cosPhi").value(hasItem(sameNumber(DEFAULT_COS_PHI))))
            .andExpect(jsonPath("$.[*].speedRpm").value(hasItem(DEFAULT_SPEED_RPM)))
            .andExpect(jsonPath("$.[*].ipRating").value(hasItem(DEFAULT_IP_RATING)))
            .andExpect(jsonPath("$.[*].insulationClass").value(hasItem(DEFAULT_INSULATION_CLASS)))
            .andExpect(jsonPath("$.[*].mountingType").value(hasItem(DEFAULT_MOUNTING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shaftDiameterMm").value(hasItem(sameNumber(DEFAULT_SHAFT_DIAMETER_MM))))
            .andExpect(jsonPath("$.[*].footDistanceAmm").value(hasItem(sameNumber(DEFAULT_FOOT_DISTANCE_AMM))))
            .andExpect(jsonPath("$.[*].footDistanceBmm").value(hasItem(sameNumber(DEFAULT_FOOT_DISTANCE_BMM))))
            .andExpect(jsonPath("$.[*].frontFlangeMm").value(hasItem(sameNumber(DEFAULT_FRONT_FLANGE_MM))))
            .andExpect(jsonPath("$.[*].rearFlangeMm").value(hasItem(sameNumber(DEFAULT_REAR_FLANGE_MM))))
            .andExpect(jsonPath("$.[*].iecAxisHeightMm").value(hasItem(sameNumber(DEFAULT_IEC_AXIS_HEIGHT_MM))))
            .andExpect(jsonPath("$.[*].dimensionsSource").value(hasItem(DEFAULT_DIMENSIONS_SOURCE)))
            .andExpect(jsonPath("$.[*].hasHeating").value(hasItem(DEFAULT_HAS_HEATING)))
            .andExpect(jsonPath("$.[*].temperatureProbeType").value(hasItem(DEFAULT_TEMPERATURE_PROBE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastCommissioningDate").value(hasItem(DEFAULT_LAST_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastMaintenanceDate").value(hasItem(DEFAULT_LAST_MAINTENANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].maintenanceCount").value(hasItem(DEFAULT_MAINTENANCE_COUNT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(assetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc
            .perform(get(ENTITY_API_URL_ID, asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.assetCode").value(DEFAULT_ASSET_CODE))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.criticality").value(DEFAULT_CRITICALITY.toString()))
            .andExpect(jsonPath("$.geofencePolicy").value(DEFAULT_GEOFENCE_POLICY.toString()))
            .andExpect(jsonPath("$.responsibleName").value(DEFAULT_RESPONSIBLE_NAME))
            .andExpect(jsonPath("$.costCenter").value(DEFAULT_COST_CENTER))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.powerKw").value(sameNumber(DEFAULT_POWER_KW)))
            .andExpect(jsonPath("$.voltageV").value(sameNumber(DEFAULT_VOLTAGE_V)))
            .andExpect(jsonPath("$.currentA").value(sameNumber(DEFAULT_CURRENT_A)))
            .andExpect(jsonPath("$.cosPhi").value(sameNumber(DEFAULT_COS_PHI)))
            .andExpect(jsonPath("$.speedRpm").value(DEFAULT_SPEED_RPM))
            .andExpect(jsonPath("$.ipRating").value(DEFAULT_IP_RATING))
            .andExpect(jsonPath("$.insulationClass").value(DEFAULT_INSULATION_CLASS))
            .andExpect(jsonPath("$.mountingType").value(DEFAULT_MOUNTING_TYPE.toString()))
            .andExpect(jsonPath("$.shaftDiameterMm").value(sameNumber(DEFAULT_SHAFT_DIAMETER_MM)))
            .andExpect(jsonPath("$.footDistanceAmm").value(sameNumber(DEFAULT_FOOT_DISTANCE_AMM)))
            .andExpect(jsonPath("$.footDistanceBmm").value(sameNumber(DEFAULT_FOOT_DISTANCE_BMM)))
            .andExpect(jsonPath("$.frontFlangeMm").value(sameNumber(DEFAULT_FRONT_FLANGE_MM)))
            .andExpect(jsonPath("$.rearFlangeMm").value(sameNumber(DEFAULT_REAR_FLANGE_MM)))
            .andExpect(jsonPath("$.iecAxisHeightMm").value(sameNumber(DEFAULT_IEC_AXIS_HEIGHT_MM)))
            .andExpect(jsonPath("$.dimensionsSource").value(DEFAULT_DIMENSIONS_SOURCE))
            .andExpect(jsonPath("$.hasHeating").value(DEFAULT_HAS_HEATING))
            .andExpect(jsonPath("$.temperatureProbeType").value(DEFAULT_TEMPERATURE_PROBE_TYPE.toString()))
            .andExpect(jsonPath("$.lastCommissioningDate").value(DEFAULT_LAST_COMMISSIONING_DATE.toString()))
            .andExpect(jsonPath("$.lastMaintenanceDate").value(DEFAULT_LAST_MAINTENANCE_DATE.toString()))
            .andExpect(jsonPath("$.maintenanceCount").value(DEFAULT_MAINTENANCE_COUNT));
    }

    @Test
    @Transactional
    void getAssetsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        Long id = asset.getId();

        defaultAssetFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAssetFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAssetFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetType equals to
        defaultAssetFiltering("assetType.equals=" + DEFAULT_ASSET_TYPE, "assetType.equals=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetType in
        defaultAssetFiltering("assetType.in=" + DEFAULT_ASSET_TYPE + "," + UPDATED_ASSET_TYPE, "assetType.in=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetType is not null
        defaultAssetFiltering("assetType.specified=true", "assetType.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByAssetCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetCode equals to
        defaultAssetFiltering("assetCode.equals=" + DEFAULT_ASSET_CODE, "assetCode.equals=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetCode in
        defaultAssetFiltering("assetCode.in=" + DEFAULT_ASSET_CODE + "," + UPDATED_ASSET_CODE, "assetCode.in=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetCode is not null
        defaultAssetFiltering("assetCode.specified=true", "assetCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByAssetCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetCode contains
        defaultAssetFiltering("assetCode.contains=" + DEFAULT_ASSET_CODE, "assetCode.contains=" + UPDATED_ASSET_CODE);
    }

    @Test
    @Transactional
    void getAllAssetsByAssetCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where assetCode does not contain
        defaultAssetFiltering("assetCode.doesNotContain=" + UPDATED_ASSET_CODE, "assetCode.doesNotContain=" + DEFAULT_ASSET_CODE);
    }

    @Test
    @Transactional
    void getAllAssetsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where reference equals to
        defaultAssetFiltering("reference.equals=" + DEFAULT_REFERENCE, "reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllAssetsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where reference in
        defaultAssetFiltering("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE, "reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllAssetsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where reference is not null
        defaultAssetFiltering("reference.specified=true", "reference.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where reference contains
        defaultAssetFiltering("reference.contains=" + DEFAULT_REFERENCE, "reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllAssetsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where reference does not contain
        defaultAssetFiltering("reference.doesNotContain=" + UPDATED_REFERENCE, "reference.doesNotContain=" + DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllAssetsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where description equals to
        defaultAssetFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where description in
        defaultAssetFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where description is not null
        defaultAssetFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where description contains
        defaultAssetFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where description does not contain
        defaultAssetFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where status equals to
        defaultAssetFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where status in
        defaultAssetFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where status is not null
        defaultAssetFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByCriticalityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where criticality equals to
        defaultAssetFiltering("criticality.equals=" + DEFAULT_CRITICALITY, "criticality.equals=" + UPDATED_CRITICALITY);
    }

    @Test
    @Transactional
    void getAllAssetsByCriticalityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where criticality in
        defaultAssetFiltering("criticality.in=" + DEFAULT_CRITICALITY + "," + UPDATED_CRITICALITY, "criticality.in=" + UPDATED_CRITICALITY);
    }

    @Test
    @Transactional
    void getAllAssetsByCriticalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where criticality is not null
        defaultAssetFiltering("criticality.specified=true", "criticality.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByGeofencePolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where geofencePolicy equals to
        defaultAssetFiltering("geofencePolicy.equals=" + DEFAULT_GEOFENCE_POLICY, "geofencePolicy.equals=" + UPDATED_GEOFENCE_POLICY);
    }

    @Test
    @Transactional
    void getAllAssetsByGeofencePolicyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where geofencePolicy in
        defaultAssetFiltering(
            "geofencePolicy.in=" + DEFAULT_GEOFENCE_POLICY + "," + UPDATED_GEOFENCE_POLICY,
            "geofencePolicy.in=" + UPDATED_GEOFENCE_POLICY
        );
    }

    @Test
    @Transactional
    void getAllAssetsByGeofencePolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where geofencePolicy is not null
        defaultAssetFiltering("geofencePolicy.specified=true", "geofencePolicy.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByResponsibleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where responsibleName equals to
        defaultAssetFiltering("responsibleName.equals=" + DEFAULT_RESPONSIBLE_NAME, "responsibleName.equals=" + UPDATED_RESPONSIBLE_NAME);
    }

    @Test
    @Transactional
    void getAllAssetsByResponsibleNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where responsibleName in
        defaultAssetFiltering(
            "responsibleName.in=" + DEFAULT_RESPONSIBLE_NAME + "," + UPDATED_RESPONSIBLE_NAME,
            "responsibleName.in=" + UPDATED_RESPONSIBLE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAssetsByResponsibleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where responsibleName is not null
        defaultAssetFiltering("responsibleName.specified=true", "responsibleName.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByResponsibleNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where responsibleName contains
        defaultAssetFiltering(
            "responsibleName.contains=" + DEFAULT_RESPONSIBLE_NAME,
            "responsibleName.contains=" + UPDATED_RESPONSIBLE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAssetsByResponsibleNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where responsibleName does not contain
        defaultAssetFiltering(
            "responsibleName.doesNotContain=" + UPDATED_RESPONSIBLE_NAME,
            "responsibleName.doesNotContain=" + DEFAULT_RESPONSIBLE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAssetsByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where costCenter equals to
        defaultAssetFiltering("costCenter.equals=" + DEFAULT_COST_CENTER, "costCenter.equals=" + UPDATED_COST_CENTER);
    }

    @Test
    @Transactional
    void getAllAssetsByCostCenterIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where costCenter in
        defaultAssetFiltering("costCenter.in=" + DEFAULT_COST_CENTER + "," + UPDATED_COST_CENTER, "costCenter.in=" + UPDATED_COST_CENTER);
    }

    @Test
    @Transactional
    void getAllAssetsByCostCenterIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where costCenter is not null
        defaultAssetFiltering("costCenter.specified=true", "costCenter.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByCostCenterContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where costCenter contains
        defaultAssetFiltering("costCenter.contains=" + DEFAULT_COST_CENTER, "costCenter.contains=" + UPDATED_COST_CENTER);
    }

    @Test
    @Transactional
    void getAllAssetsByCostCenterNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where costCenter does not contain
        defaultAssetFiltering("costCenter.doesNotContain=" + UPDATED_COST_CENTER, "costCenter.doesNotContain=" + DEFAULT_COST_CENTER);
    }

    @Test
    @Transactional
    void getAllAssetsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where brand equals to
        defaultAssetFiltering("brand.equals=" + DEFAULT_BRAND, "brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllAssetsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where brand in
        defaultAssetFiltering("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND, "brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllAssetsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where brand is not null
        defaultAssetFiltering("brand.specified=true", "brand.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByBrandContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where brand contains
        defaultAssetFiltering("brand.contains=" + DEFAULT_BRAND, "brand.contains=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllAssetsByBrandNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where brand does not contain
        defaultAssetFiltering("brand.doesNotContain=" + UPDATED_BRAND, "brand.doesNotContain=" + DEFAULT_BRAND);
    }

    @Test
    @Transactional
    void getAllAssetsByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where model equals to
        defaultAssetFiltering("model.equals=" + DEFAULT_MODEL, "model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetsByModelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where model in
        defaultAssetFiltering("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL, "model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetsByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where model is not null
        defaultAssetFiltering("model.specified=true", "model.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByModelContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where model contains
        defaultAssetFiltering("model.contains=" + DEFAULT_MODEL, "model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetsByModelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where model does not contain
        defaultAssetFiltering("model.doesNotContain=" + UPDATED_MODEL, "model.doesNotContain=" + DEFAULT_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where serialNumber equals to
        defaultAssetFiltering("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER, "serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where serialNumber in
        defaultAssetFiltering(
            "serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER,
            "serialNumber.in=" + UPDATED_SERIAL_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAssetsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where serialNumber is not null
        defaultAssetFiltering("serialNumber.specified=true", "serialNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where serialNumber contains
        defaultAssetFiltering("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER, "serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetsBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where serialNumber does not contain
        defaultAssetFiltering(
            "serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER,
            "serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw equals to
        defaultAssetFiltering("powerKw.equals=" + DEFAULT_POWER_KW, "powerKw.equals=" + UPDATED_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw in
        defaultAssetFiltering("powerKw.in=" + DEFAULT_POWER_KW + "," + UPDATED_POWER_KW, "powerKw.in=" + UPDATED_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw is not null
        defaultAssetFiltering("powerKw.specified=true", "powerKw.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw is greater than or equal to
        defaultAssetFiltering("powerKw.greaterThanOrEqual=" + DEFAULT_POWER_KW, "powerKw.greaterThanOrEqual=" + UPDATED_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw is less than or equal to
        defaultAssetFiltering("powerKw.lessThanOrEqual=" + DEFAULT_POWER_KW, "powerKw.lessThanOrEqual=" + SMALLER_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw is less than
        defaultAssetFiltering("powerKw.lessThan=" + UPDATED_POWER_KW, "powerKw.lessThan=" + DEFAULT_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByPowerKwIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where powerKw is greater than
        defaultAssetFiltering("powerKw.greaterThan=" + SMALLER_POWER_KW, "powerKw.greaterThan=" + DEFAULT_POWER_KW);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV equals to
        defaultAssetFiltering("voltageV.equals=" + DEFAULT_VOLTAGE_V, "voltageV.equals=" + UPDATED_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV in
        defaultAssetFiltering("voltageV.in=" + DEFAULT_VOLTAGE_V + "," + UPDATED_VOLTAGE_V, "voltageV.in=" + UPDATED_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV is not null
        defaultAssetFiltering("voltageV.specified=true", "voltageV.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV is greater than or equal to
        defaultAssetFiltering("voltageV.greaterThanOrEqual=" + DEFAULT_VOLTAGE_V, "voltageV.greaterThanOrEqual=" + UPDATED_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV is less than or equal to
        defaultAssetFiltering("voltageV.lessThanOrEqual=" + DEFAULT_VOLTAGE_V, "voltageV.lessThanOrEqual=" + SMALLER_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV is less than
        defaultAssetFiltering("voltageV.lessThan=" + UPDATED_VOLTAGE_V, "voltageV.lessThan=" + DEFAULT_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByVoltageVIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where voltageV is greater than
        defaultAssetFiltering("voltageV.greaterThan=" + SMALLER_VOLTAGE_V, "voltageV.greaterThan=" + DEFAULT_VOLTAGE_V);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA equals to
        defaultAssetFiltering("currentA.equals=" + DEFAULT_CURRENT_A, "currentA.equals=" + UPDATED_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA in
        defaultAssetFiltering("currentA.in=" + DEFAULT_CURRENT_A + "," + UPDATED_CURRENT_A, "currentA.in=" + UPDATED_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA is not null
        defaultAssetFiltering("currentA.specified=true", "currentA.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA is greater than or equal to
        defaultAssetFiltering("currentA.greaterThanOrEqual=" + DEFAULT_CURRENT_A, "currentA.greaterThanOrEqual=" + UPDATED_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA is less than or equal to
        defaultAssetFiltering("currentA.lessThanOrEqual=" + DEFAULT_CURRENT_A, "currentA.lessThanOrEqual=" + SMALLER_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA is less than
        defaultAssetFiltering("currentA.lessThan=" + UPDATED_CURRENT_A, "currentA.lessThan=" + DEFAULT_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCurrentAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where currentA is greater than
        defaultAssetFiltering("currentA.greaterThan=" + SMALLER_CURRENT_A, "currentA.greaterThan=" + DEFAULT_CURRENT_A);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi equals to
        defaultAssetFiltering("cosPhi.equals=" + DEFAULT_COS_PHI, "cosPhi.equals=" + UPDATED_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi in
        defaultAssetFiltering("cosPhi.in=" + DEFAULT_COS_PHI + "," + UPDATED_COS_PHI, "cosPhi.in=" + UPDATED_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi is not null
        defaultAssetFiltering("cosPhi.specified=true", "cosPhi.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi is greater than or equal to
        defaultAssetFiltering("cosPhi.greaterThanOrEqual=" + DEFAULT_COS_PHI, "cosPhi.greaterThanOrEqual=" + UPDATED_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi is less than or equal to
        defaultAssetFiltering("cosPhi.lessThanOrEqual=" + DEFAULT_COS_PHI, "cosPhi.lessThanOrEqual=" + SMALLER_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi is less than
        defaultAssetFiltering("cosPhi.lessThan=" + UPDATED_COS_PHI, "cosPhi.lessThan=" + DEFAULT_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsByCosPhiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where cosPhi is greater than
        defaultAssetFiltering("cosPhi.greaterThan=" + SMALLER_COS_PHI, "cosPhi.greaterThan=" + DEFAULT_COS_PHI);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm equals to
        defaultAssetFiltering("speedRpm.equals=" + DEFAULT_SPEED_RPM, "speedRpm.equals=" + UPDATED_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm in
        defaultAssetFiltering("speedRpm.in=" + DEFAULT_SPEED_RPM + "," + UPDATED_SPEED_RPM, "speedRpm.in=" + UPDATED_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm is not null
        defaultAssetFiltering("speedRpm.specified=true", "speedRpm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm is greater than or equal to
        defaultAssetFiltering("speedRpm.greaterThanOrEqual=" + DEFAULT_SPEED_RPM, "speedRpm.greaterThanOrEqual=" + UPDATED_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm is less than or equal to
        defaultAssetFiltering("speedRpm.lessThanOrEqual=" + DEFAULT_SPEED_RPM, "speedRpm.lessThanOrEqual=" + SMALLER_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm is less than
        defaultAssetFiltering("speedRpm.lessThan=" + UPDATED_SPEED_RPM, "speedRpm.lessThan=" + DEFAULT_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsBySpeedRpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where speedRpm is greater than
        defaultAssetFiltering("speedRpm.greaterThan=" + SMALLER_SPEED_RPM, "speedRpm.greaterThan=" + DEFAULT_SPEED_RPM);
    }

    @Test
    @Transactional
    void getAllAssetsByIpRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where ipRating equals to
        defaultAssetFiltering("ipRating.equals=" + DEFAULT_IP_RATING, "ipRating.equals=" + UPDATED_IP_RATING);
    }

    @Test
    @Transactional
    void getAllAssetsByIpRatingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where ipRating in
        defaultAssetFiltering("ipRating.in=" + DEFAULT_IP_RATING + "," + UPDATED_IP_RATING, "ipRating.in=" + UPDATED_IP_RATING);
    }

    @Test
    @Transactional
    void getAllAssetsByIpRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where ipRating is not null
        defaultAssetFiltering("ipRating.specified=true", "ipRating.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByIpRatingContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where ipRating contains
        defaultAssetFiltering("ipRating.contains=" + DEFAULT_IP_RATING, "ipRating.contains=" + UPDATED_IP_RATING);
    }

    @Test
    @Transactional
    void getAllAssetsByIpRatingNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where ipRating does not contain
        defaultAssetFiltering("ipRating.doesNotContain=" + UPDATED_IP_RATING, "ipRating.doesNotContain=" + DEFAULT_IP_RATING);
    }

    @Test
    @Transactional
    void getAllAssetsByInsulationClassIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where insulationClass equals to
        defaultAssetFiltering("insulationClass.equals=" + DEFAULT_INSULATION_CLASS, "insulationClass.equals=" + UPDATED_INSULATION_CLASS);
    }

    @Test
    @Transactional
    void getAllAssetsByInsulationClassIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where insulationClass in
        defaultAssetFiltering(
            "insulationClass.in=" + DEFAULT_INSULATION_CLASS + "," + UPDATED_INSULATION_CLASS,
            "insulationClass.in=" + UPDATED_INSULATION_CLASS
        );
    }

    @Test
    @Transactional
    void getAllAssetsByInsulationClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where insulationClass is not null
        defaultAssetFiltering("insulationClass.specified=true", "insulationClass.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByInsulationClassContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where insulationClass contains
        defaultAssetFiltering(
            "insulationClass.contains=" + DEFAULT_INSULATION_CLASS,
            "insulationClass.contains=" + UPDATED_INSULATION_CLASS
        );
    }

    @Test
    @Transactional
    void getAllAssetsByInsulationClassNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where insulationClass does not contain
        defaultAssetFiltering(
            "insulationClass.doesNotContain=" + UPDATED_INSULATION_CLASS,
            "insulationClass.doesNotContain=" + DEFAULT_INSULATION_CLASS
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMountingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where mountingType equals to
        defaultAssetFiltering("mountingType.equals=" + DEFAULT_MOUNTING_TYPE, "mountingType.equals=" + UPDATED_MOUNTING_TYPE);
    }

    @Test
    @Transactional
    void getAllAssetsByMountingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where mountingType in
        defaultAssetFiltering(
            "mountingType.in=" + DEFAULT_MOUNTING_TYPE + "," + UPDATED_MOUNTING_TYPE,
            "mountingType.in=" + UPDATED_MOUNTING_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMountingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where mountingType is not null
        defaultAssetFiltering("mountingType.specified=true", "mountingType.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm equals to
        defaultAssetFiltering("shaftDiameterMm.equals=" + DEFAULT_SHAFT_DIAMETER_MM, "shaftDiameterMm.equals=" + UPDATED_SHAFT_DIAMETER_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm in
        defaultAssetFiltering(
            "shaftDiameterMm.in=" + DEFAULT_SHAFT_DIAMETER_MM + "," + UPDATED_SHAFT_DIAMETER_MM,
            "shaftDiameterMm.in=" + UPDATED_SHAFT_DIAMETER_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm is not null
        defaultAssetFiltering("shaftDiameterMm.specified=true", "shaftDiameterMm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm is greater than or equal to
        defaultAssetFiltering(
            "shaftDiameterMm.greaterThanOrEqual=" + DEFAULT_SHAFT_DIAMETER_MM,
            "shaftDiameterMm.greaterThanOrEqual=" + UPDATED_SHAFT_DIAMETER_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm is less than or equal to
        defaultAssetFiltering(
            "shaftDiameterMm.lessThanOrEqual=" + DEFAULT_SHAFT_DIAMETER_MM,
            "shaftDiameterMm.lessThanOrEqual=" + SMALLER_SHAFT_DIAMETER_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm is less than
        defaultAssetFiltering(
            "shaftDiameterMm.lessThan=" + UPDATED_SHAFT_DIAMETER_MM,
            "shaftDiameterMm.lessThan=" + DEFAULT_SHAFT_DIAMETER_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByShaftDiameterMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where shaftDiameterMm is greater than
        defaultAssetFiltering(
            "shaftDiameterMm.greaterThan=" + SMALLER_SHAFT_DIAMETER_MM,
            "shaftDiameterMm.greaterThan=" + DEFAULT_SHAFT_DIAMETER_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm equals to
        defaultAssetFiltering("footDistanceAmm.equals=" + DEFAULT_FOOT_DISTANCE_AMM, "footDistanceAmm.equals=" + UPDATED_FOOT_DISTANCE_AMM);
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm in
        defaultAssetFiltering(
            "footDistanceAmm.in=" + DEFAULT_FOOT_DISTANCE_AMM + "," + UPDATED_FOOT_DISTANCE_AMM,
            "footDistanceAmm.in=" + UPDATED_FOOT_DISTANCE_AMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm is not null
        defaultAssetFiltering("footDistanceAmm.specified=true", "footDistanceAmm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm is greater than or equal to
        defaultAssetFiltering(
            "footDistanceAmm.greaterThanOrEqual=" + DEFAULT_FOOT_DISTANCE_AMM,
            "footDistanceAmm.greaterThanOrEqual=" + UPDATED_FOOT_DISTANCE_AMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm is less than or equal to
        defaultAssetFiltering(
            "footDistanceAmm.lessThanOrEqual=" + DEFAULT_FOOT_DISTANCE_AMM,
            "footDistanceAmm.lessThanOrEqual=" + SMALLER_FOOT_DISTANCE_AMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm is less than
        defaultAssetFiltering(
            "footDistanceAmm.lessThan=" + UPDATED_FOOT_DISTANCE_AMM,
            "footDistanceAmm.lessThan=" + DEFAULT_FOOT_DISTANCE_AMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceAmmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceAmm is greater than
        defaultAssetFiltering(
            "footDistanceAmm.greaterThan=" + SMALLER_FOOT_DISTANCE_AMM,
            "footDistanceAmm.greaterThan=" + DEFAULT_FOOT_DISTANCE_AMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm equals to
        defaultAssetFiltering("footDistanceBmm.equals=" + DEFAULT_FOOT_DISTANCE_BMM, "footDistanceBmm.equals=" + UPDATED_FOOT_DISTANCE_BMM);
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm in
        defaultAssetFiltering(
            "footDistanceBmm.in=" + DEFAULT_FOOT_DISTANCE_BMM + "," + UPDATED_FOOT_DISTANCE_BMM,
            "footDistanceBmm.in=" + UPDATED_FOOT_DISTANCE_BMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm is not null
        defaultAssetFiltering("footDistanceBmm.specified=true", "footDistanceBmm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm is greater than or equal to
        defaultAssetFiltering(
            "footDistanceBmm.greaterThanOrEqual=" + DEFAULT_FOOT_DISTANCE_BMM,
            "footDistanceBmm.greaterThanOrEqual=" + UPDATED_FOOT_DISTANCE_BMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm is less than or equal to
        defaultAssetFiltering(
            "footDistanceBmm.lessThanOrEqual=" + DEFAULT_FOOT_DISTANCE_BMM,
            "footDistanceBmm.lessThanOrEqual=" + SMALLER_FOOT_DISTANCE_BMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm is less than
        defaultAssetFiltering(
            "footDistanceBmm.lessThan=" + UPDATED_FOOT_DISTANCE_BMM,
            "footDistanceBmm.lessThan=" + DEFAULT_FOOT_DISTANCE_BMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFootDistanceBmmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where footDistanceBmm is greater than
        defaultAssetFiltering(
            "footDistanceBmm.greaterThan=" + SMALLER_FOOT_DISTANCE_BMM,
            "footDistanceBmm.greaterThan=" + DEFAULT_FOOT_DISTANCE_BMM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm equals to
        defaultAssetFiltering("frontFlangeMm.equals=" + DEFAULT_FRONT_FLANGE_MM, "frontFlangeMm.equals=" + UPDATED_FRONT_FLANGE_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm in
        defaultAssetFiltering(
            "frontFlangeMm.in=" + DEFAULT_FRONT_FLANGE_MM + "," + UPDATED_FRONT_FLANGE_MM,
            "frontFlangeMm.in=" + UPDATED_FRONT_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm is not null
        defaultAssetFiltering("frontFlangeMm.specified=true", "frontFlangeMm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm is greater than or equal to
        defaultAssetFiltering(
            "frontFlangeMm.greaterThanOrEqual=" + DEFAULT_FRONT_FLANGE_MM,
            "frontFlangeMm.greaterThanOrEqual=" + UPDATED_FRONT_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm is less than or equal to
        defaultAssetFiltering(
            "frontFlangeMm.lessThanOrEqual=" + DEFAULT_FRONT_FLANGE_MM,
            "frontFlangeMm.lessThanOrEqual=" + SMALLER_FRONT_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm is less than
        defaultAssetFiltering("frontFlangeMm.lessThan=" + UPDATED_FRONT_FLANGE_MM, "frontFlangeMm.lessThan=" + DEFAULT_FRONT_FLANGE_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByFrontFlangeMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where frontFlangeMm is greater than
        defaultAssetFiltering(
            "frontFlangeMm.greaterThan=" + SMALLER_FRONT_FLANGE_MM,
            "frontFlangeMm.greaterThan=" + DEFAULT_FRONT_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm equals to
        defaultAssetFiltering("rearFlangeMm.equals=" + DEFAULT_REAR_FLANGE_MM, "rearFlangeMm.equals=" + UPDATED_REAR_FLANGE_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm in
        defaultAssetFiltering(
            "rearFlangeMm.in=" + DEFAULT_REAR_FLANGE_MM + "," + UPDATED_REAR_FLANGE_MM,
            "rearFlangeMm.in=" + UPDATED_REAR_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm is not null
        defaultAssetFiltering("rearFlangeMm.specified=true", "rearFlangeMm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm is greater than or equal to
        defaultAssetFiltering(
            "rearFlangeMm.greaterThanOrEqual=" + DEFAULT_REAR_FLANGE_MM,
            "rearFlangeMm.greaterThanOrEqual=" + UPDATED_REAR_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm is less than or equal to
        defaultAssetFiltering(
            "rearFlangeMm.lessThanOrEqual=" + DEFAULT_REAR_FLANGE_MM,
            "rearFlangeMm.lessThanOrEqual=" + SMALLER_REAR_FLANGE_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm is less than
        defaultAssetFiltering("rearFlangeMm.lessThan=" + UPDATED_REAR_FLANGE_MM, "rearFlangeMm.lessThan=" + DEFAULT_REAR_FLANGE_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByRearFlangeMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where rearFlangeMm is greater than
        defaultAssetFiltering("rearFlangeMm.greaterThan=" + SMALLER_REAR_FLANGE_MM, "rearFlangeMm.greaterThan=" + DEFAULT_REAR_FLANGE_MM);
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm equals to
        defaultAssetFiltering(
            "iecAxisHeightMm.equals=" + DEFAULT_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.equals=" + UPDATED_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm in
        defaultAssetFiltering(
            "iecAxisHeightMm.in=" + DEFAULT_IEC_AXIS_HEIGHT_MM + "," + UPDATED_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.in=" + UPDATED_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm is not null
        defaultAssetFiltering("iecAxisHeightMm.specified=true", "iecAxisHeightMm.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm is greater than or equal to
        defaultAssetFiltering(
            "iecAxisHeightMm.greaterThanOrEqual=" + DEFAULT_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.greaterThanOrEqual=" + UPDATED_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm is less than or equal to
        defaultAssetFiltering(
            "iecAxisHeightMm.lessThanOrEqual=" + DEFAULT_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.lessThanOrEqual=" + SMALLER_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm is less than
        defaultAssetFiltering(
            "iecAxisHeightMm.lessThan=" + UPDATED_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.lessThan=" + DEFAULT_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByIecAxisHeightMmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where iecAxisHeightMm is greater than
        defaultAssetFiltering(
            "iecAxisHeightMm.greaterThan=" + SMALLER_IEC_AXIS_HEIGHT_MM,
            "iecAxisHeightMm.greaterThan=" + DEFAULT_IEC_AXIS_HEIGHT_MM
        );
    }

    @Test
    @Transactional
    void getAllAssetsByDimensionsSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where dimensionsSource equals to
        defaultAssetFiltering(
            "dimensionsSource.equals=" + DEFAULT_DIMENSIONS_SOURCE,
            "dimensionsSource.equals=" + UPDATED_DIMENSIONS_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByDimensionsSourceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where dimensionsSource in
        defaultAssetFiltering(
            "dimensionsSource.in=" + DEFAULT_DIMENSIONS_SOURCE + "," + UPDATED_DIMENSIONS_SOURCE,
            "dimensionsSource.in=" + UPDATED_DIMENSIONS_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByDimensionsSourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where dimensionsSource is not null
        defaultAssetFiltering("dimensionsSource.specified=true", "dimensionsSource.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByDimensionsSourceContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where dimensionsSource contains
        defaultAssetFiltering(
            "dimensionsSource.contains=" + DEFAULT_DIMENSIONS_SOURCE,
            "dimensionsSource.contains=" + UPDATED_DIMENSIONS_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByDimensionsSourceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where dimensionsSource does not contain
        defaultAssetFiltering(
            "dimensionsSource.doesNotContain=" + UPDATED_DIMENSIONS_SOURCE,
            "dimensionsSource.doesNotContain=" + DEFAULT_DIMENSIONS_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByHasHeatingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where hasHeating equals to
        defaultAssetFiltering("hasHeating.equals=" + DEFAULT_HAS_HEATING, "hasHeating.equals=" + UPDATED_HAS_HEATING);
    }

    @Test
    @Transactional
    void getAllAssetsByHasHeatingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where hasHeating in
        defaultAssetFiltering("hasHeating.in=" + DEFAULT_HAS_HEATING + "," + UPDATED_HAS_HEATING, "hasHeating.in=" + UPDATED_HAS_HEATING);
    }

    @Test
    @Transactional
    void getAllAssetsByHasHeatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where hasHeating is not null
        defaultAssetFiltering("hasHeating.specified=true", "hasHeating.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByTemperatureProbeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where temperatureProbeType equals to
        defaultAssetFiltering(
            "temperatureProbeType.equals=" + DEFAULT_TEMPERATURE_PROBE_TYPE,
            "temperatureProbeType.equals=" + UPDATED_TEMPERATURE_PROBE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByTemperatureProbeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where temperatureProbeType in
        defaultAssetFiltering(
            "temperatureProbeType.in=" + DEFAULT_TEMPERATURE_PROBE_TYPE + "," + UPDATED_TEMPERATURE_PROBE_TYPE,
            "temperatureProbeType.in=" + UPDATED_TEMPERATURE_PROBE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByTemperatureProbeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where temperatureProbeType is not null
        defaultAssetFiltering("temperatureProbeType.specified=true", "temperatureProbeType.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate equals to
        defaultAssetFiltering(
            "lastCommissioningDate.equals=" + DEFAULT_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.equals=" + UPDATED_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate in
        defaultAssetFiltering(
            "lastCommissioningDate.in=" + DEFAULT_LAST_COMMISSIONING_DATE + "," + UPDATED_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.in=" + UPDATED_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate is not null
        defaultAssetFiltering("lastCommissioningDate.specified=true", "lastCommissioningDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate is greater than or equal to
        defaultAssetFiltering(
            "lastCommissioningDate.greaterThanOrEqual=" + DEFAULT_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.greaterThanOrEqual=" + UPDATED_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate is less than or equal to
        defaultAssetFiltering(
            "lastCommissioningDate.lessThanOrEqual=" + DEFAULT_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.lessThanOrEqual=" + SMALLER_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate is less than
        defaultAssetFiltering(
            "lastCommissioningDate.lessThan=" + UPDATED_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.lessThan=" + DEFAULT_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastCommissioningDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastCommissioningDate is greater than
        defaultAssetFiltering(
            "lastCommissioningDate.greaterThan=" + SMALLER_LAST_COMMISSIONING_DATE,
            "lastCommissioningDate.greaterThan=" + DEFAULT_LAST_COMMISSIONING_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate equals to
        defaultAssetFiltering(
            "lastMaintenanceDate.equals=" + DEFAULT_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.equals=" + UPDATED_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate in
        defaultAssetFiltering(
            "lastMaintenanceDate.in=" + DEFAULT_LAST_MAINTENANCE_DATE + "," + UPDATED_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.in=" + UPDATED_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate is not null
        defaultAssetFiltering("lastMaintenanceDate.specified=true", "lastMaintenanceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate is greater than or equal to
        defaultAssetFiltering(
            "lastMaintenanceDate.greaterThanOrEqual=" + DEFAULT_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.greaterThanOrEqual=" + UPDATED_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate is less than or equal to
        defaultAssetFiltering(
            "lastMaintenanceDate.lessThanOrEqual=" + DEFAULT_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.lessThanOrEqual=" + SMALLER_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate is less than
        defaultAssetFiltering(
            "lastMaintenanceDate.lessThan=" + UPDATED_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.lessThan=" + DEFAULT_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByLastMaintenanceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where lastMaintenanceDate is greater than
        defaultAssetFiltering(
            "lastMaintenanceDate.greaterThan=" + SMALLER_LAST_MAINTENANCE_DATE,
            "lastMaintenanceDate.greaterThan=" + DEFAULT_LAST_MAINTENANCE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount equals to
        defaultAssetFiltering(
            "maintenanceCount.equals=" + DEFAULT_MAINTENANCE_COUNT,
            "maintenanceCount.equals=" + UPDATED_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount in
        defaultAssetFiltering(
            "maintenanceCount.in=" + DEFAULT_MAINTENANCE_COUNT + "," + UPDATED_MAINTENANCE_COUNT,
            "maintenanceCount.in=" + UPDATED_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount is not null
        defaultAssetFiltering("maintenanceCount.specified=true", "maintenanceCount.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount is greater than or equal to
        defaultAssetFiltering(
            "maintenanceCount.greaterThanOrEqual=" + DEFAULT_MAINTENANCE_COUNT,
            "maintenanceCount.greaterThanOrEqual=" + UPDATED_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount is less than or equal to
        defaultAssetFiltering(
            "maintenanceCount.lessThanOrEqual=" + DEFAULT_MAINTENANCE_COUNT,
            "maintenanceCount.lessThanOrEqual=" + SMALLER_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount is less than
        defaultAssetFiltering(
            "maintenanceCount.lessThan=" + UPDATED_MAINTENANCE_COUNT,
            "maintenanceCount.lessThan=" + DEFAULT_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByMaintenanceCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList where maintenanceCount is greater than
        defaultAssetFiltering(
            "maintenanceCount.greaterThan=" + SMALLER_MAINTENANCE_COUNT,
            "maintenanceCount.greaterThan=" + DEFAULT_MAINTENANCE_COUNT
        );
    }

    @Test
    @Transactional
    void getAllAssetsByProductionLineIsEqualToSomething() throws Exception {
        ProductionLine productionLine;
        if (TestUtil.findAll(em, ProductionLine.class).isEmpty()) {
            assetRepository.saveAndFlush(asset);
            productionLine = ProductionLineResourceIT.createEntity();
        } else {
            productionLine = TestUtil.findAll(em, ProductionLine.class).get(0);
        }
        em.persist(productionLine);
        em.flush();
        asset.setProductionLine(productionLine);
        assetRepository.saveAndFlush(asset);
        Long productionLineId = productionLine.getId();
        // Get all the assetList where productionLine equals to productionLineId
        defaultAssetShouldBeFound("productionLineId.equals=" + productionLineId);

        // Get all the assetList where productionLine equals to (productionLineId + 1)
        defaultAssetShouldNotBeFound("productionLineId.equals=" + (productionLineId + 1));
    }

    @Test
    @Transactional
    void getAllAssetsByAllowedSiteIsEqualToSomething() throws Exception {
        Site allowedSite;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            assetRepository.saveAndFlush(asset);
            allowedSite = SiteResourceIT.createEntity();
        } else {
            allowedSite = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(allowedSite);
        em.flush();
        asset.setAllowedSite(allowedSite);
        assetRepository.saveAndFlush(asset);
        Long allowedSiteId = allowedSite.getId();
        // Get all the assetList where allowedSite equals to allowedSiteId
        defaultAssetShouldBeFound("allowedSiteId.equals=" + allowedSiteId);

        // Get all the assetList where allowedSite equals to (allowedSiteId + 1)
        defaultAssetShouldNotBeFound("allowedSiteId.equals=" + (allowedSiteId + 1));
    }

    @Test
    @Transactional
    void getAllAssetsByAllowedZoneIsEqualToSomething() throws Exception {
        Zone allowedZone;
        if (TestUtil.findAll(em, Zone.class).isEmpty()) {
            assetRepository.saveAndFlush(asset);
            allowedZone = ZoneResourceIT.createEntity();
        } else {
            allowedZone = TestUtil.findAll(em, Zone.class).get(0);
        }
        em.persist(allowedZone);
        em.flush();
        asset.setAllowedZone(allowedZone);
        assetRepository.saveAndFlush(asset);
        Long allowedZoneId = allowedZone.getId();
        // Get all the assetList where allowedZone equals to allowedZoneId
        defaultAssetShouldBeFound("allowedZoneId.equals=" + allowedZoneId);

        // Get all the assetList where allowedZone equals to (allowedZoneId + 1)
        defaultAssetShouldNotBeFound("allowedZoneId.equals=" + (allowedZoneId + 1));
    }

    private void defaultAssetFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAssetShouldBeFound(shouldBeFound);
        defaultAssetShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetShouldBeFound(String filter) throws Exception {
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assetCode").value(hasItem(DEFAULT_ASSET_CODE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].criticality").value(hasItem(DEFAULT_CRITICALITY.toString())))
            .andExpect(jsonPath("$.[*].geofencePolicy").value(hasItem(DEFAULT_GEOFENCE_POLICY.toString())))
            .andExpect(jsonPath("$.[*].responsibleName").value(hasItem(DEFAULT_RESPONSIBLE_NAME)))
            .andExpect(jsonPath("$.[*].costCenter").value(hasItem(DEFAULT_COST_CENTER)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].powerKw").value(hasItem(sameNumber(DEFAULT_POWER_KW))))
            .andExpect(jsonPath("$.[*].voltageV").value(hasItem(sameNumber(DEFAULT_VOLTAGE_V))))
            .andExpect(jsonPath("$.[*].currentA").value(hasItem(sameNumber(DEFAULT_CURRENT_A))))
            .andExpect(jsonPath("$.[*].cosPhi").value(hasItem(sameNumber(DEFAULT_COS_PHI))))
            .andExpect(jsonPath("$.[*].speedRpm").value(hasItem(DEFAULT_SPEED_RPM)))
            .andExpect(jsonPath("$.[*].ipRating").value(hasItem(DEFAULT_IP_RATING)))
            .andExpect(jsonPath("$.[*].insulationClass").value(hasItem(DEFAULT_INSULATION_CLASS)))
            .andExpect(jsonPath("$.[*].mountingType").value(hasItem(DEFAULT_MOUNTING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shaftDiameterMm").value(hasItem(sameNumber(DEFAULT_SHAFT_DIAMETER_MM))))
            .andExpect(jsonPath("$.[*].footDistanceAmm").value(hasItem(sameNumber(DEFAULT_FOOT_DISTANCE_AMM))))
            .andExpect(jsonPath("$.[*].footDistanceBmm").value(hasItem(sameNumber(DEFAULT_FOOT_DISTANCE_BMM))))
            .andExpect(jsonPath("$.[*].frontFlangeMm").value(hasItem(sameNumber(DEFAULT_FRONT_FLANGE_MM))))
            .andExpect(jsonPath("$.[*].rearFlangeMm").value(hasItem(sameNumber(DEFAULT_REAR_FLANGE_MM))))
            .andExpect(jsonPath("$.[*].iecAxisHeightMm").value(hasItem(sameNumber(DEFAULT_IEC_AXIS_HEIGHT_MM))))
            .andExpect(jsonPath("$.[*].dimensionsSource").value(hasItem(DEFAULT_DIMENSIONS_SOURCE)))
            .andExpect(jsonPath("$.[*].hasHeating").value(hasItem(DEFAULT_HAS_HEATING)))
            .andExpect(jsonPath("$.[*].temperatureProbeType").value(hasItem(DEFAULT_TEMPERATURE_PROBE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastCommissioningDate").value(hasItem(DEFAULT_LAST_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastMaintenanceDate").value(hasItem(DEFAULT_LAST_MAINTENANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].maintenanceCount").value(hasItem(DEFAULT_MAINTENANCE_COUNT)));

        // Check, that the count call also returns 1
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetShouldNotBeFound(String filter) throws Exception {
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset
        Asset updatedAsset = assetRepository.findById(asset.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsset are not directly saved in db
        em.detach(updatedAsset);
        updatedAsset
            .assetType(UPDATED_ASSET_TYPE)
            .assetCode(UPDATED_ASSET_CODE)
            .reference(UPDATED_REFERENCE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .criticality(UPDATED_CRITICALITY)
            .geofencePolicy(UPDATED_GEOFENCE_POLICY)
            .responsibleName(UPDATED_RESPONSIBLE_NAME)
            .costCenter(UPDATED_COST_CENTER)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .powerKw(UPDATED_POWER_KW)
            .voltageV(UPDATED_VOLTAGE_V)
            .currentA(UPDATED_CURRENT_A)
            .cosPhi(UPDATED_COS_PHI)
            .speedRpm(UPDATED_SPEED_RPM)
            .ipRating(UPDATED_IP_RATING)
            .insulationClass(UPDATED_INSULATION_CLASS)
            .mountingType(UPDATED_MOUNTING_TYPE)
            .shaftDiameterMm(UPDATED_SHAFT_DIAMETER_MM)
            .footDistanceAmm(UPDATED_FOOT_DISTANCE_AMM)
            .footDistanceBmm(UPDATED_FOOT_DISTANCE_BMM)
            .frontFlangeMm(UPDATED_FRONT_FLANGE_MM)
            .rearFlangeMm(UPDATED_REAR_FLANGE_MM)
            .iecAxisHeightMm(UPDATED_IEC_AXIS_HEIGHT_MM)
            .dimensionsSource(UPDATED_DIMENSIONS_SOURCE)
            .hasHeating(UPDATED_HAS_HEATING)
            .temperatureProbeType(UPDATED_TEMPERATURE_PROBE_TYPE)
            .lastCommissioningDate(UPDATED_LAST_COMMISSIONING_DATE)
            .lastMaintenanceDate(UPDATED_LAST_MAINTENANCE_DATE)
            .maintenanceCount(UPDATED_MAINTENANCE_COUNT);
        AssetDTO assetDTO = assetMapper.toDto(updatedAsset);

        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetToMatchAllProperties(updatedAsset);
    }

    @Test
    @Transactional
    void putNonExistingAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset
            .assetType(UPDATED_ASSET_TYPE)
            .description(UPDATED_DESCRIPTION)
            .criticality(UPDATED_CRITICALITY)
            .geofencePolicy(UPDATED_GEOFENCE_POLICY)
            .responsibleName(UPDATED_RESPONSIBLE_NAME)
            .brand(UPDATED_BRAND)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .voltageV(UPDATED_VOLTAGE_V)
            .currentA(UPDATED_CURRENT_A)
            .shaftDiameterMm(UPDATED_SHAFT_DIAMETER_MM)
            .hasHeating(UPDATED_HAS_HEATING)
            .temperatureProbeType(UPDATED_TEMPERATURE_PROBE_TYPE)
            .lastMaintenanceDate(UPDATED_LAST_MAINTENANCE_DATE)
            .maintenanceCount(UPDATED_MAINTENANCE_COUNT);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAsset, asset), getPersistedAsset(asset));
    }

    @Test
    @Transactional
    void fullUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset
            .assetType(UPDATED_ASSET_TYPE)
            .assetCode(UPDATED_ASSET_CODE)
            .reference(UPDATED_REFERENCE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .criticality(UPDATED_CRITICALITY)
            .geofencePolicy(UPDATED_GEOFENCE_POLICY)
            .responsibleName(UPDATED_RESPONSIBLE_NAME)
            .costCenter(UPDATED_COST_CENTER)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .powerKw(UPDATED_POWER_KW)
            .voltageV(UPDATED_VOLTAGE_V)
            .currentA(UPDATED_CURRENT_A)
            .cosPhi(UPDATED_COS_PHI)
            .speedRpm(UPDATED_SPEED_RPM)
            .ipRating(UPDATED_IP_RATING)
            .insulationClass(UPDATED_INSULATION_CLASS)
            .mountingType(UPDATED_MOUNTING_TYPE)
            .shaftDiameterMm(UPDATED_SHAFT_DIAMETER_MM)
            .footDistanceAmm(UPDATED_FOOT_DISTANCE_AMM)
            .footDistanceBmm(UPDATED_FOOT_DISTANCE_BMM)
            .frontFlangeMm(UPDATED_FRONT_FLANGE_MM)
            .rearFlangeMm(UPDATED_REAR_FLANGE_MM)
            .iecAxisHeightMm(UPDATED_IEC_AXIS_HEIGHT_MM)
            .dimensionsSource(UPDATED_DIMENSIONS_SOURCE)
            .hasHeating(UPDATED_HAS_HEATING)
            .temperatureProbeType(UPDATED_TEMPERATURE_PROBE_TYPE)
            .lastCommissioningDate(UPDATED_LAST_COMMISSIONING_DATE)
            .lastMaintenanceDate(UPDATED_LAST_MAINTENANCE_DATE)
            .maintenanceCount(UPDATED_MAINTENANCE_COUNT);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetUpdatableFieldsEquals(partialUpdatedAsset, getPersistedAsset(partialUpdatedAsset));
    }

    @Test
    @Transactional
    void patchNonExistingAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asset
        restAssetMockMvc
            .perform(delete(ENTITY_API_URL_ID, asset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetRepository.count();
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

    protected Asset getPersistedAsset(Asset asset) {
        return assetRepository.findById(asset.getId()).orElseThrow();
    }

    protected void assertPersistedAssetToMatchAllProperties(Asset expectedAsset) {
        assertAssetAllPropertiesEquals(expectedAsset, getPersistedAsset(expectedAsset));
    }

    protected void assertPersistedAssetToMatchUpdatableProperties(Asset expectedAsset) {
        assertAssetAllUpdatablePropertiesEquals(expectedAsset, getPersistedAsset(expectedAsset));
    }
}
