package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author Lopes Serge 2020
 */

@Entity
@Table(name = "site")
public class Site implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;


    @Size(min = 1, max = 6)
    @Column(name = "evita_id", length = 6, unique = true, nullable = false)
    private String evitaId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "active")
    private Boolean active;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "rel_site_center",
        joinColumns = {@JoinColumn(name = "site_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "center_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<User> users = new HashSet<>();


    @JsonIgnore
    @OneToMany(
        mappedBy = "site",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvitaId() {
        return evitaId;
    }

    public void setEvitaId(String evitaId) {
        this.evitaId = evitaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site center = (Site) o;
        return id.equals(center.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, evitaId, name);
    }

    @Override
    public String toString() {
        return "Site{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", evita_id=" + evitaId +
            '}';
    }
}
