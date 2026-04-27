package com.eviden.fct.tiendaaplicaciones.application.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseListDto {
	
	private ReviewResponseInformationDto userReview;
	
	private List<ReviewResponseInformationDto> allReviews;
	
	private Integer currentPage;
	
	private Integer currentPageSize;
		
	private Integer totalPages;
	
	private Long totalElements;
	
}
