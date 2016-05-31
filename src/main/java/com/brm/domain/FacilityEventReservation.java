package com.brm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FacilityEventReservation.
 */
@Entity
@Table(name = "facility_event_reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityEventReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "start", nullable = false)
    private ZonedDateTime start;

    @NotNull
    @Column(name = "end", nullable = false)
    private ZonedDateTime end;

    @NotNull
    @Min(value = 1)
    @Column(name = "guests", nullable = false)
    private Integer guests;

    @ManyToOne
    @NotNull
    private Customer customer;

    @OneToMany(mappedBy = "facilityEventReservation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventServiceReservation> facilityEventServiceReservations = new HashSet<>();

    @ManyToOne
    @NotNull
    private FacilityEventSeatingCatalog facilityEventSeatingCatalog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<FacilityEventServiceReservation> getFacilityEventServiceReservations() {
        return facilityEventServiceReservations;
    }

    public void setFacilityEventServiceReservations(Set<FacilityEventServiceReservation> facilityEventServiceReservations) {
        this.facilityEventServiceReservations = facilityEventServiceReservations;
    }

    public FacilityEventSeatingCatalog getFacilityEventSeatingCatalog() {
        return facilityEventSeatingCatalog;
    }

    public void setFacilityEventSeatingCatalog(FacilityEventSeatingCatalog facilityEventSeatingCatalog) {
        this.facilityEventSeatingCatalog = facilityEventSeatingCatalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityEventReservation facilityEventReservation = (FacilityEventReservation) o;
        if(facilityEventReservation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityEventReservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityEventReservation{" +
            "id=" + id +
            ", start='" + start + "'" +
            ", end='" + end + "'" +
            ", guests='" + guests + "'" +
            '}';
    }
}
