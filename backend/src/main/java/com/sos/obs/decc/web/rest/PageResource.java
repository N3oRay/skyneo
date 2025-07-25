package com.sos.obs.decc.web.rest;


import com.sos.obs.decc.domain.Screen;
import com.sos.obs.decc.repository.PageRepository;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/page")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(com.sos.obs.decc.web.rest.PageResource.class);

    PageRepository pageRepository;

    public PageResource(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Screen> page = pageRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/page");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
