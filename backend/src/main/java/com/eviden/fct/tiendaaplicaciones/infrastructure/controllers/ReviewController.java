package com.eviden.fct.tiendaaplicaciones.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewRequestSetAnswerDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ReviewResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.ReviewRequestHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/review")
public class ReviewController {
	
	private final ReviewRequestHandler requestHandler;
	
	// SET REVIEW ANSWER
		@PutMapping(path = "/{id}/answer", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
		@ResponseStatus(HttpStatus.OK)
		@SecurityRequirement(name = "bearerAuth")
		@ApiResponse(responseCode = "400", description = "Provided data is not in a valid format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "403", description = "User is authenticated but doens't have the required permissions to perform this action. User must be the content creator to answer reviews.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "404", description = "The review you are trying to answer doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		public ReviewResponseInformationDto answerReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestSetAnswerDto dto) throws Exception {
			return requestHandler.handleUpdateAnswerRequest(id, dto);
		}
		
		// REMOVE REVIEW ANSWER
		@DeleteMapping(path = "/{id}/answer", produces = { MediaType.APPLICATION_JSON_VALUE })
		@ResponseStatus(HttpStatus.OK)
		@SecurityRequirement(name = "bearerAuth")
		@ApiResponse(responseCode = "400", description = "Provided data is not in a valid format.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "403", description = "User is authenticated but doens't have the required permissions to perform this action. User must be the content creator to answer reviews.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		@ApiResponse(responseCode = "404", description = "The review doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
		public ReviewResponseInformationDto deleteReviewAnswer(@PathVariable Long id) throws Exception {
			return requestHandler.handleDeleteAnswerRequest(id);
		}

}
