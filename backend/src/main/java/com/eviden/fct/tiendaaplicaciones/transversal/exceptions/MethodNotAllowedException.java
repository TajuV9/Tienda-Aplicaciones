package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class MethodNotAllowedException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5387829354030915868L;

	public MethodNotAllowedException() {
		this(AppConstants.ERRORS_GENERIC_METHOD_NOT_ALLOWED);
	}

	public MethodNotAllowedException(String message) {
		super(message, HttpStatus.METHOD_NOT_ALLOWED);
	}

	
	
}
