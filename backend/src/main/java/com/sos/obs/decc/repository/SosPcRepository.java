package com.sos.obs.decc.repository;


import com.sos.obs.decc.domain.SosPc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author SLS --- on 2025
 */

@Repository
public interface SosPcRepository extends JpaRepository<SosPc, String> {
}
