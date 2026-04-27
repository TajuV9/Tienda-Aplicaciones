package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseReviewDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

@Component
public class ProfileResponseReviewDtoMapper implements ResponseDtoMapper<Review, ProfileResponseReviewDto> {
	
	@Value("${app.base-url}")
	private String baseUrl;
	
	@Override
	public ProfileResponseReviewDto transform(Review entity) {
		
		String imageUrl = entity.getContent().getImage() ? String.format(AppConstants.PATH_CONTENT_ICON, baseUrl, entity.getContent().getId()) : null;

		return ProfileResponseReviewDto.builder()
				.id(entity.getId())
				.contentId(entity.getContent().getId())
				.contentName(entity.getContent().getName())
				.contentIcon(imageUrl)
				.review(entity.getReview())
				.response(entity.getAnswer())
				.rating(entity.getRating())
				.build();
	}

}
