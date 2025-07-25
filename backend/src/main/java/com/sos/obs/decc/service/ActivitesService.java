package com.sos.obs.decc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.domain.Activites;
import com.sos.obs.decc.repository.ActivitesRepository;
import com.sos.obs.decc.service.dto.ActivitesDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class ActivitesService {

    private final Logger log = LoggerFactory.getLogger(ActivitesService.class);

    private final ActivitesRepository activitesRepository;


    public ActivitesService(ActivitesRepository actRepository) {
        this.activitesRepository = actRepository;
    }


    @Transactional(readOnly = true)
    public List<ActivitesDTO> getAllActivites() {
        return activitesRepository.findAll().stream().map(ActivitesDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ActivitesDTO> findById(String actvitesId) {
        Activites activites = activitesRepository.findById(actvitesId).get();
        Optional<ActivitesDTO> result = Optional.empty();
        if (activites != null) {
            return Optional.of(new ActivitesDTO(activites));
        }
        return Optional.of(null);
    }


	
    @Transactional(readOnly = true)
    public Page<ActivitesDTO> findAll(Pageable paging) {	
    	List<ActivitesDTO> users = activitesRepository.findAll(paging.getSort()).stream().map(ActivitesDTO::new).collect(Collectors.toList());
    	
        Pageable pagiable = PageRequest.of(paging.getPageNumber() - 1, paging.getPageSize());
        int start = (paging.getPageNumber() - 1) * paging.getPageSize();
        int end = start + paging.getPageSize();

        if (end <= users.size()) {
            return  new PageImpl<>(users.subList(start, end), pagiable, users.size());
        } else {
            if (start < users.size()) {
            	users.size();
                return new PageImpl<>(users.subList(start, users.size()), pagiable, users.size());
  
            }
        }
		return null;
    }
    
    

    public void deleteActiviteById(String id) {
    	activitesRepository.findById(id).ifPresent(a -> {
    		activitesRepository.delete(a);
            //this.clearUserCaches(user);
            log.debug("Deleted Activite: {}", a);
        });
    }

    @Transactional(readOnly = true)
    public Optional<Activites> getActivites(Activites a) {
    	if (a.getId() != null) {
    		return activitesRepository.findById(a.getId());
    	}else {
    		return null;
    	}
    }

}

