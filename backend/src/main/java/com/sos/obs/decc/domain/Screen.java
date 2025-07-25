package com.sos.obs.decc.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "screen")
public class Screen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(min = 1, max = 45)
    @Column(name = "name", length = 45, unique = false, nullable = false)
    private String name;


    @Column(name = "level")
    private Integer level;


    @Column(name = "type", length = 45, nullable = false)
    private String type;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "theme_id", length = 45, nullable = false)
    private String theme;


    @Column(name = "activities", nullable = false)
    private String activities;


    @Column(name = "bg1", length = 45, nullable = false)
    private String bg1;

    @Column(name = "bg2", length = 45, nullable = false)
    private String bg2;


    @Column(name = "default_time")
    private int defaultTime;
    
    
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "dashboard_id")
        @JsonIgnore
        private Dashboard dashboard;


//    @Column(name = "dashboard_id", length = 36, nullable = false)
//    private String dashboardId;
  
public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	//    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "ref_ecran_activite",
        joinColumns = {@JoinColumn(name = "ecran_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "activite_id", referencedColumnName = "id")})

    @BatchSize(size = 20)
    private Set<Activites> activites = new HashSet<>();


    public Set<Activites> getActivites() {
		return activites;
	}

	public void setActivites(Set<Activites> activites) {
		this.activites = activites;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(int defaultTime) {
        this.defaultTime = defaultTime;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getDashboardId() {
//        return dashboardId;
//    }
//
//    public void setDashboardId(String dashboardId) {
//        this.dashboardId = dashboardId;
//    }

    public String getBg1() {
        return bg1;
    }

    public void setBg1(String bg1) {
        this.bg1 = bg1;
    }

    public String getBg2() {
        return bg2;
    }

    public void setBg2(String bg2) {
        this.bg2 = bg2;
    }
}
