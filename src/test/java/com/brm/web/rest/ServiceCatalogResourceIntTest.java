package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.ServiceCatalog;
import com.brm.repository.ServiceCatalogRepository;

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
 * Test class for the ServiceCatalogResource REST controller.
 *
 * @see ServiceCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class ServiceCatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private ServiceCatalogRepository serviceCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServiceCatalogMockMvc;

    private ServiceCatalog serviceCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceCatalogResource serviceCatalogResource = new ServiceCatalogResource();
        ReflectionTestUtils.setField(serviceCatalogResource, "serviceCatalogRepository", serviceCatalogRepository);
        this.restServiceCatalogMockMvc = MockMvcBuilders.standaloneSetup(serviceCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        serviceCatalog = new ServiceCatalog();
        serviceCatalog.setName(DEFAULT_NAME);
        serviceCatalog.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createServiceCatalog() throws Exception {
        int databaseSizeBeforeCreate = serviceCatalogRepository.findAll().size();

        // Create the ServiceCatalog

        restServiceCatalogMockMvc.perform(post("/api/service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(serviceCatalog)))
                .andExpect(status().isCreated());

        // Validate the ServiceCatalog in the database
        List<ServiceCatalog> serviceCatalogs = serviceCatalogRepository.findAll();
        assertThat(serviceCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        ServiceCatalog testServiceCatalog = serviceCatalogs.get(serviceCatalogs.size() - 1);
        assertThat(testServiceCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceCatalogRepository.findAll().size();
        // set the field null
        serviceCatalog.setName(null);

        // Create the ServiceCatalog, which fails.

        restServiceCatalogMockMvc.perform(post("/api/service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(serviceCatalog)))
                .andExpect(status().isBadRequest());

        List<ServiceCatalog> serviceCatalogs = serviceCatalogRepository.findAll();
        assertThat(serviceCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceCatalogs() throws Exception {
        // Initialize the database
        serviceCatalogRepository.saveAndFlush(serviceCatalog);

        // Get all the serviceCatalogs
        restServiceCatalogMockMvc.perform(get("/api/service-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(serviceCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getServiceCatalog() throws Exception {
        // Initialize the database
        serviceCatalogRepository.saveAndFlush(serviceCatalog);

        // Get the serviceCatalog
        restServiceCatalogMockMvc.perform(get("/api/service-catalogs/{id}", serviceCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(serviceCatalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceCatalog() throws Exception {
        // Get the serviceCatalog
        restServiceCatalogMockMvc.perform(get("/api/service-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceCatalog() throws Exception {
        // Initialize the database
        serviceCatalogRepository.saveAndFlush(serviceCatalog);
        int databaseSizeBeforeUpdate = serviceCatalogRepository.findAll().size();

        // Update the serviceCatalog
        ServiceCatalog updatedServiceCatalog = new ServiceCatalog();
        updatedServiceCatalog.setId(serviceCatalog.getId());
        updatedServiceCatalog.setName(UPDATED_NAME);
        updatedServiceCatalog.setDescription(UPDATED_DESCRIPTION);

        restServiceCatalogMockMvc.perform(put("/api/service-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedServiceCatalog)))
                .andExpect(status().isOk());

        // Validate the ServiceCatalog in the database
        List<ServiceCatalog> serviceCatalogs = serviceCatalogRepository.findAll();
        assertThat(serviceCatalogs).hasSize(databaseSizeBeforeUpdate);
        ServiceCatalog testServiceCatalog = serviceCatalogs.get(serviceCatalogs.size() - 1);
        assertThat(testServiceCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteServiceCatalog() throws Exception {
        // Initialize the database
        serviceCatalogRepository.saveAndFlush(serviceCatalog);
        int databaseSizeBeforeDelete = serviceCatalogRepository.findAll().size();

        // Get the serviceCatalog
        restServiceCatalogMockMvc.perform(delete("/api/service-catalogs/{id}", serviceCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceCatalog> serviceCatalogs = serviceCatalogRepository.findAll();
        assertThat(serviceCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
