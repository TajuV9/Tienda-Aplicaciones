package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestFilterDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;

@Component
public class ContentRequestFilterDtoMapper implements RequestDtoMapper<ContentFilter, ContentRequestFilterDto> {

	@Override
	public ContentFilter transform(ContentRequestFilterDto dto) {
		
		return ContentFilter.builder()
				.name(dto.getName())
				.category(dto.getCategory())
				.orderBy(dto.getOrderBy())
				.asc(dto.getAsc())
				.page(dto.getPage())
				.pageSize(dto.getPageSize())
				.state(ContentState.PUBLISHED)
				.build();
	}
	
}
