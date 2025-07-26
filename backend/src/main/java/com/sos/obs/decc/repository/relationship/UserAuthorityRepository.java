package com.sos.obs.decc.repository.relationship;

import com.sos.obs.decc.domain.User;
import com.sos.obs.decc.domain.relationship.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @Author SLS --- on mars, 2019
 */

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {
    List<UserAuthority> findByIdUserId(String userId);
}
