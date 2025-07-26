package com.sos.obs.decc.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author SLS --- on mars, 2019
 */
@Entity
@Table(name = "animation")
public class Sprite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;


    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "header_def", length = 512)
    private String headerDef;


    @Size(max = 512)
    @Column(name = "message_def", length = 512)
    private String messageDef;

    @Size(max = 512)
    @Column(name = "flash_info_def", length = 512)
    private String flashInfoDef;

    @Size(max = 512)
    @Column(name = "screens_def", length = 512)
    private String screensDef;


    @NotNull
    @Column(name = "center_id", length = 36)
    private String centerId;


    @NotNull
    @Column(name = "dashboard_id", length = 36)
    private String dashboardId;

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

    public String getHeaderDef() {
        return headerDef;
    }

    public void setHeaderDef(String headerDef) {
        this.headerDef = headerDef;
    }

    public String getMessageDef() {
        return messageDef;
    }

    public void setMessageDef(String messageDef) {
        this.messageDef = messageDef;
    }

    public String getFlashInfoDef() {
        return flashInfoDef;
    }

    public void setFlashInfoDef(String flashInfoDef) {
        this.flashInfoDef = flashInfoDef;
    }

    public String getScreensDef() {
        return screensDef;
    }

    public void setScreensDef(String screensDef) {
        this.screensDef = screensDef;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }
}
