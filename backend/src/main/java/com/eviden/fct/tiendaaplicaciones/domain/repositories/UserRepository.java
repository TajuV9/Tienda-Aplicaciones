package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
	
	@Query("SELECT u FROM User u WHERE u.email = :username OR u.userName = :username")
	Optional<User> findByUsernameOrEmail(String username);
	
	List<User> findByRole(Role role);
	
}
