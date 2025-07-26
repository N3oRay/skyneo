//package com.sos.obs.decc.web.rest;
//
//import com.sos.obs.decc.domain.ActivityBasicSkill;
//import com.sos.obs.decc.repository.ActivityBasicSkillRepository;
//import com.sos.obs.decc.web.rest.util.PaginationUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpHeaders;
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
//@RequestMapping("/api/admin/activityBasicSkill")
//@Transactional
//public class ActivityBasicSkillResource {
//    private final Logger log = LoggerFactory.getLogger(ActivityBasicSkillResource.class);
//
//    ActivityBasicSkillRepository activityBasicSkillRepository;
//
//    public ActivityBasicSkillResource(ActivityBasicSkillRepository activityBasicSkillRepository) {
//        this.activityBasicSkillRepository = activityBasicSkillRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ActivityBasicSkill>> getAll(Pageable pageable) {
//        Page<ActivityBasicSkill> page = activityBasicSkillRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activityBasicSkill");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//}
