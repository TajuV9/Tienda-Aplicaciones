package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentGetUseCase {

	private final ContentDomainService contentService;
	private final AuthenticationApplicationService authService;
	
	public Content invoke(Long id) throws NotFoundException, ForbiddenException {
		
		
		Content content = contentService.get(id);
		
		if (content.getState() == ContentState.DELETED) throw new NotFoundException();
		
		if (content.getState() == ContentState.CREATED) {
			User user = authService.getAuthenticatedUser().orElseThrow(ForbiddenException::new);
			if (!user.getId().equals(content.getDeveloper().getId())) throw new ForbiddenException();
		}
		
		return content;
	}
	
}
