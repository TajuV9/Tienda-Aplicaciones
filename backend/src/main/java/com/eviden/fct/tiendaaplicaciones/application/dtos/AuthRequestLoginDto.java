package com.eviden.fct.tiendaaplicaciones.application.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestLoginDto {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@Default
	private boolean keepLoggedIn = false;
	
}
