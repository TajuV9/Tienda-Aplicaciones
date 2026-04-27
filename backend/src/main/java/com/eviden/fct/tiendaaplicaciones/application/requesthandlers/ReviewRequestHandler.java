package com.eviden.fct.tiendaaplicaciones.application.requesthandlers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRquestReviewsDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestSetAnswerDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseListDto;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ReviewRequestCreateDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ReviewRequestUpdateDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ReviewResponseInformationDtoMapper;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewAnswerDeleteUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewAnswerSetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewCreateUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewDeleteUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewGetFromUserUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ReviewUpdateUseCase;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewRequestHandler {
	
	private final ReviewCreateUseCase reviewCreateUseCase;
	private final ReviewUpdateUseCase reviewUpdateUseCase;
	private final ReviewDeleteUseCase reviewDeleteUseCase;
	private final ReviewGetUseCase reviewGetUseCase;
	private final ReviewGetFromUserUseCase reviewGetFromUserUseCase;
	private final ReviewAnswerSetUseCase reviewAnswerSetUseCase;
	private final ReviewAnswerDeleteUseCase reviewAnswerDeleteUseCase;
	
	private final ReviewRequestCreateDtoMapper reviewRequestCreateDtoMapper;
	private final ReviewRequestUpdateDtoMapper reviewRequestUpdateDtoMapper;
	private final ReviewResponseInformationDtoMapper reviewResponseInformationDtoMapper;
	

	public ReviewResponseInformationDto handleCreateRequest(Long contentId, ReviewRequestCreateDto data) throws Exception {
		Review review = reviewRequestCreateDtoMapper.transform(data);
		Review created = reviewCreateUseCase.invoke(contentId, review);
		return reviewResponseInformationDtoMapper.transform(created);
	}
	
	public ReviewResponseInformationDto handleUpdateRequest(Long contentId, ReviewRequestUpdateDto data) throws Exception {
		Review review = reviewRequestUpdateDtoMapper.transform(data);
		Review updated = reviewUpdateUseCase.invoke(contentId, review);
		return reviewResponseInformationDtoMapper.transform(updated);
	}
	
	public GenericSuccessDto handleDeleteRequest(Long contentId) throws Exception {
		reviewDeleteUseCase.invoke(contentId);
		return new GenericSuccessDto(AppConstants.SUCCESS_REVIEW_DELETED);
	}
	
	public ReviewResponseInformationDto handleUpdateAnswerRequest(Long reviewId, ReviewRequestSetAnswerDto data) throws Exception {
		Review updated = reviewAnswerSetUseCase.invoke(reviewId, data.getAnswer());
		return reviewResponseInformationDtoMapper.transform(updated);
	}
	
	public ReviewResponseInformationDto handleDeleteAnswerRequest(Long reviewId) throws Exception {
		Review updated = reviewAnswerDeleteUseCase.invoke(reviewId);
		return reviewResponseInformationDtoMapper.transform(updated);
	}
	
	public ReviewResponseListDto handleGetRequest(Long contentId, ContentRquestReviewsDto dto) throws Exception {
		int page = dto.getPage();
		int pageSize = dto.getPageSize();
		
		Page<Review> reviews = reviewGetUseCase.invoke(contentId, page, pageSize);
		Review userReview = reviewGetFromUserUseCase.invoke(contentId);
		ReviewResponseListDto response = new ReviewResponseListDto();
		
		response.setAllReviews(reviews.getContent().stream().map(reviewResponseInformationDtoMapper::transform).toList());
		if (userReview != null) response.setUserReview(reviewResponseInformationDtoMapper.transform(userReview));
		
		response.setCurrentPage(reviews.getNumber());
		response.setTotalPages(reviews.getTotalPages());
		response.setTotalElements(reviews.getTotalElements());
		response.setCurrentPageSize(reviews.getNumberOfElements());
		
		return response;
	}

}
