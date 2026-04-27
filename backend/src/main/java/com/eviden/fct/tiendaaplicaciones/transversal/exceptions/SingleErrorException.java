package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;

public abstract class SingleErrorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6401909436718552176L;
	
	GenericSingleErrorDto dto;
	HttpStatus statusCode;

	public SingleErrorException(String message, HttpStatus statusCode) {
		this.statusCode = statusCode;
		this.dto = new GenericSingleErrorDto(message);
	}
	
	public GenericSingleErrorDto getDto() {
		return dto;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setDto(GenericSingleErrorDto dto) {
		this.dto = dto;
	}
	
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
}
