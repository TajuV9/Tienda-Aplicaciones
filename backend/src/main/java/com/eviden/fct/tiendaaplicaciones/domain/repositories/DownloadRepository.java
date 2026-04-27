package com.eviden.fct.tiendaaplicaciones.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.domain.entities.DownloadId;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

public interface DownloadRepository extends JpaRepository<Download, DownloadId> {
	
	
	public List<Download> findByUser(User user);
	
}
