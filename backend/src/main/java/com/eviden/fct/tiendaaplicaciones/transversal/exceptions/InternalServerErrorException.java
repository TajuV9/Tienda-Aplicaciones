package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class InternalServerErrorException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3729936532010916990L;

	public InternalServerErrorException() {
		this(AppConstants.ERRORS_GENERIC_INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException(String message) {
		super(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
}
