package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.SeatingCatalog;
import com.brm.repository.SeatingCatalogRepository;

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
 * Test class for the SeatingCatalogResource REST controller.
 *
 * @see SeatingCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class SeatingCatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private SeatingCatalogRepository seatingCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSeatingCatalogMockMvc;

    private SeatingCatalog seatingCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeatingCatalogResource seatingCatalogResource = new SeatingCatalogResource();
        ReflectionTestUtils.setField(seatingCatalogResource, "seatingCatalogRepository", seatingCatalogRepository);
        this.restSeatingCatalogMockMvc = MockMvcBuilders.standaloneSetup(seatingCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        seatingCatalog = new SeatingCatalog();
        seatingCatalog.setName(DEFAULT_NAME);
        seatingCatalog.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSeatingCatalog() throws Exception {
        int databaseSizeBeforeCreate = seatingCatalogRepository.findAll().size();

        // Create the SeatingCatalog

        restSeatingCatalogMockMvc.perform(post("/api/seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seatingCatalog)))
                .andExpect(status().isCreated());

        // Validate the SeatingCatalog in the database
        List<SeatingCatalog> seatingCatalogs = seatingCatalogRepository.findAll();
        assertThat(seatingCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        SeatingCatalog testSeatingCatalog = seatingCatalogs.get(seatingCatalogs.size() - 1);
        assertThat(testSeatingCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSeatingCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatingCatalogRepository.findAll().size();
        // set the field null
        seatingCatalog.setName(null);

        // Create the SeatingCatalog, which fails.

        restSeatingCatalogMockMvc.perform(post("/api/seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(seatingCatalog)))
                .andExpect(status().isBadRequest());

        List<SeatingCatalog> seatingCatalogs = seatingCatalogRepository.findAll();
        assertThat(seatingCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeatingCatalogs() throws Exception {
        // Initialize the database
        seatingCatalogRepository.saveAndFlush(seatingCatalog);

        // Get all the seatingCatalogs
        restSeatingCatalogMockMvc.perform(get("/api/seating-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(seatingCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSeatingCatalog() throws Exception {
        // Initialize the database
        seatingCatalogRepository.saveAndFlush(seatingCatalog);

        // Get the seatingCatalog
        restSeatingCatalogMockMvc.perform(get("/api/seating-catalogs/{id}", seatingCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(seatingCatalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeatingCatalog() throws Exception {
        // Get the seatingCatalog
        restSeatingCatalogMockMvc.perform(get("/api/seating-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeatingCatalog() throws Exception {
        // Initialize the database
        seatingCatalogRepository.saveAndFlush(seatingCatalog);
        int databaseSizeBeforeUpdate = seatingCatalogRepository.findAll().size();

        // Update the seatingCatalog
        SeatingCatalog updatedSeatingCatalog = new SeatingCatalog();
        updatedSeatingCatalog.setId(seatingCatalog.getId());
        updatedSeatingCatalog.setName(UPDATED_NAME);
        updatedSeatingCatalog.setDescription(UPDATED_DESCRIPTION);

        restSeatingCatalogMockMvc.perform(put("/api/seating-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSeatingCatalog)))
                .andExpect(status().isOk());

        // Validate the SeatingCatalog in the database
        List<SeatingCatalog> seatingCatalogs = seatingCatalogRepository.findAll();
        assertThat(seatingCatalogs).hasSize(databaseSizeBeforeUpdate);
        SeatingCatalog testSeatingCatalog = seatingCatalogs.get(seatingCatalogs.size() - 1);
        assertThat(testSeatingCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSeatingCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSeatingCatalog() throws Exception {
        // Initialize the database
        seatingCatalogRepository.saveAndFlush(seatingCatalog);
        int databaseSizeBeforeDelete = seatingCatalogRepository.findAll().size();

        // Get the seatingCatalog
        restSeatingCatalogMockMvc.perform(delete("/api/seating-catalogs/{id}", seatingCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SeatingCatalog> seatingCatalogs = seatingCatalogRepository.findAll();
        assertThat(seatingCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
