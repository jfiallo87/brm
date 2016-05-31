package com.brm.repository;

import com.brm.domain.FacilityEventServiceCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventServiceCatalog entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventServiceCatalogRepository extends JpaRepository<FacilityEventServiceCatalog,Long> {

}
