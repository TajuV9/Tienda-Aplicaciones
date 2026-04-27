package com.eviden.fct.tiendaaplicaciones.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseInformationDto {
	
	private Long id;
	
	private String authorName;
	
	private String review;
	
	private String response;
	
	private Integer rating;
	
}
