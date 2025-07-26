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

import com.sos.obs.decc.domain.FlashInfo;
import com.sos.obs.decc.repository.FlashInfoRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author SLS --- on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/flashInfo")
@Transactional
public class FlashInfoResource {
    private final Logger log = LoggerFactory.getLogger(FlashInfoResource.class);

    FlashInfoRepository flashInfoRepository;

    public FlashInfoResource(FlashInfoRepository flashInfoRepository) {
        this.flashInfoRepository = flashInfoRepository;
    }

    @GetMapping
    public ResponseEntity<List<FlashInfo>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<FlashInfo> page = flashInfoRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/flashInfo");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    

    @DeleteMapping("/delete/{flashInfoId}")
    public ResponseEntity<Void> deleteFlashInfo(@PathVariable String flashInfoId) {
        log.debug("REST request to delete User : {}", flashInfoId);
        flashInfoRepository.deleteById(flashInfoId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("flashInfo.deleted", flashInfoId.toString())).build();
    }

    @GetMapping("/{flashInfoId}")
    public ResponseEntity<FlashInfo> getFlashInfo(@PathVariable String flashInfoId) {
        log.debug("REST request to get User : {}", flashInfoId);
        ResponseEntity<FlashInfo> result = ResponseUtil.wrapOrNotFound(flashInfoRepository.findById(flashInfoId));
       // Hibernate.initialize(result.getBody().getFlashInfos());
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<FlashInfo> updateFlashInfo( @RequestBody FlashInfo flashInfo) {
        log.debug("REST request to update FlashInfo : {}", flashInfo);
        FlashInfo updatedFlashInfo = flashInfoRepository.save(flashInfo);
        HttpHeaders headers = HeaderUtil.createAlert("flashInfo.deleted", updatedFlashInfo.getId().toString());
        return new ResponseEntity(updatedFlashInfo, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<FlashInfo> createFlashInfo( @RequestBody FlashInfo flashInfo) {
        log.debug("REST request to update FlashInfo : {}", flashInfo);
        FlashInfo updatedFlashInfo = flashInfoRepository.save(flashInfo);
        HttpHeaders headers = HeaderUtil.createAlert("flashInfo.deleted", updatedFlashInfo.getId().toString());
        return new ResponseEntity(updatedFlashInfo, headers, HttpStatus.OK);
    }

}
