package com.sos.obs.decc.domain.relationship;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Ahmed EL FAYAFI on avr., 2019
 */

@Entity
@Table(name = "rel_user_center")
public class UserCenter implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserCenter() {
    }

    public UserCenter(String userId, String centerId) {
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
        UserCenter that = (UserCenter) o;
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
