package com.sos.obs.decc.evita.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Ahmed EL FAYAFI on avril, 2019
 */
@Entity
@Table(name = "Statistics")
public class Statistics implements Serializable {

    public Statistics() {
    }

    public Statistics(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private String id;

    @Column(name = "name")
    private String name;

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
}
