package com.sos.obs.decc.service.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sos.obs.decc.domain.Activites;
import com.sos.obs.decc.domain.Authority;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.Indicator;

/**
 * A DTO representing a user, with his authorities.
 */
public class ActivitesDTO {

    private String id;



    @Size(min = 1, max = 6)
    @Column(name = "evita_id", length = 6, unique = true, nullable = false)
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
    @Column(name = "type", length = 100, nullable = false)
    private String type;
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    private Set<Indicator> indicator;
    public Set<Indicator> getIndicator() {
		return indicator;
	}

	public void setIndicator(Set<Indicator> indicator) {
		this.indicator = indicator;
	}



	private Center center;

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public ActivitesDTO() {
        // Empty constructor needed for Jackson.
    }

    public ActivitesDTO(Activites activites) {
        this.id = activites.getId();
        this.name = activites.getName();
        this.evitaId = activites.getEvitaId();
        this.type = activites.getEvitaId();
        this.displayName = activites.getDisplayName();
        this.indicator = activites.getIndicator().stream().collect(Collectors.toSet());
        this.center = activites.getCenter();
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


}
