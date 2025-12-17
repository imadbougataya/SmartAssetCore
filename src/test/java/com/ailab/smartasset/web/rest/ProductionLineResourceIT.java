package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.ProductionLineAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.ProductionLine;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.repository.ProductionLineRepository;
import com.ailab.smartasset.service.ProductionLineService;
import com.ailab.smartasset.service.dto.ProductionLineDTO;
import com.ailab.smartasset.service.mapper.ProductionLineMapper;
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
 * Integration tests for the {@link ProductionLineResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductionLineResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/production-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductionLineRepository productionLineRepository;

    @Mock
    private ProductionLineRepository productionLineRepositoryMock;

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Mock
    private ProductionLineService productionLineServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionLineMockMvc;

    private ProductionLine productionLine;

    private ProductionLine insertedProductionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionLine createEntity() {
        return new ProductionLine().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionLine createUpdatedEntity() {
        return new ProductionLine().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        productionLine = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedProductionLine != null) {
            productionLineRepository.delete(insertedProductionLine);
            insertedProductionLine = null;
        }
    }

    @Test
    @Transactional
    void createProductionLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);
        var returnedProductionLineDTO = om.readValue(
            restProductionLineMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productionLineDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProductionLineDTO.class
        );

        // Validate the ProductionLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProductionLine = productionLineMapper.toEntity(returnedProductionLineDTO);
        assertProductionLineUpdatableFieldsEquals(returnedProductionLine, getPersistedProductionLine(returnedProductionLine));

        insertedProductionLine = returnedProductionLine;
    }

    @Test
    @Transactional
    void createProductionLineWithExistingId() throws Exception {
        // Create the ProductionLine with an existing ID
        productionLine.setId(1L);
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productionLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        productionLine.setCode(null);

        // Create the ProductionLine, which fails.
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        restProductionLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productionLineDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        productionLine.setName(null);

        // Create the ProductionLine, which fails.
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        restProductionLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productionLineDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductionLines() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductionLinesWithEagerRelationshipsIsEnabled() throws Exception {
        when(productionLineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductionLineMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productionLineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductionLinesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productionLineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductionLineMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(productionLineRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProductionLine() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get the productionLine
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL_ID, productionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionLine.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getProductionLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        Long id = productionLine.getId();

        defaultProductionLineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProductionLineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProductionLineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductionLinesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where code equals to
        defaultProductionLineFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductionLinesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where code in
        defaultProductionLineFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductionLinesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where code is not null
        defaultProductionLineFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllProductionLinesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where code contains
        defaultProductionLineFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductionLinesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where code does not contain
        defaultProductionLineFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllProductionLinesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where name equals to
        defaultProductionLineFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductionLinesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where name in
        defaultProductionLineFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductionLinesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where name is not null
        defaultProductionLineFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductionLinesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where name contains
        defaultProductionLineFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductionLinesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where name does not contain
        defaultProductionLineFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllProductionLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where description equals to
        defaultProductionLineFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductionLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where description in
        defaultProductionLineFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductionLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where description is not null
        defaultProductionLineFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllProductionLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where description contains
        defaultProductionLineFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProductionLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        // Get all the productionLineList where description does not contain
        defaultProductionLineFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductionLinesBySiteIsEqualToSomething() throws Exception {
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            productionLineRepository.saveAndFlush(productionLine);
            site = SiteResourceIT.createEntity();
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        em.persist(site);
        em.flush();
        productionLine.setSite(site);
        productionLineRepository.saveAndFlush(productionLine);
        Long siteId = site.getId();
        // Get all the productionLineList where site equals to siteId
        defaultProductionLineShouldBeFound("siteId.equals=" + siteId);

        // Get all the productionLineList where site equals to (siteId + 1)
        defaultProductionLineShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }

    private void defaultProductionLineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProductionLineShouldBeFound(shouldBeFound);
        defaultProductionLineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductionLineShouldBeFound(String filter) throws Exception {
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductionLineShouldNotBeFound(String filter) throws Exception {
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductionLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductionLine() throws Exception {
        // Get the productionLine
        restProductionLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductionLine() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productionLine
        ProductionLine updatedProductionLine = productionLineRepository.findById(productionLine.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductionLine are not directly saved in db
        em.detach(updatedProductionLine);
        updatedProductionLine.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(updatedProductionLine);

        restProductionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionLineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productionLineDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductionLineToMatchAllProperties(updatedProductionLine);
    }

    @Test
    @Transactional
    void putNonExistingProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionLineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productionLineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductionLineWithPatch() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productionLine using partial update
        ProductionLine partialUpdatedProductionLine = new ProductionLine();
        partialUpdatedProductionLine.setId(productionLine.getId());

        partialUpdatedProductionLine.name(UPDATED_NAME);

        restProductionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductionLine))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductionLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductionLine, productionLine),
            getPersistedProductionLine(productionLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductionLineWithPatch() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productionLine using partial update
        ProductionLine partialUpdatedProductionLine = new ProductionLine();
        partialUpdatedProductionLine.setId(productionLine.getId());

        partialUpdatedProductionLine.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restProductionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductionLine))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductionLineUpdatableFieldsEquals(partialUpdatedProductionLine, getPersistedProductionLine(partialUpdatedProductionLine));
    }

    @Test
    @Transactional
    void patchNonExistingProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productionLineDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productionLine.setId(longCount.incrementAndGet());

        // Create the ProductionLine
        ProductionLineDTO productionLineDTO = productionLineMapper.toDto(productionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productionLineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductionLine() throws Exception {
        // Initialize the database
        insertedProductionLine = productionLineRepository.saveAndFlush(productionLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productionLine
        restProductionLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, productionLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productionLineRepository.count();
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

    protected ProductionLine getPersistedProductionLine(ProductionLine productionLine) {
        return productionLineRepository.findById(productionLine.getId()).orElseThrow();
    }

    protected void assertPersistedProductionLineToMatchAllProperties(ProductionLine expectedProductionLine) {
        assertProductionLineAllPropertiesEquals(expectedProductionLine, getPersistedProductionLine(expectedProductionLine));
    }

    protected void assertPersistedProductionLineToMatchUpdatableProperties(ProductionLine expectedProductionLine) {
        assertProductionLineAllUpdatablePropertiesEquals(expectedProductionLine, getPersistedProductionLine(expectedProductionLine));
    }
}
