package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.payload.ApiResponse;
import com.sos.obs.decc.domain.Animation;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.Dashboard;
import com.sos.obs.decc.repository.AnimationRepository;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.DashboardRepository;
import com.sos.obs.decc.service.AnimationService;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge Lopes 2020
 */

@RestController
@RequestMapping("/api/admin/animation")
public class AnimationResource {
    private final Logger log = LoggerFactory.getLogger(AnimationResource.class);

    AnimationRepository animationRepository;
    
    CenterRepository centreRepo;
    
    DashboardRepository dashRepo;
    
    AnimationService animationService;

    public AnimationResource(AnimationRepository animationRepository, AnimationService animationService, CenterRepository centreRepo, DashboardRepository dashRepo) {
        this.animationRepository = animationRepository;
        this.animationService = animationService;
        this.centreRepo = centreRepo;
        this.dashRepo = dashRepo;
    }

    @GetMapping
    public ResponseEntity<List<Animation>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Animation> page = animationRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/animation");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<Animation>> getAllAndMore() {
        List<Animation> page = animationRepository.findAll();
        return ResponseEntity.ok().body(page);

    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllAnimations(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<Animation> animations = animationRepository.findAll(p);
       return new ResponseEntity<>(animations, HttpStatus.OK);
    }
    
    /*
    @GetMapping("/listleft")
    public ResponseEntity<?> getfetchCentreAnimationDataLeftJoin() {
        final List<?> animations = animationService.fetchAnimationDataLeftJoin();
       return new ResponseEntity<>(animations, HttpStatus.OK);
    }
    
    @GetMapping("/listright")
    public ResponseEntity<?> getfetchCentreAnimationDataRightJoin() {
        final List<?> animations = animationService.fetchAnimationDataInnerJoin();
       return new ResponseEntity<>(animations, HttpStatus.OK);
    }
    */


    @GetMapping("/load")
    public ResponseEntity<?> getAllCenterPage(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<Animation> animations = animationService.findAll(p);
        return new ResponseEntity<>(animations, HttpStatus.OK);
    }
    
    @PostMapping("/delete")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> deleteAnimationPost(@RequestBody Animation dto) {
        log.debug("REST request to delete Animation : {}", dto);
        Optional<Animation> anim = animationService.getAnimation(dto);
        
        if (anim.get().getId() != null) {
	        log.debug("REST request to delete Animation: {}", anim.get().getId());
	        animationService.deleteAnimationById(anim.get().getId());
        }
    	return ResponseUtil.wrapFound("animationManagement", "/delete", dto, true);
    }
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListAnimationPost(@RequestBody List<Animation> dDTOs) {
    	
    	for (Animation id : dDTOs) {
	        log.debug("REST request to delete Animation : {}", dDTOs);
	                Optional<Animation> optional = animationRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Animation dash = optional.get();
            animationRepository.deleteById(dash.getId());
        }
    	}
    	return ResponseUtil.wrapFound("animationManagement", "/delete/", dDTOs, true);
    }
    
    /*
    @DeleteMapping("/delete/{ids}")
    public ResponseEntity<?> deleteAnimations(@PathVariable List<String> ids) {
    	for (String id : ids) {
	      log.debug("REST request to delete Animation: {}", id);
	      animationService.deleteAnimationById(id);   
    	}
    	return ResponseUtil.wrapFound("animationManagement", "/delete/{ids}", ids, true);
  }*/
    
    @DeleteMapping("/delete/{animationId}")
    public ResponseEntity<Void> deleteAnimation(@PathVariable String animationId) {
        log.debug("REST request to delete User : {}", animationId);
        animationRepository.deleteById(animationId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("animation.deleted", animationId.toString())).build();
    }
    
    
    @GetMapping("/{animationId}")
    public ResponseEntity<Animation> getAnimation(@PathVariable String animationId) {
        log.debug("REST request to get User : {}", animationId);
        ResponseEntity<Animation> result = ResponseUtil.wrapOrNotFound(animationRepository.findById(animationId));
        return result;
    }

    /*
    @PutMapping("/update/*")
    public ResponseEntity<Animation> updateAnimation( @RequestBody Animation animation) {
        log.debug("REST request to update Animation : {}", animation);
        Animation updatedAnimation = animationRepository.save(animation);
        //createEntityUpdateAlert
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("animation.update", updatedAnimation.getId().toString());
        return new ResponseEntity(updatedAnimation, headers, HttpStatus.OK);
    }
    
    */
    
    
    @PutMapping("/update")
    public ResponseEntity<Animation> updateAnimation( @RequestBody Animation animation) {
    	Animation aInDB = animationRepository.findById(animation.getId()).get(); 
    	// Modification des données:

    	if (!animation.getName().isEmpty()) {
    		aInDB.setName(animation.getName());
    	}
    	if (!animation.getMessage_d().isEmpty()) {
    		aInDB.setMessage_d(animation.getMessage_d());
    	}
    	if (!animation.getMessage_s().isEmpty()) {
    		aInDB.setMessage_s(animation.getMessage_s());
    	}
    	
    	if (animation.getCenter() != null) {
    		// Ajout du centre si il existe
    		if (centreRepo.findByName(animation.getCenter().getName()).isPresent()) {
    			Optional<Center> cInDB = centreRepo.findByName(animation.getCenter().getName());
    			log.info("REST request to update Centre : {}", cInDB.get());
        		aInDB.setCenter(cInDB.get());
    		}
    	}
    	
    	if (animation.getDashboard() != null) {
    		// Ajout du Dashboard si il existe
    		if (dashRepo.findByName(animation.getDashboard().getName()).isPresent()) {
	    		Optional<Dashboard> cInDB = dashRepo.findByName(animation.getDashboard().getName());
	    		log.info("REST request to update Dashboard : {}", cInDB.get());
	    		aInDB.setDashboard(cInDB.get());
    		}
    	}

    	log.info("REST request to update animationRepository : {}", aInDB);
    	Animation updatedAnimation = animationRepository.save(aInDB);
    	
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("animation.update", updatedAnimation.getId().toString());
        return new ResponseEntity(updatedAnimation, headers, HttpStatus.OK);
    }

    @PutMapping("/new")
    public ResponseEntity<Animation> newAnimationAndCentre( @RequestBody Animation animation) {
        log.debug("REST request to update Animation : {}", animation);
        Animation newAnimation = animationRepository.save(animation);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert("animation.update", newAnimation.getId().toString());
        return new ResponseEntity(newAnimation, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAnimation(@RequestBody Animation animation) {
        log.debug("REST request to add Center : {}", animation);
        Animation newAnim = animationService.createAnimation(animation);
        
        if (newAnim.getName() != null) {
        	// Ajout Centre OK
        	return ResponseUtil.wrapFound("animationManagement", "/add", newAnim, true);
        }else {
        	//Ajout Centre KO
        	return ResponseUtil.wrapFound("animationManagement", "/add", newAnim, false);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAnimation(@RequestBody Animation animation) {
        log.debug("REST request to add Animation : {}", animation);
        Animation newAnimation = animationService.createAnimation(animation);
        return ResponseUtil.wrapFound("animationManagement", "/create", newAnimation, true);
    }


}
