package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

@Component
public class ProfileResponseInformationDtoMapper implements ResponseDtoMapper<User, ProfileResponseInformationDto> {

	@Override
	public ProfileResponseInformationDto transform(User entity) {
		
		return ProfileResponseInformationDto.builder()
				.id(entity.getId())
				.userName(entity.getUsername())
				.firstName(entity.getName())
				.lastName(entity.getLastName())
				.email(entity.getEmail())
				.role(entity.getRole())
				.isCreator(entity.getRole() == Role.CREATOR || entity.getRole() == Role.ADMIN)
				.build();
		
	}
	
}
