package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.example.polls.payload.ApiResponse;
import com.sos.dash.util.AppConstants;
import com.sos.obs.decc.domain.Activites;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.Site;
import com.sos.obs.decc.domain.User;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.SiteRepository;
import com.sos.obs.decc.service.CenterService;
import com.sos.obs.decc.service.dto.CenterDTO;
import com.sos.obs.decc.service.dto.CenterDTOFull;
import com.sos.obs.decc.web.rest.errors.ApiException;
import com.sos.obs.decc.web.rest.errors.EmailAlreadyUsedException;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge Lopes 2020
 */

@RestController
@RequestMapping("/api/admin/centers")
@Transactional
public class CenterResource {
    private final Logger log = LoggerFactory.getLogger(com.sos.obs.decc.web.rest.CenterResource.class);
    
    
    private final CenterService centerService;

    private final CenterRepository centerRepository;
    
    private final SiteRepository siteRepository;
    
    String serviceName = "centerManagement";
    
    //private final AnimationRepository animationsRepository;

    


    public CenterResource(CenterRepository centerRepository, CenterService centerService, SiteRepository siteRepository) {
        this.centerRepository = centerRepository;
        this.centerService = centerService;
        //this.animationsRepository = animationsRepository;
        this.siteRepository = siteRepository;
    }
    
    /**
     * GET /centers : get all centers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */

