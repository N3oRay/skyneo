package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author Serge LOPES 2020
 */

@Entity
@Table(name = "activites")
public class Activites implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;


    @Size(min = 1, max = 6)
    @Column(name = "evita_id", length = 6, nullable = false)
    private String evitaId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Size(min = 1, max = 256)
    @Column(name = "display_name", length = 100, nullable = false)
    private String displayName;
    
    
    public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
    @Size(min = 1, max = 100)
    @Column(name = "type", length = 100)
    private String type;
    
    
    
//    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "ref_activite_indic",
        joinColumns = {@JoinColumn(name = "activite_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "indic_id", referencedColumnName = "id")})

    
    @BatchSize(size = 20)
    private Set<Indicator> indicator = new HashSet<>();
    
    public Set<Indicator> getIndicator() {
		return indicator;
	}

	public void setIndicator(Set<Indicator> indicator) {
		this.indicator = indicator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "center_id")
	@Fetch(FetchMode.JOIN)
    private Center center;


    public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activites activites = (Activites) o;
        return id.equals(activites.id);
    }

}
