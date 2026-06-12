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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String ADMIN_ROLE = "ADMIN";

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> {})
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/auth/login/**",
								"/auth/create-user/**"
						).permitAll()
						.requestMatchers("/test/users/**")
						.hasAuthority(ADMIN_ROLE)
						.anyRequest()
						.authenticated()
				)
				.userDetailsService(customUserDetailService)
				.exceptionHandling(ex ->
						ex.authenticationEntryPoint(point)
				)
				.sessionManagement(session ->
						session.sessionCreationPolicy(
								SessionCreationPolicy.STATELESS
						)
				)
				.addFilterBefore(
						filter,
						UsernamePasswordAuthenticationFilter.class
				);

		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();

		provider.setUserDetailsService(
				customUserDetailService
		);

		provider.setPasswordEncoder(
				passwordEncoder
		);

		return provider;
	}
}