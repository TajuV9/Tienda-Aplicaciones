package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.domain.services.ReviewDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReviewDeleteUseCase {
	
	private final AuthenticationApplicationService authService;
	private final ReviewDomainService reviewService;
	private final ContentDomainService contentService;

	public void invoke(Long contentId) throws UnauthorizedException, NotFoundException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		Content content = contentService.get(contentId);
		Review review = reviewService.getByUserAndContent(user, content);
		
		reviewService.delete(review.getId());
	}
	
}
