package com.brm.web.rest;

import com.brm.domain.EventCatalog;
import com.brm.repository.EventCatalogRepository;
import com.brm.web.rest.util.HeaderUtils;
import com.brm.web.rest.util.PaginationUtils;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;

/**
 * REST controller for managing EventCatalog.
 */
@RestController
@RequestMapping("/api")
public class EventCatalogResource {

    private final Logger log = LoggerFactory.getLogger(EventCatalogResource.class);

    @Inject
    private EventCatalogRepository eventCatalogRepository;

    /**
     * POST  /event-catalogs : Create a new eventCatalog.
     *
     * @param eventCatalog the eventCatalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventCatalog, or with status 400 (Bad Request) if the eventCatalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/event-catalogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventCatalog> createEventCatalog(@Valid @RequestBody EventCatalog eventCatalog) throws URISyntaxException {
        log.debug("REST request to save EventCatalog : {}", eventCatalog);
        if (eventCatalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtils.createFailureAlert("eventCatalog", "idexists", "A new eventCatalog cannot already have an ID")).body(null);
        }
        EventCatalog result = eventCatalogRepository.save(eventCatalog);
        return ResponseEntity.created(new URI("/api/event-catalogs/" + result.getId()))
            .headers(HeaderUtils.createEntityCreationAlert("eventCatalog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-catalogs : Updates an existing eventCatalog.
     *
     * @param eventCatalog the eventCatalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventCatalog,
     * or with status 400 (Bad Request) if the eventCatalog is not valid,
     * or with status 500 (Internal Server Error) if the eventCatalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/event-catalogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventCatalog> updateEventCatalog(@Valid @RequestBody EventCatalog eventCatalog) throws URISyntaxException {
        log.debug("REST request to update EventCatalog : {}", eventCatalog);
        if (eventCatalog.getId() == null) {
            return createEventCatalog(eventCatalog);
        }
        EventCatalog result = eventCatalogRepository.save(eventCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtils.createEntityUpdateAlert("eventCatalog", eventCatalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-catalogs : get all the eventCatalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventCatalogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/event-catalogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventCatalog>> getAllEventCatalogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EventCatalogs");
        Page<EventCatalog> page = eventCatalogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(page, "/api/event-catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /event-catalogs/:id : get the "id" eventCatalog.
     *
     * @param id the id of the eventCatalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventCatalog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/event-catalogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventCatalog> getEventCatalog(@PathVariable Long id) {
        log.debug("REST request to get EventCatalog : {}", id);
        EventCatalog eventCatalog = eventCatalogRepository.findOne(id);
        return Optional.ofNullable(eventCatalog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /event-catalogs/:id : delete the "id" eventCatalog.
     *
     * @param id the id of the eventCatalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/event-catalogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEventCatalog(@PathVariable Long id) {
        log.debug("REST request to delete EventCatalog : {}", id);
        eventCatalogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtils.createEntityDeletionAlert("eventCatalog", id.toString())).build();
    }

}
