package com.sos.obs.decc.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sos.obs.decc.config.Constants;
import com.sos.obs.decc.domain.Authority;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.User;
import com.sos.obs.decc.repository.AuthorityRepository;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.UserRepository;
import com.sos.obs.decc.security.AuthoritiesConstants;
import com.sos.obs.decc.security.SecurityUtils;
import com.sos.obs.decc.service.dto.UserDTO;
import com.sos.obs.decc.service.util.RandomUtil;
import com.sos.obs.decc.web.rest.errors.EmailAlreadyUsedException;
import com.sos.obs.decc.web.rest.errors.InvalidPasswordException;
import com.sos.obs.decc.web.rest.errors.LoginAlreadyUsedException;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CenterRepository centerRepository;

    private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CenterRepository centerRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.centerRepository = centerRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    this.clearUserCaches(user);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::getActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    this.clearUserCaches(user);
                    return user;
                });
    }

    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail().toLowerCase());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(UserDTO userDTO) {
    	// Patterne mail
    	String regex = "^(.+)@(.+)$"; 
    	Pattern pattern = Pattern.compile(regex);
    	
    	// Création du User
        User user = new User();
        if (userDTO.getLogin() != null) {
        	user.setLogin(userDTO.getLogin().toLowerCase());
        }else {
        	 // if null ! DEFAULT_PROFILE_TEST
        	user.setLogin(Constants.DEFAULT_PROFILE_TEST.toLowerCase());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getCreatedBy() != null) {
        	user.setCreatedBy(userDTO.getCreatedBy()); 
        }else {
        	user.setCreatedBy("System"); // valeur par defaut
        }
        
        if (userDTO.getEmail() != null) {
        	Matcher matcher = pattern.matcher(userDTO.getEmail());
	        if (matcher.matches()) {
	        	user.setEmail(userDTO.getEmail());
	        }else {
	        	user.setEmail(Constants.DEFAULT_MAIL); // Si le mail est invalide on retourne la notification a l'administrateur
	        }
        }else {
        	user.setEmail(Constants.DEFAULT_MAIL); // Si le mail est NULL on retourne la notification a l'administrateur
        }
        
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        if(userDTO.getPassword()!=null){
            encryptedPassword= passwordEncoder.encode(userDTO.getPassword());
        } else {
        	// Par defaut on met le cuid (login en mot de passe)
        	if (userDTO.getLogin() != null) {
        		encryptedPassword= passwordEncoder.encode(userDTO.getLogin());
        	}
        }
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        
        // On authorise la création d'utilisateur sans Authorité ou centre valide.
        if (userDTO.getAuthorities() != null) {
        	try {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        	} catch (Exception  e ) {
        	    log.info("Created de l'utilisateur sans Authorities ID");
        	}

        }
        if (userDTO.getCenters() != null) {
        	try {
            Set<Center> centers = userDTO.getCenters().stream()
                    .map( center-> centerRepository.findByName(center.getName()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setCenters(centers);
            
        	} catch (Exception  e ) {
        		log.info("Created de l'utilisateur sans Centre ID");
        	}
        }
        // Sauvegarde de l'utilisateur:
         User values = userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", values);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param langKey   language key
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email.toLowerCase());
                    user.setLangKey(langKey);
                    user.setImageUrl(imageUrl);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail().toLowerCase());
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void deleteUserById(String id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }


    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                });
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllManagedUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable paging) {
    	
    	List<UserDTO> users = userRepository.findAll(paging.getSort()).stream().map(UserDTO::new).collect(Collectors.toList());
    	
        Pageable pagiable = PageRequest.of(paging.getPageNumber() - 1, paging.getPageSize());
        int start = (paging.getPageNumber() - 1) * paging.getPageSize();
        int end = start + paging.getPageSize();

        if (end <= users.size()) {
            return  new PageImpl<>(users.subList(start, end), pagiable, users.size());
        } else {
            if (start < users.size()) {
            	users.size();
                return new PageImpl<>(users.subList(start, users.size()), pagiable, users.size());
  
            }
        }
		return null;
    }


    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesAndCentersByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String id) {
        return userRepository.findOneWithAuthoritiesAndCentersById(id);
    }
    
    
    @Transactional(readOnly = true)
    public Optional<User> getUserLoginWithAuthorities(String name) {
        return userRepository.findOneWithAuthoritiesByLogin(name);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesAndCentersByLogin);
    }
    
    @Transactional(readOnly = true)
    public Optional<String> getUser() {
        return SecurityUtils.getCurrentUserLogin();
    }
    
    @Transactional(readOnly = true)
    public Optional<User> getUser(String name) {
        return userRepository.findOneWithAuthoritiesByLogin(name);
    }
    
   
    
    @Transactional(readOnly = true)
    public Optional<User> getUser(UserDTO userDTO) {
    	if (userDTO.getId() != null) {
    		return userRepository.findById(userDTO.getId());
    	}else {
    		if (userDTO.getEmail() != null) {
    			return userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
    		}else {
    			return null;
    		}
    	}
    }
    
    
    
    @Transactional(readOnly = true)
    public long countUser() {
    	return userRepository.countUserActivated(true);
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                });
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }


}
