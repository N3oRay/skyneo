package com.sos.obs.decc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Author SLS --- on mars, 2019
 */

@Entity
@Table(name = "FLASH_INFO")
public class FlashInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, unique = false, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CONTENT", length = 100, nullable = false)
    private String content;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "rel_animation_flash_info",
        joinColumns = {@JoinColumn(name = "animation_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "flash_info_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<Animation> animations = new HashSet<>();

    @NotNull
    @Column(name = "OBJECT")
    private Boolean object;

    @Column(name = "level")
    private Integer level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(Set<Animation> animations) {
        this.animations = animations;
    }

    public Boolean getObject() {
        return object;
    }

    public void setObject(Boolean object) {
        this.object = object;
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
        FlashInfo that = (FlashInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FlashInfo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", content='" + content + '\'' +
            ", object=" + object +
            ", level=" + level +
            '}';
    }
}
