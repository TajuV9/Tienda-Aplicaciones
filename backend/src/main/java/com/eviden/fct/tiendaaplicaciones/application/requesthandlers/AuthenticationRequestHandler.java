package com.eviden.fct.tiendaaplicaciones.application.requesthandlers;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestLoginDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthResponseLoginDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestRenewDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestSignUpDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.mappers.AuthRequestSignUpDtoMapper;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.JwtApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.AuthLoginUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ProfileCreateUseCase;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationRequestHandler {
	
	private final AuthLoginUseCase authLoginUseCase;
	private final ProfileCreateUseCase profileCreateUseCase;
	private final JwtApplicationService jwtService;
	
	private final AuthRequestSignUpDtoMapper authRequestSignUpDtoMapper;

	
	
	// Login
	public AuthResponseLoginDto handleLoginRequest(AuthRequestLoginDto requestData) throws Exception {
		String username = requestData.getUsername();
		String password = requestData.getPassword();
		boolean keepLoggedIn = requestData.isKeepLoggedIn();

		String token = authLoginUseCase.invoke(username, password, keepLoggedIn);
		Date expirationDate = jwtService.extractClaim(token, Claims::getExpiration);

		return new AuthResponseLoginDto(token, expirationDate);
	}

	// Renew
	public AuthResponseLoginDto handleRenewRequest(AuthRequestRenewDto requestData) throws Exception {
		String previous = requestData.getToken();
		String newToken = authLoginUseCase.invoke(previous);
		Date expirationDate = jwtService.extractClaim(newToken, Claims::getExpiration);
		return new AuthResponseLoginDto(newToken, expirationDate);
	}

	// Sign up 
	public GenericSuccessDto handleSignUpRequest(@Valid AuthRequestSignUpDto requestData) throws Exception {
		User user = authRequestSignUpDtoMapper.transform(requestData);

		profileCreateUseCase.invoke(user);
		return new GenericSuccessDto(AppConstants.SUCCESS_PROFILE_CREATED);
	}

}
