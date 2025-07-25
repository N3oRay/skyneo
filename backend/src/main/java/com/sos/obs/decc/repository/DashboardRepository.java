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

//	@EntityGraph(attributePaths = {"center"})
	Optional<Dashboard> findByNameLike(String recherche);

    Optional<Dashboard> findOneByName(String name);

//    @EntityGraph(attributePaths = {"center"})
    Optional<Dashboard> findOneWithScreensAndCentersById(String id);
    
//    @EntityGraph(attributePaths = {"screens", "center"})
    List<Dashboard> findByCenterId(String id);

  
    
}
