package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class UnauthorizedException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8906868194953315378L;

	public UnauthorizedException() {
		this(AppConstants.ERRORS_GENERIC_UNAUTHORIZED);
	}

	public UnauthorizedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}

	
	
}
