package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventCatalog;
import com.brm.repository.FacilityEventCatalogRepository;
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
 * REST controller for managing FacilityEventCatalog.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventCatalogResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventCatalogResource.class);

    @Inject
    private FacilityEventCatalogRepository facilityEventCatalogRepository;

    /**
     * POST  /facility-event-catalogs : Create a new facilityEventCatalog.
     *
     * @param facilityEventCatalog the facilityEventCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventCatalog, or with status 400 (Bad Request) if the facilityEventCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventCatalog> createFacilityEventCatalog(@Valid @RequestBody FacilityEventCatalog facilityEventCatalog) throws URISyntaxException {
        log.debug("REST request to save FacilityEventCatalog : {}", facilityEventCatalog);
        if (facilityEventCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventCatalog", "idexists", "A new facilityEventCatalog cannot already have an ID")).body(null);
        }
        FacilityEventCatalog result = facilityEventCatalogRepository.save(facilityEventCatalog);
        return ResponseEntity.created(new URI("/api/facility-event-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-catalogs : Updates an existing facilityEventCatalog.
     *
     * @param facilityEventCatalog the facilityEventCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventCatalog,
     * or with status 400 (Bad Request) if the facilityEventCatalog is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventCatalog> updateFacilityEventCatalog(@Valid @RequestBody FacilityEventCatalog facilityEventCatalog) throws URISyntaxException {
        log.debug("REST request to update FacilityEventCatalog : {}", facilityEventCatalog);
        if (facilityEventCatalog.getId() == null) {
            return createFacilityEventCatalog(facilityEventCatalog);
        }
        FacilityEventCatalog result = facilityEventCatalogRepository.save(facilityEventCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventCatalog", facilityEventCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-catalogs : get all the facilityEventCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventCatalog>> getAllFacilityEventCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventCatalogs");
        Page<FacilityEventCatalog> page = facilityEventCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-catalogs/:id : get the "id" facilityEventCatalog.
     *
     * @param id the id of the facilityEventCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventCatalog> getFacilityEventCatalog(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventCatalog : {}", id);
        FacilityEventCatalog facilityEventCatalog = facilityEventCatalogRepository.findOne(id);
        return Optional.ofNullable(facilityEventCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-catalogs/:id : delete the "id" facilityEventCatalog.
     *
     * @param id the id of the facilityEventCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventCatalog(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventCatalog : {}", id);
        facilityEventCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventCatalog", id.toString())).build();
    }

}
