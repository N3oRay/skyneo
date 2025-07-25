package com.sos.obs.decc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.obs.decc.domain.Dashboard;
import com.sos.obs.decc.domain.DashboardDTO;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface DashboardDTORepository extends JpaRepository<DashboardDTO, String> {
	/*
//	@EntityGraph(attributePaths = {"screens", "center"})
	DashboardDTO findById(int id);

	Optional<DashboardDTO> findByName(String name);

//	@EntityGraph(attributePaths = {"screens", "center"})
	Optional<DashboardDTO> findByNameLike(String recherche);

    Optional<DashboardDTO> findOneByName(String name);

//    @EntityGraph(attributePaths = {"screens", "center"})
    Optional<DashboardDTO> findOneWithScreensAndCentersById(String id);

//    @EntityGraph(attributePaths = {"screens", "center"})
    List<DashboardDTO> findByCenterId(String id);
    
    */
	
    List<DashboardDTO> findByCenterId(String id);
}
