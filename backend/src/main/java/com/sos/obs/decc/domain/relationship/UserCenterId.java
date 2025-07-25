package com.sos.obs.decc.domain.relationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCenterId implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserCenterId() {

    }

    public UserCenterId(String userId,String centerId) {
        this.userId = userId;
        this.centerId = centerId;
    }

    @Column(name = "user_id")
    private String userId;

    @Column(name = "CENTER_ID")
    private String centerId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCenterId that = (UserCenterId) o;
        return userId.equals(that.userId) &&
            centerId.equals(that.centerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, centerId);
    }

    @Override
    public String toString() {
        return "UserCenterId{" +
            "userId=" + userId +
            ", centerId=" + centerId +
            '}';
    }
}
