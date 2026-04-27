package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.ImageType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.VideoType;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMediaType;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentMediaDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentMediaDownloadUseCase {
	
	private final ContentMediaDomainService contentMediaService;
	private final FilesApplicationService filesService;

	public ResponseEntity<Resource> invoke(Long mediaId) throws NotFoundException, IOException {
		
		// Gets media and related information
		ContentMedia media = contentMediaService.get(mediaId);
		ContentMediaType type = media.getType();
		MediaType mediaType = type == ContentMediaType.IMAGE ? MediaType.IMAGE_PNG : MediaType.valueOf("video/mp4"); 
		
		// Checks
		if (media.getContent().getState() == ContentState.DELETED) throw new NotFoundException();
		
		// Gets file
		Resource file;
		if (type == ContentMediaType.IMAGE) {
			file = filesService.getImage(ImageType.CONTENT_MEDIA_IMAGE, mediaId);
		} else {
			file = filesService.getVideo(VideoType.CONTENT_MEDIA_VIDEO, mediaId);
		}
		
		// Builds response
		return ResponseEntity.ok()
				.contentType(mediaType)
				.body(file);
		
	}
	
	
}
