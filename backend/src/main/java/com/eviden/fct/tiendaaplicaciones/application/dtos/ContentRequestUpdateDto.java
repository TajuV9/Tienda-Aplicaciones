package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentRequestUpdateDto {

	@NotBlank
	@Length(max = 1000)
	private String description;
	
}
