package com.brm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * A FacilityEventSeatingCatalog.
 */
@Entity
@Table(name = "facility_event_seating_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityEventSeatingCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "unit_price", precision=10, scale=2, nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    @NotNull
    private SeatingCatalog seatingCatalog;

    @ManyToOne
    @NotNull
    private FacilityEventCatalog facilityEventCatalog;

    @OneToMany(mappedBy = "facilityEventSeatingCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventReservation> facilityEventReservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public SeatingCatalog getSeatingCatalog() {
        return seatingCatalog;
    }

    public void setSeatingCatalog(SeatingCatalog seatingCatalog) {
        this.seatingCatalog = seatingCatalog;
    }

    public FacilityEventCatalog getFacilityEventCatalog() {
        return facilityEventCatalog;
    }

    public void setFacilityEventCatalog(FacilityEventCatalog facilityEventCatalog) {
        this.facilityEventCatalog = facilityEventCatalog;
    }

    public Set<FacilityEventReservation> getFacilityEventReservations() {
        return facilityEventReservations;
    }

    public void setFacilityEventReservations(Set<FacilityEventReservation> facilityEventReservations) {
        this.facilityEventReservations = facilityEventReservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityEventSeatingCatalog facilityEventSeatingCatalog = (FacilityEventSeatingCatalog) o;
        if(facilityEventSeatingCatalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityEventSeatingCatalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityEventSeatingCatalog{" +
            "id=" + id +
            ", unitPrice='" + unitPrice + "'" +
            '}';
    }
}
