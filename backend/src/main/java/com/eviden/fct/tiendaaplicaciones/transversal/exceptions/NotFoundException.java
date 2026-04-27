package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class NotFoundException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8610155446604711449L;

	public NotFoundException() {
		this(AppConstants.ERRORS_GENERIC_NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	
	
}
