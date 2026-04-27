package com.eviden.fct.tiendaaplicaciones.application.dtos;

import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentMediaResponseInformationDto {
	
	private Long id;

	private ContentMediaType type;
	
	private String url;
	
}
