package com.sos.obs.decc.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.obs.decc.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	
	
	String AUTHO_CACHE = "authorityCache";
	
	@Cacheable(cacheNames = AUTHO_CACHE)
    List<Authority> findAll();
	
	
}
