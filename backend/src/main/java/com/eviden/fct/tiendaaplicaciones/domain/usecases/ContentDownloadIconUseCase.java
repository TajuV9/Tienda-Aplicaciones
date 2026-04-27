package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.ImageType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentDownloadIconUseCase {
	

	private final FilesApplicationService filesService;

	public ResponseEntity<Resource> invoke(Long contentId) throws IOException {
		
		// Gets file
		Resource file = filesService.getImage(ImageType.CONTENT_ICON, contentId);
		
		// Builds response
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(file);
		
	}
	
	
}
