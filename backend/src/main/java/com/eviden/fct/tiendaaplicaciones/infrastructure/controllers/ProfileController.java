package com.eviden.fct.tiendaaplicaciones.infrastructure.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestPasswordChangeDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseBasicInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseDownloadDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseReviewDto;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.ProfileRequestHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/profile")
public class ProfileController {

	private final ProfileRequestHandler requestHandler;
	
	
	
	// GET PROFILE INFORMATION
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app. Authentication is required in order to obtain the profile information.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	public ProfileResponseInformationDto getProfileInfo() throws Exception {
		return requestHandler.handleGetProfileRequest();
	}
	
	
	
	// UPDATE PROFILE
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "400",
		description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app. Authentication is required in order to update the profile.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	public ProfileResponseInformationDto updateProfileInfo(@Valid @RequestBody ProfileRequestUpdateDto requestData) throws Exception {
		return requestHandler.handleUpdateProfileRequest(requestData);
	}
	
	
	
	// CHANGE PASSWORD
	@PostMapping(
		path = "/change-password",
		consumes = { MediaType.APPLICATION_JSON_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "400",
		description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app or the provided current password is invalid.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "409",
		description = "A conflict has ocurred. Most likely, the old password is the same as the new one.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ResponseStatus(HttpStatus.OK)
	public GenericSuccessDto changePassword(@Valid @RequestBody AuthRequestPasswordChangeDto requestData) throws Exception {
		return requestHandler.handlePasswordChangeRequest(requestData);
	}
	
	
	
	// BECOME CREATOR
	@PostMapping(
		path = "/become-creator",
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app or the provided current password is invalid.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "409",
		description = "A conflict has ocurred. Most likely, the user is already a creator.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ResponseStatus(HttpStatus.OK)
	public GenericSuccessDto becomeCreator() throws Exception {
		return requestHandler.handleBecomeCreatorRequest();
	}
	
	
	
	// GET OWN CONTENT
	@GetMapping(
		path = "/content",
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "403",
		description = "User is not a creator.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ResponseStatus(HttpStatus.OK)
	public List<ContentResponseBasicInformationDto> getContent() throws Exception {
		return requestHandler.handleOwnContentRequest();
	}
	
	
	
	// GET DOWNLOADS
	@GetMapping(
		path = "/downloads",
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ResponseStatus(HttpStatus.OK)
	public List<ProfileResponseDownloadDto> getDownloads() throws Exception {
		return requestHandler.handleDownloadedContentRequest();
	}
	
	// GET REVIEWS
	@GetMapping(
		path = "/reviews",
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(
		responseCode = "401",
		description = "User is not authenticated in the app.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ResponseStatus(HttpStatus.OK)
	public List<ProfileResponseReviewDto> getReviews() throws Exception {
		return requestHandler.handleReviewsRequest();
	}
	
}
