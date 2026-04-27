package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentRequestFilterDto {

	@Length(min = 3, max = 50)
	private String name;
	
	private Category category;
	
	private String orderBy;
	
	@Default
	private Boolean asc = true;
	
	@NotNull
	private Integer page;

	@NotNull
	private Integer pageSize;
	
}
