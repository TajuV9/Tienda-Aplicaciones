package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.UserDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileBecomeCreatorUseCase {

	private final UserDomainService userService;
	private final AuthenticationApplicationService authService;
	
	public User invoke() throws ConflictException, UnauthorizedException, NotFoundException {
	
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		
		if (user.getRole() != Role.USER) throw new ConflictException(AppConstants.ERRORS_PROFILE_ALREADY_CREATOR);
		
		User changes = User.builder()
				.role(Role.CREATOR)
				.build();
		
		return userService.update(user.getId(), changes);
	}
	
}
