package com.ailab.smartasset.web.rest;

import static com.ailab.smartasset.domain.SiteAsserts.*;
import static com.ailab.smartasset.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailab.smartasset.IntegrationTest;
import com.ailab.smartasset.domain.Site;
import com.ailab.smartasset.repository.SiteRepository;
import com.ailab.smartasset.service.dto.SiteDTO;
import com.ailab.smartasset.service.mapper.SiteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteMockMvc;

    private Site site;

    private Site insertedSite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createEntity() {
        return new Site().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createUpdatedEntity() {
        return new Site().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        site = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSite != null) {
            siteRepository.delete(insertedSite);
            insertedSite = null;
        }
    }

    @Test
    @Transactional
    void createSite() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        var returnedSiteDTO = om.readValue(
            restSiteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SiteDTO.class
        );

        // Validate the Site in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSite = siteMapper.toEntity(returnedSiteDTO);
        assertSiteUpdatableFieldsEquals(returnedSite, getPersistedSite(returnedSite));

        insertedSite = returnedSite;
    }

    @Test
    @Transactional
    void createSiteWithExistingId() throws Exception {
        // Create the Site with an existing ID
        site.setId(1L);
        SiteDTO siteDTO = siteMapper.toDto(site);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        site.setCode(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);

        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        site.setName(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);

        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSites() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc
            .perform(get(ENTITY_API_URL_ID, site.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(site.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getSitesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        Long id = site.getId();

        defaultSiteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSiteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSiteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSitesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where code equals to
        defaultSiteFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSitesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where code in
        defaultSiteFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSitesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where code is not null
        defaultSiteFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where code contains
        defaultSiteFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSitesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where code does not contain
        defaultSiteFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSitesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where name equals to
        defaultSiteFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSitesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where name in
        defaultSiteFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSitesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where name is not null
        defaultSiteFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where name contains
        defaultSiteFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSitesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where name does not contain
        defaultSiteFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllSitesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where description equals to
        defaultSiteFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where description in
        defaultSiteFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where description is not null
        defaultSiteFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllSitesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where description contains
        defaultSiteFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSitesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList where description does not contain
        defaultSiteFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    private void defaultSiteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSiteShouldBeFound(shouldBeFound);
        defaultSiteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSiteShouldBeFound(String filter) throws Exception {
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSiteShouldNotBeFound(String filter) throws Exception {
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site
        Site updatedSite = siteRepository.findById(site.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        SiteDTO siteDTO = siteMapper.toDto(updatedSite);

        restSiteMockMvc
            .perform(put(ENTITY_API_URL_ID, siteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isOk());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSiteToMatchAllProperties(updatedSite);
    }

    @Test
    @Transactional
    void putNonExistingSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(put(ENTITY_API_URL_ID, siteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setId(site.getId());

        partialUpdatedSite.name(UPDATED_NAME);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSite, site), getPersistedSite(site));
    }

    @Test
    @Transactional
    void fullUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setId(site.getId());

        partialUpdatedSite.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteUpdatableFieldsEquals(partialUpdatedSite, getPersistedSite(partialUpdatedSite));
    }

    @Test
    @Transactional
    void patchNonExistingSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(siteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(siteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the site
        restSiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, site.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return siteRepository.count();
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

    protected Site getPersistedSite(Site site) {
        return siteRepository.findById(site.getId()).orElseThrow();
    }

    protected void assertPersistedSiteToMatchAllProperties(Site expectedSite) {
        assertSiteAllPropertiesEquals(expectedSite, getPersistedSite(expectedSite));
    }

    protected void assertPersistedSiteToMatchUpdatableProperties(Site expectedSite) {
        assertSiteAllUpdatablePropertiesEquals(expectedSite, getPersistedSite(expectedSite));
    }
}
