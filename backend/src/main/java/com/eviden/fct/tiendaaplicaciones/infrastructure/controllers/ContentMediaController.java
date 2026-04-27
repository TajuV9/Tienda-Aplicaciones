package com.eviden.fct.tiendaaplicaciones.infrastructure.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.ContentMediaRequestHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/content-media")
public class ContentMediaController {
	
	private final ContentMediaRequestHandler requestHandler;
	

	// DELETE
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "401", description = "User is not authenticated. User must be authenticated in order to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "403", description = "User doesn't have permissions to perform this action. User must be the owner of the content to perform this action.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	@ApiResponse(responseCode = "404", description = "The media you are trying to delete doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public GenericSuccessDto delete(@PathVariable Long id) throws Exception {
		return requestHandler.handleMediaDeleteRequest(id);
	}

	// DOWNLOAD MEDIA
	@GetMapping(path = "/{id}", produces = { MediaType.IMAGE_PNG_VALUE, "video/mp4" } )
	@SecurityRequirement(name = "bearerAuth")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(responseCode = "404", description = "The file you are trying to download doesn't exist.", content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class)))
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception {
		return requestHandler.handleMediaDownloadRequest(id);
	}

}
