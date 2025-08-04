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

import com.sos.obs.decc.domain.SosRouteur;
import com.sos.obs.decc.repository.SosRouteurRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on aout, 2025
 */

@RestController
@RequestMapping("/api/sos/routeur")
@Transactional
public class SosRouteurResource {
    private final Logger log = LoggerFactory.getLogger(SosRouteurResource.class);

    SosRouteurRepository sosRouteurRepository;

    public SosRouteurResource(SosRouteurRepository sosRouteurRepository) {
        this.sosRouteurRepository = sosRouteurRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosRouteur>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosRouteur> page = sosRouteurRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/RouteurInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosRouteurId}")
    public ResponseEntity<Void> deleteSosRouteur(@PathVariable String SosRouteurId) {
        log.debug("REST request to delete User : {}", SosRouteurId);
        SosRouteurRepository.deleteById(SosRouteurId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosRouteur.deleted", SosRouteurId.toString())).build();
    }
*/
    @GetMapping("/{SosRouteurId}")
    public ResponseEntity<SosRouteur> getSosRouteur(@PathVariable String SosRouteurId) {
        log.debug("REST request to get SosRouteurId : {}", SosRouteurId);
        ResponseEntity<SosRouteur> result = ResponseUtil.wrapOrNotFound(sosRouteurRepository.findById(SosRouteurId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosRouteur> updateSosRouteur( @RequestBody SosRouteur SosRouteur) {
        log.debug("REST request to update SosRouteur : {}", SosRouteur);
        SosRouteur updatedSosRouteur = SosRouteurRepository.save(SosRouteur);
        HttpHeaders headers = HeaderUtil.createAlert("SosRouteur.deleted", updatedSosRouteur.getId().toString());
        return new ResponseEntity(updatedSosRouteur, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosRouteur> createSosRouteur( @RequestBody SosRouteur SosRouteur) {
        log.debug("REST request to update SosRouteur : {}", SosRouteur);
        SosRouteur updatedSosRouteur = SosRouteurRepository.save(SosRouteur);
        HttpHeaders headers = HeaderUtil.createAlert("SosRouteur.deleted", updatedSosRouteur.getId().toString());
        return new ResponseEntity(updatedSosRouteur, headers, HttpStatus.OK);
    }
*/

}
