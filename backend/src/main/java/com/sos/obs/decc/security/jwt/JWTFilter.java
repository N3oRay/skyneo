package com.sos.obs.decc.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {
	
    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_TOKEN = "access_token";

    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

	private String getCookieValue(HttpServletRequest req, String cookieName) {
		if (req.getCookies()!= null) {
		    return Arrays.stream(req.getCookies())
		            .filter(c -> c.getName().equals(cookieName))
		            .findFirst()
		            .map(Cookie::getValue)
		            .orElse(null);
		}else {
			return "";
		}
	}

 
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }
        return "No cookies";
    }
    
	private String resolveToken(HttpServletRequest request) {
		String jwt = request.getParameter(AUTHORIZATION_TOKEN);
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

		String cookies = readAllCookies(request);
		log.debug("Cookies: " + cookies + " ");

		String simpleCookie = getCookieValue(request, "DashBoard");
		log.debug("Cookies simple: " + simpleCookie + " ");
		try {
		// On lit le cookie du Header si il existe pour le token
		if (!simpleCookie.isEmpty()) {
			System.out.println("Cookies OK: " + simpleCookie);
			return simpleCookie;
		}
		
		} catch (Exception e) {
			log.debug("Cookie null");
		}
		// Sinon on utilise le : Authorization du Header
		try {
			if (!bearerToken.isEmpty() && StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
				log.debug("Bearer OK: ");
				return bearerToken.substring(7);
			}
		} catch (Exception e) {
			log.debug("Bearer null");
		}

		if (StringUtils.hasText(jwt)) {
			log.debug("jwt OK: ");
			return jwt;
		}
		return null;

	}
}
