package com.brm.repository;

import com.brm.domain.FacilitySchedule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FacilitySchedule entity.
 */
@SuppressWarnings("unused")
public interface FacilityScheduleRepository extends JpaRepository<FacilitySchedule,Long> {

}
