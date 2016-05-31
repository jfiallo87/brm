package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.SeatingCatalog;
import com.brm.repository.SeatingCatalogRepository;
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
 * REST controller for managing SeatingCatalog.
 */
@RestController
@RequestMapping("/api")
public class SeatingCatalogResource {

    private final Logger log = LoggerFactory.getLogger(SeatingCatalogResource.class);

    @Inject
    private SeatingCatalogRepository seatingCatalogRepository;

    /**
     * POST  /seating-catalogs : Create a new seatingCatalog.
     *
     * @param seatingCatalog the seatingCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seatingCatalog, or with status 400 (Bad Request) if the seatingCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/seating-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SeatingCatalog> createSeatingCatalog(@Valid @RequestBody SeatingCatalog seatingCatalog) throws URISyntaxException {
        log.debug("REST request to save SeatingCatalog : {}", seatingCatalog);
        if (seatingCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("seatingCatalog", "idexists", "A new seatingCatalog cannot already have an ID")).body(null);
        }
        SeatingCatalog result = seatingCatalogRepository.save(seatingCatalog);
        return ResponseEntity.created(new URI("/api/seating-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("seatingCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seating-catalogs : Updates an existing seatingCatalog.
     *
     * @param seatingCatalog the seatingCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seatingCatalog,
     * or with status 400 (Bad Request) if the seatingCatalog is not valid,
     * or with status 500 (Internal Server Error) if the seatingCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/seating-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SeatingCatalog> updateSeatingCatalog(@Valid @RequestBody SeatingCatalog seatingCatalog) throws URISyntaxException {
        log.debug("REST request to update SeatingCatalog : {}", seatingCatalog);
        if (seatingCatalog.getId() == null) {
            return createSeatingCatalog(seatingCatalog);
        }
        SeatingCatalog result = seatingCatalogRepository.save(seatingCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("seatingCatalog", seatingCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seating-catalogs : get all the seatingCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seatingCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/seating-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SeatingCatalog>> getAllSeatingCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SeatingCatalogs");
        Page<SeatingCatalog> page = seatingCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/seating-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seating-catalogs/:id : get the "id" seatingCatalog.
     *
     * @param id the id of the seatingCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seatingCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/seating-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SeatingCatalog> getSeatingCatalog(@PathVariable Long id) {
        log.debug("REST request to get SeatingCatalog : {}", id);
        SeatingCatalog seatingCatalog = seatingCatalogRepository.findOne(id);
        return Optional.ofNullable(seatingCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /seating-catalogs/:id : delete the "id" seatingCatalog.
     *
     * @param id the id of the seatingCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/seating-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSeatingCatalog(@PathVariable Long id) {
        log.debug("REST request to delete SeatingCatalog : {}", id);
        seatingCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("seatingCatalog", id.toString())).build();
    }

}
