package com.brm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.brm.domain.enumeration.UnitType;

/**
 * A FacilityEventServiceCatalog.
 */
@Entity
@Table(name = "facility_event_service_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityEventServiceCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "unit_price", precision=10, scale=2, nullable = false)
    private BigDecimal unitPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false)
    private UnitType unitType;

    @ManyToOne
    @NotNull
    private ServiceCatalog serviceCatalog;

    @ManyToOne
    @NotNull
    private FacilityEventCatalog facilityEventCatalog;

    @OneToMany(mappedBy = "facilityEventServiceCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventServiceReservation> facilityEventServiceReservations = new HashSet<>();

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

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public ServiceCatalog getServiceCatalog() {
        return serviceCatalog;
    }

    public void setServiceCatalog(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

    public FacilityEventCatalog getFacilityEventCatalog() {
        return facilityEventCatalog;
    }

    public void setFacilityEventCatalog(FacilityEventCatalog facilityEventCatalog) {
        this.facilityEventCatalog = facilityEventCatalog;
    }

    public Set<FacilityEventServiceReservation> getFacilityEventServiceReservations() {
        return facilityEventServiceReservations;
    }

    public void setFacilityEventServiceReservations(Set<FacilityEventServiceReservation> facilityEventServiceReservations) {
        this.facilityEventServiceReservations = facilityEventServiceReservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityEventServiceCatalog facilityEventServiceCatalog = (FacilityEventServiceCatalog) o;
        if(facilityEventServiceCatalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityEventServiceCatalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityEventServiceCatalog{" +
            "id=" + id +
            ", unitPrice='" + unitPrice + "'" +
            ", unitType='" + unitType + "'" +
            '}';
    }
}
