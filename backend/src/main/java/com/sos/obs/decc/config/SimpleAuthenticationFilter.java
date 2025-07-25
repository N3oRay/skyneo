package com.sos.obs.decc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SimpleAuthenticationFilter
extends UsernamePasswordAuthenticationFilter {
	

	static final String ORIGIN = "127.0.0.1";

		    @Override
		    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
		    	if (request.getHeader(ORIGIN).equals("null")) {
		            String origin = request.getHeader(ORIGIN);
		            response.addHeader("Access-Control-Allow-Origin", origin);
		            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		            response.addHeader("Access-Control-Allow-Credentials", "true");
		            response.addHeader("Access-Control-Allow-Headers",
		                    request.getHeader("Access-Control-Request-Headers"));
		        }
		        if (request.getMethod().equals("OPTIONS")) {
		            try {
		                response.getWriter().print("OK");
		                response.getWriter().flush();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return super.attemptAuthentication(request, response);
		    }

}
