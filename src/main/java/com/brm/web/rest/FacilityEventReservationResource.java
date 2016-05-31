package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventReservation;
import com.brm.repository.FacilityEventReservationRepository;
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
 * REST controller for managing FacilityEventReservation.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventReservationResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventReservationResource.class);

    @Inject
    private FacilityEventReservationRepository facilityEventReservationRepository;

    /**
     * POST  /facility-event-reservations : Create a new facilityEventReservation.
     *
     * @param facilityEventReservation the facilityEventReservation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventReservation, or with status 400 (Bad Request) if the facilityEventReservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-reservations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventReservation> createFacilityEventReservation(@Valid @RequestBody FacilityEventReservation facilityEventReservation) throws URISyntaxException {
        log.debug("REST request to save FacilityEventReservation : {}", facilityEventReservation);
        if (facilityEventReservation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventReservation", "idexists", "A new facilityEventReservation cannot already have an ID")).body(null);
        }
        FacilityEventReservation result = facilityEventReservationRepository.save(facilityEventReservation);
        return ResponseEntity.created(new URI("/api/facility-event-reservations/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventReservation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-reservations : Updates an existing facilityEventReservation.
     *
     * @param facilityEventReservation the facilityEventReservation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventReservation,
     * or with status 400 (Bad Request) if the facilityEventReservation is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventReservation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-reservations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventReservation> updateFacilityEventReservation(@Valid @RequestBody FacilityEventReservation facilityEventReservation) throws URISyntaxException {
        log.debug("REST request to update FacilityEventReservation : {}", facilityEventReservation);
        if (facilityEventReservation.getId() == null) {
            return createFacilityEventReservation(facilityEventReservation);
        }
        FacilityEventReservation result = facilityEventReservationRepository.save(facilityEventReservation);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventReservation", facilityEventReservation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-reservations : get all the facilityEventReservations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventReservations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-reservations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventReservation>> getAllFacilityEventReservations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventReservations");
        Page<FacilityEventReservation> page = facilityEventReservationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-reservations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-reservations/:id : get the "id" facilityEventReservation.
     *
     * @param id the id of the facilityEventReservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventReservation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-reservations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventReservation> getFacilityEventReservation(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventReservation : {}", id);
        FacilityEventReservation facilityEventReservation = facilityEventReservationRepository.findOne(id);
        return Optional.ofNullable(facilityEventReservation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-reservations/:id : delete the "id" facilityEventReservation.
     *
     * @param id the id of the facilityEventReservation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-reservations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventReservation(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventReservation : {}", id);
        facilityEventReservationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventReservation", id.toString())).build();
    }

}
