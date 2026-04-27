package com.eviden.fct.tiendaaplicaciones.domain.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StandardDomainService<T, Id> {
	
	private final JpaRepository<T, Id> repository;
	private Map<Function<T, Optional<T>>, String> validations = new HashMap<Function<T, Optional<T>>, String>();
	
	public final T create(T entity) throws ConflictException {
		T transformed = transformOnCreate(entity);
		return save(transformed, null, true, this::onCreated);
	}
	
	public final T createWithId(T entity) throws ConflictException {
		T transformed = transformOnCreate(entity);
		return save(transformed, getId(entity), true, this::onCreated);
	}
	
	public final T update(Id id, T entity) throws ConflictException, NotFoundException {
		T previous = repository.findById(id).orElseThrow(NotFoundException::new);
		T transformed = transformOnUpdate(previous, entity);
		return save(transformed, id, false, this::onUpdated);
	}
	
	public final void delete(Id id) throws NotFoundException {
		T entity = repository.findById(id).orElseThrow(NotFoundException::new);
		Optional<T> transformed = transformOnDelete(entity);
		
		if (transformed.isEmpty()) {
			repository.delete(entity);
			onDeleted(entity);
			return;
		}
		
		try {
			save(transformed.get(), id, false, this::onDeleted);
		} catch (ConflictException e) {
			repository.delete(entity);
			onDeleted(entity);
		}
			
	}
	
	public final T get(Id id) throws NotFoundException {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}
	
	public final List<T> get() {
		return repository.findAll();
	}
	
	// Save in repository
	protected final T save(T entity, Id id, boolean created, Consumer<T> onSaved) throws ConflictException {
		setId(entity, id);
		executeAllValidations(entity);
		
		if (created) setCreateAt(entity, new Date(System.currentTimeMillis()));
		setUpdateAt(entity, new Date(System.currentTimeMillis()));
		
		onSaved.accept(entity);
		return repository.save(entity);
	}
	
	// Validation
	protected final void addExistCheck(Function<T, Optional<T>> check, String errorMessage) {
		validations.put(check, errorMessage);
	}
	
	private final void executeValidation(T entity, Map.Entry<Function<T, Optional<T>>, String> entry) throws ConflictException {
		Function<T, Optional<T>> check = entry.getKey();
		String errorMessage = entry.getValue();
		
		Optional<T> result = check.apply(entity);
		if (result.isEmpty()) return;
		
		T obtained = result.get();
		if (getId(obtained).equals(getId(entity))) return;
		
		throw new ConflictException(errorMessage);
	}
	
	private final void executeAllValidations(T entity) throws ConflictException {
		for (Map.Entry<Function<T, Optional<T>>, String> entry : validations.entrySet()) {
			executeValidation(entity, entry);
		}
	}
	
	// Event methods
	protected void onCreated(T entity) {}
	protected void onUpdated(T entity) {}
	protected void onDeleted(T entity) {}
	
	// Transform methods
	protected T transformOnCreate(T entity) {
		return entity;
	}
	
	protected T transformOnUpdate(T original, T changes) {
		return changes;
	}
	
	protected Optional<T> transformOnDelete(T entity) {
		return Optional.empty();
	}
	
	// Abstract methods
	protected abstract Id getId(T entity);
	protected abstract void setId(T entity, Id id);
	
	protected abstract void setCreateAt(T entity, Date date);
	protected abstract void setUpdateAt(T entity, Date date);
	
}
