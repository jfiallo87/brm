package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityCatalog;
import com.brm.repository.FacilityCatalogRepository;

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
 * Test class for the FacilityCatalogResource REST controller.
 *
 * @see FacilityCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityCatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_MAX_CAPACITY = 1;
    private static final Integer UPDATED_MAX_CAPACITY = 2;

    @Inject
    private FacilityCatalogRepository facilityCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityCatalogMockMvc;

    private FacilityCatalog facilityCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityCatalogResource facilityCatalogResource = new FacilityCatalogResource();
        ReflectionTestUtils.setField(facilityCatalogResource, "facilityCatalogRepository", facilityCatalogRepository);
        this.restFacilityCatalogMockMvc = MockMvcBuilders.standaloneSetup(facilityCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityCatalog = new FacilityCatalog();
        facilityCatalog.setName(DEFAULT_NAME);
        facilityCatalog.setDescription(DEFAULT_DESCRIPTION);
        facilityCatalog.setMaxCapacity(DEFAULT_MAX_CAPACITY);
    }

    @Test
    @Transactional
    public void createFacilityCatalog() throws Exception {
        int databaseSizeBeforeCreate = facilityCatalogRepository.findAll().size();

        // Create the FacilityCatalog

        restFacilityCatalogMockMvc.perform(post("/api/facility-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityCatalog)))
                .andExpect(status().isCreated());

        // Validate the FacilityCatalog in the database
        List<FacilityCatalog> facilityCatalogs = facilityCatalogRepository.findAll();
        assertThat(facilityCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        FacilityCatalog testFacilityCatalog = facilityCatalogs.get(facilityCatalogs.size() - 1);
        assertThat(testFacilityCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacilityCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFacilityCatalog.getMaxCapacity()).isEqualTo(DEFAULT_MAX_CAPACITY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityCatalogRepository.findAll().size();
        // set the field null
        facilityCatalog.setName(null);

        // Create the FacilityCatalog, which fails.

        restFacilityCatalogMockMvc.perform(post("/api/facility-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityCatalog> facilityCatalogs = facilityCatalogRepository.findAll();
        assertThat(facilityCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityCatalogRepository.findAll().size();
        // set the field null
        facilityCatalog.setMaxCapacity(null);

        // Create the FacilityCatalog, which fails.

        restFacilityCatalogMockMvc.perform(post("/api/facility-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityCatalog> facilityCatalogs = facilityCatalogRepository.findAll();
        assertThat(facilityCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityCatalogs() throws Exception {
        // Initialize the database
        facilityCatalogRepository.saveAndFlush(facilityCatalog);

        // Get all the facilityCatalogs
        restFacilityCatalogMockMvc.perform(get("/api/facility-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].maxCapacity").value(hasItem(DEFAULT_MAX_CAPACITY)));
    }

    @Test
    @Transactional
    public void getFacilityCatalog() throws Exception {
        // Initialize the database
        facilityCatalogRepository.saveAndFlush(facilityCatalog);

        // Get the facilityCatalog
        restFacilityCatalogMockMvc.perform(get("/api/facility-catalogs/{id}", facilityCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityCatalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.maxCapacity").value(DEFAULT_MAX_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityCatalog() throws Exception {
        // Get the facilityCatalog
        restFacilityCatalogMockMvc.perform(get("/api/facility-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityCatalog() throws Exception {
        // Initialize the database
        facilityCatalogRepository.saveAndFlush(facilityCatalog);
        int databaseSizeBeforeUpdate = facilityCatalogRepository.findAll().size();

        // Update the facilityCatalog
        FacilityCatalog updatedFacilityCatalog = new FacilityCatalog();
        updatedFacilityCatalog.setId(facilityCatalog.getId());
        updatedFacilityCatalog.setName(UPDATED_NAME);
        updatedFacilityCatalog.setDescription(UPDATED_DESCRIPTION);
        updatedFacilityCatalog.setMaxCapacity(UPDATED_MAX_CAPACITY);

        restFacilityCatalogMockMvc.perform(put("/api/facility-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityCatalog)))
                .andExpect(status().isOk());

        // Validate the FacilityCatalog in the database
        List<FacilityCatalog> facilityCatalogs = facilityCatalogRepository.findAll();
        assertThat(facilityCatalogs).hasSize(databaseSizeBeforeUpdate);
        FacilityCatalog testFacilityCatalog = facilityCatalogs.get(facilityCatalogs.size() - 1);
        assertThat(testFacilityCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacilityCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFacilityCatalog.getMaxCapacity()).isEqualTo(UPDATED_MAX_CAPACITY);
    }

    @Test
    @Transactional
    public void deleteFacilityCatalog() throws Exception {
        // Initialize the database
        facilityCatalogRepository.saveAndFlush(facilityCatalog);
        int databaseSizeBeforeDelete = facilityCatalogRepository.findAll().size();

        // Get the facilityCatalog
        restFacilityCatalogMockMvc.perform(delete("/api/facility-catalogs/{id}", facilityCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityCatalog> facilityCatalogs = facilityCatalogRepository.findAll();
        assertThat(facilityCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
