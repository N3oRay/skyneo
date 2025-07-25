package com.sos.obs.decc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@Entity
@Table(name = "SCROLLING_MESSAGE")
public class ScrollingMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MESSAGE", length = 100, nullable = false)
    private String message;


    @NotNull
    @Column(name = "duration")
    private Integer duration;


    @Column(name = "level")
    private Integer level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrollingMessage that = (ScrollingMessage) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ScrollingMessage{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", duration=" + duration +
            ", level=" + level +
            '}';
    }
}
