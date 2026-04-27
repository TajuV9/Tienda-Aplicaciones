package com.eviden.fct.tiendaaplicaciones.application.services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.JwtApplicationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements JwtApplicationService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration.short}")
	private long shortExpirationTime;

	@Value("${jwt.expiration.long}")
	private long longExpirationTime;

	@Override
	public String generateToken(String username, boolean keepLogged) {
		long expirationTime = keepLogged ? longExpirationTime : shortExpirationTime;
		return Jwts.builder()
			.subject(username)
			.claim("rememberMe", keepLogged)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(getKey())
			.compact();
	}

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	@Override
	public boolean extractKeepLoggedIn(String token) {
		return extractClaim(token, c -> (boolean) c.get("rememberMe"));
	}

	@Override
	public boolean validateToken(String token, String username) {
		String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(getKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

}
