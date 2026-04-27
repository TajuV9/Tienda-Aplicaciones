package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FileType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.domain.entities.DownloadId;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.domain.services.DownloadDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentDownloadFileUseCase {
	
	private final FilesApplicationService filesService;
	private final AuthenticationApplicationService authService;
	private final ContentDomainService contentService;
	private final DownloadDomainService downloadService;

	public ResponseEntity<Resource> invoke(Long contentId) throws UnauthorizedException, NotFoundException, IOException, ConflictException {
		
		Content content = contentService.get(contentId);
		if (content.getState() != ContentState.PUBLISHED) throw new NotFoundException();
		
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		
		// Gets file
		Resource file = filesService.getFile(FileType.CONTENT_FILE, contentId);
		String fileName = content.getFileName();
		
		// Logs download
		Download download = Download.builder()
				.content(content)
				.id(new DownloadId(user.getId(), content.getId()))
				.user(user)
				.build();
		downloadService.save(download);
		
		// Builds response
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
				.body(file);
		
	}
	
	
}
