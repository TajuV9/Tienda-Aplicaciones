package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;

@Component
public class ContentRequestUpdateDtoMapper implements RequestDtoMapper<Content, ContentRequestUpdateDto> {

	@Override
	public Content transform(ContentRequestUpdateDto dto) {
		
		return Content.builder()
				.description(dto.getDescription())
				.build();
		
	}
	
}
