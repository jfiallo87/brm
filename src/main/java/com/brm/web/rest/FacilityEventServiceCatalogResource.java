package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityEventServiceCatalog;
import com.brm.repository.FacilityEventServiceCatalogRepository;
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
 * REST controller for managing FacilityEventServiceCatalog.
 */
@RestController
@RequestMapping("/api")
public class FacilityEventServiceCatalogResource {

    private final Logger log = LoggerFactory.getLogger(FacilityEventServiceCatalogResource.class);

    @Inject
    private FacilityEventServiceCatalogRepository facilityEventServiceCatalogRepository;

    /**
     * POST  /facility-event-service-catalogs : Create a new facilityEventServiceCatalog.
     *
     * @param facilityEventServiceCatalog the facilityEventServiceCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityEventServiceCatalog, or with status 400 (Bad Request) if the facilityEventServiceCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-service-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceCatalog> createFacilityEventServiceCatalog(@Valid @RequestBody FacilityEventServiceCatalog facilityEventServiceCatalog) throws URISyntaxException {
        log.debug("REST request to save FacilityEventServiceCatalog : {}", facilityEventServiceCatalog);
        if (facilityEventServiceCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityEventServiceCatalog", "idexists", "A new facilityEventServiceCatalog cannot already have an ID")).body(null);
        }
        FacilityEventServiceCatalog result = facilityEventServiceCatalogRepository.save(facilityEventServiceCatalog);
        return ResponseEntity.created(new URI("/api/facility-event-service-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityEventServiceCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-event-service-catalogs : Updates an existing facilityEventServiceCatalog.
     *
     * @param facilityEventServiceCatalog the facilityEventServiceCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityEventServiceCatalog,
     * or with status 400 (Bad Request) if the facilityEventServiceCatalog is not valid,
     * or with status 500 (Internal Server Error) if the facilityEventServiceCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-event-service-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceCatalog> updateFacilityEventServiceCatalog(@Valid @RequestBody FacilityEventServiceCatalog facilityEventServiceCatalog) throws URISyntaxException {
        log.debug("REST request to update FacilityEventServiceCatalog : {}", facilityEventServiceCatalog);
        if (facilityEventServiceCatalog.getId() == null) {
            return createFacilityEventServiceCatalog(facilityEventServiceCatalog);
        }
        FacilityEventServiceCatalog result = facilityEventServiceCatalogRepository.save(facilityEventServiceCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityEventServiceCatalog", facilityEventServiceCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-event-service-catalogs : get all the facilityEventServiceCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityEventServiceCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-event-service-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityEventServiceCatalog>> getAllFacilityEventServiceCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityEventServiceCatalogs");
        Page<FacilityEventServiceCatalog> page = facilityEventServiceCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-event-service-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-event-service-catalogs/:id : get the "id" facilityEventServiceCatalog.
     *
     * @param id the id of the facilityEventServiceCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityEventServiceCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-event-service-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityEventServiceCatalog> getFacilityEventServiceCatalog(@PathVariable Long id) {
        log.debug("REST request to get FacilityEventServiceCatalog : {}", id);
        FacilityEventServiceCatalog facilityEventServiceCatalog = facilityEventServiceCatalogRepository.findOne(id);
        return Optional.ofNullable(facilityEventServiceCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-event-service-catalogs/:id : delete the "id" facilityEventServiceCatalog.
     *
     * @param id the id of the facilityEventServiceCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-event-service-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityEventServiceCatalog(@PathVariable Long id) {
        log.debug("REST request to delete FacilityEventServiceCatalog : {}", id);
        facilityEventServiceCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityEventServiceCatalog", id.toString())).build();
    }

}
