package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.EventCatalog;
import com.brm.repository.EventCatalogRepository;

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
 * Test class for the EventCatalogResource REST controller.
 *
 * @see EventCatalogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class EventCatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private EventCatalogRepository eventCatalogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEventCatalogMockMvc;

    private EventCatalog eventCatalog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventCatalogResource eventCatalogResource = new EventCatalogResource();
        ReflectionTestUtils.setField(eventCatalogResource, "eventCatalogRepository", eventCatalogRepository);
        this.restEventCatalogMockMvc = MockMvcBuilders.standaloneSetup(eventCatalogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eventCatalog = new EventCatalog();
        eventCatalog.setName(DEFAULT_NAME);
        eventCatalog.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEventCatalog() throws Exception {
        int databaseSizeBeforeCreate = eventCatalogRepository.findAll().size();

        // Create the EventCatalog

        restEventCatalogMockMvc.perform(post("/api/event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCatalog)))
                .andExpect(status().isCreated());

        // Validate the EventCatalog in the database
        List<EventCatalog> eventCatalogs = eventCatalogRepository.findAll();
        assertThat(eventCatalogs).hasSize(databaseSizeBeforeCreate + 1);
        EventCatalog testEventCatalog = eventCatalogs.get(eventCatalogs.size() - 1);
        assertThat(testEventCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventCatalog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventCatalogRepository.findAll().size();
        // set the field null
        eventCatalog.setName(null);

        // Create the EventCatalog, which fails.

        restEventCatalogMockMvc.perform(post("/api/event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventCatalog)))
                .andExpect(status().isBadRequest());

        List<EventCatalog> eventCatalogs = eventCatalogRepository.findAll();
        assertThat(eventCatalogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventCatalogs() throws Exception {
        // Initialize the database
        eventCatalogRepository.saveAndFlush(eventCatalog);

        // Get all the eventCatalogs
        restEventCatalogMockMvc.perform(get("/api/event-catalogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventCatalog.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEventCatalog() throws Exception {
        // Initialize the database
        eventCatalogRepository.saveAndFlush(eventCatalog);

        // Get the eventCatalog
        restEventCatalogMockMvc.perform(get("/api/event-catalogs/{id}", eventCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventCatalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventCatalog() throws Exception {
        // Get the eventCatalog
        restEventCatalogMockMvc.perform(get("/api/event-catalogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventCatalog() throws Exception {
        // Initialize the database
        eventCatalogRepository.saveAndFlush(eventCatalog);
        int databaseSizeBeforeUpdate = eventCatalogRepository.findAll().size();

        // Update the eventCatalog
        EventCatalog updatedEventCatalog = new EventCatalog();
        updatedEventCatalog.setId(eventCatalog.getId());
        updatedEventCatalog.setName(UPDATED_NAME);
        updatedEventCatalog.setDescription(UPDATED_DESCRIPTION);

        restEventCatalogMockMvc.perform(put("/api/event-catalogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEventCatalog)))
                .andExpect(status().isOk());

        // Validate the EventCatalog in the database
        List<EventCatalog> eventCatalogs = eventCatalogRepository.findAll();
        assertThat(eventCatalogs).hasSize(databaseSizeBeforeUpdate);
        EventCatalog testEventCatalog = eventCatalogs.get(eventCatalogs.size() - 1);
        assertThat(testEventCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventCatalog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteEventCatalog() throws Exception {
        // Initialize the database
        eventCatalogRepository.saveAndFlush(eventCatalog);
        int databaseSizeBeforeDelete = eventCatalogRepository.findAll().size();

        // Get the eventCatalog
        restEventCatalogMockMvc.perform(delete("/api/event-catalogs/{id}", eventCatalog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventCatalog> eventCatalogs = eventCatalogRepository.findAll();
        assertThat(eventCatalogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
