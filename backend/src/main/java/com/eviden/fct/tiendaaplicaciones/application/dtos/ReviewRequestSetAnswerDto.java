package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestSetAnswerDto {

	@NotBlank
	@Length(min = 5, max = 500)
	private String answer;
	
}
