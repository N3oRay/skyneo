package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository  extends JpaRepository<Screen, String> {
}
