package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.domain.services.ReviewDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReviewCreateUseCase {
	
	private final AuthenticationApplicationService authService;
	private final ReviewDomainService reviewService;
	private final ContentDomainService contentService;

	public Review invoke(Long contentId, Review data) throws UnauthorizedException, NotFoundException, ConflictException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		Content content = contentService.get(contentId);
		
		data.setUser(user);
		data.setContent(content);
		
		return reviewService.create(data);
	}
	
}
