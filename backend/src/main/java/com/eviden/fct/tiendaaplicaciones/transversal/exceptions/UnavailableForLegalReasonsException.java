package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class UnavailableForLegalReasonsException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2988309482841001577L;

	public UnavailableForLegalReasonsException() {
		this(AppConstants.ERRORS_GENERIC_UNAVAILABLE_LEGAL_REASONS);
	}

	public UnavailableForLegalReasonsException(String message) {
		super(message, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
	}

	
	
}
