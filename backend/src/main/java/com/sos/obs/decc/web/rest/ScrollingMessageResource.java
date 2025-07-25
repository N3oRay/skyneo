package com.sos.obs.decc.web.rest;

import com.sos.obs.decc.domain.ScrollingMessage;
import com.sos.obs.decc.repository.ScrollingMessageRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/scrollingMessage")
@Transactional
public class ScrollingMessageResource {
    private final Logger log = LoggerFactory.getLogger(ScrollingMessageResource.class);

    ScrollingMessageRepository scrollingMessageRepository;

    public ScrollingMessageResource(ScrollingMessageRepository scrollingMessageRepository) {
        this.scrollingMessageRepository = scrollingMessageRepository;
    }

    @GetMapping
    public ResponseEntity<List<ScrollingMessage>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<ScrollingMessage> page = scrollingMessageRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scrollingMessage");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{scrollingMessageId}")
    public ResponseEntity<Void> deleteScrollingMessage(@PathVariable String scrollingMessageId) {
        log.debug("REST request to delete User : {}", scrollingMessageId);
        scrollingMessageRepository.deleteById(scrollingMessageId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("scrollingMessage.deleted", scrollingMessageId.toString())).build();
    }

    @GetMapping("/{scrollingMessageId}")
    public ResponseEntity<ScrollingMessage> getScrollingMessage(@PathVariable String scrollingMessageId) {
        log.debug("REST request to get User : {}", scrollingMessageId);
        ResponseEntity<ScrollingMessage> result = ResponseUtil.wrapOrNotFound(scrollingMessageRepository.findById(scrollingMessageId));
        // Hibernate.initialize(result.getBody().getScrollingMessages());
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<ScrollingMessage> updateScrollingMessage( @RequestBody ScrollingMessage scrollingMessage) {
        log.debug("REST request to update ScrollingMessage : {}", scrollingMessage);
        ScrollingMessage updatedScrollingMessage = scrollingMessageRepository.save(scrollingMessage);
        HttpHeaders headers = HeaderUtil.createAlert("scrollingMessage.deleted", updatedScrollingMessage.getId().toString());
        return new ResponseEntity(updatedScrollingMessage, headers, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ScrollingMessage> createScrollingMessage( @RequestBody ScrollingMessage scrollingMessage) {
        log.debug("REST request to update ScrollingMessage : {}", scrollingMessage);
        ScrollingMessage updatedScrollingMessage = scrollingMessageRepository.save(scrollingMessage);
        HttpHeaders headers = HeaderUtil.createAlert("scrollingMessage.deleted", updatedScrollingMessage.getId().toString());
        return new ResponseEntity(updatedScrollingMessage, headers, HttpStatus.OK);
    }

}
