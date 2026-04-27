package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.UserDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileCreateUseCase {

	private final UserDomainService userService;
	
	public User invoke(User user) throws ConflictException {
		return userService.create(user);
	}
	
}
