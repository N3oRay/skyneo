package com.sos.obs.decc.repository;


import com.sos.obs.decc.domain.SosServeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author SLS --- on 2025
 */

@Repository
public interface SosServeurRepository extends JpaRepository<SosServeur, String> {
}
