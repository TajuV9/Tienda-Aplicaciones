package com.eviden.fct.tiendaaplicaciones.domain.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.domain.entities.DownloadId;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.DownloadRepository;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

@Service
public class DownloadDomainService extends StandardDomainService<Download, DownloadId> {
	
	// Fields obtained with dependency injection.
	private final DownloadRepository downloadRepository;
	
	public DownloadDomainService(DownloadRepository downloadRepository) {
		super(downloadRepository);
		this.downloadRepository = downloadRepository;
	}
	
	// Custom methods
	public Download save(Download download) throws NotFoundException, ConflictException {
		if (downloadRepository.existsById(download.getId())) {
			return update(download.getId(), download);
		} else {
			return createWithId(download);
		}
	}
	
	public List<Download> getByUser(User user) {
		return downloadRepository.findByUser(user);
	}

	// Transforms
	@Override
	protected Download transformOnCreate(Download entity) {
		entity.setVersion(entity.getContent().getVersion());
		return entity;
	}
	
	@Override
	protected Download transformOnUpdate(Download original, Download changes) {
		original.setVersion(original.getContent().getVersion());
		return original;
	}
	
	
	// Implementations
	@Override
	protected DownloadId getId(Download entity) {
		return entity.getId();
	}

	@Override
	protected void setId(Download entity, DownloadId id) {
		entity.setId(id);
	}

	@Override
	protected void setCreateAt(Download entity, Date date) {
		entity.setCreatedAt(date);
	}

	@Override
	protected void setUpdateAt(Download entity, Date date) {
		entity.setUpdatedAt(date);
	}
	
	
}
