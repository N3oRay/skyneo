//package com.sos.obs.decc.web.rest;
//
//import com.sos.obs.decc.domain.SkillGroup;
//import com.sos.obs.decc.repository.SkillGroupRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @Author SLS --- on mars, 2019
// */
//
//@RestController
//@RequestMapping("/api/admin/skillGroup")
//@Transactional
//public class SkillGroupResourceSave {
//    private final Logger log = LoggerFactory.getLogger(SkillGroupResourceSave.class);
//
//    SkillGroupRepository skillGroupRepository;
//
//    public SkillGroupResourceSave(SkillGroupRepository skillGroupRepository) {
//        this.skillGroupRepository = skillGroupRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SkillGroup>> getAll() {
//        List<SkillGroup> skills = skillGroupRepository.findAll();
////        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skillGroup");
//        return new ResponseEntity<>(skills, null, HttpStatus.OK);
//    }
//
//}
