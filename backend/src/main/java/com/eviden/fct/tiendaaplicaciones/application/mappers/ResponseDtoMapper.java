package com.eviden.fct.tiendaaplicaciones.application.mappers;

public interface ResponseDtoMapper <Entity, Dto> {

	public Dto transform(Entity entity);
	
}
