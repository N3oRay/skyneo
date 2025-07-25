package com.sos.obs.decc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.obs.decc.domain.Indicator;

/**
 * @author Serge LOPES 2020
 */

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, String> {



    List<Indicator> findAll();
    Optional<Indicator> findById(Long centerID);

    @Query("SELECT COUNT(v.id) from Indicator v where v.istime = :valide")
    long countActivated(@Param("valide") Boolean valide);
    
	Optional<Indicator> findByCode(String code);

}
