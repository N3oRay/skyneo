package com.sos.obs.decc.config;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.sos.obs.decc.domain.User;
import com.sos.obs.decc.repository.UserRepository;
import com.sos.obs.decc.service.UserService;

public class AppAuthProvider extends DaoAuthenticationProvider {
    @Autowired
    UserService userDetailsService;
    @Autowired
    UserRepository userRepository;
    
    
    //@Override
    public UsernamePasswordAuthenticationToken authenticate(UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = authentication;
        String name = auth.getName();
        String password = auth.getCredentials()
                .toString();
        
       // Optional<User> existingUser = userRepository.findOneByLogin(auth.getName().toLowerCase());
        Optional<User> user = userDetailsService.getUserLoginWithAuthorities(name);
        if (user == null) {
            throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }
        return new UsernamePasswordAuthenticationToken(user, user.get().getAuthorities());

    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
