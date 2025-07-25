//package com.sos.obs.decc.web.rest;
//
//import com.sos.obs.decc.domain.ActivityTimeSkill;
//import com.sos.obs.decc.repository.ActivityTimeSkillRepository;
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
//@RequestMapping("/api/admin/activityTimeSkill")
//@Transactional
//public class ActivityTimeSkillResource {
//    private final Logger log = LoggerFactory.getLogger(ActivityTimeSkillResource.class);
//
//    ActivityTimeSkillRepository activityTimeSkillRepository;
//
//    public ActivityTimeSkillResource(ActivityTimeSkillRepository activityTimeSkillRepository) {
//        this.activityTimeSkillRepository = activityTimeSkillRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ActivityTimeSkill>> getAll(Pageable pageable) {
//        Page<ActivityTimeSkill> page = activityTimeSkillRepository.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activityTimeSkill");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//}
