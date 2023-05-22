package com.rogue.sitecv.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogue.sitecv.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthentificationFilter.class);

	private AuthenticationManager authenticationManager;

	public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		User user = null;

		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (StreamReadException e) {
			log.error(e.getMessage());
		} catch (DatabindException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		final org.springframework.security.core.userdetails.User userSpring = (org.springframework.security.core.userdetails.User) authResult
				.getPrincipal();

		final List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.addAll(userSpring.getAuthorities());
		
		final String jwtToken = JWT.create()
				.withSubject(userSpring.getUsername())
				.withArrayClaim("roles", userSpring.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList())
						.toArray(new String[userSpring.getAuthorities().size()]))
				.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000))
				.sign(Algorithm.HMAC512("secret key token"));

		response.addHeader("Authorization", jwtToken);
	}
}
