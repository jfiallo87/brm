package com.brm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FacilityEventServiceReservation.
 */
@Entity
@Table(name = "facility_event_service_reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityEventServiceReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private FacilityEventReservation facilityEventReservation;

    @ManyToOne
    @NotNull
    private FacilityEventServiceCatalog facilityEventServiceCatalog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacilityEventReservation getFacilityEventReservation() {
        return facilityEventReservation;
    }

    public void setFacilityEventReservation(FacilityEventReservation facilityEventReservation) {
        this.facilityEventReservation = facilityEventReservation;
    }

    public FacilityEventServiceCatalog getFacilityEventServiceCatalog() {
        return facilityEventServiceCatalog;
    }

    public void setFacilityEventServiceCatalog(FacilityEventServiceCatalog facilityEventServiceCatalog) {
        this.facilityEventServiceCatalog = facilityEventServiceCatalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityEventServiceReservation facilityEventServiceReservation = (FacilityEventServiceReservation) o;
        if(facilityEventServiceReservation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityEventServiceReservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityEventServiceReservation{" +
            "id=" + id +
            '}';
    }
}
