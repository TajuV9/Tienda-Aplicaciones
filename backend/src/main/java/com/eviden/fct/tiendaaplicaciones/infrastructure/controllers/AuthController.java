package com.eviden.fct.tiendaaplicaciones.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestLoginDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthResponseLoginDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestRenewDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestSignUpDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.requesthandlers.AuthenticationRequestHandler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
	
	private final AuthenticationRequestHandler requestHandler;
	
	
	
	// LOGIN
	@PostMapping(
		path = "login",
		consumes = { MediaType.APPLICATION_JSON_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(
		responseCode = "400",
		description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "401",
		description = "Credentials are invalid. This could be because the user does not exist, because the password is invalid or because the user is locked or disabled.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	public AuthResponseLoginDto login(@Valid @RequestBody AuthRequestLoginDto request) throws Exception {
		return requestHandler.handleLoginRequest(request);
	}
	
	
	
	// SIGN UP
	@PostMapping(
		path = "signup",
		consumes = { MediaType.APPLICATION_JSON_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(
		responseCode = "400",
		description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "409",
		description = "A conflict has ocurred. This could be mainly because an user with the provided username or email already exists.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	public GenericSuccessDto signUp(@Valid @RequestBody AuthRequestSignUpDto request) throws Exception {
		return requestHandler.handleSignUpRequest(request);
	}
	
	
	
	// RENEW TOKEN
	@PostMapping(
		path = "renew",
		consumes = { MediaType.APPLICATION_JSON_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@ResponseStatus(HttpStatus.OK)
	@ApiResponse(
		responseCode = "400",
		description = "Provided data does not pass the validation. Please, check that all required itmes are included in the request, and that they follow the propper format.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	@ApiResponse(
		responseCode = "401",
		description = "The provided token is not valid or has expired. This could also happen if the user is locked or disabled.",
		content = @Content(schema = @Schema(implementation = GenericSingleErrorDto.class))
	)
	public AuthResponseLoginDto renewToken(@Valid @RequestBody AuthRequestRenewDto request) throws Exception {
		return requestHandler.handleRenewRequest(request);
	}
	
}
