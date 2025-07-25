package com.sos.obs.decc.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sos.obs.decc.domain.Site;


/**
 * @author Serge LOPES 2020
 */

@Repository
public interface SiteRepository extends JpaRepository<Site, String> {
	
    Optional<Site> findByName(String string);
    
    Optional<Site> findById(Long siteID);

}
