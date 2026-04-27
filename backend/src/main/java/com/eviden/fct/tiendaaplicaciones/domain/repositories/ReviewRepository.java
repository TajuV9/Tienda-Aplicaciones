package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review>
{
	
	@Query("SELECT r FROM Review r JOIN r.content c WHERE c.id = :contentId")
	List<Review> findByContentId(@Param("contentId")Long developerId);
	
	@Query("SELECT r FROM Review r JOIN r.user u WHERE u.id = :userId")
	List<Review> findByUserId(@Param("userId")Long userId);
	
	Optional<Review> findByUserAndContent(User user, Content content);
	
	List<Review> findByContent(Content content);
	
	Page<Review> findByContent(Content content, Pageable page);
	
	List<Review> findByUser(User user);
	
}
