package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilitySchedule;
import com.brm.repository.FacilityScheduleRepository;

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
 * Test class for the FacilityScheduleResource REST controller.
 *
 * @see FacilityScheduleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityScheduleResourceIntTest {

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
    private FacilityScheduleRepository facilityScheduleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityScheduleMockMvc;

    private FacilitySchedule facilitySchedule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityScheduleResource facilityScheduleResource = new FacilityScheduleResource();
        ReflectionTestUtils.setField(facilityScheduleResource, "facilityScheduleRepository", facilityScheduleRepository);
        this.restFacilityScheduleMockMvc = MockMvcBuilders.standaloneSetup(facilityScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilitySchedule = new FacilitySchedule();
        facilitySchedule.setDescription(DEFAULT_DESCRIPTION);
        facilitySchedule.setStart(DEFAULT_START);
        facilitySchedule.setEnd(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createFacilitySchedule() throws Exception {
        int databaseSizeBeforeCreate = facilityScheduleRepository.findAll().size();

        // Create the FacilitySchedule

        restFacilityScheduleMockMvc.perform(post("/api/facility-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilitySchedule)))
                .andExpect(status().isCreated());

        // Validate the FacilitySchedule in the database
        List<FacilitySchedule> facilitySchedules = facilityScheduleRepository.findAll();
        assertThat(facilitySchedules).hasSize(databaseSizeBeforeCreate + 1);
        FacilitySchedule testFacilitySchedule = facilitySchedules.get(facilitySchedules.size() - 1);
        assertThat(testFacilitySchedule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFacilitySchedule.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testFacilitySchedule.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityScheduleRepository.findAll().size();
        // set the field null
        facilitySchedule.setStart(null);

        // Create the FacilitySchedule, which fails.

        restFacilityScheduleMockMvc.perform(post("/api/facility-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilitySchedule)))
                .andExpect(status().isBadRequest());

        List<FacilitySchedule> facilitySchedules = facilityScheduleRepository.findAll();
        assertThat(facilitySchedules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityScheduleRepository.findAll().size();
        // set the field null
        facilitySchedule.setEnd(null);

        // Create the FacilitySchedule, which fails.

        restFacilityScheduleMockMvc.perform(post("/api/facility-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilitySchedule)))
                .andExpect(status().isBadRequest());

        List<FacilitySchedule> facilitySchedules = facilityScheduleRepository.findAll();
        assertThat(facilitySchedules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilitySchedules() throws Exception {
        // Initialize the database
        facilityScheduleRepository.saveAndFlush(facilitySchedule);

        // Get all the facilitySchedules
        restFacilityScheduleMockMvc.perform(get("/api/facility-schedules?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilitySchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START_STR)))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END_STR)));
    }

    @Test
    @Transactional
    public void getFacilitySchedule() throws Exception {
        // Initialize the database
        facilityScheduleRepository.saveAndFlush(facilitySchedule);

        // Get the facilitySchedule
        restFacilityScheduleMockMvc.perform(get("/api/facility-schedules/{id}", facilitySchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilitySchedule.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START_STR))
            .andExpect(jsonPath("$.end").value(DEFAULT_END_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFacilitySchedule() throws Exception {
        // Get the facilitySchedule
        restFacilityScheduleMockMvc.perform(get("/api/facility-schedules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilitySchedule() throws Exception {
        // Initialize the database
        facilityScheduleRepository.saveAndFlush(facilitySchedule);
        int databaseSizeBeforeUpdate = facilityScheduleRepository.findAll().size();

        // Update the facilitySchedule
        FacilitySchedule updatedFacilitySchedule = new FacilitySchedule();
        updatedFacilitySchedule.setId(facilitySchedule.getId());
        updatedFacilitySchedule.setDescription(UPDATED_DESCRIPTION);
        updatedFacilitySchedule.setStart(UPDATED_START);
        updatedFacilitySchedule.setEnd(UPDATED_END);

        restFacilityScheduleMockMvc.perform(put("/api/facility-schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilitySchedule)))
                .andExpect(status().isOk());

        // Validate the FacilitySchedule in the database
        List<FacilitySchedule> facilitySchedules = facilityScheduleRepository.findAll();
        assertThat(facilitySchedules).hasSize(databaseSizeBeforeUpdate);
        FacilitySchedule testFacilitySchedule = facilitySchedules.get(facilitySchedules.size() - 1);
        assertThat(testFacilitySchedule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFacilitySchedule.getStart()).isEqualTo(UPDATED_START);
        assertThat(testFacilitySchedule.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void deleteFacilitySchedule() throws Exception {
        // Initialize the database
        facilityScheduleRepository.saveAndFlush(facilitySchedule);
        int databaseSizeBeforeDelete = facilityScheduleRepository.findAll().size();

        // Get the facilitySchedule
        restFacilityScheduleMockMvc.perform(delete("/api/facility-schedules/{id}", facilitySchedule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilitySchedule> facilitySchedules = facilityScheduleRepository.findAll();
        assertThat(facilitySchedules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
