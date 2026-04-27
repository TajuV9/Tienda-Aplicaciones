package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class BadRequestSingleErrorException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4780727327890083514L;

	public BadRequestSingleErrorException() {
		this(AppConstants.ERRORS_GENERIC_BAD_REQUEST);
	}

	public BadRequestSingleErrorException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

	
	
}
