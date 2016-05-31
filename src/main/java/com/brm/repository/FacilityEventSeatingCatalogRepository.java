package com.brm.repository;

import com.brm.domain.FacilityEventSeatingCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventSeatingCatalog entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventSeatingCatalogRepository extends JpaRepository<FacilityEventSeatingCatalog,Long> {

}
