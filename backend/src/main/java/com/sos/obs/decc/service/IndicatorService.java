package com.sos.obs.decc.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.domain.Indicator;
import com.sos.obs.decc.repository.IndicatorRepository;

/**
 * Service class for managing indicator.
 */
@Service
@Transactional
public class IndicatorService {

    private final Logger log = LoggerFactory.getLogger(IndicatorService.class);
    
    
    private final IndicatorRepository indicatorRepository;


    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Indicator> findById(String indicator) {
    	Indicator ind = indicatorRepository.findById(indicator).get();
        if (ind != null) {
            return Optional.of(ind);
        }
        return Optional.of(null);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<Indicator> getIndicator(Indicator value) {
    	if (value.getId() != null) {
    		return indicatorRepository.findById(value.getId());
    	}else {
    		if (value.getCode() != null) {
    			return indicatorRepository.findByCode(value.getCode());
    		}else {
    			return null;
    		}
    	}
    }
	
	public Optional<Indicator> getIndicator(String id ) {
		return indicatorRepository.findById(id);
	}
	
    public void deleteIndicator(String login) {
    	indicatorRepository.findById(login).ifPresent(indicator -> {
    		indicatorRepository.delete(indicator);
            log.debug("Deleted indicator: {}", indicator);
        });
    }

    public void deleteIndicatorById(String id) {
    	indicatorRepository.findById(id).ifPresent(indicator -> {
    		indicatorRepository.delete(indicator);
            log.debug("Deleted indicator: {}", indicator);
        });
    }

	public void deletevalueByCode(String code) {
    	indicatorRepository.findByCode(code).ifPresent(indicator -> {
    		indicatorRepository.delete(indicator);
            log.debug("Deleted indicator: {}", indicator);
        });
		
	}


}
