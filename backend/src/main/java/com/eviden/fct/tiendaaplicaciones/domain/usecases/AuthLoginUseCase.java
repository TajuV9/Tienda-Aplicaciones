package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.JwtApplicationService;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthLoginUseCase {
	
	private final AuthenticationApplicationService authService;
	private final JwtApplicationService jwtService;

	public String invoke(String username, String password, boolean keepLoggedIn) throws AuthenticationException {
		authService.authenticate(username, password);
		return jwtService.generateToken(username, keepLoggedIn);
	}
	
	public String invoke(String token) throws AuthenticationException {
		String username = jwtService.extractUsername(token);
		boolean keepLoggedIn = jwtService.extractKeepLoggedIn(token);
		
		if (username == null) throw new UsernameNotFoundException(AppConstants.ERRORS_AUTH_SESSION_NOT_FOUND);
		if (!keepLoggedIn) throw new BadCredentialsException(AppConstants.ERRORS_AUTH_SESSION_NOT_FOUND);
		if (!jwtService.validateToken(token, username)) throw new BadCredentialsException(AppConstants.ERRORS_AUTH_EXPIRED_SESSION);
		
		return jwtService.generateToken(username, true);
	}
	
}
