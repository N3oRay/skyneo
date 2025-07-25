package com.sos.obs.decc.domain.relationship;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author Ahmed EL FAYAFI on avr., 2019
 */

@Entity
@Table(name = "rel_user_authority")
public class UserAuthority implements Serializable {
    private static final long serialVersionUID = 1L;
    

    public UserAuthority() {
    }

    public UserAuthority(String id , String authority) {
        this.id = new UserAuthorityId(id,authority);
    }

    @EmbeddedId
    private UserAuthorityId id;

    public UserAuthorityId getId() {
        return id;
    }

    public void setId(UserAuthorityId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority that = (UserAuthority) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
            "id=" + id +
            '}';
    }
}
