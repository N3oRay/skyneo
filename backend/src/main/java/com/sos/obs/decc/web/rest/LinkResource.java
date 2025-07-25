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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.obs.decc.domain.Link;
import com.sos.obs.decc.repository.LinkRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/link")
@Transactional
public class LinkResource {
    private final Logger log = LoggerFactory.getLogger(LinkResource.class);

    LinkRepository linkRepository;

    public LinkResource(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping
    public ResponseEntity<List<Link>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Link> page = linkRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/link");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{linkId}")
    public ResponseEntity<Void> deleteLink(@PathVariable String linkId) {
        log.debug("REST request to delete User : {}", linkId);
        linkRepository.deleteById(linkId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("link.deleted", linkId.toString())).build();
    }
    
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListLinkPost(@RequestBody List<Link> dDTOs) {
    	
    	for (Link id : dDTOs) {
	        log.debug("REST request to delete Link : {}", dDTOs);
	                Optional<Link> optional = linkRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Link dash = optional.get();
        	linkRepository.deleteById(dash.getId());
        }
    	}
    	return ResponseUtil.wrapFound("serviceName", "/delete/", dDTOs, true);
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<Link> getLink(@PathVariable String linkId) {
        log.debug("REST request to get User : {}", linkId);
        ResponseEntity<Link> result = ResponseUtil.wrapOrNotFound(linkRepository.findById(linkId));
        // Hibernate.initialize(result.getBody().getLinks());
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<Link> updateLink(@RequestBody Link link) {
        log.debug("REST request to update Link : {}", link);
        Link updatedLink = linkRepository.save(link);
        HttpHeaders headers = HeaderUtil.createAlert("link.deleted", updatedLink.getId().toString());
        return new ResponseEntity(updatedLink, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Link> createLink(@RequestBody Link link) {
        log.debug("REST request to update Link : {}", link);
        Link updatedLink = linkRepository.save(link);
        HttpHeaders headers = HeaderUtil.createAlert("link.deleted", updatedLink.getId().toString());
        return new ResponseEntity(updatedLink, headers, HttpStatus.OK);
    }

}
