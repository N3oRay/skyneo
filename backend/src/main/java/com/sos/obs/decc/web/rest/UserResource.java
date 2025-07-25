package com.sos.obs.decc.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.payload.ApiResponse;
import com.sos.dash.util.AppConstants;
import com.sos.obs.decc.config.Constants;
import com.sos.obs.decc.domain.Authority;
import com.sos.obs.decc.domain.Center;
import com.sos.obs.decc.domain.User;
import com.sos.obs.decc.repository.AuthorityRepository;
import com.sos.obs.decc.repository.CenterRepository;
import com.sos.obs.decc.repository.UserRepository;
import com.sos.obs.decc.security.AuthoritiesConstants;
import com.sos.obs.decc.service.MailService;
import com.sos.obs.decc.service.UserService;
import com.sos.obs.decc.service.dto.UserDTO;
import com.sos.obs.decc.web.rest.errors.BadRequestAlertException;
import com.sos.obs.decc.web.rest.errors.EmailAlreadyUsedException;
import com.sos.obs.decc.web.rest.errors.LoginAlreadyUsedException;
import com.sos.obs.decc.web.rest.util.HeaderUtil;
import com.sos.obs.decc.web.rest.util.ResponseUtil;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/admin")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;
    
    private final CenterRepository centerRepository;

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;


    public UserResource(UserService userService, UserRepository userRepository, AuthorityRepository authorityRepository, MailService mailService, 
    		PasswordEncoder passwordEncoder,CenterRepository centerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.mailService = mailService;
        this.centerRepository = centerRepository;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    
    /*@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")*/
    @PutMapping("/users")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
    }
    
    
    @PutMapping("/users/update")
    public ResponseEntity<?> updateUser1(@RequestBody UserDTO userDTO) {
    	if (userDTO.getId() != null){
    		
    		log.warn("ID non null - update", userDTO);
    		Optional<User> existingUser = userRepository.findById(userDTO.getId());
    		//---- Controle si le login exit deja
            Optional<User> existingUserLogin = userRepository.findOneByLogin(userDTO.getLogin());
            if (!existingUserLogin.isPresent()) {
        		return ResponseUtil.wrapFound("userManagement", "/users/update", existingUser, false);
            }
    		//-------------------------
    		User userupdate = updateuser(existingUser, userDTO);
    		log.info("Update", userupdate);
    		return ResponseUtil.wrapFound("userManagement", "/users/update", existingUser, true);
    	}
    	
    	if (userDTO.getLogin() != null){
    		log.warn("Login non null - update", userDTO);
    		Optional<User> existingUser = userRepository.findOneByLogin(userDTO.getLogin());
            if (!existingUser.isPresent()) {
        		return ResponseUtil.wrapFound("userManagement", "/users/update", existingUser, false);
            }
    		User userupdate = updateuser(existingUser, userDTO);
    		log.info("Update", userupdate);
            return ResponseUtil.wrapFound("userManagement", "/users/update", existingUser, true);
    	}	
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

/*
    @PutMapping("/users/update/*")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> updateUser2(@RequestBody UserDTO userDTO) {
    	if (userDTO.getId() != null){
    		Optional<User> existingUser = userRepository.findById(userDTO.getId());
    		updateuser(existingUser, userDTO);
    		return ResponseUtil.wrapOrNotFound(existingUser,
    	            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
    	}
    	
    	if (userDTO.getLogin() != null){
    		Optional<User> existingUser = userRepository.findOneByLogin(userDTO.getLogin());
    		updateuser(existingUser, userDTO);
    		return ResponseUtil.wrapOrNotFound(existingUser,
    	            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
    	}	
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
    }
    */
    private User updateuser(Optional<User> existingUser, UserDTO userDTO) {
    	User user = null;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setLogin(userDTO.getLogin());
            user.setLastName(userDTO.getLastName());
            user.setFirstName(userDTO.getFirstName());
            if (userDTO.getEmail() != null) {
            	user.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            if (userDTO.getAuthorities() != null) {
                Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
                user.setAuthorities(authorities);
            }
            
            if (userDTO.getCenters() != null) {
            	//user.setCenters(userDTO.getCenters());
                Set<Center> centers = userDTO.getCenters().stream()
                        .map( center-> centerRepository.findByName(center.getName()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
                    user.setCenters(centers);
            }
            user = userRepository.save(user);
            existingUser = Optional.of(user);
        }else {
            log.warn("L'utilisateur n'existe pas", userDTO);
        }
            return user;
    	
    }


    @PostMapping("/users/add")
    public ResponseEntity<ApiResponse> createUser2(@RequestBody UserDTO userDTO) {
        
        log.debug("REST request to add User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        User newUser = userService.createUser(userDTO);

        return ResponseUtil.wrapFound("userManagement", "/users/add", newUser, true);
    }



    @GetMapping("/users/list")
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
    	Pageable p = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        final Page<UserDTO> users = userService.findAll(p);
       return new ResponseEntity<>(users, HttpStatus.OK);
    }
 
    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsersPage(@RequestParam(name = "tri", required = false, defaultValue = "ASC") String tri
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
        final List<UserDTO> users = userService.getAllManagedUsers();
 
        Pageable pagiable = PageRequest.of(page - 1, size);
        int start = (page - 1) * size;
        int end = start + size;

        Page<UserDTO> pages;
        if (end <= users.size()) {
            pages = new PageImpl<>(users.subList(start, end), pagiable, users.size());
            return new ResponseEntity<>(pages, HttpStatus.OK);

        } else {
            if (start < users.size()) {
            	users.size();
                pages = new PageImpl<>(users.subList(start, users.size()), pagiable, users.size());
                return new ResponseEntity<>(pages, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    

    @GetMapping("/users_sort")
    public ResponseEntity<?> getAllUsersPageSort(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    	    , @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page
    	    , @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	int pagenumber = (page - 1);
    	Pageable paging = PageRequest.of(pagenumber, size, Sort.by(sort));
    	final Page<UserDTO> users = userService.findAll(paging);
    	return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        log.debug("REST request to get User : {}", id);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthorities(id)
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", login)).build();
    }


    @DeleteMapping("/users/delete/{id}")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> deleteUser2(@PathVariable String id) {
        log.debug("REST request to delete User: {}", id);
        userService.deleteUserById(id);
       /* return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userManagement.deleted", "" + id)).build();*/
        
        return ResponseUtil.wrapFound("userManagement", "/users/delete/{id}", id, true);
    }
    
    
    @DeleteMapping("/users/delete/{ids}")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> deleteUser3(@PathVariable List<String> ids) {
    	for (String id : ids) {
	      log.debug("REST request to delete User: {}", id);
	      userService.deleteUserById(id);   
    	}
    	/*return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userManagement.deleted", "" + ids)).build();*/
    	
    	return ResponseUtil.wrapFound("userManagement", "/users/delete/{ids}", ids, true);
  }
    
    @PostMapping("/users/delete")
    public ResponseEntity<?> deleteUserPost(@RequestBody UserDTO userDTO) {
        log.debug("REST request to delete User : {}", userDTO);
        Optional<User> user = userService.getUser(userDTO);
        
        if (user.get().getId() != null) {
	        log.debug("REST request to delete User: {}", user.get().getId());
	        userService.deleteUserById(user.get().getId());
        }
    	return ResponseUtil.wrapFound("userManagement", "/users/delete", userDTO, true);
    }
    
    @PostMapping("/users/delete/")
    public ResponseEntity<?> deleteListUserPost(@RequestBody List<UserDTO> userDTOs) {
    	
    	for (UserDTO userd : userDTOs) {
	        log.debug("REST request to delete User : {}", userDTOs);
	        
	        Optional<User> user = userService.getUser(userd);
	        
	        if (user.get().getId() != null) {
		        log.debug("REST request to delete User: {}", user.get().getId());
		        userService.deleteUserById(user.get().getId());
	        }
    	}
    	return ResponseUtil.wrapFound("userManagement", "/users/delete", userDTOs, true);
    }
    

    @GetMapping("/users/count")
    public long getUserCount() {
        return userService.countUser();
    }
    

}
