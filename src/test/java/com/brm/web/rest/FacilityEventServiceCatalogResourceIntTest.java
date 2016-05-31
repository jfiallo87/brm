package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventServiceCatalog;
import com.brm.repository.FacilityEventServiceCatalogRepository;

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

import com.brm.domain.enumeration.UnitType;

/**
 * Test class for the FacilityEventServiceCatalogResource REST controller.
 *
 * @see FacilityEventServiceCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventServiceCatalogResourceIntTest {


    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(1);

    private static final UnitType DEFAULT_UNIT_TYPE = UnitType.EACH;
    private static final UnitType UPDATED_UNIT_TYPE = UnitType.FLAT;

    @Inject
    private FacilityEventServiceCatalogRepository facilityEventServiceCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventServiceCatalogMockMvc;

    private FacilityEventServiceCatalog facilityEventServiceCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventServiceCatalogResource facilityEventServiceCatalogResource = new FacilityEventServiceCatalogResource();
        ReflectionTestUtils.setField(facilityEventServiceCatalogResource, "facilityEventServiceCatalogRepository", facilityEventServiceCatalogRepository);
        this.restFacilityEventServiceCatalogMockMvc = MockMvcBuilders.standaloneSetup(facilityEventServiceCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventServiceCatalog = new FacilityEventServiceCatalog();
        facilityEventServiceCatalog.setUnitPrice(DEFAULT_UNIT_PRICE);
        facilityEventServiceCatalog.setUnitType(DEFAULT_UNIT_TYPE);
    }

    @Test
    @Transactional
    public void createFacilityEventServiceCatalog() throws Exception {
        int databaseSizeBeforeCreate = facilityEventServiceCatalogRepository.findAll().size();

        // Create the FacilityEventServiceCatalog

        restFacilityEventServiceCatalogMockMvc.perform(post("/api/facility-event-service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventServiceCatalog)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventServiceCatalog in the database
        List<FacilityEventServiceCatalog> facilityEventServiceCatalogs = facilityEventServiceCatalogRepository.findAll();
        assertThat(facilityEventServiceCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventServiceCatalog testFacilityEventServiceCatalog = facilityEventServiceCatalogs.get(facilityEventServiceCatalogs.size() - 1);
        assertThat(testFacilityEventServiceCatalog.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testFacilityEventServiceCatalog.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventServiceCatalogRepository.findAll().size();
        // set the field null
        facilityEventServiceCatalog.setUnitPrice(null);

        // Create the FacilityEventServiceCatalog, which fails.

        restFacilityEventServiceCatalogMockMvc.perform(post("/api/facility-event-service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventServiceCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityEventServiceCatalog> facilityEventServiceCatalogs = facilityEventServiceCatalogRepository.findAll();
        assertThat(facilityEventServiceCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventServiceCatalogRepository.findAll().size();
        // set the field null
        facilityEventServiceCatalog.setUnitType(null);

        // Create the FacilityEventServiceCatalog, which fails.

        restFacilityEventServiceCatalogMockMvc.perform(post("/api/facility-event-service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventServiceCatalog)))
                .andExpect(status().isBadRequest());

        List<FacilityEventServiceCatalog> facilityEventServiceCatalogs = facilityEventServiceCatalogRepository.findAll();
        assertThat(facilityEventServiceCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityEventServiceCatalogs() throws Exception {
        // Initialize the database
        facilityEventServiceCatalogRepository.saveAndFlush(facilityEventServiceCatalog);

        // Get all the facilityEventServiceCatalogs
        restFacilityEventServiceCatalogMockMvc.perform(get("/api/facility-event-service-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventServiceCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getFacilityEventServiceCatalog() throws Exception {
        // Initialize the database
        facilityEventServiceCatalogRepository.saveAndFlush(facilityEventServiceCatalog);

        // Get the facilityEventServiceCatalog
        restFacilityEventServiceCatalogMockMvc.perform(get("/api/facility-event-service-catalogs/{id}", facilityEventServiceCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventServiceCatalog.getId().intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventServiceCatalog() throws Exception {
        // Get the facilityEventServiceCatalog
        restFacilityEventServiceCatalogMockMvc.perform(get("/api/facility-event-service-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventServiceCatalog() throws Exception {
        // Initialize the database
        facilityEventServiceCatalogRepository.saveAndFlush(facilityEventServiceCatalog);
        int databaseSizeBeforeUpdate = facilityEventServiceCatalogRepository.findAll().size();

        // Update the facilityEventServiceCatalog
        FacilityEventServiceCatalog updatedFacilityEventServiceCatalog = new FacilityEventServiceCatalog();
        updatedFacilityEventServiceCatalog.setId(facilityEventServiceCatalog.getId());
        updatedFacilityEventServiceCatalog.setUnitPrice(UPDATED_UNIT_PRICE);
        updatedFacilityEventServiceCatalog.setUnitType(UPDATED_UNIT_TYPE);

        restFacilityEventServiceCatalogMockMvc.perform(put("/api/facility-event-service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventServiceCatalog)))
                .andExpect(status().isOk());

        // Validate the FacilityEventServiceCatalog in the database
        List<FacilityEventServiceCatalog> facilityEventServiceCatalogs = facilityEventServiceCatalogRepository.findAll();
        assertThat(facilityEventServiceCatalogs).hasSize(databaseSizeBeforeUpdate);
        FacilityEventServiceCatalog testFacilityEventServiceCatalog = facilityEventServiceCatalogs.get(facilityEventServiceCatalogs.size() - 1);
        assertThat(testFacilityEventServiceCatalog.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testFacilityEventServiceCatalog.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
    }

    @Test
    @Transactional
    public void deleteFacilityEventServiceCatalog() throws Exception {
        // Initialize the database
        facilityEventServiceCatalogRepository.saveAndFlush(facilityEventServiceCatalog);
        int databaseSizeBeforeDelete = facilityEventServiceCatalogRepository.findAll().size();

        // Get the facilityEventServiceCatalog
        restFacilityEventServiceCatalogMockMvc.perform(delete("/api/facility-event-service-catalogs/{id}", facilityEventServiceCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventServiceCatalog> facilityEventServiceCatalogs = facilityEventServiceCatalogRepository.findAll();
        assertThat(facilityEventServiceCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
