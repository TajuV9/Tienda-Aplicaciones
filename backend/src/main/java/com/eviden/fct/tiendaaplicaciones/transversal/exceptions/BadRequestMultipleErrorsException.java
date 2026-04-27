package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

public class BadRequestMultipleErrorsException extends MultipleErrorsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 446713224534802222L;

	public BadRequestMultipleErrorsException(List<String> errors) {
		super(errors, HttpStatus.BAD_REQUEST);
	}
	
	public BadRequestMultipleErrorsException(Set<String> errors) {
		super(errors, HttpStatus.BAD_REQUEST);
	}
	
	public BadRequestMultipleErrorsException(String[] errors) {
		super(errors, HttpStatus.BAD_REQUEST);
	}
	
}
