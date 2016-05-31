package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventServiceReservation;
import com.brm.repository.FacilityEventServiceReservationRepository;
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
 * REST controller for managing FacilityEventServiceReservation.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventServiceReservationResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventServiceReservationResource.class);

    @Inject
    private FacilityEventServiceReservationRepository facilityEventServiceReservationRepository;

    /**
     * POST  /facility-event-service-reservations : Create a new facilityEventServiceReservation.
     *
     * @param facilityEventServiceReservation the facilityEventServiceReservation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventServiceReservation, or with status 400 (Bad Request) if the facilityEventServiceReservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-service-reservations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceReservation> createFacilityEventServiceReservation(@Valid @RequestBody FacilityEventServiceReservation facilityEventServiceReservation) throws URISyntaxException {
        log.debug("REST request to save FacilityEventServiceReservation : {}", facilityEventServiceReservation);
        if (facilityEventServiceReservation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventServiceReservation", "idexists", "A new facilityEventServiceReservation cannot already have an ID")).body(null);
        }
        FacilityEventServiceReservation result = facilityEventServiceReservationRepository.save(facilityEventServiceReservation);
        return ResponseEntity.created(new URI("/api/facility-event-service-reservations/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventServiceReservation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-service-reservations : Updates an existing facilityEventServiceReservation.
     *
     * @param facilityEventServiceReservation the facilityEventServiceReservation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventServiceReservation,
     * or with status 400 (Bad Request) if the facilityEventServiceReservation is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventServiceReservation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-service-reservations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceReservation> updateFacilityEventServiceReservation(@Valid @RequestBody FacilityEventServiceReservation facilityEventServiceReservation) throws URISyntaxException {
        log.debug("REST request to update FacilityEventServiceReservation : {}", facilityEventServiceReservation);
        if (facilityEventServiceReservation.getId() == null) {
            return createFacilityEventServiceReservation(facilityEventServiceReservation);
        }
        FacilityEventServiceReservation result = facilityEventServiceReservationRepository.save(facilityEventServiceReservation);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventServiceReservation", facilityEventServiceReservation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-service-reservations : get all the facilityEventServiceReservations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventServiceReservations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-service-reservations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventServiceReservation>> getAllFacilityEventServiceReservations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventServiceReservations");
        Page<FacilityEventServiceReservation> page = facilityEventServiceReservationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-service-reservations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-service-reservations/:id : get the "id" facilityEventServiceReservation.
     *
     * @param id the id of the facilityEventServiceReservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventServiceReservation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-service-reservations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceReservation> getFacilityEventServiceReservation(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventServiceReservation : {}", id);
        FacilityEventServiceReservation facilityEventServiceReservation = facilityEventServiceReservationRepository.findOne(id);
        return Optional.ofNullable(facilityEventServiceReservation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-service-reservations/:id : delete the "id" facilityEventServiceReservation.
     *
     * @param id the id of the facilityEventServiceReservation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-service-reservations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventServiceReservation(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventServiceReservation : {}", id);
        facilityEventServiceReservationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventServiceReservation", id.toString())).build();
    }

}
