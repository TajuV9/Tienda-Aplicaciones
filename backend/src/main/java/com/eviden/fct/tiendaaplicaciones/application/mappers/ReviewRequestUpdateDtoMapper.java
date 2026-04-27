package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;

@Component
public class ReviewRequestUpdateDtoMapper implements RequestDtoMapper<Review, ReviewRequestUpdateDto> {

	@Override
	public Review transform(ReviewRequestUpdateDto dto) {
		return Review.builder()
				.review(dto.getReview())
				.rating(dto.getRating())
				.build();
	}

}
