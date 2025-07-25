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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.obs.decc.domain.Authority;
import com.sos.obs.decc.repository.AuthorityRepository;
import com.sos.obs.decc.web.rest.util.PaginationUtil;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/authority")
@Transactional
public class AuthorityResource {
    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    AuthorityRepository authorityRepository;

    public AuthorityResource(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @GetMapping
    public ResponseEntity<List<Authority>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Authority> page = authorityRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
