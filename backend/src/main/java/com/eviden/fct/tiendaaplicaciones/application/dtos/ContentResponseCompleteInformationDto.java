package com.eviden.fct.tiendaaplicaciones.application.dtos;


import java.util.List;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResponseCompleteInformationDto {
	
	private Long id;

	private String title;
	
	private String description;
	
	private String imageUrl;
	
	private String downloadUrl;
	
	private String creatorName;
	
	private Category category;
	
	private Double rating;
	
	private Long downloads;
	
	private Boolean published;
	
	private List<ContentMediaResponseInformationDto> media;
	
}
