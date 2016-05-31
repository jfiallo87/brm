package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilitySchedule;
import com.brm.repository.FacilityScheduleRepository;
import com.brm.web.rest.util.HeaderUtils;
import com.brm.web.rest.util.PaginationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FacilitySchedule.
 */
@RestController
@RequestMapping("/api")
public class FacilityScheduleResource {

    private final Logger log = LoggerFactory.getLogger(FacilityScheduleResource.class);

    @Inject
    private FacilityScheduleRepository facilityScheduleRepository;

    /**
     * POST  /facility-schedules : Create a new facilitySchedule.
     *
     * @param facilitySchedule the facilitySchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilitySchedule, or with status 400 (Bad Request) if the facilitySchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-schedules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilitySchedule> createFacilitySchedule(@Valid @RequestBody FacilitySchedule facilitySchedule) throws URISyntaxException {
        log.debug("REST request to save FacilitySchedule : {}", facilitySchedule);
        if (facilitySchedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilitySchedule", "idexists", "A new facilitySchedule cannot already have an ID")).body(null);
        }
        FacilitySchedule result = facilityScheduleRepository.save(facilitySchedule);
        return ResponseEntity.created(new URI("/api/facility-schedules/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilitySchedule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-schedules : Updates an existing facilitySchedule.
     *
     * @param facilitySchedule the facilitySchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilitySchedule,
     * or with status 400 (Bad Request) if the facilitySchedule is not valid,
     * or with status 500 (Internal Server Error) if the facilitySchedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-schedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilitySchedule> updateFacilitySchedule(@Valid @RequestBody FacilitySchedule facilitySchedule) throws URISyntaxException {
        log.debug("REST request to update FacilitySchedule : {}", facilitySchedule);
        if (facilitySchedule.getId() == null) {
            return createFacilitySchedule(facilitySchedule);
        }
        FacilitySchedule result = facilityScheduleRepository.save(facilitySchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilitySchedule", facilitySchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-schedules : get all the facilitySchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilitySchedules in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-schedules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilitySchedule>> getAllFacilitySchedules(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilitySchedules");
        Page<FacilitySchedule> page = facilityScheduleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-schedules/:id : get the "id" facilitySchedule.
     *
     * @param id the id of the facilitySchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilitySchedule, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-schedules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilitySchedule> getFacilitySchedule(@PathVariable Long id) {
        log.debug("REST request to get FacilitySchedule : {}", id);
        FacilitySchedule facilitySchedule = facilityScheduleRepository.findOne(id);
        return Optional.ofNullable(facilitySchedule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-schedules/:id : delete the "id" facilitySchedule.
     *
     * @param id the id of the facilitySchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-schedules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilitySchedule(@PathVariable Long id) {
        log.debug("REST request to delete FacilitySchedule : {}", id);
        facilityScheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilitySchedule", id.toString())).build();
    }

}
