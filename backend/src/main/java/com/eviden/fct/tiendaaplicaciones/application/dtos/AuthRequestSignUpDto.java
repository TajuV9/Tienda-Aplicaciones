package com.eviden.fct.tiendaaplicaciones.application.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestSignUpDto {

	@NotBlank
	@Length(min = 3, max = 30)
	private String userName;
	
	@NotBlank
	@Length(min = 2, max = 50)
	private String firstName;
	
	@NotBlank
	@Length(min = 2, max = 50)
	private String lastName;
	
	@Email
	@NotBlank
	@Length(max = 100)
	private String email;
	
	@NotBlank
	@Length(min = 8, max = 30)
	private String password;
	
}
