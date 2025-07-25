package com.sos.obs.decc.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.dash.util.UserCount;
import com.sos.obs.decc.domain.User;

/**
 * @Author Ahmed EL FAYAFI on mars, 2019
 */

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = {"authorities", "centers"})
    Optional<User> findOneWithAuthoritiesAndCentersById(String id);

    @EntityGraph(attributePaths = {"authorities", "centers"})
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesAndCentersByLogin(String login);

    @EntityGraph(attributePaths = {"authorities", "centers"})
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesAndCentersByEmail(String email);


    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);
    /*
    @Query("SELECT NEW com.sos.dash.util.UserCount(v.user_id, count(v.authority_name)) FROM UserAuthority v WHERE v.user_id in :userIds GROUP BY v.user_id")
    List<UserCount> countByAuthorityInGroupByUserId(@Param("userIds") List<Long> userIds);

    @Query("SELECT NEW com.sos.dash.util.UserCount(v.user_id, count(v.authority_name)) FROM UserAuthority v WHERE v.user_id = :userId GROUP BY v.user_id")
    List<UserCount> countByAuthorityInGroupByUserId(@Param("userId") Long userId);

    @Query("SELECT NEW com.sos.dash.util.UserCount(v.user_id, count(v.UserAuthorityId.authorityId)) FROM UserAuthority v GROUP BY v.UserAuthorityId.userId")
    List<UserCount> countByAuthorityInGroupByUserId();

    @Query("SELECT v FROM UserAuthority v where v.id = :userId and v.UserAuthorityId.userId in :userIds")
    List<Authority> findByAuthorityAnduserIdIn(@Param("userId") Long userId, @Param("userIds") List<Long> userIds);

    @Query("SELECT COUNT(v.UserAuthorityId.authorityId) from UserAuthority v where v.UserAuthorityId.userId = :userId")
    long countByUserId(@Param("userId") Long userId);


    @Query("SELECT v.id FROM UserAuthority v WHERE v.UserAuthorityId.userId = :userId")
    Page<Long> findAuthoduserIdsByUserId(@Param("userId") Long userId, Pageable pageable);
    
  
    @EntityGraph(attributePaths = {"authorities", "centers"})
    @Query("SELECT NEW UserCount(v.centers, count(v.id) ) FROM User v GROUP BY v.centers")
    List<UserCount> countByUser();
    
         */

    @Query("SELECT COUNT(v.id) from User v where v.activated = :valide")
    long countUserActivated(@Param("valide") Boolean valide);
}
