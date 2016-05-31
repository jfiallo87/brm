package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventSchedule;
import com.brm.repository.FacilityEventScheduleRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FacilityEventScheduleResource REST controller.
 *
 * @see FacilityEventScheduleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventScheduleResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_STR = dateTimeFormatter.format(DEFAULT_START);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_STR = dateTimeFormatter.format(DEFAULT_END);

    @Inject
    private FacilityEventScheduleRepository facilityEventScheduleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventScheduleMockMvc;

    private FacilityEventSchedule facilityEventSchedule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventScheduleResource facilityEventScheduleResource = new FacilityEventScheduleResource();
        ReflectionTestUtils.setField(facilityEventScheduleResource, "facilityEventScheduleRepository", facilityEventScheduleRepository);
        this.restFacilityEventScheduleMockMvc = MockMvcBuilders.standaloneSetup(facilityEventScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventSchedule = new FacilityEventSchedule();
        facilityEventSchedule.setDescription(DEFAULT_DESCRIPTION);
        facilityEventSchedule.setStart(DEFAULT_START);
        facilityEventSchedule.setEnd(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createFacilityEventSchedule() throws Exception {
        int databaseSizeBeforeCreate = facilityEventScheduleRepository.findAll().size();

        // Create the FacilityEventSchedule

        restFacilityEventScheduleMockMvc.perform(post("/api/facility-event-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventSchedule)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventSchedule in the database
        List<FacilityEventSchedule> facilityEventSchedules = facilityEventScheduleRepository.findAll();
        assertThat(facilityEventSchedules).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventSchedule testFacilityEventSchedule = facilityEventSchedules.get(facilityEventSchedules.size() - 1);
        assertThat(testFacilityEventSchedule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFacilityEventSchedule.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testFacilityEventSchedule.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventScheduleRepository.findAll().size();
        // set the field null
        facilityEventSchedule.setStart(null);

        // Create the FacilityEventSchedule, which fails.

        restFacilityEventScheduleMockMvc.perform(post("/api/facility-event-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventSchedule)))
                .andExpect(status().isBadRequest());

        List<FacilityEventSchedule> facilityEventSchedules = facilityEventScheduleRepository.findAll();
        assertThat(facilityEventSchedules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventScheduleRepository.findAll().size();
        // set the field null
        facilityEventSchedule.setEnd(null);

        // Create the FacilityEventSchedule, which fails.

        restFacilityEventScheduleMockMvc.perform(post("/api/facility-event-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventSchedule)))
                .andExpect(status().isBadRequest());

        List<FacilityEventSchedule> facilityEventSchedules = facilityEventScheduleRepository.findAll();
        assertThat(facilityEventSchedules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityEventSchedules() throws Exception {
        // Initialize the database
        facilityEventScheduleRepository.saveAndFlush(facilityEventSchedule);

        // Get all the facilityEventSchedules
        restFacilityEventScheduleMockMvc.perform(get("/api/facility-event-schedules?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventSchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START_STR)))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END_STR)));
    }

    @Test
    @Transactional
    public void getFacilityEventSchedule() throws Exception {
        // Initialize the database
        facilityEventScheduleRepository.saveAndFlush(facilityEventSchedule);

        // Get the facilityEventSchedule
        restFacilityEventScheduleMockMvc.perform(get("/api/facility-event-schedules/{id}", facilityEventSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventSchedule.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START_STR))
            .andExpect(jsonPath("$.end").value(DEFAULT_END_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventSchedule() throws Exception {
        // Get the facilityEventSchedule
        restFacilityEventScheduleMockMvc.perform(get("/api/facility-event-schedules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventSchedule() throws Exception {
        // Initialize the database
        facilityEventScheduleRepository.saveAndFlush(facilityEventSchedule);
        int databaseSizeBeforeUpdate = facilityEventScheduleRepository.findAll().size();

        // Update the facilityEventSchedule
        FacilityEventSchedule updatedFacilityEventSchedule = new FacilityEventSchedule();
        updatedFacilityEventSchedule.setId(facilityEventSchedule.getId());
        updatedFacilityEventSchedule.setDescription(UPDATED_DESCRIPTION);
        updatedFacilityEventSchedule.setStart(UPDATED_START);
        updatedFacilityEventSchedule.setEnd(UPDATED_END);

        restFacilityEventScheduleMockMvc.perform(put("/api/facility-event-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventSchedule)))
                .andExpect(status().isOk());

        // Validate the FacilityEventSchedule in the database
        List<FacilityEventSchedule> facilityEventSchedules = facilityEventScheduleRepository.findAll();
        assertThat(facilityEventSchedules).hasSize(databaseSizeBeforeUpdate);
        FacilityEventSchedule testFacilityEventSchedule = facilityEventSchedules.get(facilityEventSchedules.size() - 1);
        assertThat(testFacilityEventSchedule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFacilityEventSchedule.getStart()).isEqualTo(UPDATED_START);
        assertThat(testFacilityEventSchedule.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void deleteFacilityEventSchedule() throws Exception {
        // Initialize the database
        facilityEventScheduleRepository.saveAndFlush(facilityEventSchedule);
        int databaseSizeBeforeDelete = facilityEventScheduleRepository.findAll().size();

        // Get the facilityEventSchedule
        restFacilityEventScheduleMockMvc.perform(delete("/api/facility-event-schedules/{id}", facilityEventSchedule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventSchedule> facilityEventSchedules = facilityEventScheduleRepository.findAll();
        assertThat(facilityEventSchedules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
