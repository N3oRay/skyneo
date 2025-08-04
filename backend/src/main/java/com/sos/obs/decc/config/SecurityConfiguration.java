package com.sos.obs.decc.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.sos.obs.decc.security.AuthoritiesConstants;
import com.sos.obs.decc.security.jwt.JWTConfigurer;
import com.sos.obs.decc.security.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
@ComponentScan("com.sos.obs.decc")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

	public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
			UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter,
			SecurityProblemSupport problemSupport) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userDetailsService = userDetailsService;
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.problemSupport = problemSupport;
	}

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    //https://www.baeldung.com/spring-security-session
    //https://stackoverflow.com/questions/55934004/server-session-cookie-max-age-doesnt-seem-to-work
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf()
        .disable()
        .addFilterBefore(corsFilter, SimpleAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(problemSupport)
        .accessDeniedHandler(problemSupport)
    .and()
        .headers()
        .frameOptions()
        .disable()
    .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // SessionCreationPolicy.STATELESS
        .and()
        //.authorizeRequests()
        .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // code a supprimer apres test
        .antMatchers("/api/admin/register").permitAll()
        .antMatchers("/api/admin/activate").permitAll()
        .antMatchers("/api/admin/authenticate").permitAll()
        .antMatchers("/api/admin/auth").permitAll()
        .antMatchers("/api/admin/signin").permitAll()
        .antMatchers("/api/admin/login").permitAll()
        .antMatchers("/api/admin/checkSession").authenticated()
        .antMatchers("/api/admin/account/reset-password/init").permitAll()
        .antMatchers("/api/admin/account/reset-password/finish").permitAll()
        .antMatchers("/api/admin/**").authenticated()
        .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/websocket/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/management/info").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/management/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.CENTER)
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/admin/logout")) //https://docs.spring.io/spring-security/site/docs/current/reference/html5/#csrf-logout
        .logoutSuccessUrl("/api/admin/login")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .and()
        .apply(securityConfigurerAdapter());
    }


    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }


}
