//package com.sos.obs.decc.web.rest;
//
//import com.sos.obs.decc.domain.ActivityCustomSkill;
//import com.sos.obs.decc.repository.ActivityCustomSkillRepository;
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
// * @Author Ahmed EL FAYAFI on mars, 2019
// */
//
//@RestController
//@RequestMapping("/api/admin/activityCustomSkill")
//@Transactional
//public class ActivityCustomSkillResource {
//    private final Logger log = LoggerFactory.getLogger(ActivityCustomSkillResource.class);
//
//    ActivityCustomSkillRepository activityCustomSkillRepository;
//
//    public ActivityCustomSkillResource(ActivityCustomSkillRepository activityCustomSkillRepository) {
//        this.activityCustomSkillRepository = activityCustomSkillRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ActivityCustomSkill>> getAll(Pageable pageable) {
//        Page<ActivityCustomSkill> page = activityCustomSkillRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activityCustomSkill");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//}
