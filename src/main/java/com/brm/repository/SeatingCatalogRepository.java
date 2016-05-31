package com.brm.repository;

import com.brm.domain.SeatingCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SeatingCatalog entity.
 */
@SuppressWarnings("unused")
public interface SeatingCatalogRepository extends JpaRepository<SeatingCatalog,Long> {

}
