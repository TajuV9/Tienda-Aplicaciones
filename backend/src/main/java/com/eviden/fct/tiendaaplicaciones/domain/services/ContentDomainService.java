package com.eviden.fct.tiendaaplicaciones.domain.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.ContentRepository;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.ContentSpecificationDefinition;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

@Service
public class ContentDomainService extends StandardDomainService<Content, Long> {

	// Fields obtained with dependency injection.
	private final ContentRepository contentRepository;
	private final ContentSpecificationDefinition contentSpecification;
	
	public ContentDomainService(ContentRepository contentRepository, ContentSpecificationDefinition contentSpecification) {
		super(contentRepository);
		this.contentRepository = contentRepository;
		this.contentSpecification = contentSpecification;
		
		addExistCheck(entity -> contentRepository.findByName(entity.getName()), AppConstants.ERRORS_CONTENT_NAME_DUPLICATED);
	}
	
	// Custom methods
	public List<Content> getAllAvailable() {
		return contentRepository.findByState(ContentState.PUBLISHED);
	}
	
	public List<Content> getAllUserCreated(Long userId) {
		return contentRepository.findByDeveloperIdNotDeleted(userId);
	}
	
	public List<Content> getAllPublishedWithFilters(ContentFilter filter) {
		Sort sort = Sort.unsorted();
		Direction direction = filter.getAsc() ? Direction.ASC : Direction.DESC;
		if (filter.getOrderBy() != null) {
			sort = Sort.by(direction, filter.getOrderBy());
		}
		Pageable page = PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
		
		return contentRepository.findAll(contentSpecification.filterBy(filter), page).getContent();
	}

	
	// Transforms
	@Override
	protected Content transformOnCreate(Content entity) {
		entity.setPrice(BigDecimal.valueOf(0)); // Not available right now
		entity.setVersion(0L);
		entity.setImage(false);
		entity.setState(ContentState.CREATED);
		entity.setDownloads(List.of());
		entity.setMedia(List.of());
		return entity;
	}
	
	@Override
	protected Content transformOnUpdate(Content original, Content changes) {
		if (changes.getName() != null) original.setName(changes.getName());
		if (changes.getDescription() != null) original.setDescription(changes.getDescription());
		if (changes.getImage() != null) original.setImage(changes.getImage());
		if (changes.getFileName() != null) original.setFileName(changes.getFileName());
		if (changes.getDeveloper() != null) original.setDeveloper(changes.getDeveloper());
		if (changes.getCategory() != null) original.setCategory(changes.getCategory());
		if (changes.getDownloads() != null) original.setDownloads(changes.getDownloads());
		if (changes.getMedia() != null) original.setMedia(changes.getMedia());
		if (changes.getState() != null) original.setState(changes.getState());
		if (changes.getVersion() != null) original.setVersion(changes.getVersion());
		
		return original;
	}
	
	@Override
	protected Optional<Content> transformOnDelete(Content entity) {
		entity.setState(ContentState.DELETED);
		return Optional.of(entity);
	}
	
	
	// Implementations
	@Override
	protected Long getId(Content entity) {
		return entity.getId();
	}

	@Override
	protected void setId(Content entity, Long id) {
		entity.setId(id);
	}

	@Override
	protected void setCreateAt(Content entity, Date date) {
		entity.setCreatedAt(date);
	}

	@Override
	protected void setUpdateAt(Content entity, Date date) {
		entity.setUpdatedAt(date);
	}
	
	
}
