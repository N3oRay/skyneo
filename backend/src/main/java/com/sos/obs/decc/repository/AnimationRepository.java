package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.Animation;
import com.sos.obs.decc.service.dto.AnimationDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@Repository
public interface AnimationRepository extends JpaRepository<Animation, String> {
	
	
    @EntityGraph(attributePaths = {"dashboard", "center"})
    List<Animation> findAll();

    @EntityGraph(attributePaths = {"dashboard", "center"})
    Optional<Animation> findById(Long animationID);

	Optional<Animation> findByName(String name);
	
	/*
	@Query("SELECT new com.sos.obs.decc.service.dto.AnimationDTO(e.id, d.name, e.name, e.message_s, e.message_d) "
			+ "FROM Center d LEFT JOIN d.animations e")
	List<AnimationDTO> fetchCentreAnimationDataLeftJoin();
	@Query("SELECT com.sos.obs.decc.service.dto.AnimationDTO(e.id, d.name, e.name, e.message_s, e.message_d) "
			+ "FROM Center d RIGHT JOIN d.animations e")
	List<AnimationDTO> fetchCentreAnimationDataRightJoin();
    */
    
    //Optional<Animation> findByIdSimple(Long animationID);
}
