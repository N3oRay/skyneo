package com.sos.obs.decc.web.rest;

import java.util.List;
import java.util.Optional;

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

import com.sos.obs.decc.domain.Sprite;
import com.sos.obs.decc.repository.SpriteRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.PaginationUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/sprite")
@Transactional
public class SpriteResource {
    private final Logger log = LoggerFactory.getLogger(SpriteResource.class);

    SpriteRepository spriteRepository;

    public SpriteResource(SpriteRepository spriteRepository) {
        this.spriteRepository = spriteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Sprite>> getAll(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Sprite> page = spriteRepository.findAll(p);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sprite");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{spriteId}")
    public ResponseEntity<Void> deleteSprite(@PathVariable String spriteId) {
        log.debug("REST request to delete User : {}", spriteId);
        spriteRepository.deleteById(spriteId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("sprite.deleted", spriteId.toString())).build();
    }
    
    
    @PostMapping("/delete/")
    public ResponseEntity<?> deleteListLinkPost(@RequestBody List<Sprite> dDTOs) {
    	
    	for (Sprite id : dDTOs) {
	        log.debug("REST request to delete Sprite : {}", dDTOs);
	                Optional<Sprite> optional = spriteRepository.findById(id.getId());
        if (optional.isPresent()) {
        	Sprite v = optional.get();
        	spriteRepository.deleteById(v.getId());
        }
    	}
    	return ResponseUtil.wrapFound("serviceName", "/delete/", dDTOs, true);
    }

    @GetMapping("/{spriteId}")
    public ResponseEntity<Sprite> getSprite(@PathVariable String spriteId) {
        log.debug("REST request to get User : {}", spriteId);
        ResponseEntity<Sprite> result = ResponseUtil.wrapOrNotFound(spriteRepository.findById(spriteId));
        // Hibernate.initialize(result.getBody().getSprites());
        return result;
    }

    @PutMapping("/update/*")
    public ResponseEntity<Sprite> updateSprite(@RequestBody Sprite sprite) {
        log.debug("REST request to update Sprite : {}", sprite);
        Sprite updatedSprite = spriteRepository.save(sprite);
        HttpHeaders headers = HeaderUtil.createAlert("sprite.deleted", updatedSprite.getId().toString());
        return new ResponseEntity(updatedSprite, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Sprite> createSprite(@RequestBody Sprite sprite) {
        log.debug("REST request to update Sprite : {}", sprite);
        Sprite updatedSprite = spriteRepository.save(sprite);
        HttpHeaders headers = HeaderUtil.createAlert("sprite.deleted", updatedSprite.getId().toString());
        return new ResponseEntity(updatedSprite, headers, HttpStatus.OK);
    }

}
