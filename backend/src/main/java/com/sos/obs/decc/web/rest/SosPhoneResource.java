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

import com.sos.obs.decc.domain.SosPhone;
import com.sos.obs.decc.repository.SosPhoneRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on mars, 2025
 */

@RestController
@RequestMapping("/api/sos/phone")
@Transactional
public class SosPhoneResource {
    private final Logger log = LoggerFactory.getLogger(SosPhoneResource.class);

    SosPhoneRepository sosPhoneRepository;

    public SosPhoneResource(SosPhoneRepository sosPhoneRepository) {
        this.sosPhoneRepository = sosPhoneRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosPhone>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosPhone> page = sosPhoneRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/PhoneInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosPhoneId}")
    public ResponseEntity<Void> deleteSosPhone(@PathVariable String SosPhoneId) {
        log.debug("REST request to delete User : {}", SosPhoneId);
        SosPhoneRepository.deleteById(SosPhoneId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosPhone.deleted", SosPhoneId.toString())).build();
    }
*/
    @GetMapping("/{SosPhoneId}")
    public ResponseEntity<SosPhone> getSosPhone(@PathVariable String SosPhoneId) {
        log.debug("REST request to get SosPhoneId : {}", SosPhoneId);
        ResponseEntity<SosPhone> result = ResponseUtil.wrapOrNotFound(sosPhoneRepository.findById(SosPhoneId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosPhone> updateSosPhone( @RequestBody SosPhone SosPhone) {
        log.debug("REST request to update SosPhone : {}", SosPhone);
        SosPhone updatedSosPhone = SosPhoneRepository.save(SosPhone);
        HttpHeaders headers = HeaderUtil.createAlert("SosPhone.deleted", updatedSosPhone.getId().toString());
        return new ResponseEntity(updatedSosPhone, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosPhone> createSosPhone( @RequestBody SosPhone SosPhone) {
        log.debug("REST request to update SosPhone : {}", SosPhone);
        SosPhone updatedSosPhone = SosPhoneRepository.save(SosPhone);
        HttpHeaders headers = HeaderUtil.createAlert("SosPhone.deleted", updatedSosPhone.getId().toString());
        return new ResponseEntity(updatedSosPhone, headers, HttpStatus.OK);
    }
*/

}
