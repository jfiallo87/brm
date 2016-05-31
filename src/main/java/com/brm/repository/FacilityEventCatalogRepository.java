package com.brm.repository;

import com.brm.domain.FacilityEventCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventCatalog entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventCatalogRepository extends JpaRepository<FacilityEventCatalog,Long> {

}
