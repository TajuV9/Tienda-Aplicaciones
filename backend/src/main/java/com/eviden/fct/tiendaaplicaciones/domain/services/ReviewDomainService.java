package com.eviden.fct.tiendaaplicaciones.domain.services;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.ReviewRepository;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

@Service
public class ReviewDomainService extends StandardDomainService<Review, Long> {

	// Dependency injection
	private final ReviewRepository reviewRepository;
	
	public ReviewDomainService(ReviewRepository reviewRepository) {
		super(reviewRepository);
		this.reviewRepository = reviewRepository;
		
		addExistCheck(entity -> reviewRepository.findByUserAndContent(entity.getUser(), entity.getContent()), AppConstants.ERRORS_REVIEW_ALREADY_REVIEWED);
	}
	
	// Custom methods
	public Review getByUserAndContent(User user, Content content) throws NotFoundException {
		return reviewRepository.findByUserAndContent(user, content).orElseThrow(NotFoundException::new);
	}
	
	public Page<Review> getByContent(Content content, Pageable pageInfo) {
		return reviewRepository.findByContent(content, pageInfo);
	}
	
	public List<Review> getByUser(User user) {
		return reviewRepository.findByUser(user);
	}
	
	public Review removeReviewResponse(Long reviewId) throws NotFoundException, ConflictException {
		Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);
		review.setAnswer(null);
		return save(review, reviewId, false, _ -> {});
	}
	
	// Transforms
	@Override
	protected Review transformOnUpdate(Review original, Review changes) {
		if(changes.getUser() != null) original.setUser(changes.getUser());
		if(changes.getContent() != null) original.setContent(changes.getContent());
		if(changes.getReview() != null) original.setReview(changes.getReview());
		if(changes.getAnswer() != null) original.setAnswer(changes.getAnswer());
		if(changes.getRating() != null) original.setRating(changes.getRating());
		
		return original;
	}

	// Required methods implementation
	@Override
	protected Long getId(Review entity) {
		return entity.getId();
	}

	@Override
	protected void setId(Review entity, Long id) {
		entity.setId(id);
	}

	@Override
	protected void setCreateAt(Review entity, Date date) {
		entity.setCreatedAt(date);
	}

	@Override
	protected void setUpdateAt(Review entity, Date date) {
		entity.setUpdatedAt(date);
	}
	
}
