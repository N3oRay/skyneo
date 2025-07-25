package com.sos.obs.decc.domain;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */
@Entity
@Table(name = "dashboard")
public class Dashboard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;

    @Size(min = 1, max = 45)
    @Column(name = "name", length = 45, nullable = false)
    private String name;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "center_id")
	@Fetch(FetchMode.JOIN)
    private Center center;
    
    
    @OneToMany(
            mappedBy = "dashboard",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
        )
        private Set<ScreenDTO> screens;


    public Set<ScreenDTO> getScreens() {
		return screens;
	}

	public void setScreens(Set<ScreenDTO> screens) {
		this.screens = screens;
	}

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


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Dashboard dash = (Dashboard) o;
        return !(dash.getId() == null || getId() == null) && Objects.equals(getId(), dash.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
