package com.sos.obs.decc.web.rest;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sos.obs.decc.security.jwt.JWTFilter;
import com.sos.obs.decc.security.jwt.TokenProvider;
import com.sos.obs.decc.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api/admin")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }
    
    

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginVM loginVM) {
    	
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        
        String cookieHeader = "Bearer " + jwt;
		httpHeaders.add(HttpHeaders.COOKIE, cookieHeader  );
		httpHeaders.add("Set-Cookie","platform=mobile; Max-Age=604800; Path=/; Secure; HttpOnly");
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    
   
    
    @PostMapping("/auth")
    public ResponseEntity<?> loginGet(@RequestBody LoginVM loginVM, HttpServletResponse response) {
        // TODO: add your login logic here
        String token = "NOT_AVAILABLE";
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        token = tokenProvider.createToken(authentication, rememberMe);

        // create More cookie Sample !
        boolean dashcookie = (loginVM.getCookie() == null) ? false : loginVM.getCookie();
        if (dashcookie) {
	        Cookie cookie = new Cookie("DashBoard",token);
	        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days 
	        cookie.setSecure(true); // optional properties
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        //cookie.set
	        // add cookie to response
	        response.addCookie(cookie);
        }
        // return response entity
        return new ResponseEntity<>(new JWTToken(token), HttpStatus.OK);
    }
    

    @GetMapping("/auth/read")
    public String readCookie(@CookieValue(value = "platform", defaultValue = "Atta") String username) {

        return "Hey! My username is " + username;

    }
    
    @GetMapping("/auth/all-cookies")
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }

        return "No cookies";

    }
    
    
    public String setCookie(HttpServletResponse response, String key) {
    	String cookieHeader = "Bearer " + key;
        // create a cookie
        Cookie cookie = new Cookie("Bearer", cookieHeader);
        //add cookie to response
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        response.addCookie(cookie);
        return "Username is changed!";

    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
        
}
