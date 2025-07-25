package com.sos.obs.decc.domain.relationship.model;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author Ahmed EL FAYAFI on avr., 2019
 */

public class Relationship<T, S> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private String updateType;

    private Set<T> ids;

    private Set<S> relations;

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public Set<T> getIds() {
        return ids;
    }

    public void setIds(Set<T> ids) {
        this.ids = ids;
    }

    public Set<S> getRelations() {
        return relations;
    }

    public void setRelations(Set<S> relations) {
        this.relations = relations;
    }
}
