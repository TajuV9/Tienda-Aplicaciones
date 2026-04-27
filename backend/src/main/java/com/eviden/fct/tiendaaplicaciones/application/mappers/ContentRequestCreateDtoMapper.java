package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;

@Component
public class ContentRequestCreateDtoMapper implements RequestDtoMapper<Content, ContentRequestCreateDto> {

	@Override
	public Content transform(ContentRequestCreateDto dto) {
		
		return Content.builder()
				.name(dto.getTitle())
				.description(dto.getDescription())
				.category(dto.getCategory())
				.build();
		
	}

	
	
}
