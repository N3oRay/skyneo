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
@Table(name = "REF_TEMPLATE")
public class Template  implements Serializable {

    @Id
    @Size(min = 1, max = 6)
    @Column(name = "ID", length = 36, unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME", length = 45, nullable = false)
    private String name;

    @Size(max = 250)
    @Column(name = "DESCRIPTION", length = 250)
    private String description;

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
        Template template = (Template) o;
        return id.equals(template.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Template{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
