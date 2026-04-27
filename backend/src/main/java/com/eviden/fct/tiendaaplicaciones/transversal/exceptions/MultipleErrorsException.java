package com.eviden.fct.tiendaaplicaciones.transversal.exceptions;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericMultipleErrorsDto;

public abstract class MultipleErrorsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -838441587093720352L;
	
	GenericMultipleErrorsDto dto;
	HttpStatus statusCode;

	public MultipleErrorsException(List<String> errors, HttpStatus statusCode) {
		this.statusCode = statusCode;
		this.dto = new GenericMultipleErrorsDto(errors);
	}
	
	public MultipleErrorsException(Set<String> errors, HttpStatus statusCode) {
		this.statusCode = statusCode;
		this.dto = new GenericMultipleErrorsDto(errors);
	}
	
	public MultipleErrorsException(String[] errors, HttpStatus statusCode) {
		this.statusCode = statusCode;
		this.dto = new GenericMultipleErrorsDto(errors);
	}
	
	public GenericMultipleErrorsDto getDto() {
		return dto;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setDto(GenericMultipleErrorsDto dto) {
		this.dto = dto;
	}
	
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
}