    @GetMapping
    public ResponseEntity<Page<CenterDTO>> getAllUsersPage(@RequestParam(name = "tri", required = false, defaultValue = "ASC") String tri
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
        final List<CenterDTO> users = centerService.getAllCenters();
 
        Pageable pagiable = PageRequest.of(page - 1, size);
        int start = (page - 1) * size;
        int end = start + size;

        Page<CenterDTO> pages;
        if (end <= users.size()) {
            pages = new PageImpl<>(users.subList(start, end), pagiable, users.size());
            return new ResponseEntity<>(pages, HttpStatus.OK);
        } else {
            if (start < users.size()) {
            	users.size();
                pages = new PageImpl<>(users.subList(start, users.size()), pagiable, users.size());
                return new ResponseEntity<>(pages, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<Page<CenterDTOFull>> getAllUsersPageAll(@RequestParam(name = "tri", required = false, defaultValue = "ASC") String tri
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
        final List<CenterDTOFull> users = centerService.getAllCentersFull();
        Pageable pagiable = PageRequest.of(page - 1, size);
        int start = (page - 1) * size;
        int end = start + size;

        Page<CenterDTOFull> pages;
        if (end <= users.size()) {
            pages = new PageImpl<>(users.subList(start, end), pagiable, users.size());
            return new ResponseEntity<>(pages, HttpStatus.OK);
        } else {
            if (start < users.size()) {
            	users.size();
                pages = new PageImpl<>(users.subList(start, users.size()), pagiable, users.size());
                return new ResponseEntity<>(pages, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @GetMapping("/list")
    public ResponseEntity<List<CenterDTO>> getAll() {
        List<CenterDTO> centers = centerService.getAllCenters();
        return new ResponseEntity<>(centers,  HttpStatus.OK);
    }
    
    
    @GetMapping("/animations")
    public ResponseEntity<List<Center>> getAllCenterAnimations() {
        List<Center> centers = centerService.getAllCentersAnimation();
        return new ResponseEntity<>(centers,  HttpStatus.OK);
    }



    @GetMapping("/{centerId}")
    public ResponseEntity<CenterDTOFull> getCenter(@PathVariable String centerId) {
        log.debug("REST request to get Center : {}", centerId);
        ResponseEntity<CenterDTOFull> result = ResponseUtil.wrapOrNotFound(centerService.findById(centerId));
        Hibernate.initialize(result.getBody().getSites());
        return result;
    }
    /*
    @PutMapping("/update/*")
    public ResponseEntity<Center> updateCenter( @RequestBody Center center) {
        log.debug("REST request to update Center : {}", center);
        Center updatedCenter = centerRepository.save(center);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("center.update", updatedCenter.getId().toString());
        return new ResponseEntity(updatedCenter, headers, HttpStatus.OK);
    }
    
    */
    @PutMapping("/update")
    public ResponseEntity<?> updateCenterDTO( @RequestBody CenterDTO centerDTO) { 
    	if (centerDTO.getId() != null){
			log.warn("ID non null - update", centerDTO);
			Optional<Center> existingCenter = centerRepository.findById(centerDTO.getId());

	        Center userupdate = updatecenter(existingCenter, centerDTO);
			log.info("Update : {}", userupdate);
			/*return ResponseUtil.wrapOrNotFound(existingUser,
		            HeaderUtil.createEntityUpdateAlert("userManagement.updated", centerDTO.getName()));*/
			return ResponseUtil.wrapFound(serviceName, "/update", existingCenter, true);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private Center updatecenter(Optional<Center> existingCenter, CenterDTO centerDTO) {
    	Center centre = null;
        if (existingCenter.isPresent()) {
            centre = existingCenter.get();
            
            centre.setName(centerDTO.getName());
            if (centerDTO.getActive() != null) {
            centre.setActive(centerDTO.getActive());
            }
            
            if (centerDTO.getEvitaId() != null) {
            centre.setEvitaId(centerDTO.getEvitaId());
            }
            // Affectation de Site
	        if (centerDTO.getSites() != null) {
	        	try {
       		
	            Set<Site> sites = centerDTO.getSites().stream()
	                    .map( site-> siteRepository.findByName(site.getName()))
	                    .filter(Optional::isPresent)
	                    .map(Optional::get)
	                    .collect(Collectors.toSet());
	            centre.setSites(sites);
	            
	            
	        	} catch (Exception  e ) {
	        	    log.info("Created de Centre sans Site");
	        	    return new Center();
	        	}
	
	        }
	        
            centre = centerRepository.save(centre);
            existingCenter = Optional.of(centre);
        }else {
            log.warn("Le centre n'existe pas : {}", centerDTO);
        }
            return centre;
    	
    }
    
    @PutMapping("/{centerId}")
    public ResponseEntity<Center> updateCenter2(@Valid @RequestBody Center center) {
        log.debug("REST request to update Center : {}", center);
        Center updatedCenter = centerRepository.save(center);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("center.update", updatedCenter.getId().toString());
        return new ResponseEntity(updatedCenter, headers, HttpStatus.OK);

    }
    
    @PostMapping("/new")
    public ResponseEntity<?> createCenter( @RequestBody Center center) {
        log.debug("REST request to update Center : {}", center);

        if (center.getActive() != null) {
        	center.setActive(true);
        }

        if (center.getUsers() != null) {
        	log.info("User non pris en charge lors de l'ajout de centre");
        }
        Center updatedCenter = centerRepository.save(center);
       
        return ResponseUtil.wrapFound(serviceName, "/new", updatedCenter, true);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> createCenter2(@RequestBody CenterDTO centreDTO) {
        log.debug("REST request to update Center : {}", centreDTO);
 
		//---- Controle si le centre exite deja
        Optional<Center> existingCenterName = centerRepository.findByName(centreDTO.getName());
        if (existingCenterName.isPresent()) {
        	log.info("Le centre deja existant : {}", centreDTO.getName());
    		return new ResponseEntity(new ApiResponse(false, "centerManagement.add.contraint.error"),
                    HttpStatus.BAD_REQUEST);
        }
		//-------------------------
        
        Center newUser = centerService.createCentre(centreDTO);
        
        if (newUser.getName() != null) {
        	// Ajout Centre OK
        	return ResponseUtil.wrapFound(serviceName, "/add", newUser, true);
        }else {
        	//Ajout Centre KO
        	return ResponseUtil.wrapFound(serviceName, "/add", newUser, false);
        }
    }
    
    @PostMapping("/delete")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> deleteCentrePost(@RequestBody Center center) {
        log.debug("REST request to delete Center : {}", center);
        Optional<CenterDTOFull> opcenter = centerService.getCenter(center);
        if(opcenter==null) throw new ApiException("L'enregistrement est INTROUVABLE.");
        if (opcenter.get().getId() != null) {
	        log.debug("REST request to delete Center: {}", opcenter.get().getId());
	        centerRepository.deleteById(opcenter.get().getId());

        }
    	return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("center.deleted", "" + opcenter.get().getId())).build();
    }
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListCenterPost(@RequestBody List<Center> dDTOs) {
    	
    	for (Center id : dDTOs) {
	        log.debug("REST request to delete Center : {}", dDTOs);
	                Optional<Center> optional = centerRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Center v = optional.get();
        	centerRepository.deleteById(v.getId());
        }
    	}
    	return ResponseUtil.wrapFound(serviceName, "/delete/", dDTOs, true);
    }
    
    @DeleteMapping("/delete/{centerId}")
    public ResponseEntity<Void> deleteCenter(@PathVariable String centerId) {
        log.debug("REST request to delete Center : {}", centerId);
        centerRepository.deleteById(centerId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("center.deleted", centerId.toString())).build();
    }
    
    @GetMapping("/count")
    public long getCentreCount() {
        return centerRepository.countActivated(true);
    }


}
