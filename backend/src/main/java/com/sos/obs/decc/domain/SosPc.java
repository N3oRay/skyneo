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
 * @Author SLS --- on aout, 2025
 */

@Entity
@Table(name = "sos_pc")
public class SosPc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, unique = false, nullable = false)
    private String name;

    @Column(name = "level")
    private Integer level;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description", length = 200, nullable = false)
    private String content;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "type", length = 45, nullable = false)
    private String type;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "classe", length = 45, nullable = false)
    private String classe;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "eco", length = 45, nullable = false)
    private String eco;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "consum", length = 45, nullable = false)
    private String consum;




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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public String getConsum() {
        return consum;
    }

    public void setConsum(String consum) {
        this.consum = consum;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SosPc that = (SosPc) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SosPc{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", level=" + level + '\'' +
            ", content='" + content + '\'' +
            ", type=" + type + '\'' +
            ", classe=" + classe + '\'' +
            ", eco=" + eco + '\'' +
            ", consum=" + consum +
            '}';
    }
}
