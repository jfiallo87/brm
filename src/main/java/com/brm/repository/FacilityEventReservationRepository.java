package com.brm.repository;

import com.brm.domain.FacilityEventReservation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventReservation entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventReservationRepository extends JpaRepository<FacilityEventReservation,Long> {

}
