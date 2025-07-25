package com.sos.obs.decc.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.config.Constants;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.Site;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.SiteRepository;
import com.sos.obs.decc.service.dto.CenterDTO;
import com.sos.obs.decc.service.dto.CenterDTOFull;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CenterService {

    private final Logger log = LoggerFactory.getLogger(CenterService.class);

    private final CenterRepository centerRepository;
    
    private final SiteRepository siteRepository;
    
    private final CacheManager cacheManager;


    public CenterService(CenterRepository centerRepository, SiteRepository siteRepository, CacheManager cacheManager) {
        this.centerRepository = centerRepository;
        this.siteRepository = siteRepository;
        this.cacheManager = cacheManager;
    }


    @Transactional(readOnly = true)
    public List<CenterDTO> getAllCenters() {
        return centerRepository.findAll().stream().map(CenterDTO::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<Center> getAllCentersAnimation() {
        return centerRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<CenterDTOFull> getAllCentersFull() {
        return centerRepository.findAll().stream().map(CenterDTOFull::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CenterDTOFull> findById(String centerId) {
        Center center = centerRepository.findById(centerId).get();
        //Optional<CenterDTOFull> result = Optional.empty();
        if (center != null) {
            return Optional.of(new CenterDTOFull(center));
        }
        return Optional.of(null);
    }

    @Transactional(readOnly = true)
	public Optional<CenterDTOFull> getCenter(Center center) {
		Center loadcenter = centerRepository.findById(center.getId()).get();
        if (loadcenter != null) {
            return Optional.of(new CenterDTOFull(loadcenter));
        }
        return Optional.of(null);
	}


    /**
     * Creation d'un nouveau centre
     * @author Serge LOPES
     * @param centreDTO
     * @return
     */
	public Center createCentre(CenterDTO centreDTO) {
    	// Création du Centre
        Center centre = new Center();
        if (centreDTO.getName() != null) {
        	centre.setName(centreDTO.getName().toLowerCase());
        }else {
        	 // if null ! DEFAULT_PROFILE_TEST
        	centre.setName(Constants.DEFAULT_PROFILE_TEST.toLowerCase());
        }

        if (centerRepository.findByName(centre.getName()).isPresent()) {
        	 log.warn("Created de Centre : Le nom du centre exite deja. Il ne sera pas ajouter");
        	return new Center();
        }else {	
	        if (centreDTO.getEvitaId() != null) {
	        	centre.setEvitaId(centreDTO.getEvitaId()); 
	        }else {
	        	centre.setEvitaId("0"); // valeur par defaut
	        }
	        centre.setActive(true); // valeur par defaut a la creation d'un centre
	        
	        // On authorise la creation d'un centre sans Site de rattachement.
	        if (centreDTO.getSites() != null) {
	        	try {
	            Set<Site> sites = centreDTO.getSites().stream()
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
	
	        // Sauvegarde de l'utilisateur:
	        Center values = centerRepository.save(centre);
	        this.clearCenterCaches(centre);
	        log.debug("Created Information for Center: {}", values);
	        return centre;
        }
    }
	
    private void clearCenterCaches(Center centre) {
        Objects.requireNonNull(cacheManager.getCache(CenterRepository.CENTRE_CACHE)).evict(centre.getId());
        Objects.requireNonNull(cacheManager.getCache(CenterRepository.CENTRE_CACHE_NAME)).evict(centre.getName());
    }
    
    public void clearCache(){
    	Cache cache = cacheManager.getCache(CenterRepository.CENTRE_CACHE_NAME);
    	cache.clear();
    }

}

