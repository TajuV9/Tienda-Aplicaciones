package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long>, JpaSpecificationExecutor<Content> {
	
	Optional<Content> findByName(String name);
	
	List<Content> findByState(ContentState state);
	

	@Query("SELECT c FROM Content c JOIN c.developer d WHERE d.name = :developerName")
	List<Content> findByDeveloperName(@Param("developerName") String developerName);

	@Query("SELECT c FROM Content c JOIN c.developer d WHERE d.id = :developerId")
	List<Content> findByDeveloperId(@Param("developerId") Long developerId);
	
	@Query("SELECT c FROM Content c JOIN c.developer d WHERE d.id = :developerId AND c.state IN (0, 1)")
	List<Content> findByDeveloperIdNotDeleted(@Param("developerId") Long developerId);
	
}
