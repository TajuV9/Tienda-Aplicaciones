package com.eviden.fct.tiendaaplicaciones.application.mappers;

public interface RequestDtoMapper <Entity, Dto> {

	public Entity transform(Dto dto);
	
}
