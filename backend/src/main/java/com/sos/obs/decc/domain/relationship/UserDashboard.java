package com.sos.obs.decc.domain.relationship;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author SLS --- on avr., 2019
 */

@Entity
@Table(name = "rel_user_center")
public class UserDashboard implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserDashboard() {
    }

    public UserDashboard(String userId, String centerId) {
        this.id = new UserCenterId( userId,centerId);
    }

    @EmbeddedId
    private UserCenterId id;

    public UserCenterId getId() {
        return id;
    }

    public void setId(UserCenterId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDashboard that = (UserDashboard) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserCenter{" +
            "id=" + id +
            '}';
    }
}
