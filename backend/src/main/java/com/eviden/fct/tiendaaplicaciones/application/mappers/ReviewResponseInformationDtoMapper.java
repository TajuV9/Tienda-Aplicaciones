package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;

@Component
public class ReviewResponseInformationDtoMapper implements ResponseDtoMapper<Review, ReviewResponseInformationDto> {

	@Override
	public ReviewResponseInformationDto transform(Review entity) {
		return ReviewResponseInformationDto.builder()
				.id(entity.getId())
				.authorName(String.format("%s %s", entity.getUser().getName(), entity.getUser().getLastName()))
				.review(entity.getReview())
				.response(entity.getAnswer())
				.rating(entity.getRating())
				.build();
	}

}
