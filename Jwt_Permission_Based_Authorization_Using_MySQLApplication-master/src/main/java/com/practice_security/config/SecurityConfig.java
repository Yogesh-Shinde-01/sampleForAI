
package com.practice_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.practice_security.JwtTokenCreate.JwtAuthenticationEntryPoint;
import com.practice_security.JwtTokenCreate.JwtAuthenticationFilter;
import com.practice_security.services.CustomUserDetailService;
import com.practice_security.services.UserService;

import jakarta.annotation.security.PermitAll;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
				.authorizeRequests(auth -> auth.requestMatchers("/auth/login/**", "/auth/create-user/**").permitAll()
						.requestMatchers("/test/users/**").hasAuthority("ADMIN")// Only users with role "ADMIN" can
																				// access this endpoint
						.anyRequest().authenticated())
				.userDetailsService(customUserDetailService).exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// change in code
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

}
