package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventReservation;
import com.brm.repository.FacilityEventReservationRepository;

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
 * Test class for the FacilityEventReservationResource REST controller.
 *
 * @see FacilityEventReservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventReservationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_STR = dateTimeFormatter.format(DEFAULT_START);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_STR = dateTimeFormatter.format(DEFAULT_END);

    private static final Integer DEFAULT_GUESTS = 1;
    private static final Integer UPDATED_GUESTS = 2;

    @Inject
    private FacilityEventReservationRepository facilityEventReservationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventReservationMockMvc;

    private FacilityEventReservation facilityEventReservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventReservationResource facilityEventReservationResource = new FacilityEventReservationResource();
        ReflectionTestUtils.setField(facilityEventReservationResource, "facilityEventReservationRepository", facilityEventReservationRepository);
        this.restFacilityEventReservationMockMvc = MockMvcBuilders.standaloneSetup(facilityEventReservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventReservation = new FacilityEventReservation();
        facilityEventReservation.setStart(DEFAULT_START);
        facilityEventReservation.setEnd(DEFAULT_END);
        facilityEventReservation.setGuests(DEFAULT_GUESTS);
    }

    @Test
    @Transactional
    public void createFacilityEventReservation() throws Exception {
        int databaseSizeBeforeCreate = facilityEventReservationRepository.findAll().size();

        // Create the FacilityEventReservation

        restFacilityEventReservationMockMvc.perform(post("/api/facility-event-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventReservation)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventReservation in the database
        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventReservation testFacilityEventReservation = facilityEventReservations.get(facilityEventReservations.size() - 1);
        assertThat(testFacilityEventReservation.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testFacilityEventReservation.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testFacilityEventReservation.getGuests()).isEqualTo(DEFAULT_GUESTS);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventReservationRepository.findAll().size();
        // set the field null
        facilityEventReservation.setStart(null);

        // Create the FacilityEventReservation, which fails.

        restFacilityEventReservationMockMvc.perform(post("/api/facility-event-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventReservation)))
                .andExpect(status().isBadRequest());

        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventReservationRepository.findAll().size();
        // set the field null
        facilityEventReservation.setEnd(null);

        // Create the FacilityEventReservation, which fails.

        restFacilityEventReservationMockMvc.perform(post("/api/facility-event-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventReservation)))
                .andExpect(status().isBadRequest());

        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGuestsIsRequired() throws Exception {
        int databaseSizeBeforeTest = facilityEventReservationRepository.findAll().size();
        // set the field null
        facilityEventReservation.setGuests(null);

        // Create the FacilityEventReservation, which fails.

        restFacilityEventReservationMockMvc.perform(post("/api/facility-event-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventReservation)))
                .andExpect(status().isBadRequest());

        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacilityEventReservations() throws Exception {
        // Initialize the database
        facilityEventReservationRepository.saveAndFlush(facilityEventReservation);

        // Get all the facilityEventReservations
        restFacilityEventReservationMockMvc.perform(get("/api/facility-event-reservations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventReservation.getId().intValue())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START_STR)))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END_STR)))
                .andExpect(jsonPath("$.[*].guests").value(hasItem(DEFAULT_GUESTS)));
    }

    @Test
    @Transactional
    public void getFacilityEventReservation() throws Exception {
        // Initialize the database
        facilityEventReservationRepository.saveAndFlush(facilityEventReservation);

        // Get the facilityEventReservation
        restFacilityEventReservationMockMvc.perform(get("/api/facility-event-reservations/{id}", facilityEventReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventReservation.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START_STR))
            .andExpect(jsonPath("$.end").value(DEFAULT_END_STR))
            .andExpect(jsonPath("$.guests").value(DEFAULT_GUESTS));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventReservation() throws Exception {
        // Get the facilityEventReservation
        restFacilityEventReservationMockMvc.perform(get("/api/facility-event-reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventReservation() throws Exception {
        // Initialize the database
        facilityEventReservationRepository.saveAndFlush(facilityEventReservation);
        int databaseSizeBeforeUpdate = facilityEventReservationRepository.findAll().size();

        // Update the facilityEventReservation
        FacilityEventReservation updatedFacilityEventReservation = new FacilityEventReservation();
        updatedFacilityEventReservation.setId(facilityEventReservation.getId());
        updatedFacilityEventReservation.setStart(UPDATED_START);
        updatedFacilityEventReservation.setEnd(UPDATED_END);
        updatedFacilityEventReservation.setGuests(UPDATED_GUESTS);

        restFacilityEventReservationMockMvc.perform(put("/api/facility-event-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventReservation)))
                .andExpect(status().isOk());

        // Validate the FacilityEventReservation in the database
        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeUpdate);
        FacilityEventReservation testFacilityEventReservation = facilityEventReservations.get(facilityEventReservations.size() - 1);
        assertThat(testFacilityEventReservation.getStart()).isEqualTo(UPDATED_START);
        assertThat(testFacilityEventReservation.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testFacilityEventReservation.getGuests()).isEqualTo(UPDATED_GUESTS);
    }

    @Test
    @Transactional
    public void deleteFacilityEventReservation() throws Exception {
        // Initialize the database
        facilityEventReservationRepository.saveAndFlush(facilityEventReservation);
        int databaseSizeBeforeDelete = facilityEventReservationRepository.findAll().size();

        // Get the facilityEventReservation
        restFacilityEventReservationMockMvc.perform(delete("/api/facility-event-reservations/{id}", facilityEventReservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventReservation> facilityEventReservations = facilityEventReservationRepository.findAll();
        assertThat(facilityEventReservations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
