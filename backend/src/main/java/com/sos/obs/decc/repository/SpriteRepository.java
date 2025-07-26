package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.Animation;
import com.sos.obs.decc.domain.Sprite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author SLS --- on mars, 2019
 */

@Repository
public interface SpriteRepository extends JpaRepository<Sprite, String> {
}
