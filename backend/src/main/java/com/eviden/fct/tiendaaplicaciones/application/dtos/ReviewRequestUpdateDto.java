package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestUpdateDto {

	@NotBlank
	@Length(min = 5, max = 500)
	private String review;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Integer rating;
	
}
