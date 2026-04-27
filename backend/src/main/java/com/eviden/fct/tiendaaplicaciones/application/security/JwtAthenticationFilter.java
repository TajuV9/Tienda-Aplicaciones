package com.eviden.fct.tiendaaplicaciones.application.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAthenticationFilter extends OncePerRequestFilter {
	
	private final AuthenticationApplicationService authService;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String token = getTokenFromRequest(request);

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		authService.authenticate(token);

		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {

		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {
			return authHeader.substring(7);
		}
		return null;
	}

}
