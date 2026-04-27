package com.eviden.fct.tiendaaplicaciones.domain.filters;

import org.hibernate.validator.constraints.Length;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;

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
public class ContentFilter {

	@Length(min = 3, max = 50)
	private String name;
	
	private Category category;
	
	private String orderBy;
	
	private ContentState state;
	
	@Default
	private Boolean asc = true;
	
	@Default
	@NotNull
	private Integer page = 0;
	
	@Default
	@NotNull
	private Integer pageSize = 10;
	
}
