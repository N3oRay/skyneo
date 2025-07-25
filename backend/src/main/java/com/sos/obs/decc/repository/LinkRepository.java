package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@Repository
public interface LinkRepository extends JpaRepository<Link, String> {
}
