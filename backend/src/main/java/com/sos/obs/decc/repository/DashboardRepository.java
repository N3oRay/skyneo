package com.sos.obs.decc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.obs.decc.domain.Dashboard;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface DashboardRepository extends JpaRepository<Dashboard, String> {

	Optional<Dashboard> findByName(String name);

	Dashboard findById(int id);

	Optional<Dashboard> findByNameLike(String recherche);

    Optional<Dashboard> findOneByName(String name);

    Optional<Dashboard> findOneWithScreensAndCentersById(String id);

    List<Dashboard> findByCenterId(String id);

}
