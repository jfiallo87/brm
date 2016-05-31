package com.brm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FacilityEventCatalog.
 */
@Entity
@Table(name = "facility_event_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityEventCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "min_guests", nullable = false)
    private Integer minGuests;

    @ManyToOne
    @NotNull
    private FacilityCatalog facilityCatalog;

    @ManyToOne
    @NotNull
    private EventCatalog eventCatalog;

    @OneToMany(mappedBy = "facilityEventCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = new HashSet<>();

    @OneToMany(mappedBy = "facilityEventCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventServiceCatalog> facilityEventServiceCatalogs = new HashSet<>();

    @OneToMany(mappedBy = "facilityEventCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventSchedule> facilityEventSchedules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(Integer minGuests) {
        this.minGuests = minGuests;
    }

    public FacilityCatalog getFacilityCatalog() {
        return facilityCatalog;
    }

    public void setFacilityCatalog(FacilityCatalog facilityCatalog) {
        this.facilityCatalog = facilityCatalog;
    }

    public EventCatalog getEventCatalog() {
        return eventCatalog;
    }

    public void setEventCatalog(EventCatalog eventCatalog) {
        this.eventCatalog = eventCatalog;
    }

    public Set<FacilityEventSeatingCatalog> getFacilityEventSeatingCatalogs() {
        return facilityEventSeatingCatalogs;
    }

    public void setFacilityEventSeatingCatalogs(Set<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs) {
        this.facilityEventSeatingCatalogs = facilityEventSeatingCatalogs;
    }

    public Set<FacilityEventServiceCatalog> getFacilityEventServiceCatalogs() {
        return facilityEventServiceCatalogs;
    }

    public void setFacilityEventServiceCatalogs(Set<FacilityEventServiceCatalog> facilityEventServiceCatalogs) {
        this.facilityEventServiceCatalogs = facilityEventServiceCatalogs;
    }

    public Set<FacilityEventSchedule> getFacilityEventSchedules() {
        return facilityEventSchedules;
    }

    public void setFacilityEventSchedules(Set<FacilityEventSchedule> facilityEventSchedules) {
        this.facilityEventSchedules = facilityEventSchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityEventCatalog facilityEventCatalog = (FacilityEventCatalog) o;
        if(facilityEventCatalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityEventCatalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityEventCatalog{" +
            "id=" + id +
            ", minGuests='" + minGuests + "'" +
            '}';
    }
}
