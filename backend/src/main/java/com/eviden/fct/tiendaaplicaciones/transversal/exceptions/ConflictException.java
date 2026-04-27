package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class ConflictException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668157109738605504L;

	public ConflictException() {
		this(AppConstants.ERRORS_GENERIC_CONFLICT);
	}

	public ConflictException(String message) {
		super(message, HttpStatus.CONFLICT);
	}

	
	
}
