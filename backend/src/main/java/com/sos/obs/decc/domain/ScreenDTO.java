package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

@Entity
@Table(name = "screen")
public class ScreenDTO implements Serializable {
	
	
    public ScreenDTO()  {
        // Empty constructor needed for Jackson.
    }

	public ScreenDTO(Screen screens) {
		this.id = screens.getId();
		this.name = screens.getName();
		this.level = screens.getLevel();
		this.type = screens.getType();
		this.active = screens.isActive();
		this.theme = screens.getTheme();
		this.activites = screens.getActivites().stream().collect(Collectors.toSet());
		this.bg1 = screens.getBg1();
		this.bg2 = screens.getBg2();
		this.defaultTime = screens.getDefaultTime();
		this.dashboard = screens.getDashboard();
		this.activities = screens.getActivities();
	}
    
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

    @Column(name = "activities", length = 36, nullable = false)
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

    
    @ManyToMany
    @JoinTable(
        name = "ref_ecran_activite",
        joinColumns = {@JoinColumn(name = "ecran_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "activite_id", referencedColumnName = "id")})

    @BatchSize(size = 20)
    private Set<Activites> activites = new HashSet<>();
    
    
    /* KO
    @OneToMany(
            mappedBy = "screen",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
        )
    private Set<Activites> activites;
    */


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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


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
