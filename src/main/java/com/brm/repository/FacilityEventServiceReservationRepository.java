package com.brm.repository;

import com.brm.domain.FacilityEventServiceReservation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventServiceReservation entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventServiceReservationRepository extends JpaRepository<FacilityEventServiceReservation,Long> {

}
