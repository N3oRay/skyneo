package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.dash.util.AppConstants;
import com.sos.obs.decc.domain.Activites;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.repository.ActivitesRepository;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.service.ActivitesService;
import com.sos.obs.decc.service.CenterService;
import com.sos.obs.decc.service.dto.ActivitesDTO;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge Lopes 2020
 */

@RestController
@RequestMapping("/api/admin/activites")
@Transactional
public class ActivitesResource {
    private final Logger log = LoggerFactory.getLogger(com.sos.obs.decc.web.rest.ActivitesResource.class);

    ActivitesRepository activitesRepository;
    
    CenterRepository centreRepo;
    
    CenterService centreService;

    ActivitesService activitesService;


    public ActivitesResource(ActivitesRepository activitesRepository, ActivitesService activitesService, CenterRepository centreRepo, CenterService centreService) {
        this.activitesRepository = activitesRepository;
        this.activitesService = activitesService;
        this.centreRepo = centreRepo;
        this.centreService = centreService;
    }

    @GetMapping
    public ResponseEntity<List<ActivitesDTO>> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<ActivitesDTO> activites = activitesService.getAllActivites();
        return new ResponseEntity<>(activites,  HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllAndMore() {
        List<Activites> page = activitesRepository.findAll();
        return ResponseEntity.ok().body(page);

    }
    
    @GetMapping("/list_sort")
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<ActivitesDTO> activites = activitesService.findAll(p);
       return new ResponseEntity<>(activites, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsersPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	Pageable paging = PageRequest.of(page, size, Sort.by(sort));

    	final Page<ActivitesDTO> activites = activitesService.findAll(paging);
    	return new ResponseEntity<>(activites, new HttpHeaders(), HttpStatus.OK);
    }



    @GetMapping("/{activitesId}")
    public ResponseEntity<ActivitesDTO> getActivites(@PathVariable String activitesId) {
        log.debug("REST request to get User : {}", activitesId);
        ResponseEntity<ActivitesDTO> result = ResponseUtil.wrapOrNotFound(activitesService.findById(activitesId));
        Hibernate.initialize(result.getBody().toString());
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<?> updateActivites( @RequestBody Activites activites) {
        log.debug("REST request to update Activites : {}", activites);
        Activites updatedActivites = activitesRepository.save(activites);
        HttpHeaders headers = HeaderUtil.createAlert("activites.update", updatedActivites.getId().toString());
        return new ResponseEntity(updatedActivites, headers, HttpStatus.OK);
    }
    
 
    @PutMapping("/update")
    public ResponseEntity<?> updateActivitesSimple( @RequestBody Activites activites) {
    	Activites aInDB = activitesRepository.findById(activites.getId()).get(); 
    	// Modification des données:

    	if (!activites.getName().isEmpty()) {
    		aInDB.setName(activites.getName());
    	}
    	if (!activites.getDisplayName().isEmpty()) {
    		aInDB.setDisplayName(activites.getDisplayName());
    	}
    	if (!activites.getEvitaId().isEmpty()) {
    		aInDB.setEvitaId(activites.getEvitaId());
    	}
    	
    	if (!activites.getType().isEmpty()) {
    		aInDB.setType(activites.getType());
    	}

    	if (activites.getCenter() != null) {
    		// Ajout du centre si il existe
    		if (activites.getCenter().getId() != null && centreRepo.findById(activites.getCenter().getId()).isPresent()) {
    			centreService.clearCache();
    			Optional<Center> cInDB = centreRepo.findByName(activites.getCenter().getId());
    			log.info("REST request to update Centre : {}", cInDB.get());
        		aInDB.setCenter(cInDB.get());
    		} else if (activites.getCenter().getName() != null | centreRepo.findByName(activites.getCenter().getName()).isPresent()) {
    			centreService.clearCache();
    			Optional<Center> cInDB = centreRepo.findByName(activites.getCenter().getName());
    			log.info("REST request to update Centre : {}", cInDB.get());
        		aInDB.setCenter(cInDB.get());
    		}
    	}
    	
    	log.info("REST request to update activiteRepository : {}", aInDB);
    	Activites updated = activitesRepository.save(aInDB);
    	
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("activite.update", updated.getId().toString());
        return new ResponseEntity(updated, headers, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Activites> createActivites( @RequestBody Activites activites) {
        log.debug("REST request to update Activites : {}", activites);
        Activites updatedActivites = activitesRepository.save(activites);
        HttpHeaders headers = HeaderUtil.createAlert("activites.deleted", updatedActivites.getId().toString());
        return new ResponseEntity(updatedActivites, headers, HttpStatus.OK);
    }
    @PutMapping("/{activitesId}")
    public ResponseEntity<Activites> updateActivites2(@Valid @RequestBody Activites activites) {
        log.debug("REST request to update Activites : {}", activites);
        Activites updatedActivites = activitesRepository.save(activites);
        HttpHeaders headers = HeaderUtil.createAlert("activites.deleted", updatedActivites.getId().toString());
        return new ResponseEntity(updatedActivites, headers, HttpStatus.OK);

    }
    
    /*
    @DeleteMapping("/delete/{ids}")
    public ResponseEntity<?> deleteActivites(@PathVariable List<String> ids) {
    	for (String id : ids) {
	      log.debug("REST request to delete activites: {}", id);
	      activitesService.deleteActiviteById(id);   
    	}
    	return ResponseUtil.wrapFound("activitesManagement", "/delete/{ids}", ids, true);
  }*/
    
    @PostMapping("/delete")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> deleteActivitesPost(@RequestBody Activites dto) {
        log.debug("REST request to delete activites : {}", dto);
        Optional<Activites> anim = activitesService.getActivites(dto);
        
        if (anim.get().getId() != null) {
	        log.debug("REST request to delete activites: {}", anim.get().getId());
	        activitesService.deleteActiviteById(anim.get().getId());
        }
    	return ResponseUtil.wrapFound("activites.deleted", "/delete", dto, true);
    }
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListActivitesPost(@RequestBody List<Activites> dDTOs) {
    	
    	for (Activites id : dDTOs) {
	        log.debug("REST request to delete Activites : {}", dDTOs);
	        Optional<Activites> optional = activitesRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Activites v = optional.get();
        	activitesRepository.deleteById(v.getId());
        }
    	}
    	return ResponseUtil.wrapFound("activites.deleted", "/delete/", dDTOs, true);
    }
    
    @DeleteMapping("/delete/{activitesId}")
    public ResponseEntity<Void> deleteActivites(@PathVariable String activitesId) {
        log.debug("REST request to delete Activites : {}", activitesId);
        activitesRepository.deleteById(activitesId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("activites.deleted", activitesId.toString())).build();
    }
    

}
