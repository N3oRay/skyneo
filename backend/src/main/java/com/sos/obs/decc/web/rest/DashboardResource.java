package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.sos.obs.decc.domain.Dashboard;
import com.sos.obs.decc.domain.DashboardDTO;
import com.sos.obs.decc.repository.DashboardDTORepository;
import com.sos.obs.decc.repository.DashboardRepository;
import com.sos.obs.decc.repository.ScreenRepository;
import com.sos.obs.decc.web.rest.errors.ApiException;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Serge LOPES 2020
 */

@RestController
@RequestMapping("/api/admin/dashboard")
@Transactional
public class DashboardResource {
    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    DashboardDTORepository dashboardDTORepository;
    DashboardRepository dashboardRepository;
    ScreenRepository screenRepository;
    
    String serviceName = "dashboard";


    public DashboardResource(DashboardDTORepository dashboardDTORepository, DashboardRepository dashboardRepository, ScreenRepository screenRepository) {
        this.dashboardDTORepository = dashboardDTORepository;
        this.dashboardRepository = dashboardRepository;
        this.screenRepository = screenRepository;
    }

    @GetMapping
    public ResponseEntity<List<DashboardDTO>> getAll() {
        List<DashboardDTO> dashboards = dashboardDTORepository.findAll();
        return new ResponseEntity<>(dashboards, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllAndMore() {
        List<DashboardDTO> page = dashboardDTORepository.findAll();
        return ResponseEntity.ok().body(page);

    }
    
    
    @GetMapping("/{dashboardId}")
    public ResponseEntity<?> getDashboard(@PathVariable String dashboardId) {
        log.debug("REST request to get User : {}", dashboardId);
        ResponseEntity<DashboardDTO> result = ResponseUtil.wrapOrNotFound(dashboardDTORepository.findById(dashboardId));
        return result;
    }
    
    @GetMapping("/by_centre/{centreid}")
    public ResponseEntity<?> getDashboardByCentre(@PathVariable String centreid) {
        log.debug("REST request to get Dashboard : {}", centreid);
        List<DashboardDTO> dashboards = dashboardDTORepository.findByCenterId(centreid);
        return new ResponseEntity<>(dashboards, HttpStatus.OK);
    }
    
    @GetMapping("/like/{dashboardId}")
    public ResponseEntity<?> getDashboardLikeName(@PathVariable String dashboardId) {
        log.debug("REST request to get Dashboard : {}", dashboardId);
        ResponseEntity<Dashboard> result = ResponseUtil.wrapOrNotFound(dashboardRepository.findByNameLike(dashboardId));
        return result;
    }


    @GetMapping("/list_sort")
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<DashboardDTO> values = dashboardDTORepository.findAll(p);
       return new ResponseEntity<>(values, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsersPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	Pageable paging = PageRequest.of(page, size, Sort.by(sort));
    	final Page<DashboardDTO> values = dashboardDTORepository.findAll(paging);
    	return new ResponseEntity<>(values, new HttpHeaders(), HttpStatus.OK);
    }
    

    @DeleteMapping("/delete/{dashboardId}")
    public ResponseEntity<Void> deleteDashboard(@PathVariable String dashboardId) {
        log.debug("REST request to delete User : {}", dashboardId);
        Optional<DashboardDTO> optional = dashboardDTORepository.findById(dashboardId);
        if (optional.isPresent()) {
            DashboardDTO dash = optional.get();
            dash.getScreens().forEach(screen -> this.screenRepository.deleteById(screen.getId()));
            dashboardRepository.deleteById(dashboardId);
        }else {
        	throw new ApiException("L'enregistrement est INTROUVABLE.");
        }
        
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(serviceName+".deleted", dashboardId.toString())).build();
    }
    
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListDashboardPost(@RequestBody List<Dashboard> dDTOs) {
    	
    	for (Dashboard dashboardId : dDTOs) {
	        log.debug("REST request to delete Dashboard : {}", dDTOs);
	                Optional<DashboardDTO> optional = dashboardDTORepository.findById(dashboardId.getId());
	        if (optional.isPresent()) {
	            DashboardDTO dash = optional.get();
	            // On supprime les screen rattacher au Dashboard
	            dash.getScreens().forEach(screen -> this.screenRepository.deleteById(screen.getId()));
	            // Puis on supprimer les dashboards
	            dashboardDTORepository.deleteById(dash.getId());
	            
	        }else {
	        	throw new ApiException("L'enregistrement est INTROUVABLE."+ dashboardId.getId());
	        }
    	}
    	return ResponseUtil.wrapFound("dashboardManagement", "/delete/", dDTOs, true);
    }



    @PutMapping("/update/*")
    public ResponseEntity<?> updateDashboard(@RequestBody Dashboard dashboard) {
        log.debug("REST request to update Dashboard : {}", dashboard);
        Dashboard updatedDashboard = dashboardRepository.save(dashboard);
        HttpHeaders headers = HeaderUtil.createAlert(serviceName+".update", updatedDashboard.getId().toString());
        return new ResponseEntity(updatedDashboard, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createDashboard(@RequestBody Dashboard dashboard) {
        log.debug("REST request to add Dashboard : {}", dashboard);
        Dashboard updatedDashboard = dashboardRepository.save(dashboard);
        HttpHeaders headers = HeaderUtil.createAlert(serviceName+".add", updatedDashboard.getId().toString());
        return new ResponseEntity(updatedDashboard, headers, HttpStatus.OK);
    }

    @PutMapping("/{dashboardId}")
    public ResponseEntity<?> updateDashboard2(@Valid @RequestBody DashboardDTO dashboard) {
        log.debug("REST request to update Dashboard : {}", dashboard);
        DashboardDTO updatedDashboard = dashboardDTORepository.save(dashboard);
        HttpHeaders headers = HeaderUtil.createAlert(serviceName+".deleted", updatedDashboard.getId().toString());
        return new ResponseEntity(updatedDashboard, headers, HttpStatus.OK);
    }

}
