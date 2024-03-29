package com.panasi.interview_questions.security.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.panasi.interview_questions.security.jwt.AuthEntryPointJwt;
import com.panasi.interview_questions.security.jwt.AuthTokenFilter;
import com.panasi.interview_questions.security.repository.entity.Role;
import com.panasi.interview_questions.security.repository.entity.User;
import com.panasi.interview_questions.security.service.UserDetailsImpl;
import com.panasi.interview_questions.security.service.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	   
	    return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
	    return authConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsImpl getAnonimus() {
		Set<Role> roles = new HashSet<>();
		User testUser = new User(0, "Guest", "guest@gmail.com", "password", roles);
		return UserDetailsImpl.build(testUser);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	    	.anonymous().principal(getAnonimus()).authorities("GUEST_ROLE").and()
	        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .authorizeRequests()
	        .antMatchers("/auth/**").permitAll()
	        .antMatchers(HttpMethod.GET, "/questions/**", "/categories/**", "/answers/**").permitAll()
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .antMatchers("/swagger.html", "/swagger-ui/**", "/api-docs/**").hasRole("ADMIN")
	        .antMatchers("/h2-console/**").hasRole("ADMIN")
	        .antMatchers("/sendEmail", "/sendSms").hasRole("ADMIN")
	        .anyRequest().authenticated();
	    
	    http.authenticationProvider(authenticationProvider());
	    
	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}

}
