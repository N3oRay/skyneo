package com.sos.obs.decc.repository.relationship;

import com.sos.obs.decc.domain.relationship.UserCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author SLS --- on mars, 2019
 */

@Repository
public interface UserCenterRepository extends JpaRepository<UserCenter, String> {
    List<UserCenter> findByIdUserId(String userId);

    List<UserCenter> findByIdCenterId(String userId);
}
