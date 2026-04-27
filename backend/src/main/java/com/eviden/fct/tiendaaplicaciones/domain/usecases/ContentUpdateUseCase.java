package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentUpdateUseCase {
	
	private final ContentDomainService contentService;
	private final AuthenticationApplicationService authService;
	
	public Content invoke(Content changes) throws NotFoundException, ForbiddenException, ConflictException, UnauthorizedException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		
		Content content = contentService.get(changes.getId());
		
		if (content.getState() == ContentState.DELETED ) throw new NotFoundException();
		if (!content.getDeveloper().getId().equals(user.getId())) throw new ForbiddenException();
		
		return contentService.update(content.getId(), changes);
	}
	
}
