package com.eviden.fct.tiendaaplicaciones.domain.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.ContentMediaRepository;

@Service
public class ContentMediaDomainService extends StandardDomainService<ContentMedia, Long> {

	// Dependency injection
	public ContentMediaDomainService(ContentMediaRepository contentMediaRepository) {
		super(contentMediaRepository);
	}

	
	// Required implementations
	@Override
	protected Long getId(ContentMedia entity) {
		return entity.getId();
	}

	@Override
	protected void setId(ContentMedia entity, Long id) {
		entity.setId(id);
	}

	@Override
	protected void setCreateAt(ContentMedia entity, Date date) {
		entity.setCreatedAt(date);
	}

	@Override
	protected void setUpdateAt(ContentMedia entity, Date date) {
		entity.setUpdatedAt(date);
	}
	
}
