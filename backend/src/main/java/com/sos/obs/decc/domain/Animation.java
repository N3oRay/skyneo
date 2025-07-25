package com.sos.obs.decc.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * @Author Serge LOPES 2020
 */
@Entity
@Table(name = "animation")
public class Animation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;


    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "center_id")
	@Fetch(FetchMode.JOIN)
    private Center center;

    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dashboard_id")
	@Fetch(FetchMode.JOIN)
    private Dashboard dashboard;
    
    
    // Message simple
    @Size(min = 1, max = 256)
    @Column(name = "message_s", length = 256, nullable = true)
    private String message_s;
    


	// Message defilant
    @Size(min = 1, max = 256)
    @Column(name = "message_d", length = 256, nullable = true)
    private String message_d;
    
    
    public String getMessage_s() {
		return message_s;
	}

	public void setMessage_s(String message_s) {
		this.message_s = message_s;
	}
    
    
    public String getMessage_d() {
		return message_d;
	}

	public void setMessage_d(String message_d) {
		this.message_d = message_d;
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

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
}
