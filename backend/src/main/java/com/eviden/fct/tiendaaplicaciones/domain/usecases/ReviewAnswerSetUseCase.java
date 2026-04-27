package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ReviewDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReviewAnswerSetUseCase {
	
	private final AuthenticationApplicationService authService;
	private final ReviewDomainService reviewService;

	public Review invoke(Long reviewId, String response) throws UnauthorizedException, ForbiddenException, NotFoundException, ConflictException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		Review review = reviewService.get(reviewId);
		Content content = review.getContent();
		
		if (!content.getDeveloper().getId().equals(user.getId())) throw new ForbiddenException();
		
		Review changes = Review.builder().answer(response).build();
		return reviewService.update(review.getId(), changes);
	}
	
}
