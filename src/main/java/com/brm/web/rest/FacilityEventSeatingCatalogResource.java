package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventSeatingCatalog;
import com.brm.repository.FacilityEventSeatingCatalogRepository;
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
 * REST controller for managing FacilityEventSeatingCatalog.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventSeatingCatalogResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventSeatingCatalogResource.class);

    @Inject
    private FacilityEventSeatingCatalogRepository facilityEventSeatingCatalogRepository;

    /**
     * POST  /facility-event-seating-catalogs : Create a new facilityEventSeatingCatalog.
     *
     * @param facilityEventSeatingCatalog the facilityEventSeatingCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventSeatingCatalog, or with status 400 (Bad Request) if the facilityEventSeatingCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-seating-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSeatingCatalog> createFacilityEventSeatingCatalog(@Valid @RequestBody FacilityEventSeatingCatalog facilityEventSeatingCatalog) throws URISyntaxException {
        log.debug("REST request to save FacilityEventSeatingCatalog : {}", facilityEventSeatingCatalog);
        if (facilityEventSeatingCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventSeatingCatalog", "idexists", "A new facilityEventSeatingCatalog cannot already have an ID")).body(null);
        }
        FacilityEventSeatingCatalog result = facilityEventSeatingCatalogRepository.save(facilityEventSeatingCatalog);
        return ResponseEntity.created(new URI("/api/facility-event-seating-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventSeatingCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-seating-catalogs : Updates an existing facilityEventSeatingCatalog.
     *
     * @param facilityEventSeatingCatalog the facilityEventSeatingCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventSeatingCatalog,
     * or with status 400 (Bad Request) if the facilityEventSeatingCatalog is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventSeatingCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-seating-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSeatingCatalog> updateFacilityEventSeatingCatalog(@Valid @RequestBody FacilityEventSeatingCatalog facilityEventSeatingCatalog) throws URISyntaxException {
        log.debug("REST request to update FacilityEventSeatingCatalog : {}", facilityEventSeatingCatalog);
        if (facilityEventSeatingCatalog.getId() == null) {
            return createFacilityEventSeatingCatalog(facilityEventSeatingCatalog);
        }
        FacilityEventSeatingCatalog result = facilityEventSeatingCatalogRepository.save(facilityEventSeatingCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventSeatingCatalog", facilityEventSeatingCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-seating-catalogs : get all the facilityEventSeatingCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventSeatingCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-seating-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventSeatingCatalog>> getAllFacilityEventSeatingCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventSeatingCatalogs");
        Page<FacilityEventSeatingCatalog> page = facilityEventSeatingCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-seating-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-seating-catalogs/:id : get the "id" facilityEventSeatingCatalog.
     *
     * @param id the id of the facilityEventSeatingCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventSeatingCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-seating-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventSeatingCatalog> getFacilityEventSeatingCatalog(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventSeatingCatalog : {}", id);
        FacilityEventSeatingCatalog facilityEventSeatingCatalog = facilityEventSeatingCatalogRepository.findOne(id);
        return Optional.ofNullable(facilityEventSeatingCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-seating-catalogs/:id : delete the "id" facilityEventSeatingCatalog.
     *
     * @param id the id of the facilityEventSeatingCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-seating-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventSeatingCatalog(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventSeatingCatalog : {}", id);
        facilityEventSeatingCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventSeatingCatalog", id.toString())).build();
    }

}
