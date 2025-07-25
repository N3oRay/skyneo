package com.sos.obs.decc.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Serge Lopes 2020
 */
@Entity
@Table(name = "ref_indicator")
public class Indicator implements Serializable {

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", code=" + code + ", description=" + description + ", istime=" + istime
				+ ", order=" + order + ", seuilmin=" + seuilmin + ", seuilmax=" + seuilmax + ", seuilorder="
				+ seuilorder + "]";
	}

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private String id;

    @Column(name = "code", length = 45, nullable = false)
    private String code;

	@Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "is_Time", nullable = false)
    private Boolean istime;

	@Column(name = "order")
    private Integer order;
    
    @Column(name = "seuil_min", length = 64, nullable = true)
    private String seuilmin;
    
    @Column(name = "seuil_max", length = 64, nullable = true)
    private String seuilmax;
    
    @Column(name = "seuil_order", length = 64, nullable = true)
    private String seuilorder;
    
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
    public Boolean getIstime() {
		return istime;
	}

	public void setIstime(Boolean istime) {
		this.istime = istime;
	}

    public String getSeuilorder() {
		return seuilorder;
	}

	public void setSeuilorder(String seuilorder) {
		this.seuilorder = seuilorder;
	}

	public String getSeuilmax() {
		return seuilmax;
	}

	public void setSeuilmax(String seuilmax) {
		this.seuilmax = seuilmax;
	}

	public String getSeuilmin() {
		return seuilmin;
	}

	public void setSeuilmin(String seuilmin) {
		this.seuilmin = seuilmin;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indicator indicator = (Indicator) o;
        return id.equals(indicator.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    
}
