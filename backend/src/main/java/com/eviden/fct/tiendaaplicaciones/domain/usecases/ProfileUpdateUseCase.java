package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.UserDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileUpdateUseCase {
	
	private final UserDomainService userService;
	private final AuthenticationApplicationService authService;
	
	public User invoke(User newDetails) throws NotFoundException, UnauthorizedException, ConflictException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		return userService.update(user.getId(), newDetails);		
	}
	
}
