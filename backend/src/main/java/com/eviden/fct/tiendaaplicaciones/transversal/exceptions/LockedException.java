package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

public class LockedException extends SingleErrorException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -551124814518248396L;

	public LockedException() {
		this(AppConstants.ERRORS_GENERIC_LOCKED);
	}

	public LockedException(String message) {
		super(message, HttpStatus.LOCKED);
	}

	
	
}
