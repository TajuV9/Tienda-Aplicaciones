package com.eviden.fct.tiendaaplicaciones.application.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
		GenericSingleErrorDto error = new GenericSingleErrorDto(AppConstants.ERRORS_GENERIC_FORBIDDEN);
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), error);
		
	}
	
}
