package com.eviden.fct.tiendaaplicaciones.domain.appservices;

import java.util.function.Function;

import io.jsonwebtoken.Claims;

public interface JwtApplicationService {
	
	public String generateToken(String username, boolean keepLogged);
	public String extractUsername(String token);
	public boolean extractKeepLoggedIn(String token);
	public boolean validateToken(String token, String username);
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

}
