package com.brm.web.rest;

import com.brm.BrmApp;
import com.brm.domain.FacilityEventServiceReservation;
import com.brm.repository.FacilityEventServiceReservationRepository;

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
 * Test class for the FacilityEventServiceReservationResource REST controller.
 *
 * @see FacilityEventServiceReservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BrmApp.class)
@WebAppConfiguration
@IntegrationTest
public class FacilityEventServiceReservationResourceIntTest {


    @Inject
    private FacilityEventServiceReservationRepository facilityEventServiceReservationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacilityEventServiceReservationMockMvc;

    private FacilityEventServiceReservation facilityEventServiceReservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacilityEventServiceReservationResource facilityEventServiceReservationResource = new FacilityEventServiceReservationResource();
        ReflectionTestUtils.setField(facilityEventServiceReservationResource, "facilityEventServiceReservationRepository", facilityEventServiceReservationRepository);
        this.restFacilityEventServiceReservationMockMvc = MockMvcBuilders.standaloneSetup(facilityEventServiceReservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        facilityEventServiceReservation = new FacilityEventServiceReservation();
    }

    @Test
    @Transactional
    public void createFacilityEventServiceReservation() throws Exception {
        int databaseSizeBeforeCreate = facilityEventServiceReservationRepository.findAll().size();

        // Create the FacilityEventServiceReservation

        restFacilityEventServiceReservationMockMvc.perform(post("/api/facility-event-service-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(facilityEventServiceReservation)))
                .andExpect(status().isCreated());

        // Validate the FacilityEventServiceReservation in the database
        List<FacilityEventServiceReservation> facilityEventServiceReservations = facilityEventServiceReservationRepository.findAll();
        assertThat(facilityEventServiceReservations).hasSize(databaseSizeBeforeCreate + 1);
        FacilityEventServiceReservation testFacilityEventServiceReservation = facilityEventServiceReservations.get(facilityEventServiceReservations.size() - 1);
    }

    @Test
    @Transactional
    public void getAllFacilityEventServiceReservations() throws Exception {
        // Initialize the database
        facilityEventServiceReservationRepository.saveAndFlush(facilityEventServiceReservation);

        // Get all the facilityEventServiceReservations
        restFacilityEventServiceReservationMockMvc.perform(get("/api/facility-event-service-reservations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(facilityEventServiceReservation.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFacilityEventServiceReservation() throws Exception {
        // Initialize the database
        facilityEventServiceReservationRepository.saveAndFlush(facilityEventServiceReservation);

        // Get the facilityEventServiceReservation
        restFacilityEventServiceReservationMockMvc.perform(get("/api/facility-event-service-reservations/{id}", facilityEventServiceReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(facilityEventServiceReservation.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityEventServiceReservation() throws Exception {
        // Get the facilityEventServiceReservation
        restFacilityEventServiceReservationMockMvc.perform(get("/api/facility-event-service-reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityEventServiceReservation() throws Exception {
        // Initialize the database
        facilityEventServiceReservationRepository.saveAndFlush(facilityEventServiceReservation);
        int databaseSizeBeforeUpdate = facilityEventServiceReservationRepository.findAll().size();

        // Update the facilityEventServiceReservation
        FacilityEventServiceReservation updatedFacilityEventServiceReservation = new FacilityEventServiceReservation();
        updatedFacilityEventServiceReservation.setId(facilityEventServiceReservation.getId());

        restFacilityEventServiceReservationMockMvc.perform(put("/api/facility-event-service-reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFacilityEventServiceReservation)))
                .andExpect(status().isOk());

        // Validate the FacilityEventServiceReservation in the database
        List<FacilityEventServiceReservation> facilityEventServiceReservations = facilityEventServiceReservationRepository.findAll();
        assertThat(facilityEventServiceReservations).hasSize(databaseSizeBeforeUpdate);
        FacilityEventServiceReservation testFacilityEventServiceReservation = facilityEventServiceReservations.get(facilityEventServiceReservations.size() - 1);
    }

    @Test
    @Transactional
    public void deleteFacilityEventServiceReservation() throws Exception {
        // Initialize the database
        facilityEventServiceReservationRepository.saveAndFlush(facilityEventServiceReservation);
        int databaseSizeBeforeDelete = facilityEventServiceReservationRepository.findAll().size();

        // Get the facilityEventServiceReservation
        restFacilityEventServiceReservationMockMvc.perform(delete("/api/facility-event-service-reservations/{id}", facilityEventServiceReservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FacilityEventServiceReservation> facilityEventServiceReservations = facilityEventServiceReservationRepository.findAll();
        assertThat(facilityEventServiceReservations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
