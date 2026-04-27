package com.eviden.fct.tiendaaplicaciones.application.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseLoginDto {

	private String token;

	private Date expirationTime;
	
}
