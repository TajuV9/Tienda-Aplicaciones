package com.eviden.fct.tiendaaplicaciones.infrastructure;

import java.util.NoSuchElementException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericMultipleErrorsDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSingleErrorDto;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.BadRequestMultipleErrorsException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.BadRequestSingleErrorException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.InternalServerErrorException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.MethodNotAllowedException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.MultipleErrorsException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.SingleErrorException;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	// Generic
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleGenericError(SingleErrorException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.contentType(MediaType.APPLICATION_JSON)
				.body(e.getDto());
	}
	
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericMultipleErrorsDto> handleGenericError(MultipleErrorsException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.contentType(MediaType.APPLICATION_JSON)
				.body(e.getDto());
	}
	
	// Default exceptions
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericMultipleErrorsDto> handleDefaultExceptions(MethodArgumentNotValidException e) {
        String[] errors = e.getBindingResult().getFieldErrors().stream().map(violation -> {
        	String field = violation.getField();
        	String error = violation.getDefaultMessage();
        	return String.format(AppConstants.ERRORS_VALIDATION_FIELD_ERROR, field, error);
        }).toArray(String[]::new);
        
        return handleGenericError(new BadRequestMultipleErrorsException(errors));
    }
	
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleDefaultExceptions(HttpMessageNotReadableException e) {
		return handleGenericError(new BadRequestSingleErrorException());
    }
	
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleDefaultExceptions(ValidationException e) {
		return handleGenericError(new BadRequestSingleErrorException());
    }
	
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleDefaultExceptions(NoSuchElementException e) {
		return handleGenericError(new NotFoundException());
    }
	
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleDefaultExceptions(HttpRequestMethodNotSupportedException e) {
		return handleGenericError(new MethodNotAllowedException());
    }

	// Other
	@ExceptionHandler(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GenericSingleErrorDto> handleDefaultExceptions(Exception e) {
		return handleGenericError(new InternalServerErrorException());
	}
	
}
