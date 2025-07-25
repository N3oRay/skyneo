package com.sos.obs.decc.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.domain.Site;
import com.sos.obs.decc.repository.SiteRepository;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class SiteService {

    private final Logger log = LoggerFactory.getLogger(SiteService.class);

    private final SiteRepository siteRepository;


    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    
    

	public Optional<Site> getSite(Site value) {
		Site load = siteRepository.findById(value.getId()).get();
        if (load != null) {
            return Optional.of(load);
        }
        return Optional.of(null);
	}

    @Transactional(readOnly = true)
    public Optional<Site> findById(String value) {
    	Site ind = siteRepository.findById(value).get();
        if (ind != null) {
            return Optional.of(ind);
        }
        return Optional.of(null);
    }


	public Optional<Site> getvalue(Site value) {
		return siteRepository.findById(value.getId());
	}
	
    public void deletevalue(String login) {
    		siteRepository.findById(login).ifPresent(value -> {
    		siteRepository.delete(value);
            log.debug("Deleted Site: {}", value);
        });
    }

    public void deletevalueById(String id) {
    		siteRepository.findById(id).ifPresent(value -> {
    		siteRepository.delete(value);
            log.debug("Deleted Site: {}", value);
        });
    }
    
    public void deletevalueByName(String name) {
    	siteRepository.findByName(name).ifPresent(value -> {
		siteRepository.delete(value);
        log.debug("Deleted Site: {}", value);
    });
    }

}

