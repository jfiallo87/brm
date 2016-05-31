package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.FacilityCatalog;
import com.brm.repository.FacilityCatalogRepository;
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
 * REST controller for managing FacilityCatalog.
 */
@RestController
@RequestMapping("/api")
public class FacilityCatalogResource {

    private final Logger log = LoggerFactory.getLogger(FacilityCatalogResource.class);

    @Inject
    private FacilityCatalogRepository facilityCatalogRepository;

    /**
     * POST  /facility-catalogs : Create a new facilityCatalog.
     *
     * @param facilityCatalog the facilityCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facilityCatalog, or with status 400 (Bad Request) if the facilityCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityCatalog> createFacilityCatalog(@Valid @RequestBody FacilityCatalog facilityCatalog) throws URISyntaxException {
        log.debug("REST request to save FacilityCatalog : {}", facilityCatalog);
        if (facilityCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("facilityCatalog", "idexists", "A new facilityCatalog cannot already have an ID")).body(null);
        }
        FacilityCatalog result = facilityCatalogRepository.save(facilityCatalog);
        return ResponseEntity.created(new URI("/api/facility-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("facilityCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facility-catalogs : Updates an existing facilityCatalog.
     *
     * @param facilityCatalog the facilityCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facilityCatalog,
     * or with status 400 (Bad Request) if the facilityCatalog is not valid,
     * or with status 500 (Internal Server Error) if the facilityCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/facility-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityCatalog> updateFacilityCatalog(@Valid @RequestBody FacilityCatalog facilityCatalog) throws URISyntaxException {
        log.debug("REST request to update FacilityCatalog : {}", facilityCatalog);
        if (facilityCatalog.getId() == null) {
            return createFacilityCatalog(facilityCatalog);
        }
        FacilityCatalog result = facilityCatalogRepository.save(facilityCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("facilityCatalog", facilityCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facility-catalogs : get all the facilityCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facilityCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/facility-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FacilityCatalog>> getAllFacilityCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FacilityCatalogs");
        Page<FacilityCatalog> page = facilityCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/facility-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facility-catalogs/:id : get the "id" facilityCatalog.
     *
     * @param id the id of the facilityCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facilityCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/facility-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FacilityCatalog> getFacilityCatalog(@PathVariable Long id) {
        log.debug("REST request to get FacilityCatalog : {}", id);
        FacilityCatalog facilityCatalog = facilityCatalogRepository.findOne(id);
        return Optional.ofNullable(facilityCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facility-catalogs/:id : delete the "id" facilityCatalog.
     *
     * @param id the id of the facilityCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/facility-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFacilityCatalog(@PathVariable Long id) {
        log.debug("REST request to delete FacilityCatalog : {}", id);
        facilityCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("facilityCatalog", id.toString())).build();
    }

}
