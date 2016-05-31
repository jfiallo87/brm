package com.brm.repository;

import com.brm.domain.EventCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventCatalog entity.
 */
@SuppressWarnings("unused")
public interface EventCatalogRepository extends JpaRepository<EventCatalog,Long> {

}
