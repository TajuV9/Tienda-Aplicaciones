package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentRequestCreateDto {
	
	@NotBlank
	@Length(min = 3, max = 50)
	private String title;
	
	@NotBlank
	@Length(max = 1000)
	private String description;
	
	@NotNull
	private Category category;
	
}
