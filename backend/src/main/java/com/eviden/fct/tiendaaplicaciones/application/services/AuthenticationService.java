package com.eviden.fct.tiendaaplicaciones.application.services;

import java.util.Optional;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.JwtApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.UserDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationApplicationService {
	
	private final AuthenticationManager authManager;
	private final JwtApplicationService jwtService;
	private final UserDomainService userService;

	@Override
	public void authenticate(String username, String password) throws AuthenticationException {
		try {
			Authentication authToken = new UsernamePasswordAuthenticationToken(username, password);
			authManager.authenticate(authToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(AppConstants.ERRORS_AUTH_BAD_CREDENTIALS, e);
		} catch (LockedException e) {
			throw new LockedException(AppConstants.ERRORS_AUTH_LOCKED_ACCOUNT, e);
		} catch (AccountExpiredException e) {
			throw new AccountExpiredException(AppConstants.ERRORS_AUTH_EXPIRED_ACCOUNT, e);
		} catch (DisabledException e) {
			throw new DisabledException(AppConstants.ERRORS_AUTH_DISABLED_ACCOUNT, e);
		}
		
	}

	@Override
	public void authenticate(String token) throws AuthenticationException {
		if (SecurityContextHolder.getContext().getAuthentication() != null) return;
		
		String username = jwtService.extractUsername(token);
		if (username == null) throw new BadCredentialsException(AppConstants.ERRORS_AUTH_SESSION_NOT_FOUND);
		
		UserDetails userDetails = userService.loadUserByUsername(username);
		
		if (!jwtService.validateToken(token, username)) throw new BadCredentialsException(AppConstants.ERRORS_AUTH_EXPIRED_SESSION);
		if (!userDetails.isAccountNonLocked()) throw new LockedException(AppConstants.ERRORS_AUTH_LOCKED_ACCOUNT);
		if (!userDetails.isAccountNonExpired()) throw new AccountExpiredException(AppConstants.ERRORS_AUTH_EXPIRED_ACCOUNT);
		if (!userDetails.isCredentialsNonExpired()) throw new CredentialsExpiredException(AppConstants.ERRORS_AUTH_EXPIRED_CREDENTIALS);
		if (!userDetails.isEnabled()) throw new DisabledException(AppConstants.ERRORS_AUTH_DISABLED_ACCOUNT);
		
		Authentication authToken = UsernamePasswordAuthenticationToken.authenticated(username, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
	
	@Override
	public Optional<User> getAuthenticatedUser() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
			if (username == null) return Optional.empty();
			return Optional.ofNullable(userService.getUserInformation(username));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
		
	}

}
