package com.eviden.fct.tiendaaplicaciones.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseReviewDto {
	
	private Long id;
	
	private Long contentId;
	
	private String contentName;
	
	private String contentIcon;
	
	private String review;
	
	private String response;
	
	private Integer rating;
	
}
