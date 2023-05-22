package com.rogue.sitecv.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers,Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method,Access-Control-Request-Headers, Authorization");
		response.addHeader("Access-Control-Expose-Headers",
				"Authorization, Access-Control-Allow-Origin,Access-Control-Allow-Credentials ");

		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		final String jwtToken = request.getHeader("Authorization");

		if (jwtToken == null || !jwtToken.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("secret key token")).build();

		final String tokenWithoutPrefix = jwtToken.substring(7);

		final DecodedJWT decodedJWT = jwtVerifier.verify(tokenWithoutPrefix);

		final String userName = decodedJWT.getSubject();

		final List<GrantedAuthority> authorities = decodedJWT.getClaims().get("roles").asList(String.class).stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userName, null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		filterChain.doFilter(request, response);
	}

}
