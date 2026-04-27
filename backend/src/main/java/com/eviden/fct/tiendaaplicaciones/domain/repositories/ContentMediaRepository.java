package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;

@Repository
public interface ContentMediaRepository extends JpaRepository<ContentMedia, Long> {
	
}
