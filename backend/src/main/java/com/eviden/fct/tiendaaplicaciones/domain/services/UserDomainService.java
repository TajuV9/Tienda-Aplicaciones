package com.eviden.fct.tiendaaplicaciones.domain.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.UserRepository;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

@Service
public class UserDomainService extends StandardDomainService<User, Long> implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserDomainService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super(userRepository);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		
		addExistCheck(user -> userRepository.findByUsernameOrEmail(user.getUsername()), AppConstants.ERRORS_PROFILE_USERNAME_DUPLICATED);
		addExistCheck(user -> userRepository.findByUsernameOrEmail(user.getEmail()), AppConstants.ERRORS_PROFILE_EMAIL_DUPLICATED);
	}
	
	
	// Custom methods
	public boolean doesUserExist(String username) {
		return userRepository.findByUsernameOrEmail(username).isPresent();
	}
	
	public User getUserInformation(String username) throws NotFoundException {
		Optional<User> user = userRepository.findByUsernameOrEmail(username);
		return user.orElseThrow(NotFoundException::new);
	}
	
	public boolean checkPassword(Long id, String password) throws NotFoundException {
		User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	public boolean checkPassword(String username, String password) throws NotFoundException {
		User user = userRepository.findByUsernameOrEmail(username).orElseThrow(NotFoundException::new);
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	public boolean checkPassword(User user, String password) {
		return passwordEncoder.matches(password, user.getPassword());
	}
	

	// Implementations for getters and setters
	@Override
	protected Long getId(User entity) {
		return entity.getId();
	}

	@Override
	protected void setId(User entity, Long id) {
		entity.setId(id);
	}

	@Override
	protected void setCreateAt(User entity, Date date) {
		entity.setCreatedAt(date);
	}

	@Override
	protected void setUpdateAt(User entity, Date date) {
		entity.setUpdatedAt(date);
	}

	
	// Transform implementations
	@Override
	protected User transformOnCreate(User entity) {
		entity.setRole(Role.USER);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return entity;
	}
	
	@Override
	protected User transformOnUpdate(User original, User changes) {
		if (changes.getName() != null) original.setName(changes.getName());
		if (changes.getLastName() != null) original.setLastName(changes.getLastName());
		if (changes.getEmail() != null) original.setEmail(changes.getEmail());
		if (changes.getRole() != null) original.setRole(changes.getRole());
		
		if (changes.getPassword() != null) {
			String encoded = passwordEncoder.encode(changes.getPassword());
			original.setPassword(encoded);
		}
		
		return original;
	}
	
	// Other implementations
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsernameOrEmail(username);
		return user.orElseThrow(() -> new UsernameNotFoundException(AppConstants.ERRORS_GENERIC_NOT_FOUND));
	}

}
