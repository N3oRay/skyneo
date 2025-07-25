package com.sos.obs.decc.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @Author Serge LOPES 2020
 */

@Entity
@Table(name = "ref_ecran_activite")
public class ScreenActivites implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;


    @Column(name = "ecran_id", nullable = false)
    private String ecranId;
    
    @Column(name = "activite_id", nullable = false)
    private String activiteid;
    
    
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = true)
    private String name;
    
    
    @Size(min = 1, max = 256)
    @Column(name = "ordre", length = 100, nullable = false)
    private String ordre;
    
    
    @Size(min = 1, max = 100)
    @Column(name = "temps", length = 100)
    private String temps;
    

    public String getTemps() {
		return temps;
	}

	public void setTemps(String temps) {
		this.temps = temps;
	}

	public String getOrdre() {
		return ordre;
	}

	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}

	public String getEcranId() {
		return ecranId;
	}

	public void setEcranId(String ecranId) {
		this.ecranId = ecranId;
	}
	

	public String getActiviteid() {
		return activiteid;
	}

	public void setActiviteid(String activiteid) {
		this.activiteid = activiteid;
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        ScreenActivites activites = (ScreenActivites) o;
        return id.equals(activites.id);
    }

}
