package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ContentGetFilteredUseCase {
	
	private final ContentDomainService contentService;

	public List<Content> invoke(ContentFilter filter) {
		return contentService.getAllPublishedWithFilters(filter);
	}
	
}
