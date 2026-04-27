package com.eviden.fct.tiendaaplicaciones.infrastructure.controllers;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentMediaResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestFilterDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseCompleteInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRquestReviewsDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseListDto;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.ContentRequestHandler;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.ReviewRequestHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/content")
public class ContentController {
	
	private final ContentRequestHandler requestHandler;
	private final ReviewRequestHandler reviewRequestHandler;
	
	// CREATE
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "400", description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. Creator role is required in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "409", description = "An unique field is duplicated.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ContentResponseCompleteInformationDto create(@Valid @RequestBody ContentRequestCreateDto request)
			throws Exception {
		return requestHandler.handleCreateRequest(request);
	}

	// UPDATE
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to update doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "409", description = "An unique field is duplicated.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ContentResponseCompleteInformationDto update(@PathVariable Long id,
			@Valid @RequestBody ContentRequestUpdateDto request) throws Exception {
		return requestHandler.handleUpdateRequest(id, request);
	}

	// DELETE
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to delete doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public GenericSuccessDto delete(@PathVariable Long id) throws Exception {
		return requestHandler.handleDeleteRequest(id);
	}

	// UPLOAD FILE
	@PutMapping(path = "/{id}/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Provided file does not pass the validation.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to update doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public GenericSuccessDto uploadFile(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception {
		return requestHandler.handleFileUploadRequest(id, file);
	}

	// DOWNLOAD FILE
	@GetMapping(path = "/{id}/file")
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The file you are trying to download doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception {
		return requestHandler.handleFileDownloadRequest(id);
	}

	// UPLOAD ICON
	@PutMapping(path = "/{id}/icon", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Provided file does not pass the validation. Probably because it is not an image.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to update doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public GenericSuccessDto uploadIcon(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception {
		return requestHandler.handleIconUploadRequest(id, file);
	}

	// DOWNLOAD ICON
	@GetMapping(path = "/{id}/icon")
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404", description = "The icon you are trying to download doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ResponseEntity<Resource> downloadIcon(@PathVariable Long id) throws Exception {
		return requestHandler.handleIconDownloadRequest(id);
	}

	// GET SINGLE
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. If the content is not published, user must be the owner to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to get doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ContentResponseCompleteInformationDto getSingle(@PathVariable Long id) throws Exception {
		return requestHandler.handleGetRequest(id);
	}

	// GET ALL WITH FILTERS
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Provided filters are not valid.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public List<ContentResponseCompleteInformationDto> getFiltered(@Valid @ModelAttribute ContentRequestFilterDto dto)
			throws Exception {
		return requestHandler.handleGetWithFiltersRequest(dto);
	}

	// GET REVIEWS
	@GetMapping(path = "/{id}/review", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(responseCode = "400", description = "Provided filters are not valid.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to get doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ReviewResponseListDto getReviews(@PathVariable Long id, @Valid @ModelAttribute ContentRquestReviewsDto dto) throws Exception {
		return reviewRequestHandler.handleGetRequest(id, dto);
	}
	
	// CREATE REVIEW
	@PostMapping(path = "/{id}/review", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(responseCode = "400", description = "Provided data is not in a valid format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The content you are trying to review doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "409", description = "Conflict. Most likely the user has already commented on that content.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ReviewResponseInformationDto createReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestCreateDto dto) throws Exception {
		return reviewRequestHandler.handleCreateRequest(id, dto);
	}
	
	// UPDATE REVIEW
	@PutMapping(path = "/{id}/review", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(responseCode = "400", description = "Provided data is not in a valid format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The content or review you are trying to update doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ReviewResponseInformationDto updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestUpdateDto dto) throws Exception {
		return reviewRequestHandler.handleUpdateRequest(id, dto);
	}
	
	// DELETE REVIEW
	@DeleteMapping(path = "/{id}/review", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The content or review you are trying to delete doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public GenericSuccessDto deleteReview(@PathVariable Long id) throws Exception {
		return reviewRequestHandler.handleDeleteRequest(id);
	}
	
	// UPLOAD MEDIA
	@PostMapping(path = "/{id}/media", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "400", description = "Provided file does not pass the validation. Probably because it is not a valid file.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The contentent you are trying to update doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ContentMediaResponseInformationDto uploadMedia(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception {
		return requestHandler.handleMediaUploadRequest(id, file);
	}

}
