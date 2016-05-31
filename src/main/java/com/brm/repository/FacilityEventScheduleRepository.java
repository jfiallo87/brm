package com.brm.repository;

import com.brm.domain.FacilityEventSchedule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilityEventSchedule entity.
 */
@SuppressWarnings("unused")
public interface FacilityEventScheduleRepository extends JpaRepository<FacilityEventSchedule,Long> {

}
