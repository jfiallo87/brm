package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventCatalog;
import com.brm.repository.FacilityEventCatalogRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FacilityEventCatalogResource REST controller.
 *
 * @see FacilityEventCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventCatalogResourceIntTest {


    private static final Integer DEFAULT_MIN_GUESTS = 1;
    private static final Integer UPDATED_MIN_GUESTS = 2;

    @Inject
    private FacilityEventCatalogRepository facilityEventCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventCatalogMockMvc;

    private FacilityEventCatalog facilityEventCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventCatalogResource facilityEventCatalogResource = new FacilityEventCatalogResource();
        ReflectionTestUtils.setField(facilityEventCatalogResource, "facilityEventCatalogRepository", facilityEventCatalogRepository);
        this.restFacilityEventCatalogMockMvc = MockMvcBuilders.standaloneSetup(facilityEventCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventCatalog = new FacilityEventCatalog();
        facilityEventCatalog.setMinGuests(DEFAULT_MIN_GUESTS);
    }

    @Test
    @Transactional
    public void createFacilityEventCatalog() throws Exception {
        int databaseSizeBeforeCreate = facilityEventCatalogRepository.findAll().size();

        // Create the FacilityEventCatalog

        restFacilityEventCatalogMockMvc.perform(post("/api/facility-event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventCatalog)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventCatalog in the database
        List<FacilityEventCatalog> facilityEventCatalogs = facilityEventCatalogRepository.findAll();
        assertThat(facilityEventCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventCatalog testFacilityEventCatalog = facilityEventCatalogs.get(facilityEventCatalogs.size() - 1);
        assertThat(testFacilityEventCatalog.getMinGuests()).isEqualTo(DEFAULT_MIN_GUESTS);
    }

    @Test
    @Transactional
    public void checkMinGuestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventCatalogRepository.findAll().size();
        // set the field null
        facilityEventCatalog.setMinGuests(null);

        // Create the FacilityEventCatalog, which fails.

        restFacilityEventCatalogMockMvc.perform(post("/api/facility-event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityEventCatalog> facilityEventCatalogs = facilityEventCatalogRepository.findAll();
        assertThat(facilityEventCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityEventCatalogs() throws Exception {
        // Initialize the database
        facilityEventCatalogRepository.saveAndFlush(facilityEventCatalog);

        // Get all the facilityEventCatalogs
        restFacilityEventCatalogMockMvc.perform(get("/api/facility-event-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].minGuests").value(hasItem(DEFAULT_MIN_GUESTS)));
    }

    @Test
    @Transactional
    public void getFacilityEventCatalog() throws Exception {
        // Initialize the database
        facilityEventCatalogRepository.saveAndFlush(facilityEventCatalog);

        // Get the facilityEventCatalog
        restFacilityEventCatalogMockMvc.perform(get("/api/facility-event-catalogs/{id}", facilityEventCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventCatalog.getId().intValue()))
            .andExpect(jsonPath("$.minGuests").value(DEFAULT_MIN_GUESTS));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventCatalog() throws Exception {
        // Get the facilityEventCatalog
        restFacilityEventCatalogMockMvc.perform(get("/api/facility-event-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventCatalog() throws Exception {
        // Initialize the database
        facilityEventCatalogRepository.saveAndFlush(facilityEventCatalog);
        int databaseSizeBeforeUpdate = facilityEventCatalogRepository.findAll().size();

        // Update the facilityEventCatalog
        FacilityEventCatalog updatedFacilityEventCatalog = new FacilityEventCatalog();
        updatedFacilityEventCatalog.setId(facilityEventCatalog.getId());
        updatedFacilityEventCatalog.setMinGuests(UPDATED_MIN_GUESTS);

        restFacilityEventCatalogMockMvc.perform(put("/api/facility-event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventCatalog)))
                .andExpect(status().isOk());

        // Validate the FacilityEventCatalog in the database
        List<FacilityEventCatalog> facilityEventCatalogs = facilityEventCatalogRepository.findAll();
        assertThat(facilityEventCatalogs).hasSize(databaseSizeBeforeUpdate);
        FacilityEventCatalog testFacilityEventCatalog = facilityEventCatalogs.get(facilityEventCatalogs.size() - 1);
        assertThat(testFacilityEventCatalog.getMinGuests()).isEqualTo(UPDATED_MIN_GUESTS);
    }

    @Test
    @Transactional
    public void deleteFacilityEventCatalog() throws Exception {
        // Initialize the database
        facilityEventCatalogRepository.saveAndFlush(facilityEventCatalog);
        int databaseSizeBeforeDelete = facilityEventCatalogRepository.findAll().size();

        // Get the facilityEventCatalog
        restFacilityEventCatalogMockMvc.perform(delete("/api/facility-event-catalogs/{id}", facilityEventCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventCatalog> facilityEventCatalogs = facilityEventCatalogRepository.findAll();
        assertThat(facilityEventCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
