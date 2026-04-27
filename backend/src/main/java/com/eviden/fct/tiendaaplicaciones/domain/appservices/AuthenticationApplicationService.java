package com.eviden.fct.tiendaaplicaciones.domain.appservices;


import java.util.Optional;

import org.springframework.security.core.AuthenticationException;

import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

public interface AuthenticationApplicationService {

	public void authenticate(String username, String password) throws AuthenticationException;
	public void authenticate(String token) throws AuthenticationException;
	
	public Optional<User> getAuthenticatedUser();
	
}
