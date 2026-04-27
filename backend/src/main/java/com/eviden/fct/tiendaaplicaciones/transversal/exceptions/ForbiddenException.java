package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class ForbiddenException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4316500488467713951L;

	public ForbiddenException() {
		this(AppConstants.ERRORS_GENERIC_FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(message, HttpStatus.FORBIDDEN);
	}

	
	
}
