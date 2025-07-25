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
import com.sos.obs.decc.domain.Indicator;
import com.sos.obs.decc.repository.IndicatorRepository;
import com.sos.obs.decc.service.IndicatorService;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge Lopes 2020
 */

@RestController
@RequestMapping("/api/admin/indicator")
@Transactional
public class IndicatorResource {
    private final Logger log = LoggerFactory.getLogger(IndicatorResource.class);
    
    String serviceName = "indicatorManagement";

    IndicatorRepository indicatorRepository;
    
    IndicatorService indicatorService;


    public IndicatorResource(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Indicator>> getAll() {
        List<Indicator> indicator = indicatorRepository.findAll();
        return new ResponseEntity<>(indicator, HttpStatus.OK);
    }
       
    @GetMapping("/list_sort")
    public ResponseEntity<List<Indicator>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Indicator> page = indicatorRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/admin/indicator");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsersPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	Pageable paging = PageRequest.of(page, size, Sort.by(sort));
    	final Page<Indicator> users = indicatorRepository.findAll(paging);
    	return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/{indicateurId}")
    public ResponseEntity<Indicator> getIndicator(@PathVariable String indicateurId) {
        log.debug("REST request to get Indicator : {}", indicateurId);
        ResponseEntity<Indicator> result = ResponseUtil.wrapOrNotFound(indicatorRepository.findById(indicateurId));
        return result;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> createIndicateur( @RequestBody Indicator indicateur) {
        log.debug("REST request to add Indicateur : {}", indicateur);
        Indicator addIndicateur = indicatorRepository.save(indicateur);
        return ResponseUtil.wrapFound(serviceName, "/add", addIndicateur, true);
    }

    @GetMapping("/count")
    public long getIndicatorCount() {
        return indicatorRepository.countActivated(true);
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteIndicatorPost(@RequestBody Indicator indicator) {
    	
    	try {

		    log.debug("REST request to delete Indicator : {}", indicator);
		    Optional<Indicator> indic = indicatorService.getIndicator(indicator);

	        
	        if (indic.get().getId() != null) {
		        log.debug("REST request to delete Indicator: {}", indic.get().getId());
		        indicatorService.deleteIndicatorById(indic.get().getId());
	        }
	        
	        if (indic.get().getCode() != null) {
		        log.debug("REST request to delete Site: {}", indic.get().getCode());
		        indicatorService.deletevalueByCode(indic.get().getCode());
	        }
	    	return ResponseUtil.wrapFound(serviceName, "/delete", indicator, true);
    	
     	} catch (Exception e) {
    	    log.error("REST request to delete Site: {}", e);
    		return ResponseUtil.wrapFound(serviceName+ " erreur", "/delete", indicator, false);
    	}
    }
    
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListIndicatorPost(@RequestBody List<Indicator> dDTOs) {
    	
    	for (Indicator id : dDTOs) {
	        log.debug("REST request to delete Indicator : {}", dDTOs);
	                Optional<Indicator> optional = indicatorRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Indicator dash = optional.get();
        	indicatorRepository.deleteById(dash.getId());
        }
    	}
    	return ResponseUtil.wrapFound("serviceName", "/delete/", dDTOs, true);
    }



}
