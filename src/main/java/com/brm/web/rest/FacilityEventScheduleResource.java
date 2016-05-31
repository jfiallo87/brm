package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventSchedule;
import com.brm.repository.FacilityEventScheduleRepository;
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
 * REST controller for managing FacilityEventSchedule.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventScheduleResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventScheduleResource.class);

    @Inject
    private FacilityEventScheduleRepository facilityEventScheduleRepository;

    /**
     * POST  /facility-event-schedules : Create a new facilityEventSchedule.
     *
     * @param facilityEventSchedule the facilityEventSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventSchedule, or with status 400 (Bad Request) if the facilityEventSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-schedules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSchedule> createFacilityEventSchedule(@Valid @RequestBody FacilityEventSchedule facilityEventSchedule) throws URISyntaxException {
        log.debug("REST request to save FacilityEventSchedule : {}", facilityEventSchedule);
        if (facilityEventSchedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventSchedule", "idexists", "A new facilityEventSchedule cannot already have an ID")).body(null);
        }
        FacilityEventSchedule result = facilityEventScheduleRepository.save(facilityEventSchedule);
        return ResponseEntity.created(new URI("/api/facility-event-schedules/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventSchedule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-schedules : Updates an existing facilityEventSchedule.
     *
     * @param facilityEventSchedule the facilityEventSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventSchedule,
     * or with status 400 (Bad Request) if the facilityEventSchedule is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventSchedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-schedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSchedule> updateFacilityEventSchedule(@Valid @RequestBody FacilityEventSchedule facilityEventSchedule) throws URISyntaxException {
        log.debug("REST request to update FacilityEventSchedule : {}", facilityEventSchedule);
        if (facilityEventSchedule.getId() == null) {
            return createFacilityEventSchedule(facilityEventSchedule);
        }
        FacilityEventSchedule result = facilityEventScheduleRepository.save(facilityEventSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventSchedule", facilityEventSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-schedules : get all the facilityEventSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventSchedules in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-schedules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventSchedule>> getAllFacilityEventSchedules(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventSchedules");
        Page<FacilityEventSchedule> page = facilityEventScheduleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-schedules/:id : get the "id" facilityEventSchedule.
     *
     * @param id the id of the facilityEventSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventSchedule, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-schedules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSchedule> getFacilityEventSchedule(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventSchedule : {}", id);
        FacilityEventSchedule facilityEventSchedule = facilityEventScheduleRepository.findOne(id);
        return Optional.ofNullable(facilityEventSchedule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-schedules/:id : delete the "id" facilityEventSchedule.
     *
     * @param id the id of the facilityEventSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-schedules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventSchedule(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventSchedule : {}", id);
        facilityEventScheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventSchedule", id.toString())).build();
    }

}
