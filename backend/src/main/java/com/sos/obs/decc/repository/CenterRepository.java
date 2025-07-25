package com.sos.obs.decc.repository;


import com.sos.obs.decc.domain.Center;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 * @author Serge LOPES 2020
 */

@Repository
public interface CenterRepository extends JpaRepository<Center, String> {
	
	String CENTRE_CACHE = "centreCache";
	String CENTRE_CACHE_NAME = "centreCacheName";
	
	//@Cacheable(cacheNames = CENTRE_CACHE)
    List<Center> findAll();
    

    //@EntityGraph(attributePaths = {"users", "animations"})
    //Optional<Center> findById(Long centerID);
    
    
    @Cacheable(cacheNames = CENTRE_CACHE_NAME)
    Optional<Center> findByName(String name);
    
    
    @Query("SELECT COUNT(v.id) from Center v where v.active = :valide")
    long countActivated(@Param("valide") Boolean valide);
}
