package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.AuthRequestSignUpDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

@Component
public class AuthRequestSignUpDtoMapper implements RequestDtoMapper<User, AuthRequestSignUpDto> {

	@Override
	public User transform(AuthRequestSignUpDto dto) {
		return User.builder()
			.userName(dto.getUserName())
			.name(dto.getFirstName())
			.lastName(dto.getLastName())
			.email(dto.getEmail())
			.password(dto.getPassword())
			.build();
	}

}
