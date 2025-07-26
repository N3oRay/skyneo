package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.SkillGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author SLS --- on mars, 2019
 */

@Repository
public interface SkillGroupRepository extends JpaRepository<SkillGroup, String> {

}
