package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventSeatingCatalog;
import com.brm.repository.FacilityEventSeatingCatalogRepository;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FacilityEventSeatingCatalogResource REST controller.
 *
 * @see FacilityEventSeatingCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventSeatingCatalogResourceIntTest {


    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(1);

    @Inject
    private FacilityEventSeatingCatalogRepository facilityEventSeatingCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventSeatingCatalogMockMvc;

    private FacilityEventSeatingCatalog facilityEventSeatingCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventSeatingCatalogResource facilityEventSeatingCatalogResource = new FacilityEventSeatingCatalogResource();
        ReflectionTestUtils.setField(facilityEventSeatingCatalogResource, "facilityEventSeatingCatalogRepository", facilityEventSeatingCatalogRepository);
        this.restFacilityEventSeatingCatalogMockMvc = MockMvcBuilders.standaloneSetup(facilityEventSeatingCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventSeatingCatalog = new FacilityEventSeatingCatalog();
        facilityEventSeatingCatalog.setUnitPrice(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void createFacilityEventSeatingCatalog() throws Exception {
        int databaseSizeBeforeCreate = facilityEventSeatingCatalogRepository.findAll().size();

        // Create the FacilityEventSeatingCatalog

        restFacilityEventSeatingCatalogMockMvc.perform(post("/api/facility-event-seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventSeatingCatalog)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventSeatingCatalog in the database
        List<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = facilityEventSeatingCatalogRepository.findAll();
        assertThat(facilityEventSeatingCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventSeatingCatalog testFacilityEventSeatingCatalog = facilityEventSeatingCatalogs.get(facilityEventSeatingCatalogs.size() - 1);
        assertThat(testFacilityEventSeatingCatalog.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventSeatingCatalogRepository.findAll().size();
        // set the field null
        facilityEventSeatingCatalog.setUnitPrice(null);

        // Create the FacilityEventSeatingCatalog, which fails.

        restFacilityEventSeatingCatalogMockMvc.perform(post("/api/facility-event-seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventSeatingCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = facilityEventSeatingCatalogRepository.findAll();
        assertThat(facilityEventSeatingCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityEventSeatingCatalogs() throws Exception {
        // Initialize the database
        facilityEventSeatingCatalogRepository.saveAndFlush(facilityEventSeatingCatalog);

        // Get all the facilityEventSeatingCatalogs
        restFacilityEventSeatingCatalogMockMvc.perform(get("/api/facility-event-seating-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventSeatingCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getFacilityEventSeatingCatalog() throws Exception {
        // Initialize the database
        facilityEventSeatingCatalogRepository.saveAndFlush(facilityEventSeatingCatalog);

        // Get the facilityEventSeatingCatalog
        restFacilityEventSeatingCatalogMockMvc.perform(get("/api/facility-event-seating-catalogs/{id}", facilityEventSeatingCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventSeatingCatalog.getId().intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventSeatingCatalog() throws Exception {
        // Get the facilityEventSeatingCatalog
        restFacilityEventSeatingCatalogMockMvc.perform(get("/api/facility-event-seating-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventSeatingCatalog() throws Exception {
        // Initialize the database
        facilityEventSeatingCatalogRepository.saveAndFlush(facilityEventSeatingCatalog);
        int databaseSizeBeforeUpdate = facilityEventSeatingCatalogRepository.findAll().size();

        // Update the facilityEventSeatingCatalog
        FacilityEventSeatingCatalog updatedFacilityEventSeatingCatalog = new FacilityEventSeatingCatalog();
        updatedFacilityEventSeatingCatalog.setId(facilityEventSeatingCatalog.getId());
        updatedFacilityEventSeatingCatalog.setUnitPrice(UPDATED_UNIT_PRICE);

        restFacilityEventSeatingCatalogMockMvc.perform(put("/api/facility-event-seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventSeatingCatalog)))
                .andExpect(status().isOk());

        // Validate the FacilityEventSeatingCatalog in the database
        List<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = facilityEventSeatingCatalogRepository.findAll();
        assertThat(facilityEventSeatingCatalogs).hasSize(databaseSizeBeforeUpdate);
        FacilityEventSeatingCatalog testFacilityEventSeatingCatalog = facilityEventSeatingCatalogs.get(facilityEventSeatingCatalogs.size() - 1);
        assertThat(testFacilityEventSeatingCatalog.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void deleteFacilityEventSeatingCatalog() throws Exception {
        // Initialize the database
        facilityEventSeatingCatalogRepository.saveAndFlush(facilityEventSeatingCatalog);
        int databaseSizeBeforeDelete = facilityEventSeatingCatalogRepository.findAll().size();

        // Get the facilityEventSeatingCatalog
        restFacilityEventSeatingCatalogMockMvc.perform(delete("/api/facility-event-seating-catalogs/{id}", facilityEventSeatingCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = facilityEventSeatingCatalogRepository.findAll();
        assertThat(facilityEventSeatingCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
