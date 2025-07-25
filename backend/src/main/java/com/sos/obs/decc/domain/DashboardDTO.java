package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */
@Entity
@Table(name = "dashboard")
public class DashboardDTO implements Serializable {
	
	
    public DashboardDTO()  {
        // Empty constructor needed for Jackson.
    }

	public DashboardDTO(String id, @Size(min = 1, max = 45) String name, Set<ScreenDTO> screens, Center center) {
		//super();
		this.id = id;
		this.name = name;
		this.screens = screens;
		this.center = center;
	}
	
	
	public DashboardDTO(Dashboard dashboard) {
		//super();
		this.id = dashboard.getId();
		this.name = dashboard.getName();
		this.screens = dashboard.getScreens().stream().collect(Collectors.toSet());
		this.center = dashboard.getCenter();
	}

	private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(min = 1, max = 45)
    @Column(name = "name", length = 45, nullable = false)
    private String name;


    @OneToMany(
        mappedBy = "dashboard",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private Set<ScreenDTO> screens;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "center_id")
    private Center center;

    public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
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


    public Set<ScreenDTO> getScreens() {
        return screens;
    }

    public void setScreens(Set<ScreenDTO> screens) {
        this.screens = screens;
    }


}
