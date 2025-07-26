package com.sos.obs.decc.evita.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author SLS --- on avril, 2019
 */
@Entity
@Table(name = "afficheurs_cfg")
public class CiscoSkillGroup implements Serializable {

    public CiscoSkillGroup() {
    }


    @Id
    @Column(name = "IDESKGSKG")
    private Long id;
    @Column(name = "Entité")
    private String entity;
    @Column(name = "Site")
    private String site;
    @Column(name = "EnterpriseSkillGroup")
    private String companySkillGroup;
    @Column(name = "SkillGroup")
    private String skillGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCompanySkillGroup() {
        return companySkillGroup;
    }

    public void setCompanySkillGroup(String companySkillGroup) {
        this.companySkillGroup = companySkillGroup;
    }

    public String getSkillGroup() {
        return skillGroup;
    }

    public void setSkillGroup(String skillGroup) {
        this.skillGroup = skillGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CiscoSkillGroup that = (CiscoSkillGroup) o;
        return id.equals(that.id) &&
                Objects.equals(entity, that.entity) &&
                Objects.equals(site, that.site) &&
                Objects.equals(companySkillGroup, that.companySkillGroup) &&
                Objects.equals(skillGroup, that.skillGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entity, site, companySkillGroup, skillGroup);
    }

    @Override
    public String toString() {
        return "CiscoSkillGroup{" +
                "id=" + id +
                ", entity='" + entity + '\'' +
                ", site='" + site + '\'' +
                ", companySkillGroup='" + companySkillGroup + '\'' +
                ", skillGroup='" + skillGroup + '\'' +
                '}';
    }
}
