package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentCreateUseCase {
	
	private final ContentDomainService contentService;
	private final AuthenticationApplicationService authService;
	
	public Content invoke(Content content) throws ConflictException, UnauthorizedException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		content.setDeveloper(user);		
		return contentService.create(content);
	}
	
}
