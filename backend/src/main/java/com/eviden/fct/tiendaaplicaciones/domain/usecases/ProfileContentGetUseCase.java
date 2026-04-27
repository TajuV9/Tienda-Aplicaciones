package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileContentGetUseCase {
	
	private final ContentDomainService contentService;
	private final AuthenticationApplicationService authService;
	
	public List<Content> invoke() throws UnauthorizedException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		return contentService.getAllUserCreated(user.getId());
	}
	
}
