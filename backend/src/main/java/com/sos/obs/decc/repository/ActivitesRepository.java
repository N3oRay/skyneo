package com.sos.obs.decc.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sos.obs.decc.domain.Activites;


/**
 * @Author SLS --- on mars, 2019
 */

@Repository
public interface ActivitesRepository extends JpaRepository<Activites, String> {


    List<Activites> findAll();
    Optional<Activites> findById(Long activiteID);
	Optional<Activites> findByName(String name);


}
