package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

@Component
public class ProfileRequestUpdateDtoMapper implements RequestDtoMapper<User, ProfileRequestUpdateDto> {

	@Override
	public User transform(ProfileRequestUpdateDto dto) {
		
		return User.builder()
				.name(dto.getFirstName())
				.lastName(dto.getLastName())
				.email(dto.getEmail())
				.build();
		
	}	
	
}
