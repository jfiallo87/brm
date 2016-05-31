package com.brm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brm.domain.ServiceCatalog;
import com.brm.repository.ServiceCatalogRepository;
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
 * REST controller for managing ServiceCatalog.
 */
@RestController
@RequestMapping("/api")
public class ServiceCatalogResource {

    private final Logger log = LoggerFactory.getLogger(ServiceCatalogResource.class);

    @Inject
    private ServiceCatalogRepository serviceCatalogRepository;

    /**
     * POST  /service-catalogs : Create a new serviceCatalog.
     *
     * @param serviceCatalog the serviceCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceCatalog, or with status 400 (Bad Request) if the serviceCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/service-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCatalog> createServiceCatalog(@Valid @RequestBody ServiceCatalog serviceCatalog) throws URISyntaxException {
        log.debug("REST request to save ServiceCatalog : {}", serviceCatalog);
        if (serviceCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("serviceCatalog", "idexists", "A new serviceCatalog cannot already have an ID")).body(null);
        }
        ServiceCatalog result = serviceCatalogRepository.save(serviceCatalog);
        return ResponseEntity.created(new URI("/api/service-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("serviceCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-catalogs : Updates an existing serviceCatalog.
     *
     * @param serviceCatalog the serviceCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceCatalog,
     * or with status 400 (Bad Request) if the serviceCatalog is not valid,
     * or with status 500 (Internal Server Error) if the serviceCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/service-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCatalog> updateServiceCatalog(@Valid @RequestBody ServiceCatalog serviceCatalog) throws URISyntaxException {
        log.debug("REST request to update ServiceCatalog : {}", serviceCatalog);
        if (serviceCatalog.getId() == null) {
            return createServiceCatalog(serviceCatalog);
        }
        ServiceCatalog result = serviceCatalogRepository.save(serviceCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("serviceCatalog", serviceCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-catalogs : get all the serviceCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/service-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ServiceCatalog>> getAllServiceCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ServiceCatalogs");
        Page<ServiceCatalog> page = serviceCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/service-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-catalogs/:id : get the "id" serviceCatalog.
     *
     * @param id the id of the serviceCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/service-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ServiceCatalog> getServiceCatalog(@PathVariable Long id) {
        log.debug("REST request to get ServiceCatalog : {}", id);
        ServiceCatalog serviceCatalog = serviceCatalogRepository.findOne(id);
        return Optional.ofNullable(serviceCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /service-catalogs/:id : delete the "id" serviceCatalog.
     *
     * @param id the id of the serviceCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/service-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServiceCatalog(@PathVariable Long id) {
        log.debug("REST request to delete ServiceCatalog : {}", id);
        serviceCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("serviceCatalog", id.toString())).build();
    }

}
