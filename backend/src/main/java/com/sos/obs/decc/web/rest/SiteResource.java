package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.dash.util.AppConstants;
import com.sos.obs.decc.domain.Site;
import com.sos.obs.decc.repository.SiteRepository;
import com.sos.obs.decc.service.SiteService;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge Lopes 2020
 */

@RestController
@RequestMapping("/api/admin/site")
@Transactional
public class SiteResource {
    private final Logger log = LoggerFactory.getLogger(SiteResource.class);

    SiteRepository siteRepository;
    
    SiteService siteService;


    public SiteResource(SiteRepository siteRepository, SiteService siteService) {
        this.siteRepository = siteRepository;
        this.siteService = siteService;
    }

    @GetMapping
    public ResponseEntity<List<Site>> getAll() {
        List<Site> site = siteRepository.findAll();
        return new ResponseEntity<>(site, HttpStatus.OK);
    }
       
    @GetMapping("/list_sort")
    public ResponseEntity<List<Site>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Site> page = siteRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/admin/Site");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

    	Pageable paging = PageRequest.of(page, size, Sort.by(sort));
    	final Page<Site> users = siteRepository.findAll(paging);
    	return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/{indicateurId}")
    public ResponseEntity<Site> getSite(@PathVariable String indicateurId) {
        log.debug("REST request to get Site : {}", indicateurId);
        ResponseEntity<Site> result = ResponseUtil.wrapOrNotFound(siteRepository.findById(indicateurId));
        return result;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> createIndicateur( @RequestBody Site indicateur) {
        log.debug("REST request to add Indicateur : {}", indicateur);
        Site addIndicateur = siteRepository.save(indicateur);
        return ResponseUtil.wrapFound("SiteManagement", "/add", addIndicateur, true);
    }

    @GetMapping("/count")
    public long getSiteCount() {
        return siteRepository.count();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteSitePost(@RequestBody Site Site) {
    	
    	try {
	        log.debug("REST request to delete Site : {}", Site);
	        Optional<Site> indic = siteService.getvalue(Site);
	        
	        if (indic.get().getId() != null) {
		        log.debug("REST request to delete Site: {}", indic.get().getId());
		        siteService.deletevalueById(indic.get().getId());
	        }
	        
	        if (indic.get().getName() != null) {
		        log.debug("REST request to delete Site: {}", indic.get().getName());
		        siteService.deletevalueByName(indic.get().getName());
	        }
	    	return ResponseUtil.wrapFound("SiteManagement", "/delete", Site, true);
    	
    	} catch (Exception e) {
    	    log.error("REST request to delete Site: {}", e);
    		return ResponseUtil.wrapFound("SiteManagement", "/delete", Site, false);
    	}
    }
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListLinkPost(@RequestBody List<Site> dDTOs) {
    	
    	for (Site id : dDTOs) {
	        log.debug("REST request to delete Link : {}", dDTOs);
	                Optional<Site> optional = siteRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Site v = optional.get();
        	siteRepository.deleteById(v.getId());
        }
    	}
    	return ResponseUtil.wrapFound("serviceName", "/delete/", dDTOs, true);
    }



}
