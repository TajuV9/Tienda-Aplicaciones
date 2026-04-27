package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentMediaDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentMediaDeleteUseCase {

	private final AuthenticationApplicationService authService;
	private final ContentMediaDomainService contentMediaService;
	
	public void invoke(Long mediaId) throws UnauthorizedException, NotFoundException, ForbiddenException {
		
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		ContentMedia media = contentMediaService.get(mediaId);
		Content content = media.getContent();
		
		if (content.getState() == ContentState.DELETED) throw new NotFoundException();
		if (!content.getDeveloper().getId().equals(user.getId())) throw new ForbiddenException();
		
		contentMediaService.delete(mediaId);
		
	}
	
}
