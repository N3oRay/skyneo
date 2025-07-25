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
@Table(name = "skill_group")
public class SkillGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "evita_id", length = 6, nullable = false)
    private String evitaId;

    @NotNull
    @Column(name = "label")
    private String label;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ENTITY", length = 64, nullable = false)
    private String entity;

    @Size(max = 64)
    @Column(name = "SITE", length = 64, nullable = false)
    private String site;

    @Size(max = 64)
    @Column(name = "SKILL_GROUP", length = 64, nullable = false)
    private String skillGroup;


    @Size(max = 64)
    @Column(name = "COMPANY_SKILL_GROUP", length = 64, nullable = false)
    private String companySkillGr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvitaId() {
        return evitaId;
    }

    public void setEvitaId(String evitaId) {
        this.evitaId = evitaId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSkillGroup() {
        return skillGroup;
    }

    public void setSkillGroup(String skillGroup) {
        this.skillGroup = skillGroup;
    }

    public String getCompanySkillGr() {
        return companySkillGr;
    }

    public void setCompanySkillGr(String companySkillGr) {
        this.companySkillGr = companySkillGr;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillGroup that = (SkillGroup) o;
        return id.equals(that.id) &&
                Objects.equals(entity, that.entity) &&
                Objects.equals(site, that.site) &&
                Objects.equals(skillGroup, that.skillGroup) &&
                Objects.equals(companySkillGr, that.companySkillGr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SkillGroup{" +
                "id=" + id +
                ", evitaId='" + evitaId + '\'' +
                ", entity='" + entity + '\'' +
                ", site='" + site + '\'' +
                ", skillGroup='" + skillGroup + '\'' +
                ", companySkillGr='" + companySkillGr + '\'' +
                '}';
    }
}
