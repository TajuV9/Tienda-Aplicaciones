package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;

public interface ContentSpecificationDefinition {

	public Specification<Content> filterBy(Map<String, Object> filters);
	public Specification<Content> filterBy(ContentFilter filters);
	
	public Specification<Content> isExpensiveContent();
	public Specification<Content> nameContains(String name);
	public Specification<Content> recentlyUpdated();
	
}
