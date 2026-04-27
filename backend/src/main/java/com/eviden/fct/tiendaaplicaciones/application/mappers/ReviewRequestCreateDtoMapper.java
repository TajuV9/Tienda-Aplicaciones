package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;

@Component
public class ReviewRequestCreateDtoMapper implements RequestDtoMapper<Review, ReviewRequestCreateDto> {

	@Override
	public Review transform(ReviewRequestCreateDto dto) {
		return Review.builder()
				.review(dto.getReview())
				.rating(dto.getRating())
				.build();
	}

}
