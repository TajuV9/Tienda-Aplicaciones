package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileGetUseCase {

	private final AuthenticationApplicationService authService;
	
	public User invoke() throws UnauthorizedException {
		return authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
	}
	
}
