package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.HashSet;
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
 * @Author Serge LOPES 2020
 */

@Entity
@Table(name = "center")
public class Center implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;


    @JsonIgnore
    @Column(name = "evita_id", length = 6, unique = false, nullable = true)
    private String evitaId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "rel_user_center",
        joinColumns = {@JoinColumn(name = "center_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<User> users = new HashSet<>();
    
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "rel_site_center",
        joinColumns = {@JoinColumn(name = "center_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "site_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<Site> sites = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(targetEntity = Animation.class, mappedBy = "id", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Animation> animations;


    public Set<Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(Set<Animation> animations) {
		this.animations = animations;
	}

	@JsonIgnore
    @OneToMany(
        mappedBy = "center",
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


    public Set<Site> getSites() {
        return sites;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
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
        Center center = (Center) o;
        return id.equals(center.id);
    }


}
