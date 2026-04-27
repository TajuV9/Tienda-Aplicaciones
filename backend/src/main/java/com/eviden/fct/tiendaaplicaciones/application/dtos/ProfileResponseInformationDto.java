package com.eviden.fct.tiendaaplicaciones.application.dtos;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseInformationDto {
	
	private Long id;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Role role;
	
	private Boolean isCreator;
	
}
