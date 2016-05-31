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
 * A SeatingCatalog.
 */
@Entity
@Table(name = "seating_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeatingCatalog implements Serializable {

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

    @OneToMany(mappedBy = "seatingCatalog")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs = new HashSet<>();

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

    public Set<FacilityEventSeatingCatalog> getFacilityEventSeatingCatalogs() {
        return facilityEventSeatingCatalogs;
    }

    public void setFacilityEventSeatingCatalogs(Set<FacilityEventSeatingCatalog> facilityEventSeatingCatalogs) {
        this.facilityEventSeatingCatalogs = facilityEventSeatingCatalogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatingCatalog seatingCatalog = (SeatingCatalog) o;
        if(seatingCatalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, seatingCatalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeatingCatalog{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
