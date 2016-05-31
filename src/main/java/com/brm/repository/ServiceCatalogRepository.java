package com.brm.repository;

import com.brm.domain.ServiceCatalog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceCatalog entity.
 */
@SuppressWarnings("unused")
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog,Long> {

}
