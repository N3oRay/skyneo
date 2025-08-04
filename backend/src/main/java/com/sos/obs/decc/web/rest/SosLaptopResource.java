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

import com.sos.obs.decc.domain.SosLaptop;
import com.sos.obs.decc.repository.SosLaptopRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on aout, 2025
 */

@RestController
@RequestMapping("/api/sos/laptop")
@Transactional
public class SosLaptopResource {
    private final Logger log = LoggerFactory.getLogger(SosLaptopResource.class);

    SosLaptopRepository sosLaptopRepository;

    public SosLaptopResource(SosLaptopRepository sosLaptopRepository) {
        this.sosLaptopRepository = sosLaptopRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosLaptop>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosLaptop> page = sosLaptopRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/LaptopInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosLaptopId}")
    public ResponseEntity<Void> deleteSosLaptop(@PathVariable String SosLaptopId) {
        log.debug("REST request to delete User : {}", SosLaptopId);
        SosLaptopRepository.deleteById(SosLaptopId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosLaptop.deleted", SosLaptopId.toString())).build();
    }
*/
    @GetMapping("/{SosLaptopId}")
    public ResponseEntity<SosLaptop> getSosLaptop(@PathVariable String SosLaptopId) {
        log.debug("REST request to get SosLaptopId : {}", SosLaptopId);
        ResponseEntity<SosLaptop> result = ResponseUtil.wrapOrNotFound(sosLaptopRepository.findById(SosLaptopId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosLaptop> updateSosLaptop( @RequestBody SosLaptop SosLaptop) {
        log.debug("REST request to update SosLaptop : {}", SosLaptop);
        SosLaptop updatedSosLaptop = SosLaptopRepository.save(SosLaptop);
        HttpHeaders headers = HeaderUtil.createAlert("SosLaptop.deleted", updatedSosLaptop.getId().toString());
        return new ResponseEntity(updatedSosLaptop, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosLaptop> createSosLaptop( @RequestBody SosLaptop SosLaptop) {
        log.debug("REST request to update SosLaptop : {}", SosLaptop);
        SosLaptop updatedSosLaptop = SosLaptopRepository.save(SosLaptop);
        HttpHeaders headers = HeaderUtil.createAlert("SosLaptop.deleted", updatedSosLaptop.getId().toString());
        return new ResponseEntity(updatedSosLaptop, headers, HttpStatus.OK);
    }
*/

}
