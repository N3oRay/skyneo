package com.sos.obs.decc.web.rest;

import java.util.List;

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

import com.sos.obs.decc.domain.SosAlim;
import com.sos.obs.decc.repository.SosAlimRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on mars, 2025
 */

@RestController
@RequestMapping("/api/sos/alimentation")
@Transactional
public class SosAlimResource {
    private final Logger log = LoggerFactory.getLogger(SosAlimResource.class);

    SosAlimRepository sosAlimRepository;

    public SosAlimResource(SosAlimRepository sosAlimRepository) {
        this.sosAlimRepository = sosAlimRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosAlim>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosAlim> page = sosAlimRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/AlimInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosAlimId}")
    public ResponseEntity<Void> deleteSosAlim(@PathVariable String SosAlimId) {
        log.debug("REST request to delete User : {}", SosAlimId);
        SosAlimRepository.deleteById(SosAlimId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosAlim.deleted", SosAlimId.toString())).build();
    }
*/
    @GetMapping("/{SosAlimId}")
    public ResponseEntity<SosAlim> getSosAlim(@PathVariable String SosAlimId) {
        log.debug("REST request to get SosAlimId : {}", SosAlimId);
        ResponseEntity<SosAlim> result = ResponseUtil.wrapOrNotFound(sosAlimRepository.findById(SosAlimId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosAlim> updateSosAlim( @RequestBody SosAlim SosAlim) {
        log.debug("REST request to update SosAlim : {}", SosAlim);
        SosAlim updatedSosAlim = SosAlimRepository.save(SosAlim);
        HttpHeaders headers = HeaderUtil.createAlert("SosAlim.deleted", updatedSosAlim.getId().toString());
        return new ResponseEntity(updatedSosAlim, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosAlim> createSosAlim( @RequestBody SosAlim SosAlim) {
        log.debug("REST request to update SosAlim : {}", SosAlim);
        SosAlim updatedSosAlim = SosAlimRepository.save(SosAlim);
        HttpHeaders headers = HeaderUtil.createAlert("SosAlim.deleted", updatedSosAlim.getId().toString());
        return new ResponseEntity(updatedSosAlim, headers, HttpStatus.OK);
    }
*/

}
