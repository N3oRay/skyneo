package com.sos.obs.decc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.obs.decc.domain.Dashboard;
import com.sos.obs.decc.domain.DashboardDTO;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface DashboardDTORepository extends JpaRepository<DashboardDTO, String> {

    List<DashboardDTO> findByCenterId(String id);
}
