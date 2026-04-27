package com.eviden.fct.tiendaaplicaciones.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResponseBasicInformationDto {
	
	private Long id;

	private String title;
	
	private String imageUrl;
	
	private Double rating;
	
	private Boolean published;
	
}
