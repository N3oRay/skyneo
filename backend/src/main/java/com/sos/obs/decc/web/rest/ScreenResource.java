package com.sos.obs.decc.web.rest;

import java.util.List;

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
import com.sos.obs.decc.domain.Screen;
import com.sos.obs.decc.repository.ActivitesRepository;
import com.sos.obs.decc.repository.ScreenRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/screen")
@Transactional
public class ScreenResource {
    private final Logger log = LoggerFactory.getLogger(ScreenResource.class);

    ScreenRepository screenRepository;
    
    ActivitesRepository activitesRepository;

    public ScreenResource(ScreenRepository screenRepository, ActivitesRepository activitesRepository) {
        this.screenRepository = screenRepository;
        this.activitesRepository = activitesRepository;
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Screen> page = screenRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/screen");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllAndMore() {
        List<Screen> page = screenRepository.findAll();
        return ResponseEntity.ok().body(page);

    }
    
    @GetMapping("/list_sort")
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<Screen> values = screenRepository.findAll(p);
       return new ResponseEntity<>(values, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsersPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	Pageable paging = PageRequest.of(page, size, Sort.by(sort));

    	final Page<Screen> values = screenRepository.findAll(paging);
    	return new ResponseEntity<>(values, new HttpHeaders(), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{screenId}")
    public ResponseEntity<Void> deleteScreen(@PathVariable String screenId) {
        log.debug("REST request to delete User : {}", screenId);
        screenRepository.deleteById(screenId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("screen.deleted", screenId.toString())).build();
    }

    @GetMapping("/{screenId}")
    public ResponseEntity<Screen> getScreen(@PathVariable String screenId) {
        log.debug("REST request to get User : {}", screenId);
        ResponseEntity<Screen> result = ResponseUtil.wrapOrNotFound(screenRepository.findById(screenId));
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<Screen> updateScreen(@RequestBody Screen screen) {
        log.debug("REST request to update Screen : {}", screen);
        Screen updatedScreen = screenRepository.save(screen);
        HttpHeaders headers = HeaderUtil.createAlert("screen.deleted", updatedScreen.getId().toString());
        return new ResponseEntity(updatedScreen, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Screen> createScreen(@RequestBody Screen screen) {
        log.debug("REST request to update Screen : {}", screen);
        Screen updatedScreen = screenRepository.save(screen);
        HttpHeaders headers = HeaderUtil.createAlert("screen.deleted", updatedScreen.getId().toString());
        return new ResponseEntity(updatedScreen, headers, HttpStatus.OK);
    }

}
