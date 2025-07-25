package com.sos.obs.decc.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.config.Constants;
import com.sos.obs.decc.domain.Animation;
import com.sos.obs.decc.repository.AnimationRepository;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.DashboardRepository;

/**
 * Service class for managing users.
 */
@Service
public class AnimationService {

    private final Logger log = LoggerFactory.getLogger(AnimationService.class);

    private final AnimationRepository animationRepository;

    private final DashboardRepository dashboardRepository;

    private final CenterRepository centerRepository;

    //private final CacheManager cacheManager;

    public AnimationService(AnimationRepository animationRepository, DashboardRepository dashboardRepository, CenterRepository centerRepository) {
        this.animationRepository = animationRepository;
        this.dashboardRepository = dashboardRepository;
        this.centerRepository = centerRepository;
    }

    
    
    public void deletevalue(String login) {
    	animationRepository.findById(login).ifPresent(value -> {
    	animationRepository.delete(value);
        log.debug("Deleted Site: {}", value);
    });
	}
	
	public void deletevalueById(String id) {
		animationRepository.findById(id).ifPresent(value -> {
			animationRepository.delete(value);
	        log.debug("Deleted Site: {}", value);
	    });
	}
	
	public void deletevalueByName(String name) {
		animationRepository.findByName(name).ifPresent(value -> {
		animationRepository.delete(value);
	    log.debug("Deleted Site: {}", value);
	});
	}


    public Animation createAnimation(Animation addanim) {

    	// Création du Animation
        Animation animation = new Animation();
        if (addanim.getName() != null) {
        	animation.setName(addanim.getName());
        }else {
        	 // if null ! DEFAULT_PROFILE_TEST
        	animation.setName(Constants.DEFAULT_PROFILE_TEST.toLowerCase());
        }
        if (addanim.getMessage_d() == null) {
            animation.setMessage_d("");
        } else {
        	animation.setMessage_d(addanim.getMessage_d());
        }

        if (addanim.getMessage_s() == null) {
            animation.setMessage_s("");
        } else {
        	animation.setMessage_s(addanim.getMessage_s());
        }
        // On authorise la création sans DashBoard de rattachement
        if (addanim.getDashboard() != null) {
        	/*
        	try {
        		dashboardRepository.findById(addanim.getDashboard().getId()).ifPresent(value -> {
        		animation.setDashboard(value);
        		log.info("Created de l'animation avec DashBoard ID");
        		});
        	} catch (Exception  e ) {
        	    log.info("Created de l'animation sans DashBoard ID");
        	}*/
        	
        	try {
        		dashboardRepository.findByName(addanim.getDashboard().getName()).ifPresent(value -> {
        		animation.setDashboard(value);
        		log.info("Created de l'animation avec DashBoard ID");
        		});
        	} catch (Exception  e ) {
        	    log.info("Created de l'animation sans DashBoard ID");
        	}

        }
        
        // On authorise la création sans Centre de rattachement
        // Le centre depend uniquement du Dashboard, il n'est pas obligatoire.

        if (addanim.getCenter() != null) {
        	// Recherche par id
        	/*
        	try {
        		centerRepository.findById(addanim.getCenter().getId()).ifPresent(value -> {
    			animation.setCenter(value);
    			log.info("Created de l'animation avec Centre ID");
    		});
        	} catch (Exception  e ) {
        		log.info("Created de l'animation sans Centre ID");
        	}*/
        	
        	// Recherche par nom
        	try {
        		centerRepository.findByName(addanim.getCenter().getName()).ifPresent(value -> {
    			animation.setCenter(value);
    			log.info("Created de l'animation avec Centre ID");
    		});
        	} catch (Exception  e ) {
        		log.info("Created de l'animation sans Centre ID");
        	}
        }
        // Sauvegarde de l'utilisateur:
         Animation values = animationRepository.save(animation);
        //this.clearUserCaches(user);
        log.debug("Created Information for Animation: {}", values);
        return animation;
    }

    public void deleteAnimationById(String id) {
        animationRepository.findById(id).ifPresent(animation -> {
            animationRepository.delete(animation);
            //this.clearUserCaches(user);
            log.debug("Deleted Animation: {}", animation);
        });
    }


      
    @Transactional(readOnly = true)
    public Page<Animation> findAll(Pageable paging) {
    	
    	List<Animation> users = animationRepository.findAll();
    	
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

    
    @Transactional(readOnly = true)
    public Optional<Animation> getAnimation(Animation a) {
    	if (a.getId() != null) {
    		return animationRepository.findById(a.getId());
    	}else {
    		return null;
    	}
    }


}
