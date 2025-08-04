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

import com.sos.obs.decc.domain.SosPc;
import com.sos.obs.decc.repository.SosPcRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on mars, 2025
 */

@RestController
@RequestMapping("/api/sos/pc")
@Transactional
public class SosPcResource {
    private final Logger log = LoggerFactory.getLogger(SosPcResource.class);

    SosPcRepository sosPcRepository;

    public SosPcResource(SosPcRepository sosPcRepository) {
        this.sosPcRepository = sosPcRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosPc>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosPc> page = sosPcRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/PcInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosPcId}")
    public ResponseEntity<Void> deleteSosPc(@PathVariable String SosPcId) {
        log.debug("REST request to delete User : {}", SosPcId);
        SosPcRepository.deleteById(SosPcId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosPc.deleted", SosPcId.toString())).build();
    }
*/
    @GetMapping("/{SosPcId}")
    public ResponseEntity<SosPc> getSosPc(@PathVariable String SosPcId) {
        log.debug("REST request to get SosPcId : {}", SosPcId);
        ResponseEntity<SosPc> result = ResponseUtil.wrapOrNotFound(sosPcRepository.findById(SosPcId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosPc> updateSosPc( @RequestBody SosPc SosPc) {
        log.debug("REST request to update SosPc : {}", SosPc);
        SosPc updatedSosPc = SosPcRepository.save(SosPc);
        HttpHeaders headers = HeaderUtil.createAlert("SosPc.deleted", updatedSosPc.getId().toString());
        return new ResponseEntity(updatedSosPc, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosPc> createSosPc( @RequestBody SosPc SosPc) {
        log.debug("REST request to update SosPc : {}", SosPc);
        SosPc updatedSosPc = SosPcRepository.save(SosPc);
        HttpHeaders headers = HeaderUtil.createAlert("SosPc.deleted", updatedSosPc.getId().toString());
        return new ResponseEntity(updatedSosPc, headers, HttpStatus.OK);
    }
*/

}
