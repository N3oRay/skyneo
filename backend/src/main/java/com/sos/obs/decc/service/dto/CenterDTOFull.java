package com.sos.obs.decc.service.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.Site;

/**
 * A DTO representing a user, with his authorities.
 */
public class CenterDTOFull {

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


    private Set<Site> sites = new HashSet<>();



    public CenterDTOFull() {
        // Empty constructor needed for Jackson.
    }
    

    public CenterDTOFull(Center center) {
        this.id = center.getId();
        this.active = center.getActive();
        this.name = center.getName();
        this.evitaId = center.getEvitaId();
        this.sites = center.getSites().stream().collect(Collectors.toSet());
    }

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


    @Override
    public String toString() {
        return "CenterDTO{" +
            "id=" + id +
            ", evitaId='" + evitaId + '\'' +
            ", name='" + name + '\'' +
            ", sites=" + sites +
            ", active=" + active +
            '}';
    }


	public Set<Site> getSites() {
		return sites;
	}


	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}
}
