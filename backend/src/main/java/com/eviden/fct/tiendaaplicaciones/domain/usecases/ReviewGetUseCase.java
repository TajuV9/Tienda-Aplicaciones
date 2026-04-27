package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.domain.services.ReviewDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReviewGetUseCase {
	
	private final ReviewDomainService reviewService;
	private final ContentDomainService contentService;

	public Page<Review> invoke(Long contentId, int page, int pageSize) throws NotFoundException {	
		Content content = contentService.get(contentId);
		Pageable pageData = PageRequest.of(page, pageSize);

		return reviewService.getByContent(content, pageData);
	}
	
}
