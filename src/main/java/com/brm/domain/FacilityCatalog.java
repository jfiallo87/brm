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
 * A FacilityCatalog.
 */
@Entity
@Table(name = "facility_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Min(value = 1)
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @OneToMany(mappedBy = "facilityCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventCatalog> facilityEventCatalogs = new HashSet<>();

    @OneToMany(mappedBy = "facilityCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilitySchedule> facilitySchedules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<FacilityEventCatalog> getFacilityEventCatalogs() {
        return facilityEventCatalogs;
    }

    public void setFacilityEventCatalogs(Set<FacilityEventCatalog> facilityEventCatalogs) {
        this.facilityEventCatalogs = facilityEventCatalogs;
    }

    public Set<FacilitySchedule> getFacilitySchedules() {
        return facilitySchedules;
    }

    public void setFacilitySchedules(Set<FacilitySchedule> facilitySchedules) {
        this.facilitySchedules = facilitySchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityCatalog facilityCatalog = (FacilityCatalog) o;
        if(facilityCatalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityCatalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityCatalog{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", maxCapacity='" + maxCapacity + "'" +
            '}';
    }
}
