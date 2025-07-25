package com.sos.obs.decc.domain.relationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAuthorityId implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserAuthorityId() {

    }

    public UserAuthorityId(String userId, String authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }

    @Column(name = "user_id")
    private String userId;

    @Column(name = "authority_name")
    private String authorityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthorityId that = (UserAuthorityId) o;
        return userId.equals(that.userId) &&
            authorityId.equals(that.authorityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, authorityId);
    }

    @Override
    public String toString() {
        return "UserAuthorityId{" +
            "userId=" + userId +
            ", authorityId='" + authorityId + '\'' +
            '}';
    }
}
