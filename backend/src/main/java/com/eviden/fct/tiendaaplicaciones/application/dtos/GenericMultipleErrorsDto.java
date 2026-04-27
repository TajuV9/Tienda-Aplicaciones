package com.eviden.fct.tiendaaplicaciones.application.dtos;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericMultipleErrorsDto {

	private String[] errors;
	
	
	// Alternative constructors
	public GenericMultipleErrorsDto(List<String> errors) {
		this(errors.toArray(String[]::new));
	}
	
	public GenericMultipleErrorsDto(Set<String> errors) {
		this(errors.toArray(String[]::new));
	}
	
}
