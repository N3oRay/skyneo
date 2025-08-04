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

import com.sos.obs.decc.domain.SosServeur;
import com.sos.obs.decc.repository.SosServeurRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on aout, 2025
 */

@RestController
@RequestMapping("/api/sos/serveur")
@Transactional
public class SosServeurResource {
    private final Logger log = LoggerFactory.getLogger(SosServeurResource.class);

    SosServeurRepository sosServeurRepository;

    public SosServeurResource(SosServeurRepository sosServeurRepository) {
        this.sosServeurRepository = sosServeurRepository;
    }

/*

Exemple:
http://127.0.0.1:8080/api/sos/alimentation?page=1&size=10&sort=id

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=consum

http://127.0.0.1:8080/api/sos/alimentation?page=1&size=100&sort=name

*/
    @GetMapping
    public ResponseEntity<List<SosServeur>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<SosServeur> page = sosServeurRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sos/ServeurInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

/*
    @DeleteMapping("/delete/{SosServeurId}")
    public ResponseEntity<Void> deleteSosServeur(@PathVariable String SosServeurId) {
        log.debug("REST request to delete User : {}", SosServeurId);
        SosServeurRepository.deleteById(SosServeurId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("SosServeur.deleted", SosServeurId.toString())).build();
    }
*/
    @GetMapping("/{SosServeurId}")
    public ResponseEntity<SosServeur> getSosServeur(@PathVariable String SosServeurId) {
        log.debug("REST request to get SosServeurId : {}", SosServeurId);
        ResponseEntity<SosServeur> result = ResponseUtil.wrapOrNotFound(sosServeurRepository.findById(SosServeurId));
        return result;
    }


/*
    @PutMapping("/update/*")
    public ResponseEntity<SosServeur> updateSosServeur( @RequestBody SosServeur SosServeur) {
        log.debug("REST request to update SosServeur : {}", SosServeur);
        SosServeur updatedSosServeur = SosServeurRepository.save(SosServeur);
        HttpHeaders headers = HeaderUtil.createAlert("SosServeur.deleted", updatedSosServeur.getId().toString());
        return new ResponseEntity(updatedSosServeur, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SosServeur> createSosServeur( @RequestBody SosServeur SosServeur) {
        log.debug("REST request to update SosServeur : {}", SosServeur);
        SosServeur updatedSosServeur = SosServeurRepository.save(SosServeur);
        HttpHeaders headers = HeaderUtil.createAlert("SosServeur.deleted", updatedSosServeur.getId().toString());
        return new ResponseEntity(updatedSosServeur, headers, HttpStatus.OK);
    }
*/

}
