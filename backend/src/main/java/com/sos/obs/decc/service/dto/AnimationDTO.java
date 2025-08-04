package com.sos.obs.decc.service.dto;

import javax.validation.constraints.Size;

import com.sos.obs.decc.domain.Animation;
import com.sun.istack.NotNull;

/**
 * A DTO representing a user, with his Animations.
 */
public class AnimationDTO {

    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;


    private String centrename;


    // Message simple
    @Size(min = 1, max = 256)
    private String message_s;



	// Message defilant
    @Size(min = 1, max = 256)
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

    //private Set<Center> centers;

    public AnimationDTO() {
        // Empty constructor needed for Jackson.
    }

    public AnimationDTO(Animation animation) {
        this.id = animation.getId();
        this.name = animation.getName();
    }


    public AnimationDTO(String id, String centrename, String anName, String message_s, String message_d) {
    	this.id = id;
		this.centrename = centrename;
		this.name = anName;
		this.message_s = message_s;
		this.message_d = message_d;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getCentrename() {
		return centrename;
	}

	public void setCentrename(String centrename) {
		this.centrename = centrename;
	}


}
