package com.sos.obs.decc.repository;

import com.sos.obs.decc.domain.ScrollingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrollingMessageRepository extends JpaRepository<ScrollingMessage, String> {
}
