package com.eviden.fct.tiendaaplicaciones.application.requesthandlers;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestPasswordChangeDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseBasicInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseDownloadDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseReviewDto;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentResponseBasicInformationDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ProfileRequestUpdateDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ProfileResponseDownloadDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ProfileResponseInformationDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ProfileResponseReviewDtoMapper;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileBecomeCreatorUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileChangePasswordUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileContentGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileDownloadsGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileReviewsGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileUpdateUseCase;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileRequestHandler {

	private final ProfileGetUseCase profileGetUseCase;
	private final ProfileUpdateUseCase profileUpdateUseCase;
	private final ProfileChangePasswordUseCase profileChangePasswordUseCase;
	private final ProfileBecomeCreatorUseCase profileBecomeCreatorUseCase;
	
	private final ProfileContentGetUseCase profileContentGetUseCase;
	private final ProfileDownloadsGetUseCase profileDownloadsGetUseCase;
	private final ProfileReviewsGetUseCase profileReviewsGetUseCase;
	
	private final ProfileResponseInformationDtoMapper profileResponseInformationDtoMapper;
	private final ProfileRequestUpdateDtoMapper profileRequestUpdateDtoMapper;
	private final ContentResponseBasicInformationDtoMapper contentResponseBasicInformationDtoMapper;
	private final ProfileResponseDownloadDtoMapper profileResponseDownloadDtoMapper;
	private final ProfileResponseReviewDtoMapper profileResponseReviewDtoMapper;

	
	
	public ProfileResponseInformationDto handleGetProfileRequest() throws Exception {
		User user = profileGetUseCase.invoke();
		return profileResponseInformationDtoMapper.transform(user);
	}

	public ProfileResponseInformationDto handleUpdateProfileRequest(ProfileRequestUpdateDto data) throws Exception {
		User updateInfo = profileRequestUpdateDtoMapper.transform(data);
		User updatedUser = profileUpdateUseCase.invoke(updateInfo);
		return profileResponseInformationDtoMapper.transform(updatedUser);
	}

	public GenericSuccessDto handlePasswordChangeRequest(AuthRequestPasswordChangeDto data) throws Exception {
		profileChangePasswordUseCase.invoke(data.getOldPassword(), data.getNewPassword());
		return new GenericSuccessDto(AppConstants.SUCCESS_PROFILE_PASSWORD_CHANGED);
	}
	
	public List<ContentResponseBasicInformationDto> handleOwnContentRequest() throws Exception {
		List<Content> content = profileContentGetUseCase.invoke();
		return content.stream().map(contentResponseBasicInformationDtoMapper::transform).toList();
	};
	
	public GenericSuccessDto handleBecomeCreatorRequest() throws Exception {
		profileBecomeCreatorUseCase.invoke();
		return new GenericSuccessDto(AppConstants.SUCCESS_PROFILE_BECAME_CREATOR);
	};
	
	public List<ProfileResponseDownloadDto> handleDownloadedContentRequest() throws Exception {
		List<Download> downloads = profileDownloadsGetUseCase.invoke();
		return downloads.stream().map(profileResponseDownloadDtoMapper::transform).toList();
	};
	
	public List<ProfileResponseReviewDto> handleReviewsRequest() throws Exception {
		List<Review> reviews = profileReviewsGetUseCase.invoke();
		return reviews.stream().map(profileResponseReviewDtoMapper::transform).toList();
	};

}
