package com.sos.obs.decc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */
@Entity
@Table(name = "link")
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "animation_id")
	@Fetch(FetchMode.JOIN)
    private Center Animation;


    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "center_id")
	@Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Center center;

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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Center getAnimation() {
        return Animation;
    }

    public void setAnimation(Center animation) {
        Animation = animation;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link that = (Link) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", description='" + description + '\'' +
                '}';
    }
}
