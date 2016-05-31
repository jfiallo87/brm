package com.brm.repository;

import com.brm.domain.FacilityCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityCatalog entity.
 */
@SuppressWarnings("unused")
public interface FacilityCatalogRepository extends JpaRepository<FacilityCatalog,Long> {

}
