package com.brm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FacilitySchedule.
 */
@Entity
@Table(name = "facility_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilitySchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "start", nullable = false)
    private ZonedDateTime start;

    @NotNull
    @Column(name = "end", nullable = false)
    private ZonedDateTime end;

    @ManyToOne
    @NotNull
    private FacilityCatalog facilityCatalog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public FacilityCatalog getFacilityCatalog() {
        return facilityCatalog;
    }

    public void setFacilityCatalog(FacilityCatalog facilityCatalog) {
        this.facilityCatalog = facilityCatalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilitySchedule facilitySchedule = (FacilitySchedule) o;
        if(facilitySchedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilitySchedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilitySchedule{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", start='" + start + "'" +
            ", end='" + end + "'" +
            '}';
    }
}
