package com.sos.obs.decc.web.rest;

import com.sos.obs.decc.domain.relationship.UserAuthority;
import com.sos.obs.decc.domain.relationship.UserCenter;
import com.sos.obs.decc.domain.relationship.model.RelationshipUserAuthority;
import com.sos.obs.decc.domain.relationship.model.RelationshipUserCenter;
import com.sos.obs.decc.repository.relationship.UserAuthorityRepository;
import com.sos.obs.decc.repository.relationship.UserCenterRepository;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@RestController
@RequestMapping("/api/admin/relationship")
@Transactional
public class RelationshipResource {
    private final Logger log = LoggerFactory.getLogger(RelationshipResource.class);

    UserAuthorityRepository userAuthorityRepository;
    UserCenterRepository userCenterRepository;

    public RelationshipResource(UserAuthorityRepository userAuthorityRepository, UserCenterRepository userCenterRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
        this.userCenterRepository = userCenterRepository;
    }

    private void processRelationsAuthority(String id, Set<String> relations, Set<UserAuthority> updates, Set<UserAuthority> deletes) {
        List<UserAuthority> exists = this.userAuthorityRepository.findByIdUserId(id);
        deletes.addAll(exists);
        relations.forEach(role -> {
            UserAuthority relation = new UserAuthority(id, role);
            if (deletes.contains(relation)) {
                deletes.remove(relation);
            } else {
                updates.add(relation);
            }
        });
    }

    private void processRelations(String id, Set<String> relations, Set<UserCenter> updates, Set<UserCenter> deletes) {
        List<UserCenter> exists = this.userCenterRepository.findByIdUserId(id);
        deletes.addAll(exists);
        relations.forEach(role -> {
            UserCenter relation = new UserCenter(id, role);
            if (deletes.contains(relation)) {
                deletes.remove(relation);
            } else {
                updates.add(relation);
            }
        });
    }


    @PostMapping("/userAuthorities")
    public ResponseEntity<Void> updateUserAuthorityRelations(@RequestBody RelationshipUserAuthority data) {
        Set<UserAuthority> updates = new HashSet();
        Set<UserAuthority> deletes = new HashSet();
        data.getIds().forEach(id -> processRelationsAuthority(id, data.getRelations(), updates, deletes));
        userAuthorityRepository.saveAll(updates);
        userAuthorityRepository.deleteAll(deletes);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userAuthority.updated", "centerId.toString()")).build();
    }


    private ResponseEntity<Void> updateUserCenterRelations(Set<String> userIds, Set<String> centerIds) {
        Set<UserCenter> updates = new HashSet();
        Set<UserCenter> deletes = new HashSet();
        userIds.forEach(id -> processRelations(id, centerIds, updates, deletes));
        userCenterRepository.saveAll(updates);
        userCenterRepository.deleteAll(deletes);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("update user center relastionship", "")).build();

    }

    @PostMapping("/userCenters")
    public ResponseEntity<Void> updateUserCenterRelations(@RequestBody RelationshipUserCenter data) {
        return updateUserCenterRelations(data.getIds(), data.getRelations());
    }

    @PostMapping("/centerUsers")
    public ResponseEntity<Void> updateCenterUserRelations(@RequestBody RelationshipUserCenter data) {
        return updateUserCenterRelations(data.getRelations(), data.getIds());
    }


    @PostMapping("/dashboardUsers")
    public ResponseEntity<Void> updateDashboardUserRelations(@RequestBody RelationshipUserCenter data) {
        return updateUserCenterRelations(data.getRelations(), data.getIds());
    }

}
