package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.DownloadDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileDownloadsGetUseCase {
	
	private final AuthenticationApplicationService authService;
	private final DownloadDomainService downloadService;
	
	public List<Download> invoke() throws UnauthorizedException {
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		return downloadService.getByUser(user);
	}
	
}
