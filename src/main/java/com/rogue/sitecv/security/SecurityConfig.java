package com.rogue.sitecv.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		

		http.authorizeHttpRequests(
				authz -> {
					authz.requestMatchers(HttpMethod.GET, "/api/section").permitAll();
					authz.requestMatchers(HttpMethod.GET, "/image/**").permitAll();
					authz.requestMatchers(HttpMethod.POST, "/api/section").hasAuthority("ADMIN");
					authz.requestMatchers("/api/category", "/user/**", "/image/**", "/api/section/**")
							.hasAuthority("ADMIN");
					authz.requestMatchers("/login").permitAll();
					authz.anyRequest().denyAll();
				}).csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilter(new JwtAuthentificationFilter(authenticationManager()));
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {

		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getBCryptPasswordEncoder());

		List<AuthenticationProvider> providers = List.of(authProvider);

		return new ProviderManager(providers);
	}

	@Bean
	protected BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
